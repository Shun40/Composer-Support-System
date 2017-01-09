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

import engine_yamashita.ArrangePattern;
import engine_yamashita.DrumPattern;
import engine_yamashita.DrumPatternAnalyzer;
import engine_yamashita.DrumPatternParameter;
import engine_yamashita.MelodyAnalyzer;
import engine_yamashita.PredictionInformation;
import gui.NoteBlock;
import gui.NoteInformation;

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

	public void addNoteToEngine(NoteBlock noteBlock) {
		NoteInformation noteInformation = noteBlock.getNoteInformation();
		int track    = noteInformation.getTrack();
		int program  = noteInformation.getProgram();
		int note     = noteInformation.getNote();
		int position = noteInformation.getPosition() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		int duration = noteInformation.getDuration() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		int velocity = noteInformation.getVelocity();

		// 既に登録済みのイベントが編集されて渡された場合
		if(note2events.containsKey(noteBlock.hashCode())) {
			removeNoteFromEngine(noteBlock); // 登録済みのイベントを一回消してから追加し直す
		}
		try {
			// Program Change
			ShortMessage programChange = new ShortMessage();
			programChange.setMessage(ShortMessage.PROGRAM_CHANGE, track - 1, program - 1, 0);
			MidiEvent programChangeEvent = new MidiEvent(programChange, position);

			// NoteOn
			ShortMessage noteOn = new ShortMessage();
			noteOn.setMessage(ShortMessage.NOTE_ON, track - 1, note, velocity);
			MidiEvent noteOnEvent = new MidiEvent(noteOn, position);

			// NoteOff
			ShortMessage noteOff = new ShortMessage();
			noteOff.setMessage(ShortMessage.NOTE_OFF, track - 1, note, 0);
			MidiEvent noteOffEvent = new MidiEvent(noteOff, position + duration);

			sequence.getTracks()[track - 1].add(programChangeEvent);
			sequence.getTracks()[track - 1].add(noteOnEvent);
			sequence.getTracks()[track - 1].add(noteOffEvent);

			ArrayList<MidiEvent> events = new ArrayList<MidiEvent>();
			events.addAll(Arrays.asList(programChangeEvent, noteOnEvent, noteOffEvent));
			note2events.put(noteBlock.hashCode(), events);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void removeNoteFromEngine(NoteBlock noteBlock) {
		NoteInformation noteInformation = noteBlock.getNoteInformation();
		int track = noteInformation.getTrack();
		ArrayList<MidiEvent> events = note2events.get(noteBlock.hashCode());
		for(MidiEvent event : events) {
			sequence.getTracks()[track - 1].remove(event);
		}
		note2events.remove(noteBlock.hashCode());
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

	public ArrayList<ArrangePattern> prediction(ArrayList<NoteInformation> melody, PredictionInformation predictionInformation) {
		ArrayList<ArrangePattern> arrangePatterns = new ArrayList<ArrangePattern>();
		// メロディ分析
		MelodyAnalyzer melodyAnalyzer = new MelodyAnalyzer();
		ArrayList<Double> accentScores = melodyAnalyzer.getAccentScores(melody);

		// ドラムパターン分析と生成
		DrumPatternAnalyzer drumPatternAnalyzer = new DrumPatternAnalyzer();
		DrumPatternParameter drumPatternParameter = predictionInformation.getDrumPatternParameter();
		ArrayList<DrumPattern> baseDrumPatterns = drumPatternAnalyzer.getBaseDrumPattern(drumPatternParameter);
		ArrayList<DrumPattern> rhythmicalDrumPatterns = drumPatternAnalyzer.getRhythmicalDrumPattern(drumPatternParameter, baseDrumPatterns, melody, accentScores);

		for(int n = 0; n < 20; n++) {
			ArrangePattern arrangePattern = new ArrangePattern("Pattern" + (n + 1));
			arrangePattern.setDrumPattern(rhythmicalDrumPatterns.get(n));
			arrangePatterns.add(arrangePattern);
		}

		return arrangePatterns;
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
