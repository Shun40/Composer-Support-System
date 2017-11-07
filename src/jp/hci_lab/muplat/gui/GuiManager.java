package gui;
import java.util.ArrayList;
import java.util.List;

import engine.AccompanimentMaker;
import engine.AlgorithmInformation;
import engine.PredictionInformation;
import engine.PredictionPattern;
import file.FileUtil;
import file.MupFileData;
import gui.component.AlgorithmBar;
import gui.component.BpmSelector;
import gui.component.Menubar;
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
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import system.AppConstants;
import system.UIController;

/**
 * GUIコンポーネントを持つクラス
 * @author Shun Yamashita
 */
public class GuiManager extends Scene {
	private UIController uiController;

	private ResolutionSelector resolutionSelector;
	private BpmSelector bpmSelector;
	private StopButton stopButton;
	private PlayButton playButton;
	private Pianoroll pianoroll;
	private Keyboard keyboard;
	private AlgorithmBar algorithmBar;
	private PatternBar patternBar;
	private MeasureBar measureBar;
	private ScrollBar hScrollBar;
	private ScrollBar vScrollBar;

	public GuiManager(Group root, int width, int height) {
		super(root, width, height);
		uiController = new UIController();
		setupMenuBar();
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
		readWordDictionary(AppConstants.DEFAULT_WORD_DICTIONARY_FILE);
		readPhraseDictionary(AppConstants.DEFAULT_PHRASE_DICTIONARY_FILE);
	}

	public void setupMenuBar() {
		Menubar menubar = new Menubar(this);
		((Group)getRoot()).getChildren().add(menubar);
	}

	public void setupResolutionSelector() {
		resolutionSelector = new ResolutionSelector(this);
		((Group)getRoot()).getChildren().add(resolutionSelector);
	}

	public void setupBpmSelector() {
		bpmSelector = new BpmSelector(this);
		((Group)getRoot()).getChildren().add(bpmSelector);
	}

	public void setupStopButton() {
		stopButton = new StopButton(this);
		((Group)getRoot()).getChildren().add(stopButton);
	}

	public void setupPlayButton() {
		playButton = new PlayButton(this);
		((Group)getRoot()).getChildren().add(playButton);
	}

	public void setupPianoroll() {
		pianoroll = new Pianoroll(this);
		((Group)getRoot()).getChildren().add(pianoroll);
	}

	public void setupKeyboard() {
		keyboard = new Keyboard();
		((Group)getRoot()).getChildren().add(keyboard);
	}

	public void setupMeasurebar() {
		measureBar = new MeasureBar(this);
		((Group)getRoot()).getChildren().add(measureBar);
	}

	public void setupAlgorithmBar() {
		algorithmBar = new AlgorithmBar();
		((Group)getRoot()).getChildren().add(algorithmBar);
	}

	public void setupPatternBar() {
		patternBar = new PatternBar(this);
		((Group)getRoot()).getChildren().add(patternBar);
	}

