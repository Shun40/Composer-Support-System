package gui;
import java.util.ArrayList;
import java.util.List;

import engine.AlgorithmParameter;
import engine.PredictionParameter;
import engine.accompaniment.AccompanimentMaker;
import engine.melody.CandidateMelody;
import engine.melody.ChordProgression;
import engine.melody.Melody;
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
import system.AppManager;

/**
 * GUIコンポーネントを持つクラス
 * @author Shun Yamashita
 */
public class GuiManager extends Scene {
	private AppManager owner;
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

	public GuiManager(AppManager owner) {
		super(new Group(), GuiConstants.AppSize.WIDTH, GuiConstants.AppSize.HEIGHT);
		this.owner = owner;
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
		owner.stop();
		// GUIへの停止指示
		pianoroll.stop();
		// 再生実行後の処理
		setupAfterPlay();
	}

	public void play() {
		// 再生実行前の処理
		setupBeforePlay();
		// エンジンへの再生指示
		owner.play();
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

	public List<CandidateMelody> prediction() {
		// 予測変換対象小節
		int targetMeasure = getPredictionTargetMeasure();
		// メロディ
		Melody melody = new Melody();
		for(Note note : pianoroll.getNotes()) {
			if(note.getModel().getTrack() == AppConstants.MelodySettings.MELODY_TRACK) {
				melody.add(note.getModel());
			}
		}
		// コード進行
		ChordProgression chordProgression = new ChordProgression();
		for(int i = 0; i < AppConstants.Settings.MEASURES; i++) {
			chordProgression.add(measureBar.getChord(i + 1, 1)); // (i+1)小節目の1~2拍のコード
			chordProgression.add(measureBar.getChord(i + 1, 3)); // (i+1)小節目の3~4拍のコード
		}
		// 選択アルゴリズム
		List<AppConstants.Algorithm> selectedAlgorithms = algorithmBar.getSelectedAlgorithms();
		AppConstants.MelodyStructurePattern selectedMelodyStructurePattern = algorithmBar.getSelectedMelodyStructurePattern();
		// 予測変換
		PredictionParameter predictionParameter = new PredictionParameter(targetMeasure, melody, chordProgression);
		AlgorithmParameter algorithmParameter = new AlgorithmParameter(selectedAlgorithms, selectedMelodyStructurePattern);
		return owner.prediction(predictionParameter, algorithmParameter);
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
		owner.writeMidiFile();
	}

	public void readWordDictionary() {
		owner.readWordDictionary();
	}

	public void readWordDictionary(String filename) {
		owner.readWordDictionary(filename);
	}

	public void readPhraseDictionary() {
		owner.readPhraseDictionary();
	}

	public void readPhraseDictionary(String filename) {
		owner.readPhraseDictionary(filename);
	}

	public void showWordDictionary() {
		owner.showWordDictionary();
	}

	public void showPhraseDictionary() {
		owner.showPhraseDictionary();
	}

	public void incWordDictionaryFrequency(String wordId) {
		owner.incFrequencyOfWordDictionary(wordId);
	}

	public void incPhraseDictionaryFrequency(String contextId, String wordId) {
		owner.incFrequencyOfPhraseDictionary(contextId, wordId);
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
		owner.setBpmToSequencer(bpm);
	}

	public int getPredictionTargetMeasure() {
		return measureBar.getPredictionTarget();
	}
}
