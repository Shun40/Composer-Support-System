package gui;
import java.util.ArrayList;
import java.util.List;

import engine.Accompaniment;
import engine.AlgorithmInformation;
import engine.PredictionInformation;
import engine.PredictionPattern;
import gui.component.AlgorithmBar;
import gui.component.BpmSelector;
import gui.component.PlayButton;
import gui.component.ResolutionSelector;
import gui.component.StopButton;
import gui.component.keyboard.Keyboard;
import gui.component.measurebar.MeasureBar;
import gui.component.patternbar.PatternBar;
import gui.component.pianoroll.Pianoroll;
import gui.component.pianoroll.note.Note;
import gui.component.pianoroll.note.NoteModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.ScrollBar;
import midi.MidiConstants;
import system.AppConstants;

/**
 * GUIコンポーネントをまとめて持つクラス
 * @author Shun Yamashita
 */
public class GuiManager extends Group {
	private int bpm;
	private int currentTrack;
	private AppScene owner;

	private ResolutionSelector resolutionSelector;
	private BpmSelector bpmSelector;
	private StopButton stopButton;
	private PlayButton playButton;
	private Pianoroll pianoroll;
	private Keyboard keyboard;
	private MeasureBar measureBar;
	private ScrollBar hScrollBar;
	private ScrollBar vScrollBar;
	private PatternBar patternBar;
	private AlgorithmBar algorithmBar;

	public GuiManager(AppScene parent) {
		super();
		bpm = AppConstants.Settings.DEFAULT_BPM;
		currentTrack = 1;
		this.owner = parent;
		setupResolutionSelector();
		setupBpmSelector();
		setupStopButton();
		setupPlayButton();
		setupPianoroll();
		setupKeyboard();
		setupAlgorithmBar();
		setupPatternBar();
		setupMeasurebar();
		setupHScrollBar();
		setupVScrollBar();
	}

	public void setupResolutionSelector() {
		resolutionSelector = new ResolutionSelector(this);
		getChildren().add(resolutionSelector);
	}

	public void setupBpmSelector() {
		bpmSelector = new BpmSelector(bpm, this);
		getChildren().add(bpmSelector);
	}

	public void setupStopButton() {
		stopButton = new StopButton(this);
		getChildren().add(stopButton);
	}

	public void setupPlayButton() {
		playButton = new PlayButton(this);
		getChildren().add(playButton);
	}

	public void setupPianoroll() {
		pianoroll = new Pianoroll(this);
		getChildren().add(pianoroll);
	}

	public void setupMeasurebar() {
		measureBar = new MeasureBar(this);
		getChildren().add(measureBar);
	}

	public void setupKeyboard() {
		keyboard = new Keyboard();
		getChildren().add(keyboard);
	}

