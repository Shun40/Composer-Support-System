package system;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import engine_yamashita.Accompaniment;
import engine_yamashita.AccompanimentMaker;
import engine_yamashita.Melody;
import engine_yamashita.PredictionInformation;
import engine_yamashita.PredictionPattern;
import engine_yamashita.melody.MelodyAnalyzer;
import gui.Note;

/**
 * UIとエンジンを仲介するコントローラのクラス
 * @author Shun Yamashita
 */
public class UIController {
	//private DAW daw;
	private Sequencer sequencer;
	private Sequence sequence;
	private HashMap<Object, ArrayList<MidiEvent>> note2events;

	public UIController() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 480);
			for(int t = 0; t < 16; t++) {
				sequence.createTrack();
			}
			sequencer.setSequence(sequence);
		} catch(Exception e) {
			e.printStackTrace();
		}
		note2events = new HashMap<Object, ArrayList<MidiEvent>>();
	}

	public void setBpm(int bpm) {
		try {
			MetaMessage bpmChange = new MetaMessage();
			int l = 60 * 1000000 / bpm;
			bpmChange.setMessage(0x51, new byte[]{ (byte)(l / 65536), (byte)(l % 65536 / 256), (byte)(l % 256) }, 3);
			MidiEvent bpmChangeEvent = new MidiEvent(bpmChange, 0);
			for(Track track : sequence.getTracks()) {
				track.add(bpmChangeEvent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addNoteToEngine(Note note) {
		int track    = note.getTrack();
		int program  = note.getProgram();
		int pitch    = note.getPitch();
		int position = note.getPosition();
		int duration = note.getDuration();
		int velocity = note.getVelocity();

		// 既に登録済みのイベントが編集されて渡された場合
		if(note2events.containsKey(note.hashCode())) {
			removeNoteFromEngine(note); // 登録済みのイベントを一回消してから追加し直す
		}
		try {
			// Program Change
			ShortMessage programChange = new ShortMessage();
			programChange.setMessage(ShortMessage.PROGRAM_CHANGE, track - 1, program - 1, 0);
			MidiEvent programChangeEvent = new MidiEvent(programChange, position);

			// NoteOn
			ShortMessage noteOn = new ShortMessage();
			noteOn.setMessage(ShortMessage.NOTE_ON, track - 1, pitch, velocity);
			MidiEvent noteOnEvent = new MidiEvent(noteOn, position);

			// NoteOff
			ShortMessage noteOff = new ShortMessage();
			noteOff.setMessage(ShortMessage.NOTE_OFF, track - 1, pitch, 0);
			MidiEvent noteOffEvent = new MidiEvent(noteOff, position + duration);

			sequence.getTracks()[track - 1].add(programChangeEvent);
			sequence.getTracks()[track - 1].add(noteOnEvent);
			sequence.getTracks()[track - 1].add(noteOffEvent);

			ArrayList<MidiEvent> events = new ArrayList<MidiEvent>();
			events.addAll(Arrays.asList(programChangeEvent, noteOnEvent, noteOffEvent));
			note2events.put(note.hashCode(), events);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void removeNoteFromEngine(Note note) {
		int track = note.getTrack();
		ArrayList<MidiEvent> events = note2events.get(note.hashCode());
		for(MidiEvent event : events) {
			sequence.getTracks()[track - 1].remove(event);
		}
		note2events.remove(note.hashCode());
	}

	public void clearNoteFromEngine() {
		for(Track track : sequence.getTracks()) {
			ArrayList<MidiEvent> events = new ArrayList<MidiEvent>();
			for(int n = 0; n < track.size(); n++) {
				events.add(track.get(n));
			}
			for(MidiEvent event : events) {
				track.remove(event);
			}
		}
		note2events.clear();
	}

	public void setTrackMute(int track, boolean mute) {
		sequencer.setTrackMute(track - 1, mute);
	}

	public void setTrackSolo(int track, boolean solo) {
		sequencer.setTrackSolo(track - 1, solo);
	}

	public void play() {
		try {
			sequencer.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		if(sequencer.isRunning()) {
			sequencer.stop();
		}
		sequencer.setTickPosition(0);
	}

	public void close() {
		sequencer.close();
	}

	public ArrayList<PredictionPattern> prediction(PredictionInformation predictionInformation) {
		ArrayList<PredictionPattern> predictionPatterns = new ArrayList<PredictionPattern>();

		// メロディ分析
		MelodyAnalyzer melodyAnalyzer = new MelodyAnalyzer();
		ArrayList<Melody> melodies = melodyAnalyzer.getMelodies(predictionInformation);

		for(int n = 0; n < melodies.size(); n++) {
			PredictionPattern predictionPattern = new PredictionPattern(melodies.get(n).getName());
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

	public Sequence getSequence() { return sequence; }

	/*
	public UIController() {
		daw = new DAW();
	}

	public void setBpm(int bpm) {
		daw.SetBPM(bpm);
	}

	public void addNote(Note note) {
		int trackNumber = note.getNoteInformation().getTrackNumber();
		int progNumber  = note.getNoteInformation().getProgNumber();
		int noteNumber  = note.getNoteInformation().getNoteNumber();
		int position    = note.getNoteInformation().getPosition() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		int duration    = note.getNoteInformation().getDuration() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		try {
			ShortMessage progChange = new ShortMessage();
			progChange.setMessage(ShortMessage.PROGRAM_CHANGE, 0, progNumber, 0);
			MidiEvent event = new MidiEvent(progChange, 1);
			daw.track[trackNumber].AddMidiEvent(event);
		} catch(Exception e) {
		}
		daw.score.SetTrack(trackNumber);
		daw.score.AddNote(position, noteNumber, duration);
		System.out.println(progNumber + ", " + trackNumber);
	}

	// エンジン側で実装すべき機能だがとりあえずコントローラ内で実装
	public void removeAllNote() {
		ArrayList<ArrayList<MidiEvent>> allTrackEvents = new ArrayList<ArrayList<MidiEvent>>();
		for(int t = 0; t < 16; t++) {
			ArrayList<MidiEvent> trackEvents = new ArrayList<MidiEvent>();
			for(int n = 0; n < daw.track[t].track_.size(); n++) {
				trackEvents.add(daw.track[t].track_.get(n));
			}
			allTrackEvents.add(trackEvents);
		}
		for(int t = 0; t < 16; t++) {
			for(MidiEvent event : allTrackEvents.get(t)) {
				daw.track[t].track_.remove(event);
			}
		}
	}

	public void play() {
		daw.controller.Start();
	}

	public void stop() {
		daw.controller.Stop();
	}
	*/
}
