package engine_yamashita;

import java.util.ArrayList;

import gui.NoteInformation;

public class BassPattern {
	private ArrayList<NoteInformation> notes;

	public BassPattern() {
		notes = new ArrayList<NoteInformation>();
		for(int n = 0; n < 16; n++) {
			notes.add(null);
		}
	}

	public void addNote(int note, int position, int duration, int velocity) {
		NoteInformation _note = new NoteInformation(9, 1, note, position, duration, velocity, null);
		notes.set(calcIndex(position), _note);
	}

	public void removeNote(int position) {
		notes.set(calcIndex(position), null);
	}

	public ArrayList<NoteInformation> getBassPatternNotes() {
		ArrayList<NoteInformation> bassPattern = new ArrayList<NoteInformation>();
		for(int n = 0; n < 16; n++) {
			if(notes.get(n) != null) bassPattern.add(notes.get(n));
		}
		return bassPattern;
	}

	public static int calcPosition(int beat, int subPosition) {
		return 960 * (beat - 1) + (960 / 4) * (subPosition - 1);
	}

	public static int calcDuration(int separation) {
		return (960 * 4) / separation;
	}

	public static int calcIndex(int position) {
		return 4 * (position / 960) + (position % 960 / 240);
	}
}
