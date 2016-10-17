import static constants.EditAreaConstants.*;
import static constants.UniversalConstants.*;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;

/**
 * ノート編集領域(ノートを配置する格子や小節線が置かれた領域)のクラス
 * @author Shun Yamashita
 */

public class EditArea extends Group {
	private int measureCount;
	private int octaveCount;
	private ArrayList<Note> notes;
	private Timeline playTimeline;
	private NoteGrid[][] noteGrids;
	private Line[] vFrameLines;
	private Line[] snapLines;
	private Line playLine;
	private Pianoroll parent;

	public EditArea(int measureCount, int octaveCount, int x, int y, Pianoroll parent) {
		super();
		this.measureCount = measureCount;
		this.octaveCount = octaveCount;
		this.notes = new ArrayList<Note>();
		this.parent = parent;
		setClip(new Rectangle(MEASURE_WIDTH * SHOW_MEASURE_COUNT + 0.5, MEASURE_HEIGHT * SHOW_OCTAVE_COUNT + 0.5)); // 実際に表示する領域サイズ
		setupPoint(x, y);
		setupNoteGrids();
		setupFrameLines();
		setupSnapLines();
		setupPlayLine();
		setupOptionalLines();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupNoteGrids() {
		int verticalSize = 12 * octaveCount; // 12 is number of keys in 1 octave
		int horizontalSize = 4 * measureCount; // 4 is number of beats in 1 measure
		noteGrids = new NoteGrid[verticalSize][horizontalSize];
		for(int j = 0; j < verticalSize; j++) {
			String interval = INTERVALS[j % 12];
			int octave = MAX_OCTAVE - (j / 12);
			for(int i = 0; i < horizontalSize; i++) {
				int x = NOTE_GRID_X[(int)(i % 4)] + NOTE_GRID_X_OFFSET * (int)(i / 4);
				int y = NOTE_GRID_Y[(int)(j % 12)] + NOTE_GRID_Y_OFFSET * (int)(j / 12);
				int w = NOTE_GRID_WIDTH;
				int h = NOTE_GRID_HEIGHT;
				noteGrids[j][i] = new NoteGrid(4, interval, octave, x, y, w, h, this);
				getChildren().add(noteGrids[j][i]);
			}
		}
	}

	public void setupFrameLines() {
		// 垂直方向のライン
		vFrameLines = new Line[measureCount + 1];
		int vStartY = 0;
		int vEndY = vStartY + MEASURE_HEIGHT * octaveCount;
		for(int n = 0; n < measureCount + 1; n++) {
			int x = MEASURE_WIDTH * n;
			vFrameLines[n] = new Line(x + 0.5, vStartY, x + 0.5, vEndY);
			vFrameLines[n].setStrokeLineCap(StrokeLineCap.BUTT);
			getChildren().add(vFrameLines[n]);
		}
		// 水平方向のライン
		int hStartX = 0;
		int hEndX = MEASURE_WIDTH * measureCount;
		int[] hStartY = {0, MEASURE_HEIGHT * SHOW_OCTAVE_COUNT};
		int[] hEndY = hStartY;
		for(int n = 0; n < hStartY.length; n++) {
			Line line = new Line(hStartX, hStartY[n] + 0.5, hEndX, hEndY[n] + 0.5);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			getChildren().add(line);
		}
	}

	public void setupSnapLines() {
		snapLines = new Line[16 * measureCount];
		int[] xArray = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150};
		int startY = 0;
		int endY = startY + MEASURE_HEIGHT * octaveCount;
		for(int n = 0; n < snapLines.length; n++) {
			int x = xArray[n % 16] + MEASURE_WIDTH * (n / 16);
			snapLines[n] = new Line(x + 0.5, startY, x + 0.5, endY);
			snapLines[n].setStrokeLineCap(StrokeLineCap.BUTT);
			snapLines[n].setStroke(Color.web("#888888"));
			snapLines[n].getStrokeDashArray().addAll(4.0, 2.0);
			snapLines[n].setVisible(false);
			getChildren().add(snapLines[n]);
		}
	}

