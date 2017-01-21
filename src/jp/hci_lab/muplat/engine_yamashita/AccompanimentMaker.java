package engine_yamashita;

import java.util.ArrayList;
import java.util.Arrays;

import gui.Note;

public class AccompanimentMaker {
	public AccompanimentMaker() {
	}

	public ArrayList<Note> makePianoPart(String chord) {
		ArrayList<Note> pianoPart = new ArrayList<Note>();
		ArrayList<Integer> chordPitches = new ArrayList<Integer>();

		switch(chord) {
		case "C": chordPitches.addAll(Arrays.asList(60, 64, 67)); break;
		case "CM7": chordPitches.addAll(Arrays.asList(60, 64, 67, 71)); break;
		case "Dm": chordPitches.addAll(Arrays.asList(62, 65, 69)); break;
		case "Dm7": chordPitches.addAll(Arrays.asList(62, 65, 69, 72)); break;
		case "Em": chordPitches.addAll(Arrays.asList(64, 67, 71)); break;
		case "Em7": chordPitches.addAll(Arrays.asList(64, 67, 71, 74)); break;
		case "F": chordPitches.addAll(Arrays.asList(65, 69, 72)); break;
		case "FM7": chordPitches.addAll(Arrays.asList(65, 69, 72, 76)); break;
		case "G": chordPitches.addAll(Arrays.asList(67, 71, 74)); break;
		case "G7": chordPitches.addAll(Arrays.asList(67, 71, 74, 77)); break;
		case "Am": chordPitches.addAll(Arrays.asList(69, 72, 76)); break;
		case "Am7": chordPitches.addAll(Arrays.asList(69, 72, 76, 79)); break;
		case "Bmb5": chordPitches.addAll(Arrays.asList(71, 74, 77)); break;
		case "Bm7b5": chordPitches.addAll(Arrays.asList(71, 74, 77, 81)); break;
		}
		for(int n = 0; n < chordPitches.size(); n++) {
			pianoPart.add(new Note(1, 82, chordPitches.get(n), 0, 960, 100, null));
		}
		return pianoPart;
	}

	public ArrayList<Note> makeBassPart(String chord) {
		ArrayList<Note> bassPart = new ArrayList<Note>();
		int rootTone = 0;

		if(chord.equals("N.C.")) return bassPart;

		switch(chord) {
		case "C":
		case "CM7":
			rootTone = 48; break;
		case "Dm":
		case "Dm7":
			rootTone = 50; break;
		case "Em":
		case "Em7":
			rootTone = 52; break;
		case "F":
		case "FM7":
			rootTone = 53; break;
		case "G":
		case "G7":
			rootTone = 55; break;
		case "Am":
		case "Am7":
			rootTone = 57; break;
		case "Bmb5":
		case "Bm7b5":
			rootTone = 59; break;
		}
		for(int i = 0; i < 4; i++) {
			bassPart.add(new Note(9, 34, rootTone, 240 * i, 240, 100, null));
		}
		return bassPart;
	}

	public ArrayList<Note> makeDrumPart() {
		ArrayList<Note> drumPart = new ArrayList<Note>();

		drumPart.add(new Note(10, 1, 36, 0, 120, 100, null));
		drumPart.add(new Note(10, 1, 38, 480, 120, 100, null));
		drumPart.add(new Note(10, 1, 42, 0, 120, 100, null));
		drumPart.add(new Note(10, 1, 42, 240, 120, 100, null));
		drumPart.add(new Note(10, 1, 42, 480, 120, 100, null));
		drumPart.add(new Note(10, 1, 42, 720, 120, 100, null));
		return drumPart;
	}
}
