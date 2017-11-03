package system;

import java.util.ArrayList;

import MIDI.MIDIManager;
import engine_yamashita.Accompaniment;
import engine_yamashita.AccompanimentMaker;
import engine_yamashita.AlgorithmInformation;
import engine_yamashita.Melody;
import engine_yamashita.PredictionInformation;
import engine_yamashita.PredictionPattern;
import engine_yamashita.melody.MelodyAnalyzer;
import engine_yamashita.melody.reference.MelodyPattern;
import engine_yamashita.melody.reference.PhraseDictionary;
import engine_yamashita.melody.reference.WordDictionary;
import gui.MainScene;
import gui.Note;

/**
 * UIとエンジンを仲介するコントローラのクラス
 * @author Shun Yamashita
 */
public class UIController {
	private MIDIManager midiManager;
	private MainScene mainScene;
	private MelodyAnalyzer melodyAnalyzer;

	public UIController(MainScene mainScene) {
		midiManager = new MIDIManager();
		this.mainScene = mainScene;
		melodyAnalyzer = new MelodyAnalyzer(this);
	}

	public void setBpm(int bpm) {
		midiManager.setBpm(bpm);
	}

	public void addNoteToSequencer(Note note) {
		int id       = note.hashCode();
		int track    = note.getTrack();
		int program  = note.getProgram();
		int pitch    = note.getPitch();
		int position = note.getPosition();
		int duration = note.getDuration();
		int velocity = note.getVelocity();
		midiManager.addNote(id, track, program, pitch, position, duration, velocity);
	}

	public void removeNoteFromSequencer(Note note) {
		int id       = note.hashCode();
		int track    = note.getTrack();
		midiManager.removeNote(id, track);
	}

	public void clearNoteFromSequencer() {
		midiManager.clearNote();
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

	public void saveMidiFile() {
		midiManager.saveMidiFile();
	}

	public ArrayList<PredictionPattern> prediction(PredictionInformation predictionInformation, AlgorithmInformation algorithmInformation) {
		ArrayList<PredictionPattern> predictionPatterns = new ArrayList<PredictionPattern>();

		// メロディ分析
		ArrayList<Melody> melodies = melodyAnalyzer.getMelodies(predictionInformation, algorithmInformation);

		for(int n = 0; n < melodies.size(); n++) {
			PredictionPattern predictionPattern = new PredictionPattern();
			predictionPattern.setMelody(melodies.get(n));
			predictionPatterns.add(predictionPattern);
		}

		return predictionPatterns;
	}

	public Accompaniment makeAccompaniment(String chord) {
		Accompaniment accompaniment = new Accompaniment();
		AccompanimentMaker accompanimentMaker = new AccompanimentMaker();
		accompaniment.setPianoPart(accompanimentMaker.makePianoPart(chord));
		accompaniment.setBassPart(accompanimentMaker.makeBassPart(chord));
		accompaniment.setDrumPart(accompanimentMaker.makeDrumPart());

		return accompaniment;
	}

	public void incWordDictionaryFrequency(String wordId) {
		melodyAnalyzer.incWordDictionaryFrequency(wordId);
	}

	public void incPhraseDictionaryFrequency(String contextId, String wordId) {
		melodyAnalyzer.incPhraseDictionaryFrequency(contextId, wordId);
	}

	public void readWordDictionary(ArrayList<String> lines) {
		melodyAnalyzer.readWordDictionary(lines);
	}

	public void readPhraseDictionary(ArrayList<String> lines) {
		melodyAnalyzer.readPhraseDictionary(lines);
	}

	public WordDictionary getWordDictionary() {
		return melodyAnalyzer.getWordDictionary();
	}

	public PhraseDictionary getPhraseDictionary() {
		return melodyAnalyzer.getPhraseDictionary();
	}

	public String getNewPhraseEntryName(MelodyPattern context, MelodyPattern word) {
		return mainScene.getNewPhraseEntryName(context, word);
	}
}
