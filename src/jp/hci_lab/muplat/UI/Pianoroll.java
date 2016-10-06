import static constants.PianorollConstants.*;

import java.util.ArrayList;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * ノートを打ち込むピアノロールのクラス
 * @author Shun Yamashita
 */
public class Pianoroll extends Group {
	private int measureCount;
	private int octaveCount;
	private int bpm;
	private int currentMeasure;
	private ArrayList<Note> notes;
	private MainScene parent;

	private Timeline playTimeline;
	private NoteResolutionSelector noteResolutionSelector;
	private BpmLabel bpmLabel;
	private StopButton stopButton;
	private PlayButton playButton;

	private ScrollPane sp;
	private Group editArea;
	private Label[] measureLabels;
	private NoteGrid[][] noteGrids;
	private Line[] snapLines;
	private Line playLine;


	public Pianoroll(int measureCount, int octaveCount, int bpm, int x, int y, MainScene parent) {
		super();
		this.measureCount = measureCount;
		this.octaveCount = octaveCount;
		this.bpm = bpm;
		this.currentMeasure = 1;
		this.notes = new ArrayList<Note>();
		this.parent = parent;
		setupPoint(x, y);
		setupNoteResolutionSelector();
		setupBpmLabel();
		setupStopButton();
		setupPlayButton();
		setupScrollPane();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupScrollPane() {
		int width = PIANOROLL_WIDTH + 3;
		int height = PIANOROLL_HEIGHT * octaveCount + 33;
		int x = 1;
		int y = 28;
		sp = new ScrollPane();
		sp.setPrefSize(width, height);
		sp.setLayoutX(x);
		sp.setLayoutY(y);

		setupEditArea();
		setupMeasureLabels();
		setupNoteGrids();
		setupSnapLines();
		setupFrameLines();
		setupPlayLine();
		sp.setContent(editArea);
		getChildren().add(sp);
	}

	public void setupEditArea() {
		editArea = new Group();
	}

	public void setupNoteResolutionSelector() {
		int x = NOTE_RESOLUTION_SELECTOR_X;
		int y = NOTE_RESOLUTION_SELECTOR_Y;
		noteResolutionSelector = new NoteResolutionSelector(x, y, this);
		getChildren().add(noteResolutionSelector);
	}

	public void setupBpmLabel() {
		int x = BPM_LABEL_X;
		int y = BPM_LABEL_Y;
		bpmLabel = new BpmLabel(bpm, x, y, this);
		getChildren().add(bpmLabel);
	}

	public void setupStopButton() {
		int x = STOP_BUTTON_X;
		int y = STOP_BUTTON_Y;
		stopButton = new StopButton(x, y, this);
		getChildren().add(stopButton);
	}

	public void setupPlayButton() {
		int x = PLAY_BUTTON_X;
		int y = PLAY_BUTTON_Y;
		playButton = new PlayButton(x, y, this);
		getChildren().add(playButton);
	}

	public void setupMeasureLabels() {
		measureLabels = new Label[measureCount];
		for(int n = 0; n < measureCount; n++) {
			int x = MEASURE_LABEL_X + MEASURE_WIDTH * n;
			int y = MEASURE_LABEL_Y;
			measureLabels[n] = new Label(Integer.toString(n + 1));
			measureLabels[n].setFont(Font.font("Arial", 12));
			measureLabels[n].setTextFill(Color.web("#000000"));
			measureLabels[n].setLayoutX(x);
			measureLabels[n].setLayoutY(y);
			editArea.getChildren().add(measureLabels[n]);
		}
	}

	public void setupNoteGrids() {
		int verticalSize = 12 * octaveCount;
		int horizontalSize = 4 * measureCount;
		noteGrids = new NoteGrid[verticalSize][horizontalSize];
		for(int j = 0; j < verticalSize; j++) {
			// 0 2 4 6 7 9 11
			boolean isWhite = true;
			int temp = j % 12;
			switch(temp) {
			case 0:
			case 2:
			case 4:
			case 6:
			case 7:
			case 9:
			case 11:
				isWhite = true;
				break;
			default:
				isWhite = false;
				break;
			}
			for(int i = 0; i < horizontalSize; i++) {
				int x = NOTE_GRID_X[(int)(i % 4)] + NOTE_GRID_X_OFFSET * (int)(i / 4);
				int y = NOTE_GRID_Y[(int)(j % 12)] + NOTE_GRID_Y_OFFSET * (int)(j / 12);
				int w = NOTE_GRID_WIDTH;
				int h = NOTE_GRID_HEIGHT;
				noteGrids[j][i] = new NoteGrid(isWhite, 4, x, y, w, h, this);
				editArea.getChildren().add(noteGrids[j][i]);
			}
		}
	}

	public void setupFrameLines() {
		// Vertical lines
		int vStartY = 29;
		int vEndY = MEASURE_HEIGHT * octaveCount + 46;
		for(int n = 0; n < measureCount + 1; n++) {
			int x = 160 * n;
			Line line = new Line(x + 0.5, vStartY, x + 0.5, vEndY);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			editArea.getChildren().add(line);
		}
		// Horizontal lines
		int hStartX = 0;
		int hEndX = MEASURE_WIDTH * measureCount;
		int[] hStartY = {29, 46, MEASURE_HEIGHT * octaveCount + 46};
		int[] hEndY = hStartY;
		for(int n = 0; n < hStartY.length; n++) {
			Line line = new Line(hStartX, hStartY[n] + 0.5, hEndX, hEndY[n] + 0.5);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			editArea.getChildren().add(line);
		}
	}

	public void setupSnapLines() {
		snapLines = new Line[16 * measureCount];
		int[] xArray = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150};
		int startY = 46;
		int endY = MEASURE_HEIGHT * octaveCount + 46;
		for(int n = 0; n < snapLines.length; n++) {
			int x = xArray[n % 16] + 160 * (n / 16);
			snapLines[n] = new Line(x + 0.5, startY, x + 0.5, endY);
			snapLines[n].setStrokeLineCap(StrokeLineCap.BUTT);
			snapLines[n].setStroke(Color.web("#888888"));
			snapLines[n].getStrokeDashArray().addAll(4.0, 2.0);
			snapLines[n].setVisible(false);
			editArea.getChildren().add(snapLines[n]);
		}
	}

