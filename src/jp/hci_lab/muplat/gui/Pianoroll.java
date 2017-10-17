package gui;
import static gui.constants.PianorollConstants.*;
import static gui.constants.UniversalConstants.*;

import java.util.ArrayList;

import engine_yamashita.Accompaniment;
import engine_yamashita.PredictionInformation;
import engine_yamashita.PredictionPattern;
import gui.constants.UniversalConstants.Algorithm;
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
	private int currentTrack;
	private MainScene parent;

	private NoteResolutionSelector noteResolutionSelector;
	private BpmLabel bpmLabel;
	private StopButton stopButton;
	private PlayButton playButton;
	private EditArea editArea;
	private Keyboard keyboard;
	//private InstrumentSelector instrumentSelector;
	private MeasureArea measureArea;
	private ScrollBar hScrollBar;
	private ScrollBar vScrollBar;
	private TrackMuteSelector trackMuteSelector;
	private TrackSoloSelector trackSoloSelector;
	private PatternArea patternArea;
	private AlgorithmLabel algorithmLabel;

	public Pianoroll(int measureCount, int octaveCount, int bpm, MainScene parent) {
		super();
		this.measureCount = measureCount;
		this.octaveCount = octaveCount;
		this.bpm = bpm;
		this.currentTrack = 1;
		this.parent = parent;
		setupNoteResolutionSelector();
		setupBpmLabel();
		setupStopButton();
		setupPlayButton();
		setupEditArea();
		setupKeyboard();
		//setupInstrumentSelector();
		setupHScrollBar();
		setupVScrollBar();
		//setupMuteTrackSelector();
		//setupSoloTrackSelector();
		setupPatternArea();
		setupMeasureArea();
		setupAlgorithmLabel();
	}

	public void setupNoteResolutionSelector() {
		noteResolutionSelector = new NoteResolutionSelector(NOTE_RESOLUTION_SELECTOR_X, NOTE_RESOLUTION_SELECTOR_Y, this);
		getChildren().add(noteResolutionSelector);
	}

	public void setupBpmLabel() {
		bpmLabel = new BpmLabel(bpm, BPM_LABEL_X, BPM_LABEL_Y, this);
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
		measureArea = new MeasureArea(measureCount, MEASURE_AREA_X, MEASURE_AREA_Y, this);
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

	/*
	public void setupInstrumentSelector() {
		instrumentSelector = new InstrumentSelector(INSTRUMENT_SELECTOR_X, INSTRUMENT_SELECTOR_Y, this);
		getChildren().add(instrumentSelector);
	}
	*/

	public void setupMuteTrackSelector() {
		trackMuteSelector = new TrackMuteSelector(TRACK_MUTE_SELECTOR_X, TRACK_MUTE_SELECTOR_Y, this);
		getChildren().add(trackMuteSelector);
	}

	public void setupSoloTrackSelector() {
		trackSoloSelector = new TrackSoloSelector(TRACK_SOLO_SELECTOR_X, TRACK_SOLO_SELECTOR_Y, this);
		getChildren().add(trackSoloSelector);
	}

	public void setupPatternArea() {
		patternArea = new PatternArea(PATTERN_AREA_X, PATTERN_AREA_Y, this);
		getChildren().add(patternArea);
	}

	public void setupAlgorithmLabel() {
		algorithmLabel = new AlgorithmLabel(Algorithm.PC, ALGORITHM_LABEL_X, ALGORITHM_LABEL_Y);
		getChildren().add(algorithmLabel);
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
		parent.setBpm(bpm);
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
	}

	public void setupAfterPlay() {
		noteResolutionSelector.setDisable(false);
		bpmLabel.setDisable(false);
		stopButton.setDisable(true);
		playButton.setDisable(false);
		editArea.setupAfterPlay();
		editArea.changeCurrentTrack(currentTrack);
		hScrollBar.setValue(0.0);
	}

	public void putNote(int pitch, int position, int duration) {
		editArea.putNote(pitch, position, duration);
	}

	public void removeNoteInMeasure(int targetMeasure, int targetTrack) {
		editArea.removeNoteInMeasure(targetMeasure, targetTrack);
	}

	public void removeNoteIn2Beat(int targetMeasure, int targetBeat1, int targetBeat2, int targetTrack) {
		editArea.removeNoteIn2Beat(targetMeasure, targetBeat1, targetBeat2, targetTrack);
	}

	public void addNoteToEngine(Note note) {
		parent.addNoteToEngine(note);
	}

	public void removeNoteFromEngine(Note note) {
		parent.removeNoteFromEngine(note);
	}

	public void clearNoteFromUi() {
		editArea.clearNoteFromUi();
	}

	public void clearNoteFromEngine() {
		parent.clearNoteFromEngine();
	}

	/*
	 * メロディの予測変換を行う
	 */
	public ArrayList<PredictionPattern> prediction() {
		// 予測変換に必要な情報を取得
		// 予測変換対象小節
		int targetMeasure = getPredictionTargetMeasure();
		// メロディ音符
		ArrayList<Note> melodyNotes = new ArrayList<Note>();
		for(NoteBlock noteBlock : editArea.getNoteBlocks()) {
			Note note = noteBlock.getNote();
			int track = note.getTrack();
			if(track == 1) {
				melodyNotes.add(note);
			}
		}
		// コード進行
		ChordPair[] chordProgression = new ChordPair[measureCount];
		for(int i = 0; i < measureCount; i++) {
			chordProgression[i] = new ChordPair();
			chordProgression[i].setChord(measureArea.getChord(i+1, 0), 0);
			chordProgression[i].setChord(measureArea.getChord(i+1, 1), 1);
		}
		PredictionInformation predictionInformation = new PredictionInformation(targetMeasure, melodyNotes, chordProgression);

		// 予測変換
		return parent.prediction(predictionInformation);
	}

	public void incWordDictionaryFrequency(String wordId) {
		parent.incWordDictionaryFrequency(wordId);
	}

	public void incPhraseDictionaryFrequency(String contextId, String wordId) {
		parent.incPhraseDictionaryFrequency(contextId, wordId);
	}

	public void setPredictionPatternList(int targetMeasure) {
		patternArea.setPredictionPatternList(targetMeasure);
	}

	public void makeAccompaniment(String chord, int measure, int index) {
		Accompaniment accompaniment = parent.makeAccompaniment(chord);
		ArrayList<Note> pianoPart = accompaniment.getPianoPart();
		ArrayList<Note> bassPart = accompaniment.getBassPart();
		ArrayList<Note> drumPart = accompaniment.getDrumPart();
		int pianoTrack = 2;
		int bassTrack = 9;
		int drumTrack = 10;
		int targetMeasure = measure;
		int targetBeat1 = index * 2 + 1;
		int targetBeat2 = index * 2 + 2;
		removeNoteIn2Beat(targetMeasure, targetBeat1, targetBeat2, pianoTrack);
		removeNoteIn2Beat(targetMeasure, targetBeat1, targetBeat2, bassTrack);
		removeNoteIn2Beat(targetMeasure, targetBeat1, targetBeat2, drumTrack);
		for(int i = 0; i < pianoPart.size(); i++) {
			int pitch    = pianoPart.get(i).getPitch();
			int position = pianoPart.get(i).getPosition() + (PPQ * 4) * (targetMeasure - 1) + PPQ * (targetBeat1 - 1);
			int duration = pianoPart.get(i).getDuration();
			setCurrentTrack(pianoTrack);
			putNote(pitch, position, duration);
		}
		for(int i = 0; i < bassPart.size(); i++) {
			int pitch    = bassPart.get(i).getPitch();
			int position = bassPart.get(i).getPosition() + (PPQ * 4) * (targetMeasure - 1) + PPQ * (targetBeat1 - 1);
			int duration = bassPart.get(i).getDuration();
			setCurrentTrack(bassTrack);
			putNote(pitch, position, duration);
		}
		for(int i = 0; i < drumPart.size(); i++) {
			int pitch    = drumPart.get(i).getPitch();
			int position = drumPart.get(i).getPosition() + (PPQ * 4) * (targetMeasure - 1) + PPQ * (targetBeat1 - 1);
			int duration = drumPart.get(i).getDuration();
			setCurrentTrack(drumTrack);
			putNote(pitch, position, duration);
		}
		setCurrentTrack(1);
	}

	public void setAlgorithm(Algorithm algorithm) { algorithmLabel.setAlgorithm(algorithm); }

	public int getResolution() { return noteResolutionSelector.getIntValue(); }
	public ArrayList<NoteBlock> getNoteBlocks() { return editArea.getNoteBlocks(); }
	public double getHScrollBarValue() { return hScrollBar.getValue(); }
	public double getVScrollBarValue() { return vScrollBar.getValue(); }
	public DoubleProperty getHScrollBarValueProperty() { return hScrollBar.valueProperty(); }

	public int getMeasureCount() { return measureCount; }
	public int getOctaveCount() { return octaveCount; }
	public int getBpm() { return bpm; }
	public void setBpm(int bpm) {
		this.bpm = bpm;
		bpmLabel.changeBpm(bpm);
		parent.setBpm(bpm);
	}
	public int getCurrentTrack() { return currentTrack; }
	public void setCurrentTrack(int currentTrack) {
		this.currentTrack = currentTrack;
		editArea.changeCurrentTrack(currentTrack);
		keyboard.changeInstrument(currentTrack, PROGRAM_NUMBERS[currentTrack - 1]);
		//instrumentSelector.setSelectedTrack(currentTrack);
	}
	public void setTrackMute(int track, boolean mute) { parent.setTrackMute(track, mute); }
	public void setTrackSolo(int track, boolean solo) { parent.setTrackSolo(track, solo); }
	public int getPredictionTargetMeasure() { return measureArea.getPredictionTarget(); }
}
