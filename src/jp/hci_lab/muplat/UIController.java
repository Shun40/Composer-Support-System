import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;

//import java.util.HashMap;

/**
 * UIとエンジンを仲介するコントローラのクラス
 * @author Shun Yamashita
 */
public class UIController {
	//private DAW daw;
	private Sequencer sequencer;
	private Sequence sequence;
	private HashMap<Note, ArrayList<MidiEvent>> note2events;

	public UIController() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 480);
			sequence.createTrack();
			sequencer.setSequence(sequence);
		} catch(Exception e) {
			e.printStackTrace();
		}
		note2events = new HashMap<Note, ArrayList<MidiEvent>>();
	}

	public void setBpm(int bpm) {
		sequencer.setTempoInBPM((float)bpm);
	}

	public void addNote(Note note) {
		int channel     = note.getNoteInformation().getChannel();
		int progNumber  = note.getNoteInformation().getProgNumber();
		int noteNumber  = note.getNoteInformation().getNoteNumber();
		int position    = note.getNoteInformation().getPosition() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		int duration    = note.getNoteInformation().getDuration() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		int velocity    = note.getNoteInformation().getVelocity();
		try {
			// Program Change
			ShortMessage progChange = new ShortMessage();
			progChange.setMessage(ShortMessage.PROGRAM_CHANGE, channel - 1, progNumber - 1, 0);
			MidiEvent progChangeEvent = new MidiEvent(progChange, position);

			// NoteOn
			ShortMessage noteOn = new ShortMessage();
			noteOn.setMessage(ShortMessage.NOTE_ON, channel - 1, noteNumber, velocity);
			MidiEvent noteOnEvent = new MidiEvent(noteOn, position);

			// NoteOff
			ShortMessage noteOff = new ShortMessage();
			noteOff.setMessage(ShortMessage.NOTE_OFF, channel - 1, noteNumber, 0);
			MidiEvent noteOffEvent = new MidiEvent(noteOff, position + duration);

			sequence.getTracks()[0].add(progChangeEvent);
			sequence.getTracks()[0].add(noteOnEvent);
			sequence.getTracks()[0].add(noteOffEvent);

			ArrayList<MidiEvent> events = new ArrayList<MidiEvent>();
			events.addAll(Arrays.asList(progChangeEvent, noteOnEvent, noteOffEvent));
			note2events.put(note, events);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void removeNote(Note note) {
		ArrayList<MidiEvent> events = note2events.get(note);
		for(MidiEvent event : events) {
			sequence.getTracks()[0].remove(event);
		}
		note2events.remove(note);
	}

	public void removeAllNote() {
		ArrayList<MidiEvent> events = new ArrayList<MidiEvent>();
		for(int n = 0; n < sequence.getTracks()[0].size(); n++) {
			events.add(sequence.getTracks()[0].get(n));
		}
		for(MidiEvent event : events) {
			sequence.getTracks()[0].remove(event);
		}
		note2events.clear();
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