	public void setupPlayLine() {
		int startY = 29;
		int endY = MEASURE_HEIGHT * octaveCount + 46;
		playLine = new Line(0 + 0.5, startY, 0 + 0.5, endY);
		playLine.setStrokeLineCap(StrokeLineCap.BUTT);
		playLine.setStroke(Color.web("#FF0000"));
		editArea.getChildren().add(playLine);
	}

	public void updateSnapLines(int resolution) {
		int indexResolution = 4;
		switch(resolution) {
		case 4: indexResolution = 4; break;
		case 8: indexResolution = 2; break;
		case 16: indexResolution = 1; break;
		default: break;
		}
		for(int n = 0; n < snapLines.length; n++) {
			if(n % indexResolution == 0) snapLines[n].setVisible(true);
			else snapLines[n].setVisible(false);
		}
	}

	public void putNote(int x, int y, int width, int height) {
		int resolution = noteResolutionSelector.getIntValue();
		int minX = 0;
		int maxX = minX + MEASURE_WIDTH * measureCount;
		int minY = 46;
		int maxY = minY + MEASURE_HEIGHT * octaveCount;
		Note note = new Note(x, y, width, height, resolution, minX, maxX, minY, maxY, this);
		notes.add(note);
		editArea.getChildren().add(note);
	}

	public void removeNote(Note note) {
		notes.remove(note);
		editArea.getChildren().remove(note);
	}

