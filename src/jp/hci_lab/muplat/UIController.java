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
		int trackNumber = note.getNoteInformation().getTrackNumber();
		int progNumber  = note.getNoteInformation().getProgNumber();
		int noteNumber  = note.getNoteInformation().getNoteNumber();
		int position    = note.getNoteInformation().getPosition() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		int duration    = note.getNoteInformation().getDuration() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		int velocity    = note.getNoteInformation().getVelocity();

		// 既に登録済みのイベントが編集されて渡された場合
		if(note2events.containsKey(note.hashCode())) {
			removeNoteFromEngine(note); // 登録済みのイベントを一回消してから追加し直す
		}
		try {
			// Program Change
			ShortMessage progChange = new ShortMessage();
			progChange.setMessage(ShortMessage.PROGRAM_CHANGE, trackNumber - 1, progNumber - 1, 0);
			MidiEvent progChangeEvent = new MidiEvent(progChange, position);

			// NoteOn
			ShortMessage noteOn = new ShortMessage();
			noteOn.setMessage(ShortMessage.NOTE_ON, trackNumber - 1, noteNumber, velocity);
			MidiEvent noteOnEvent = new MidiEvent(noteOn, position);

			// NoteOff
			ShortMessage noteOff = new ShortMessage();
			noteOff.setMessage(ShortMessage.NOTE_OFF, trackNumber - 1, noteNumber, 0);
			MidiEvent noteOffEvent = new MidiEvent(noteOff, position + duration);

			sequence.getTracks()[trackNumber - 1].add(progChangeEvent);
			sequence.getTracks()[trackNumber - 1].add(noteOnEvent);
			sequence.getTracks()[trackNumber - 1].add(noteOffEvent);

			ArrayList<MidiEvent> events = new ArrayList<MidiEvent>();
			events.addAll(Arrays.asList(progChangeEvent, noteOnEvent, noteOffEvent));
			note2events.put(note.hashCode(), events);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void removeNoteFromEngine(Note note) {
		int trackNumber = note.getNoteInformation().getTrackNumber();
		ArrayList<MidiEvent> events = note2events.get(note.hashCode());
		for(MidiEvent event : events) {
			sequence.getTracks()[trackNumber - 1].remove(event);
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
