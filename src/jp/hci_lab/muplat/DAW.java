package jp.hci_lab.muplat;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class DAW {
	public Controller controller;
	public Mixer mixer;
	public Score score;
	public Config config;
	public DawTrack track[] = new DawTrack[16];
	
	DAW() {
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			controller = new Controller(sequencer);

			score = new Score(this);
			
		     // TicksPerBeatに480を指定
		     Sequence sequence = new Sequence(Sequence.PPQ, 480);			
		      // トラックを生成
		     for (int i = 0; i < 16; i++) {
		    	 track[i] = new DawTrack(sequence.createTrack());
		     }
		    	 
		      // シーケンスをシーケンサに追加する
		      sequencer.setSequence(sequence);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void NewProject() {
		
	}
	
	public void OpenProject() {
		
	}
	
	public void SaveProject() {
		
	}

	public void CloseProject() {
		
	}

}
