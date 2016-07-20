package jp.hci_lab.muplat;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;

public class Score {

	private DAW daw_;
	private int current_track_;
	private int current_velocity_;
	
	Score(DAW daw) {
		daw_ = daw;
		current_track_ = 0;
		current_velocity_ = 127;
	}
	
	public void SetTrack(int track_no) {
		current_track_ = track_no;
	}
	
	public void AddNote(int position, int note_no, int duration) {
		try {
			// ノートオンイベントの生成
		      ShortMessage noteOn = new ShortMessage();
		      noteOn.setMessage(ShortMessage.NOTE_ON, note_no, current_velocity_);
		      MidiEvent e1 = new MidiEvent(noteOn, position);
	
		      // ノートオフイベントの生成
		      ShortMessage noteOff = new ShortMessage();
		      noteOff.setMessage(ShortMessage.NOTE_OFF, note_no, 0);
		      MidiEvent e2 = new MidiEvent(noteOff, position + duration);
	
		      // イベントをトラックに追加する
		      daw_.track[current_track_].AddMidiEvent(e1);
		      daw_.track[current_track_].AddMidiEvent(e2);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
