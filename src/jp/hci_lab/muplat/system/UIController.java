package system;

import java.util.ArrayList;
import java.util.List;

import engine.AlgorithmInformation;
import engine.Melody;
import engine.PredictionInformation;
import engine.PredictionPattern;
import engine.melody.MelodyAnalyzer;
import gui.component.pianoroll.note.Note;
import midi.MidiManager;

/**
 * UIとエンジンを仲介するコントローラのクラス
 * @author Shun Yamashita
 */
public class UIController {
	private MidiManager midiManager;
	private MelodyAnalyzer melodyAnalyzer;

	public UIController() {
		midiManager = new MidiManager();
		melodyAnalyzer = new MelodyAnalyzer();
	}

	public void setBpm(int bpm) {
		midiManager.setBpm(bpm);
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

	public void play() {
		midiManager.play();
	}

	public void stop() {
		midiManager.stop();
	}

	public void close() {
		midiManager.close();
	}

	public void writeMidiFile() {
		midiManager.writeMidiFile();
	}

	public List<PredictionPattern> prediction(PredictionInformation predictionInformation, AlgorithmInformation algorithmInformation) {
		List<PredictionPattern> predictionPatterns = new ArrayList<PredictionPattern>();

		// メロディ分析
		List<Melody> melodies = melodyAnalyzer.getMelodies(predictionInformation, algorithmInformation);

		for(int n = 0; n < melodies.size(); n++) {
			PredictionPattern predictionPattern = new PredictionPattern();
			predictionPattern.setMelody(melodies.get(n));
			predictionPatterns.add(predictionPattern);
		}

		return predictionPatterns;
	}

	public void readWordDictionary() {
		melodyAnalyzer.readWordDictionary();
	}

	public void readWordDictionary(String filename) {
		melodyAnalyzer.readWordDictionary(filename);
	}

	public void readPhraseDictionary() {
		melodyAnalyzer.readPhraseDictionary();
	}

	public void readPhraseDictionary(String filename) {
		melodyAnalyzer.readPhraseDictionary(filename);
	}

	public void incFrequencyOfWordDictionary(String wordId) {
		melodyAnalyzer.incFrequencyOfWordDictionary(wordId);
	}

	public void incFrequencyOfPhraseDictionary(String contextId, String wordId) {
		melodyAnalyzer.incFrequencyOfPhraseDictionary(contextId, wordId);
	}

	public void showWordDictionary() {
		melodyAnalyzer.showWordDictionary();
	}

	public void showPhraseDictionary() {
		melodyAnalyzer.showPhraseDictionary();
	}
}
