import java.util.ArrayList;

import javax.sound.midi.MidiEvent;

//import java.util.HashMap;

/**
 * UIとエンジンを仲介するコントローラのクラス
 * @author Shun Yamashita
 */
public class UIController {
	private DAW daw;

	public UIController() {
		daw = new DAW();
	}

	public void setBpm(int bpm) {
		daw.SetBPM(bpm);
	}

	public void addNote(Note note) {
		int position = note.getPosition() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		int noteNumber = note.getNoteNumber();
		int duration = note.getDuration() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		daw.score.AddNote(position, noteNumber, duration);
	}

	// エンジン側で実装すべき機能だがとりあえずコントローラ内で実装
	public void removeAllNote() {
		ArrayList<MidiEvent> events = new ArrayList<MidiEvent>();
		for(int n = 0; n < daw.track[0].track_.size(); n++) {
			events.add(daw.track[0].track_.get(n));
		}
		for(MidiEvent event : events) {
			daw.track[0].track_.remove(event);
		}
	}

	public void play() {
		daw.controller.Start();
	}

	public void stop() {
		daw.controller.Stop();
	}
}
