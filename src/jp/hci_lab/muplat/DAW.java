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
	private Sequence sequence_;
	
	DAW() {
		try {
			sequencer_ = MidiSystem.getSequencer();
			controller = new Controller(sequencer_);
			config = new Config();
			score = new Score(this);

			NewProject();
		    sequencer_.open();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void Close() {
		controller.Close();
	}

	public void SetBPM(float bpm) {
		sequencer_.setTempoInBPM(bpm);
	}
	public void SetBPM(int bpm) {
		sequencer_.setTempoInBPM((float)bpm);
	}
	
	public void NewProject() {
		try {
			// TicksPerBeatに480を指定
			sequence_ = new Sequence(Sequence.PPQ, 480);			
			// トラックを生成
			for (int i = 0; i < 16; i++) {
				track[i] = new DawTrack(sequence_.createTrack());
			}
			// シーケンスをシーケンサに追加する
			sequencer_.setSequence(sequence_);		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void OpenProject(String filename) {
		try {
		    FileInputStream in = new FileInputStream(filename);
		    sequence_ = MidiSystem.getSequence(in);
		    in.close();//ファイルをクローズ
		    sequencer_.setSequence(sequence_);
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void SaveProject(String filename) {
		try {
			MidiSystem.write(sequence_, 1, new java.io.File(filename));		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void CloseProject() {
		NewProject();
	}

}
