package jp.hci_lab.muplat;

import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Track;

public class DawTrack {

	private Synthesizer synth_;
	private MidiChannel channel_;
	Track track_;
	
	DawTrack(Track track) {
		try {
			track_ = track;
			
			synth_ = MidiSystem.getSynthesizer();
			synth_.open();
			channel_ = synth_.getChannels()[0];
		} catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public void NoteOn(int note_no, int velocity) {
		channel_.noteOn(note_no, velocity);
	}
	
	public void NoteOff(int note_no) {
		channel_.noteOff(note_no);
	}

	public void AddMidiEvent(MidiEvent e) {
		track_.add(e);
	}
	
	public void SetInstrument(int n) {
		channel_.programChange(n);
	}
	
}
