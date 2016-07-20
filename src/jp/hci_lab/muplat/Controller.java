package jp.hci_lab.muplat;

import javax.sound.midi.Sequencer;


public class Controller {

	private Sequencer sequencer;
	
	Controller(Sequencer seq) {
		sequencer = seq;
	}
	
	public void Start() {
		try {
	      // 演奏開始
	      sequencer.open();
	      sequencer.start();
	
	      // 標準入力でブロック
	      System.in.read();
	
	      // 終了
	      sequencer.stop();
	      sequencer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Stop() {
		
	}
	
	public void Pause() {
		
	}
	
	public void Restart() {
		
	}
	
	
}