	public void setupHScrollBar() {
		hScrollBar = new ScrollBar();
		hScrollBar.setOrientation(Orientation.HORIZONTAL);;
		hScrollBar.setLayoutX(GuiConstants.Pianoroll.X);
		hScrollBar.setLayoutY(GuiConstants.Pianoroll.Y + GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.SHOW_OCTAVES + 1);
		hScrollBar.setPrefWidth(GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.SHOW_MEASURES + 1);
		hScrollBar.setPrefHeight(16);
		hScrollBar.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
			hTranslate();
		});
		getChildren().add(hScrollBar);
	}

	public void setupVScrollBar() {
		vScrollBar = new ScrollBar();
		vScrollBar.setOrientation(Orientation.VERTICAL);
		vScrollBar.setLayoutX(GuiConstants.Pianoroll.X + GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.SHOW_MEASURES + 1);
		vScrollBar.setLayoutY(GuiConstants.Pianoroll.Y);
		vScrollBar.setPrefWidth(16);
		vScrollBar.setPrefHeight(GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.SHOW_OCTAVES + 1);
		vScrollBar.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
			vTranslate();
		});
		vScrollBar.setValue(50.0); // 真ん中らへんの音程(C4~C5あたり)を表示するよう垂直方向スクロール値を設定
		getChildren().add(vScrollBar);
	}

	public void setupAlgorithmBar() {
		algorithmBar = new AlgorithmBar();
		getChildren().add(algorithmBar);
	}

	public void setupPatternBar() {
		patternBar = new PatternBar(this);
		getChildren().add(patternBar);
	}

	public void hTranslate() {
		double incPerUnit = -(GuiConstants.Pianoroll.MEASURE_WIDTH * (AppConstants.Settings.MEASURES - AppConstants.Settings.SHOW_MEASURES)) / 100.0;
		double hScrollBarVal = hScrollBar.getValue();
		int move = (int)(incPerUnit * hScrollBarVal);
		pianoroll.translateX(move);
		measureBar.translate(move);
	}

	public void vTranslate() {
		double incPerUnit = -(GuiConstants.Pianoroll.MEASURE_HEIGHT * (AppConstants.Settings.OCTAVES - AppConstants.Settings.SHOW_OCTAVES)) / 100.0;
		double vScrollBarVal = vScrollBar.getValue();
		int move = (int)(incPerUnit * vScrollBarVal);
		pianoroll.translateY(move);
		keyboard.translate(move);
	}

	public void changeResolution(int resolution) {
		pianoroll.updateResolution(resolution);
	}

	public void stop() {
		//if(pianoroll.getPlayTimeline() == null) return;

		// エンジンへの停止指示
		owner.stop();

		pianoroll.stop();
		setupAfterPlay();
	}

	public void play() {
		setupBeforePlay();

		// エンジンへの再生指示
		owner.setBpm(bpm);
		owner.play();

		pianoroll.playAnimation(bpm);
	}

	public void setupBeforePlay() {
		resolutionSelector.setDisable(true);
		bpmSelector.setDisable(true);
		stopButton.setDisable(false);
		playButton.setDisable(true);
		pianoroll.setupBeforePlay();
		hScrollBar.setValue(0.0);
	}

	public void setupAfterPlay() {
		resolutionSelector.setDisable(false);
		bpmSelector.setDisable(false);
		stopButton.setDisable(true);
		playButton.setDisable(false);
		pianoroll.setupAfterPlay();
		pianoroll.changeCurrentTrack(currentTrack);
		hScrollBar.setValue(0.0);
	}

	public void putNote(int pitch, int position, int duration) {
		pianoroll.putNote(pitch, position, duration);
	}

	public void removeNoteInMeasure(int targetMeasure, int targetTrack) {
		pianoroll.removeNoteInMeasure(targetMeasure, targetTrack);
	}

	public void removeNoteIn2Beat(int targetMeasure, int targetBeat1, int targetBeat2, int targetTrack) {
		pianoroll.removeNoteIn2Beat(targetMeasure, targetBeat1, targetBeat2, targetTrack);
	}

	public void addNoteToSequencer(Note note) {
		owner.addNoteToSequencer(note);
	}

	public void removeNoteFromSequencer(Note note) {
		owner.removeNoteFromSequencer(note);
	}

	public void clearNoteFromPianoroll() {
		pianoroll.clearNoteFromPianoroll();
	}

	public void clearNoteFromSequencer() {
		owner.clearNoteFromSequencer();
	}

	/*
	 * メロディの予測変換を行う
	 */
	public List<PredictionPattern> prediction() {
		// 予測変換に必要な情報を取得
		// 予測変換対象小節
		int targetMeasure = getPredictionTargetMeasure();
		// メロディ音符
		List<Note> melodyNotes = new ArrayList<Note>();
		for(Note note : pianoroll.getNotes()) {
			int track = note.getModel().getTrack();
			if(track == 1) {
				melodyNotes.add(note);
			}
		}
		// コード進行
		List<String> chordProgression = new ArrayList<String>();
		for(int i = 0; i < AppConstants.Settings.MEASURES; i++) {
			chordProgression.add(measureBar.getChord(i + 1, 1)); // (i+1)小節目の1~2拍のコード
			chordProgression.add(measureBar.getChord(i + 1, 3)); // (i+1)小節目の3~4拍のコード
		}
		// 選択アルゴリズム
		List<AppConstants.Algorithm> selectedAlgorithms = algorithmBar.getSelectedAlgorithms();
		AppConstants.MelodyStructurePattern selectedMelodyStructurePattern = algorithmBar.getSelectedMelodyStructurePattern();

		// 予測変換
		PredictionInformation predictionInformation = new PredictionInformation(targetMeasure, melodyNotes, chordProgression);
		AlgorithmInformation algorithmInformation = new AlgorithmInformation(selectedAlgorithms, selectedMelodyStructurePattern);
		return owner.prediction(predictionInformation, algorithmInformation);
	}

	public void incWordDictionaryFrequency(String wordId) {
		owner.incFrequencyOfWordDictionary(wordId);
	}

	public void incPhraseDictionaryFrequency(String contextId, String wordId) {
		owner.incFrequencyOfPhraseDictionary(contextId, wordId);
	}

	public void setPredictionPatternList(int targetMeasure) {
		patternBar.setPredictionPatternList(targetMeasure);
	}

	public void makeAccompaniment(String chord, int measure, int beat) {
		Accompaniment accompaniment = owner.makeAccompaniment(chord);
		List<NoteModel> pianoPart = accompaniment.getPianoPart();
		List<NoteModel> bassPart = accompaniment.getBassPart();
		List<NoteModel> drumPart = accompaniment.getDrumPart();
		int pianoTrack = 2;
		int bassTrack = 9;
		int drumTrack = 10;
		int targetMeasure = measure;
		int targetBeat1 = beat;
		int targetBeat2 = beat + 1;
		removeNoteIn2Beat(targetMeasure, targetBeat1, targetBeat2, pianoTrack);
		removeNoteIn2Beat(targetMeasure, targetBeat1, targetBeat2, bassTrack);
		removeNoteIn2Beat(targetMeasure, targetBeat1, targetBeat2, drumTrack);
		for(int i = 0; i < pianoPart.size(); i++) {
			int pitch    = pianoPart.get(i).getPitch();
			int position = pianoPart.get(i).getPosition() + (MidiConstants.PPQ * 4) * (targetMeasure - 1) + MidiConstants.PPQ * (targetBeat1 - 1);
			int duration = pianoPart.get(i).getDuration();
			setCurrentTrack(pianoTrack);
			putNote(pitch, position, duration);
		}
		for(int i = 0; i < bassPart.size(); i++) {
			int pitch    = bassPart.get(i).getPitch();
			int position = bassPart.get(i).getPosition() + (MidiConstants.PPQ * 4) * (targetMeasure - 1) + MidiConstants.PPQ * (targetBeat1 - 1);
			int duration = bassPart.get(i).getDuration();
			setCurrentTrack(bassTrack);
			putNote(pitch, position, duration);
		}
		for(int i = 0; i < drumPart.size(); i++) {
			int pitch    = drumPart.get(i).getPitch();
			int position = drumPart.get(i).getPosition() + (MidiConstants.PPQ * 4) * (targetMeasure - 1) + MidiConstants.PPQ * (targetBeat1 - 1);
			int duration = drumPart.get(i).getDuration();
			setCurrentTrack(drumTrack);
			putNote(pitch, position, duration);
		}
		setCurrentTrack(1);
	}

	public void setChordProgression(int index) {
		AppConstants.ChordProgression chordProgression = AppConstants.ChordProgression.values()[index];
		String[] chords = chordProgression.toString().split("_");
		for(int n = 0; n < chords.length; n++) {
			if(n % 2 == 0) {
				measureBar.setChord(chords[n], (n / 2) + 1, 1); // 1~2拍目
			} else {
				measureBar.setChord(chords[n], (n / 2) + 1, 3); // 3~4拍目
			}
		}
	}

	public int getResolution() {
		return resolutionSelector.getResolution();
	}

	public List<Note> getNotes() {
		return pianoroll.getNotes();
	}

	public double getHScrollBarValue() {
		return hScrollBar.getValue();
	}

	public double getVScrollBarValue() {
		return vScrollBar.getValue();
	}

	public DoubleProperty getHScrollBarValueProperty() {
		return hScrollBar.valueProperty();
	}

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
		bpmSelector.changeBpm(bpm);
		owner.setBpm(bpm);
	}

	public int getCurrentTrack() {
		return currentTrack;
	}

	public void setCurrentTrack(int currentTrack) {
		this.currentTrack = currentTrack;
		pianoroll.changeCurrentTrack(currentTrack);
		keyboard.changeInstrument(currentTrack, MidiConstants.PROGRAM_NUMBERS[currentTrack - 1]);
	}

	public int getPredictionTargetMeasure() {
		return measureBar.getPredictionTarget();
	}
}