	public void setupHScrollBar() {
		hScrollBar = new ScrollBar();
		hScrollBar.setOrientation(Orientation.HORIZONTAL);
		hScrollBar.setLayoutX(GuiConstants.Pianoroll.X);
		hScrollBar.setLayoutY(GuiConstants.Pianoroll.Y + GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.SHOW_OCTAVES + 1);
		hScrollBar.setPrefWidth(GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.SHOW_MEASURES + 1);
		hScrollBar.setPrefHeight(16);
		hScrollBar.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
			hTranslate();
		});
		((Group)getRoot()).getChildren().add(hScrollBar);
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
		((Group)getRoot()).getChildren().add(vScrollBar);
	}

	/**
	 * 水平方向のコンポーネント移動
	 */
	public void hTranslate() {
		double incPerUnit = -(GuiConstants.Pianoroll.MEASURE_WIDTH * (AppConstants.Settings.MEASURES - AppConstants.Settings.SHOW_MEASURES)) / 100.0;
		double scrollBarVal = hScrollBar.getValue();
		int move = (int)(incPerUnit * scrollBarVal);
		pianoroll.translateX(move);
		measureBar.translate(move);
	}

	/**
	 * 垂直方向のコンポーネント移動
	 */
	public void vTranslate() {
		double incPerUnit = -(GuiConstants.Pianoroll.MEASURE_HEIGHT * (AppConstants.Settings.OCTAVES - AppConstants.Settings.SHOW_OCTAVES)) / 100.0;
		double scrollBarVal = vScrollBar.getValue();
		int move = (int)(incPerUnit * scrollBarVal);
		pianoroll.translateY(move);
		keyboard.translate(move);
	}

	public void changeResolution(int resolution) {
		pianoroll.updateResolution(resolution);
	}

	public void stop() {
		// エンジンへの停止指示
		uiController.stop();
		// GUIへの停止指示
		pianoroll.stop();
		// 再生実行後の処理
		setupAfterPlay();
	}

	public void play() {
		// 再生実行前の処理
		setupBeforePlay();
		// エンジンへの再生指示
		uiController.play();
		// GUIへの再生指示
		pianoroll.playAnimation(bpmSelector.getBpm());
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
		hScrollBar.setValue(0.0);
	}

	public void putNote(NoteModel noteModel) {
		int track = noteModel.getTrack();
		int program = noteModel.getProgram();
		int pitch = noteModel.getPitch();
		int position = noteModel.getPosition();
		int duration = noteModel.getDuration();
		int velocity = noteModel.getVelocity();
		pianoroll.putNote(track, program, pitch, position, duration, velocity);
	}

	public void removeNoteInMeasure(int targetMeasure, int targetTrack) {
		pianoroll.removeNoteInMeasure(targetMeasure, targetTrack);
	}

	public void removeNoteIn2Beats(int targetMeasure, int targetBeat, int targetTrack) {
		pianoroll.removeNoteIn2Beats(targetMeasure, targetBeat, targetTrack);
	}

	public void addNoteToSequencer(Note note) {
		uiController.addNoteToSequencer(note);
	}

	public void removeNoteFromSequencer(Note note) {
		uiController.removeNoteFromSequencer(note);
	}

	public void clearNoteFromPianoroll() {
		pianoroll.clearNoteFromPianoroll();
	}

	public void clearNoteFromSequencer() {
		uiController.clearNoteFromSequencer();
	}

	public void makeAccompaniment(String chord, int targetMeasure, int targetBeat) {
		List<NoteModel> chordPart = AccompanimentMaker.makeChordPart(chord, targetMeasure, targetBeat);
		List<NoteModel> bassPart = AccompanimentMaker.makeBassPart(chord, targetMeasure, targetBeat);
		List<NoteModel> drumPart = AccompanimentMaker.makeDrumPart(targetMeasure, targetBeat);
		removeNoteIn2Beats(targetMeasure, targetBeat, AppConstants.AccompanimentSettings.CHORD_TRACK);
		removeNoteIn2Beats(targetMeasure, targetBeat, AppConstants.AccompanimentSettings.BASS_TRACK);
		removeNoteIn2Beats(targetMeasure, targetBeat, AppConstants.AccompanimentSettings.DRUM_TRACK);
		for(NoteModel noteModel : chordPart) {
			putNote(noteModel);
		}
		for(NoteModel noteModel : bassPart) {
			putNote(noteModel);
		}
		for(NoteModel noteModel : drumPart) {
			putNote(noteModel);
		}
		pianoroll.updateNoteView();
	}

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
		return uiController.prediction(predictionInformation, algorithmInformation);
	}

	public void setPredictionPatternList(int targetMeasure) {
		patternBar.setPredictionPatternList(targetMeasure);
	}

	public void setChordProgression(int chordProgressionIndex) {
		AppConstants.ChordProgression chordProgression = AppConstants.ChordProgression.values()[chordProgressionIndex];
		String[] chords = chordProgression.toString().split("_");
		for(int n = 0; n < chords.length; n++) {
			if(n % 2 == 0) {
				measureBar.setChord(chords[n], (n / 2) + 1, 1); // 1~2拍目
			} else {
				measureBar.setChord(chords[n], (n / 2) + 1, 3); // 3~4拍目
			}
		}
	}

	public void readMupFile() {
		MupFileData mupFileData = FileUtil.readMupFile();
		setBpmToPianoroll(mupFileData.getBpm());
		setBpmToSequencer(mupFileData.getBpm());
		clearNoteFromPianoroll();
		clearNoteFromSequencer();
		for(NoteModel noteModel : mupFileData.getNoteModels()) {
			putNote(noteModel);
		}
	}

	public void writeMupFile() {
		int bpm = getBpm();
		List<Note> notes = getNotes();
		List<NoteModel> noteModels = new ArrayList<NoteModel>();
		for(Note note : notes) {
			noteModels.add(note.getModel());
		}
		MupFileData mupFileData = new MupFileData(bpm, noteModels);
		FileUtil.writeMupFile(mupFileData);
	}

	public void writeMidiFile() {
		uiController.writeMidiFile();
	}

	public void readWordDictionary() {
		uiController.readWordDictionary();
	}

	public void readWordDictionary(String filename) {
		uiController.readWordDictionary(filename);
	}

	public void readPhraseDictionary() {
		uiController.readPhraseDictionary();
	}

	public void readPhraseDictionary(String filename) {
		uiController.readPhraseDictionary(filename);
	}

	public void showWordDictionary() {
		uiController.showWordDictionary();
	}

	public void showPhraseDictionary() {
		uiController.showPhraseDictionary();
	}

	public void incWordDictionaryFrequency(String wordId) {
		uiController.incFrequencyOfWordDictionary(wordId);
	}

	public void incPhraseDictionaryFrequency(String contextId, String wordId) {
		uiController.incFrequencyOfPhraseDictionary(contextId, wordId);
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
		return bpmSelector.getBpm();
	}

	public void setBpmToPianoroll(int bpm) {
		bpmSelector.setBpm(bpm);
	}

	public void setBpmToSequencer(int bpm) {
		uiController.setBpm(bpm);
	}

	public int getPredictionTargetMeasure() {
		return measureBar.getPredictionTarget();
	}

	public void close() {
		uiController.close();
	}
}
