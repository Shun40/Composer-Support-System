package engine_yamashita;

import java.util.ArrayList;

import gui.NoteInformation;

public class AccompanimentMaker {
	public AccompanimentMaker() {
	}

	public ArrayList<NoteInformation> makePianoPart(String chord) {
		ArrayList<NoteInformation> pianoPart = new ArrayList<NoteInformation>();

		switch(chord) {
		case "C":
			pianoPart.add(new NoteInformation(2, 1, 60, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 64, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 67, 0, 960, 100, null));
			break;
		case "Dm":
			pianoPart.add(new NoteInformation(2, 1, 62, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 65, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 69, 0, 960, 100, null));
			break;
		case "Em":
			pianoPart.add(new NoteInformation(2, 1, 64, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 67, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 71, 0, 960, 100, null));
			break;
		case "F":
			pianoPart.add(new NoteInformation(2, 1, 65, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 69, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 72, 0, 960, 100, null));
			break;
		case "G":
			pianoPart.add(new NoteInformation(2, 1, 67, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 71, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 74, 0, 960, 100, null));
			break;
		case "Am":
			pianoPart.add(new NoteInformation(2, 1, 69, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 72, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 76, 0, 960, 100, null));
			break;
		case "Bdim":
			pianoPart.add(new NoteInformation(2, 1, 71, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 74, 0, 960, 100, null));
			pianoPart.add(new NoteInformation(2, 1, 77, 0, 960, 100, null));
			break;
		}
		return pianoPart;
	}

	public ArrayList<NoteInformation> makeBassPart(String chord) {
		ArrayList<NoteInformation> bassPart = new ArrayList<NoteInformation>();
		int rootTone = 0;

		if(chord.equals("N.C.")) return bassPart;

		switch(chord) {
		case "C": rootTone = 48; break;
		case "Dm": rootTone = 50; break;
		case "Em": rootTone = 52; break;
		case "F": rootTone = 53; break;
		case "G": rootTone = 55; break;
		case "Am": rootTone = 57; break;
		case "Bdim": rootTone = 59; break;
		}
		for(int i = 0; i < 4; i++) {
			bassPart.add(new NoteInformation(9, 34, rootTone, 240 * i, 240, 100, null));
		}
		return bassPart;
	}

	public ArrayList<NoteInformation> makeDrumPart() {
		ArrayList<NoteInformation> drumPart = new ArrayList<NoteInformation>();

		drumPart.add(new NoteInformation(10, 1, 36, 0, 120, 100, null));
		drumPart.add(new NoteInformation(10, 1, 38, 480, 120, 100, null));
		drumPart.add(new NoteInformation(10, 1, 42, 0, 120, 100, null));
		drumPart.add(new NoteInformation(10, 1, 42, 240, 120, 100, null));
		drumPart.add(new NoteInformation(10, 1, 42, 480, 120, 100, null));
		drumPart.add(new NoteInformation(10, 1, 42, 720, 120, 100, null));
		return drumPart;
	}
}