	public void setupPlayLine() {
		int startY = 0;
		int endY = startY + MEASURE_HEIGHT * octaveCount;
		playLine = new Line(0 + 0.5, startY, 0 + 0.5, endY);
		playLine.setStrokeLineCap(StrokeLineCap.BUTT);
		playLine.setStroke(Color.web("#FF0000"));
		getChildren().add(playLine);
	}

	public void setupOptionalLines() {
		int[] xArray = {0, MEASURE_WIDTH * SHOW_MEASURE_COUNT};
		int startY = 0;
		int endY = startY + MEASURE_HEIGHT * SHOW_OCTAVE_COUNT;
		for(int n = 0; n < xArray.length; n++) {
			Line line = new Line(xArray[n] + 0.5, startY, xArray[n] + 0.5, endY);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			getChildren().add(line);
		}
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

	public void updateNoteResolution(int resolution) {
		for(Note note : notes) {
			note.setResolution(resolution);
		}
	}

	public void updateNoteGridResolution(int resolution) {
		for(NoteGrid[] array : noteGrids) {
			for(NoteGrid element : array) {
				element.setResolution(resolution);
			}
		}
	}

	public void translateX(int move) {
		for(Note note : notes) {
			note.setTranslateX(move);
		}
		for(NoteGrid[] array : noteGrids) {
			for(NoteGrid element : array) {
				element.setTranslateX(move);
			}
		}
		for(Line vFrameLine : vFrameLines) {
			vFrameLine.setTranslateX(move);
		}
		for(Line snapLine : snapLines) {
			snapLine.setTranslateX(move);
		}
		playLine.setTranslateX(move);
	}

	public void translateY(int move) {
		for(Note note : notes) {
			note.setTranslateY(move);
		}
		for(NoteGrid[] array : noteGrids) {
			for(NoteGrid element : array) {
				element.setTranslateY(move);
			}
		}
		for(Line vFrameLine : vFrameLines) {
			vFrameLine.setTranslateY(move);
		}
		for(Line snapLine : snapLines) {
			snapLine.setTranslateY(move);
		}
		playLine.setTranslateY(move);
	}

	// ピアノロールの格子をクリックしてノートを置く際に呼ばれる
	public void putNote(int x, int y, int width, int height) {
		int resolution = parent.getResolution();
		int minX = 0;
		int maxX = minX + MEASURE_WIDTH * measureCount;
		int minY = 0;
		int maxY = minY + MEASURE_HEIGHT * octaveCount;
		double hIncPerUnit = -(MEASURE_WIDTH * (measureCount - SHOW_MEASURE_COUNT)) / 100.0;
		double hScrollBarVal = parent.getHScrollBarValue();
		double hMove = hIncPerUnit * hScrollBarVal;
		double vIncPerUnit = -(MEASURE_HEIGHT * (octaveCount - SHOW_OCTAVE_COUNT)) / 100.0;
		double vScrollBarVal = parent.getVScrollBarValue();
		double vMove = vIncPerUnit * vScrollBarVal;
		Note note = new Note(x, y, width, height, resolution, minX, maxX, minY, maxY, true, this);
		note.setTranslateX(hMove);
		note.setTranslateY(vMove);
		notes.add(note);
		getChildren().add(note);
	}

	// ファイルからノートを読み込んで置く際に呼ばれる
	public void putNote(int measure, int beat, int place, int duration, String interval, int octave) {
		HashMap<String, Integer> interval2index = new HashMap<String, Integer>() {
			{put("C", 11);} {put("C#", 10);} {put("D", 9);} {put("D#", 8);} {put("E", 7);}
			{put("F", 6);} {put("F#", 5);} {put("G", 4);} {put("G#", 3);} {put("A", 2);} {put("A#", 1);} {put("B", 0);}
		};
		int x = MEASURE_WIDTH * (measure - 1) + BEAT_WIDTH * (beat - 1) + 10 * (place / 240);
		int y = MEASURE_HEIGHT * (MAX_OCTAVE - octave) + NOTE_GRID_HEIGHT * interval2index.get(interval);
		int width = 10 * (duration / 240);
		int height = NOTE_GRID_HEIGHT;

		int resolution = parent.getResolution();
		int minX = 0;
		int maxX = minX + MEASURE_WIDTH * measureCount;
		int minY = 0;
		int maxY = minY + MEASURE_HEIGHT * octaveCount;
		double hIncPerUnit = -(MEASURE_WIDTH * (measureCount - SHOW_MEASURE_COUNT)) / 100.0;
		double hScrollBarVal = parent.getHScrollBarValue();
		double hMove = hIncPerUnit * hScrollBarVal;
		double vIncPerUnit = -(MEASURE_HEIGHT * (octaveCount - SHOW_OCTAVE_COUNT)) / 100.0;
		double vScrollBarVal = parent.getVScrollBarValue();
		double vMove = vIncPerUnit * vScrollBarVal;
		Note note = new Note(x, y, width, height, resolution, minX, maxX, minY, maxY, false, this);
		note.setTranslateX(hMove);
		note.setTranslateY(vMove);
		note.setCanTone(true); // ノートの配置が完了したら発音許可フラグを真にしてやる
		notes.add(note);
		getChildren().add(note);
	}

	public void removeNote(Note note) {
		notes.remove(note);
		getChildren().remove(note);
	}

	public void setupBeforePlay() {
		for(Note note : notes) {
			note.setDisable(true);
		}
		for(NoteGrid[] array : noteGrids) {
			for(NoteGrid element : array) {
				element.setDisable(true);
			}
		}
		playLine.toFront();
		playLine.setLayoutX(0);
	}

	public void setupAfterPlay() {
		for(Note note : notes) {
			note.setDisable(false);
		}
		for(NoteGrid[] array : noteGrids) {
			for(NoteGrid element : array) {
				element.setDisable(false);
			}
		}
		playLine.setLayoutX(0);
	}

	public void playAnimation(int bpm) {
		float secPerBeat = 60.f / bpm;
		float secPerMeasure = secPerBeat * 4;
		float playTimeSec = secPerMeasure * measureCount;
		playTimeline = new Timeline();
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.stop();
			}
		};
		KeyValue kvStart = new KeyValue(playLine.layoutXProperty(), 0);
		KeyFrame kfStart = new KeyFrame(Duration.ZERO, kvStart);
		KeyValue kvEnd = new KeyValue(playLine.layoutXProperty(), MEASURE_WIDTH * measureCount);
		KeyFrame kfEnd = new KeyFrame(Duration.millis(playTimeSec * 1000), onFinished, kvEnd);
		KeyValue[] kvMeasureChange = new KeyValue[measureCount / SHOW_MEASURE_COUNT - 1];
		KeyFrame[] kfMeasureChange = new KeyFrame[measureCount / SHOW_MEASURE_COUNT - 1];
		for(int n = 0; n < kfMeasureChange.length; n++) {
			kvMeasureChange[n] = new KeyValue(parent.getHScrollBarValueProperty(), (100.0 / (measureCount - SHOW_MEASURE_COUNT)) * 4 * (n + 1), Interpolator.DISCRETE);
			kfMeasureChange[n] = new KeyFrame(Duration.millis(secPerMeasure * 4 * (n + 1) * 1000), kvMeasureChange[n]);
			playTimeline.getKeyFrames().add(kfMeasureChange[n]);
		}
		playTimeline.getKeyFrames().add(kfStart);
		playTimeline.getKeyFrames().add(kfEnd);
		playTimeline.setCycleCount(1);
		playTimeline.setAutoReverse( true );
		playTimeline.play();
	}

	public void stop() {
		playTimeline.stop();
	}

	public int getCurrentMeasure() { return parent.getCurrentMeasure(); }

	public ArrayList<Note> getNotes() { return notes; }
	public Timeline getPlayTimeline() { return playTimeline; }
	public int getOctaveCount() { return octaveCount; }
}