	public void changedNoteResolution(int resolution) {
		updateSnapLines(resolution);
		for (NoteGrid[] array : noteGrids) {
			for (NoteGrid element : array) {
				element.setResolution(resolution);
			}
		}
		for(Note note : notes) {
			note.setResolution(resolution);
		}
	}

	public void stop() {
		if(playTimeline == null) return;

		// エンジンへの停止指示
		parent.stop();

		playTimeline.stop();
		setupAfterPlay();
	}

	public void play() {
		int n = 1;
		for(Note note : notes) {
			System.out.println("Index: " + n + ", Measure: " + note.getMeasure() + ", Beat: " + note.getBeat() + ", Place: " + note.getPlace() + ", Duration: " + note.getDuration() + ", Interval: " + note.getInterval() + note.getOctave());
			n++;
		}
		setupBeforePlay();

		// エンジンへの再生指示
		parent.play();

		playAnimation();
	}

	public void setupBeforePlay() {
		noteResolutionSelector.setDisable(true);
		bpmLabel.setDisable(true);
		stopButton.setDisable(false);
		playButton.setDisable(true);
		for (NoteGrid[] array : noteGrids) {
			for (NoteGrid element : array) {
				element.setDisable(true);
			}
		}
		for(Note note : notes) {
			note.setDisable(true);
		}
		playLine.toFront();
		playLine.setLayoutX(0);
		sp.setHvalue(0.0);

		// エンジンへのセットアップ
		parent.setBpm(bpm);
		for(Note note : notes) {
			parent.addNote(note);
		}
	}

	public void setupAfterPlay() {
		noteResolutionSelector.setDisable(false);
		bpmLabel.setDisable(false);
		stopButton.setDisable(true);
		playButton.setDisable(false);
		for (NoteGrid[] array : noteGrids) {
			for (NoteGrid element : array) {
				element.setDisable(false);
			}
		}
		for(Note note : notes) {
			note.setDisable(false);
		}
		playLine.setLayoutX(0);
		sp.setHvalue(0.0);
	}

	public void playAnimation() {
		float secPerBeat = 60.f / bpm;
		float secPerMeasure = secPerBeat * 4;
		float playTimeSec = secPerMeasure * measureCount;
		playTimeline = new Timeline();
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				setupAfterPlay();
			}
		};
		KeyValue kvStart = new KeyValue(playLine.layoutXProperty(), 0);
		KeyFrame kfStart = new KeyFrame(Duration.ZERO, kvStart);
		KeyValue kvEnd = new KeyValue(playLine.layoutXProperty(), MEASURE_WIDTH * measureCount);
		KeyFrame kfEnd = new KeyFrame(Duration.millis(playTimeSec * 1000), onFinished, kvEnd);
		KeyValue[] kvMeasureChange = new KeyValue[measureCount / 4 - 1];
		KeyFrame[] kfMeasureChange = new KeyFrame[measureCount / 4 - 1];
		for(int n = 0; n < kfMeasureChange.length; n++) {
			kvMeasureChange[n] = new KeyValue(sp.hvalueProperty(), (1.0 / (measureCount - 4)) * 4 * (n + 1), Interpolator.DISCRETE);
			kfMeasureChange[n] = new KeyFrame(Duration.millis(secPerMeasure * 4 * (n + 1) * 1000), kvMeasureChange[n]);
			playTimeline.getKeyFrames().add(kfMeasureChange[n]);
		}
		playTimeline.getKeyFrames().add(kfStart);
		playTimeline.getKeyFrames().add(kfEnd);
		playTimeline.setCycleCount(1);
		playTimeline.setAutoReverse( true );
		playTimeline.play();
	}

	public int getOctaveCount() { return octaveCount; }
	public int getBpm() { return bpm; }
	public void setBpm(int bpm) { this.bpm = bpm; }
	public int getCurrentMeasure() { return currentMeasure; }
}
