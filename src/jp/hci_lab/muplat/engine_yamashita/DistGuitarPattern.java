package engine_yamashita;

import java.util.ArrayList;

import gui.NoteInformation;

public class DistGuitarPattern {
	private ArrayList<ArrayList<NoteInformation>> notes;

	public DistGuitarPattern() {
		notes = new ArrayList<ArrayList<NoteInformation>>();
		for(int n = 0; n < 16; n++) {
			notes.add(new ArrayList<NoteInformation>());
		}
	}

	public void addNote(int note, int position, int duration, int velocity) {
		NoteInformation _note = new NoteInformation(6, 1, note, position, duration, velocity, null);
		notes.get(calcIndex(position)).add(_note);
	}

	public void removeNote(int note, int position, int duration) {
		NoteInformation removeNote = null;
		for(NoteInformation _note : notes.get(calcIndex(position))) {
			if(_note.getNote() == note && _note.getPosition() == position && _note.getDuration() == duration) {
				removeNote = _note;
			}
		}
		if(removeNote != null) notes.get(calcIndex(position)).remove(removeNote);
	}

	public ArrayList<NoteInformation> getDistGuitarPatternNotes() {
		ArrayList<NoteInformation> distGuitarPattern = new ArrayList<NoteInformation>();
		for(int n = 0; n < 16; n++) {
			if(notes.get(n) == null) break;
			for(NoteInformation note : notes.get(n)) {
				distGuitarPattern.add(note);
			}
		}
		return distGuitarPattern;
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
