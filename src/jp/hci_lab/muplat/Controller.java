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
//	      sequencer_.open();
	      sequencer_.start();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Stop() {
		if (sequencer_.isRunning()) {
			sequencer_.stop();
		}
		sequencer_.setTickPosition(0); // move to start point
	}
	
	public void Pause() {
		if (sequencer_.isRunning()) {
			sequencer_.stop();
		}
	}
	
	public void Restart() {
		Stop();
		Start();
	}
	
	public void Close() {
		sequencer_.close();
	}
	
}
