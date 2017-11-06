package gui;
import java.util.ArrayList;
import java.util.List;

import engine.Accompaniment;
import engine.AlgorithmInformation;
import engine.PredictionInformation;
import engine.PredictionPattern;
import file.FileUtil;
import file.MupFileData;
import gui.component.Menubar;
import gui.component.pianoroll.note.Note;
import gui.component.pianoroll.note.NoteModel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import system.AppConstants;
import system.UIController;

/**
 * GUIコンポーネントを配置するシーンのクラス
 * @author Shun Yamashita
 */
public class AppScene extends Scene {
	private UIController uiController;
	private GuiManager pianoroll;

	public AppScene(Group root, int width, int height) {
		super(root, width, height);
		uiController = new UIController();
		setupMenuBar();
		setupPianoroll();
		readWordDictionary(AppConstants.DEFAULT_WORD_DICTIONARY_FILE);
		readPhraseDictionary(AppConstants.DEFAULT_PHRASE_DICTIONARY_FILE);
	}

	public void setupMenuBar() {
		MenuBar menubar = new Menubar(this);
		((Group)getRoot()).getChildren().add(menubar);
	}

	public void setupPianoroll() {
		pianoroll = new GuiManager(this);
		((Group)getRoot()).getChildren().add(pianoroll);
	}

	public void setBpm(int bpm) {
		uiController.setBpm(bpm);
	}

	public void addNoteToSequencer(Note note) {
		uiController.addNoteToSequencer(note);
	}

	public void removeNoteFromSequencer(Note note) {
		uiController.removeNoteFromSequencer(note);
	}

	public void clearNoteFromSequencer() {
		uiController.clearNoteFromSequencer();
	}

	public void play() {
		uiController.play();
	}

	public void stop() {
		uiController.stop();
	}

	public void close() {
		uiController.close();
	}

	public List<PredictionPattern> prediction(PredictionInformation predictionInformation, AlgorithmInformation algorithmInformation) {
		return uiController.prediction(predictionInformation, algorithmInformation);
	}

	public Accompaniment makeAccompaniment(String chord) {
		return uiController.makeAccompaniment(chord);
	}

	public void setChordProgression(int index) {
		pianoroll.setChordProgression(index);
	}

	public void readMupFile() {
		MupFileData mupFileData = FileUtil.readMupFile();
		pianoroll.setBpm(mupFileData.getBpm());
		pianoroll.clearNoteFromPianoroll();
		pianoroll.clearNoteFromSequencer();
		for(NoteModel noteModel : mupFileData.getNoteModels()) {
			pianoroll.setCurrentTrack(noteModel.getTrack());
			pianoroll.putNote(noteModel.getPitch(), noteModel.getPosition(), noteModel.getDuration());
		}
	}

	public void writeMupFile() {
		int bpm = pianoroll.getBpm();
		List<Note> notes = pianoroll.getNotes();
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

	public void incFrequencyOfWordDictionary(String wordId) {
		uiController.incFrequencyOfWordDictionary(wordId);
	}

	public void incFrequencyOfPhraseDictionary(String contextId, String wordId) {
		uiController.incFrequencyOfPhraseDictionary(contextId, wordId);
	}

	public void showWordDictionary() {
		uiController.showWordDictionary();
	}

	public void showPhraseDictionary() {
		uiController.showPhraseDictionary();
	}
}
