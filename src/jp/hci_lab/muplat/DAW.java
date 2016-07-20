package jp.hci_lab.muplat;

import java.io.FileInputStream;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class DAW {
	public Controller controller;
	public Mixer mixer;
	public Score score;
	public Config config;
	public DawTrack track[] = new DawTrack[16];
	private Sequencer sequencer_;
	
	DAW() {
		try {
			sequencer_ = MidiSystem.getSequencer();
			controller = new Controller(sequencer_);
			config = new Config();

			score = new Score(this);
			
		     // TicksPerBeatに480を指定
		     Sequence sequence = new Sequence(Sequence.PPQ, 480);			
		      // トラックを生成
		     for (int i = 0; i < 16; i++) {
		    	 track[i] = new DawTrack(sequence.createTrack());
		     }
		    	 
		      // シーケンスをシーケンサに追加する
		      sequencer_.setSequence(sequence);
		      sequencer_.open();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void Close() {
		controller.Close();
	}

	public void PlayMidiFile(String filename) {
		try {
		    FileInputStream in=new FileInputStream(filename);
		    Sequence sequence=MidiSystem.getSequence(in);
		    in.close();//ファイルをクローズ
		    sequencer_.setSequence(sequence);//音源セット
		    sequencer_.start();//再生開始							
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void SetBPM(float bpm) {
		sequencer_.setTempoInBPM(bpm);
	}
	public void SetBPM(int bpm) {
		sequencer_.setTempoInBPM((float)bpm);
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
