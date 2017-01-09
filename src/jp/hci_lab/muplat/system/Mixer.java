package system;

import javax.sound.midi.Sequencer;

public class Mixer {
	Sequencer seq_;
	
	Mixer(Sequencer seq) {
		seq_ = seq;
	}

	public void SetMasterLevel(int level) {
		
	}
		
	public void SetLevel(int tr, int level) {
		
	}

	public void SetPan(int tr, int pan) {
		
	}
	
	public void SetSolo(int tr, boolean solo) {
		seq_.setTrackSolo(tr,  solo);
	}

	public void SetMute(int tr, boolean mute) {
		seq_.setTrackMute(tr,  mute);		
	}
}
