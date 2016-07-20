package jp.hci_lab.muplat;

import javax.sound.midi.Sequencer;


public class Controller {

	private Sequencer sequencer_;
	
	Controller(Sequencer seq) {
		sequencer_ = seq;
	}
	
	public void Start() {
		try {
	      // 演奏開始
	      sequencer_.open();
	      sequencer_.start();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Stop() {
		// 終了
		sequencer_.stop();
		sequencer_.close();		
	}
	
	public void Pause() {
		
	}
	
	public void Restart() {
		
	}
	
	
}
