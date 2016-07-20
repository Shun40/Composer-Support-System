package jp.hci_lab.muplat;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class DAW {
	public Controller controller;
	public Mixer mixer;
	public Score score;
	public Config config;
	
	DAW() {
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			controller = new Controller(sequencer);

		     // TicksPerBeatに480を指定
		     Sequence sequence = new Sequence(Sequence.PPQ, 480);			
			
		      // トラックを生成
		      Track track = sequence.createTrack();

		      // ノートオンイベントの生成
		      ShortMessage noteOn = new ShortMessage();
		      noteOn.setMessage(ShortMessage.NOTE_ON, 60, 127);
		      MidiEvent e1 = new MidiEvent(noteOn, 480);

		      // ノートオフイベントの生成
		      ShortMessage noteOff = new ShortMessage();
		      noteOff.setMessage(ShortMessage.NOTE_OFF, 60, 0);
		      MidiEvent e2 = new MidiEvent(noteOff, 960);

		      // イベントをトラックに追加する
		      track.add(e1);
		      track.add(e2);

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
