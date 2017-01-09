package system;

import javax.sound.midi.Synthesizer;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Instrument;
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

	// publicにするか要検討
	public void AddMidiEvent(MidiEvent e) {
		track_.add(e);
	}
	
	public int GetEventCount() {
		return track_.size();
	}
	
	public void SetInstrument(int n) {
		channel_.programChange(n);
	}
	
	public List<String> GetInstrumentList() {
		List<String> names = new ArrayList<String>();
		Instrument[] list = synth_.getAvailableInstruments();
		for (Instrument inst : list) {
			names.add(inst.getName());
		}
		return names;
	}
}
