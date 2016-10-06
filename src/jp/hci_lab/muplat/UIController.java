//import java.util.HashMap;

import java.util.HashMap;

/**
 * UIとエンジンを仲介するコントローラのクラス
 * @author Shun Yamashita
 */
public class UIController {
	private static final HashMap<String, Integer> midiNumbers = new HashMap<String, Integer>() {
		{put("C", 0);} {put("C#", 1);} {put("D", 2);} {put("D#", 3);} {put("E", 4);}
		{put("F", 5);} {put("F#", 6);} {put("G", 7);} {put("G#", 8);} {put("A", 9);} {put("A#", 10);} {put("B", 11);}
	};

	private DAW daw;

	public UIController() {
		daw = new DAW();
	}

	public void setBpm(int bpm) {
		daw.SetBPM(bpm);
	}

	public void addNote(Note note) {
		int position = ((960 * 4) * (note.getMeasure() - 1) + 960 * (note.getBeat() - 1) + note.getPlace()) / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		int noteNo = 60 + (12 * (note.getOctave() - 4)) + midiNumbers.get(note.getInterval());
		int duration = note.getDuration() / 2; // 4分音符 = 960 tickで計算しているため1/2しておく
		daw.score.AddNote(position, noteNo, duration);
	}

	public void play() {
		daw.controller.Start();
	}

	public void stop() {
		daw.controller.Stop();
	}
}
