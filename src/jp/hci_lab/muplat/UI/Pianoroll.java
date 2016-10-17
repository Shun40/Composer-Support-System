import static constants.PianorollConstants.*;
import static constants.UniversalConstants.*;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.ScrollBar;

/**
 * ノートを打ち込むピアノロールのクラス
 * @author Shun Yamashita
 */
public class Pianoroll extends Group {
	private int measureCount;
	private int octaveCount;
	private int bpm;
	private int currentMeasure;
	private MainScene parent;

	private NoteResolutionSelector noteResolutionSelector;
	private BpmLabel bpmLabel;
	private StopButton stopButton;
	private PlayButton playButton;
	private EditArea editArea;
	private MeasureArea measureArea;
	private Keyboard keyboard;
	private ScrollBar hScrollBar;
	private ScrollBar vScrollBar;

	public Pianoroll(int measureCount, int octaveCount, int bpm, MainScene parent) {
		super();
		this.measureCount = measureCount;
		this.octaveCount = octaveCount;
		this.bpm = bpm;
		this.currentMeasure = 1;
		this.parent = parent;
		setupNoteResolutionSelector();
		setupBpmLabel();
		setupStopButton();
		setupPlayButton();
		setupEditArea();
		setupMeasureArea();
		setupKeyboard();
		setupHScrollBar();
		setupVScrollBar();
	}

	public void setupNoteResolutionSelector() {
		noteResolutionSelector = new NoteResolutionSelector(NOTE_RESOLUTION_SELECTOR_X, NOTE_RESOLUTION_SELECTOR_Y, this);
		getChildren().add(noteResolutionSelector);
	}

	public void setupBpmLabel() {
		bpmLabel = new BpmLabel(bpm,  BPM_LABEL_X,  BPM_LABEL_Y, this);
		getChildren().add(bpmLabel);
	}

	public void setupStopButton() {
		stopButton = new StopButton(STOP_BUTTON_X, STOP_BUTTON_Y, this);
		getChildren().add(stopButton);
	}

	public void setupPlayButton() {
		playButton = new PlayButton(PLAY_BUTTON_X, PLAY_BUTTON_Y, this);
		getChildren().add(playButton);
	}

	public void setupEditArea() {
		editArea = new EditArea(measureCount, octaveCount, EDIT_AREA_X, EDIT_AREA_Y, this);
		getChildren().add(editArea);
	}

	public void setupMeasureArea() {
		measureArea = new MeasureArea(measureCount, MEASURE_AREA_X, MEASURE_AREA_Y);
		getChildren().add(measureArea);
	}

	public void setupKeyboard() {
		keyboard = new Keyboard(octaveCount, KEYBOARD_X, KEYBOARD_Y);
		getChildren().add(keyboard);
	}

	public void setupHScrollBar() {
		hScrollBar = new ScrollBar();
		hScrollBar.setOrientation(Orientation.HORIZONTAL);;
		hScrollBar.setLayoutX(EDIT_AREA_X);
		hScrollBar.setLayoutY(EDIT_AREA_Y + MEASURE_HEIGHT * SHOW_OCTAVE_COUNT + 1);
		hScrollBar.setPrefWidth(MEASURE_WIDTH * SHOW_MEASURE_COUNT + 1);
		hScrollBar.setPrefHeight(16);
		hScrollBar.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
			hTranslate();
		});
		getChildren().add(hScrollBar);
	}

	public void setupVScrollBar() {
		vScrollBar = new ScrollBar();
		vScrollBar.setOrientation(Orientation.VERTICAL);
		vScrollBar.setLayoutX(EDIT_AREA_X + MEASURE_WIDTH * SHOW_MEASURE_COUNT + 1);
		vScrollBar.setLayoutY(EDIT_AREA_Y);
		vScrollBar.setPrefWidth(16);
		vScrollBar.setPrefHeight(MEASURE_HEIGHT * SHOW_OCTAVE_COUNT + 1);
		vScrollBar.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
			vTranslate();
		});
		vScrollBar.setValue(50.0); // 真ん中らへんの音程(C4~C5あたり)を表示するよう垂直方向スクロール値を設定
		getChildren().add(vScrollBar);
	}

	public void hTranslate() {
		double incPerUnit = -(MEASURE_WIDTH * (measureCount - SHOW_MEASURE_COUNT)) / 100.0;
		double hScrollBarVal = hScrollBar.getValue();
		int move = (int)(incPerUnit * hScrollBarVal);
		editArea.translateX(move);
		measureArea.translate(move);
	}

	public void vTranslate() {
		double incPerUnit = -(MEASURE_HEIGHT * (octaveCount - SHOW_OCTAVE_COUNT)) / 100.0;
		double vScrollBarVal = vScrollBar.getValue();
		int move = (int)(incPerUnit * vScrollBarVal);
		editArea.translateY(move);
		keyboard.translate(move);
	}

	public void changedNoteResolution(int resolution) {
		editArea.updateSnapLines(resolution);
		editArea.updateNoteResolution(resolution);
		editArea.updateNoteGridResolution(resolution);
	}

	public void stop() {
		if(editArea.getPlayTimeline() == null) return;

		// エンジンへの停止指示
		parent.stop();

		editArea.stop();
		setupAfterPlay();
	}

	public void play() {
		setupBeforePlay();

		// エンジンへの再生指示
		parent.play();

		editArea.playAnimation(bpm);
	}

	public void setupBeforePlay() {
		noteResolutionSelector.setDisable(true);
		bpmLabel.setDisable(true);
		stopButton.setDisable(false);
		playButton.setDisable(true);
		editArea.setupBeforePlay();
		hScrollBar.setValue(0.0);

		// エンジンへのセットアップ
		parent.setBpm(bpm);
		for(Note note : editArea.getNotes()) {
			parent.addNote(note);
		}
	}

	public void setupAfterPlay() {
		noteResolutionSelector.setDisable(false);
		bpmLabel.setDisable(false);
		stopButton.setDisable(true);
		playButton.setDisable(false);
		editArea.setupAfterPlay();
		hScrollBar.setValue(0.0);
	}

	public int getResolution() { return noteResolutionSelector.getIntValue(); }
	public double getHScrollBarValue() { return hScrollBar.getValue(); }
	public double getVScrollBarValue() { return vScrollBar.getValue(); }
	public DoubleProperty getHScrollBarValueProperty() { return hScrollBar.valueProperty(); }

	public int getMeasureCount() { return measureCount; }
	public int getOctaveCount() { return octaveCount; }
	public int getBpm() { return bpm; }
	public void setBpm(int bpm) { this.bpm = bpm; }
	public int getCurrentMeasure() { return currentMeasure; }
}
