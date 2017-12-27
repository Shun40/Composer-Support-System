package system;

import java.util.List;

import engine.AlgorithmParameter;
import engine.EngineManager;
import engine.PredictionParameter;
import engine.melody.CandidateMelody;
import gui.GuiManager;
import gui.component.pianoroll.note.Note;
import javafx.application.Platform;
import javafx.stage.Stage;
import midi.MidiManager;
import midi.Synth;

/**
 * エンジンやGUIを管理し橋渡しをするクラス
 * @author Shun Yamashita
 */
public class AppManager {
	private EngineManager engineManager;
	private GuiManager guiManager;
	private MidiManager midiManager;

	public AppManager(Stage stage, AppConstants.Version version) {
		// GUIはエンジンの情報を参照するので初期化はエンジン→GUIの順で
		engineManager = new EngineManager(version);
		guiManager = new GuiManager(this);
		midiManager = new MidiManager();

		// シンセサイザーの初期化
		new Synth();

		// 辞書データの読み込み
		readWordDictionary(AppConstants.DEFAULT_WORD_DICTIONARY_FILE);
		readPhraseDictionary(AppConstants.DEFAULT_PHRASE_DICTIONARY_FILE);

		stage.setTitle(AppConstants.APP_NAME);
		stage.setResizable(false);
		stage.setOnCloseRequest(req -> close());
		stage.setScene(guiManager);
		stage.show();
	}

	public void setBpmToSequencer(int bpm) {
		midiManager.setBpmToSequencer(bpm);
	}

	public void addNoteToSequencer(Note note) {
		int id = note.hashCode();
		int track = note.getModel().getTrack();
		int program = note.getModel().getProgram();
		int pitch = note.getModel().getPitch();
		int position = note.getModel().getPosition();
		int duration = note.getModel().getDuration();
		int velocity = note.getModel().getVelocity();
		midiManager.addNoteToSequencer(id, track, program, pitch, position, duration, velocity);
	}

	public void removeNoteFromSequencer(Note note) {
		int id = note.hashCode();
		int track = note.getModel().getTrack();
		midiManager.removeNoteFromSequencer(id, track);
	}

	public void clearNoteFromSequencer() {
		midiManager.clearNoteFromSequencer();
	}

	public void play(int playPosition) {
		midiManager.play(playPosition);
	}

	public void stop() {
		midiManager.stop();
	}

	public void writeMidiFile() {
		midiManager.writeMidiFile();
	}

	public List<CandidateMelody> prediction(PredictionParameter predictionParameter, AlgorithmParameter algorithmParameter) {
		// メロディ分析
		return engineManager.makeCandidateMelodies(predictionParameter, algorithmParameter);
	}

	public void readWordDictionary() {
		engineManager.readWordDictionary();
	}

	public void readWordDictionary(String filename) {
		engineManager.readWordDictionary(filename);
	}

	public void readPhraseDictionary() {
		engineManager.readPhraseDictionary();
	}

	public void readPhraseDictionary(String filename) {
		engineManager.readPhraseDictionary(filename);
	}

	public void incFrequencyOfWordDictionary(String wordId) {
		engineManager.incFrequencyOfWordDictionary(wordId);
	}

	public void incFrequencyOfPhraseDictionary(String contextId, String wordId) {
		engineManager.incFrequencyOfPhraseDictionary(contextId, wordId);
	}

	public void showWordDictionary() {
		engineManager.showWordDictionary();
	}

	public void showPhraseDictionary() {
		engineManager.showPhraseDictionary();
	}

	/**
	 * アプリケーション終了時に実行される
	 */
	private void close() {
		midiManager.close();
		Synth.close();
		Platform.exit();
	}
}
