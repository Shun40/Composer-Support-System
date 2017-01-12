package engine_yamashita.melody;

import java.util.ArrayList;

public class MelodyPitch {
	private ArrayList<Integer> pitches;

	public MelodyPitch() {
		pitches = new ArrayList<Integer>();
	}

	public MelodyPitch(String firstLevel, String secondLevel, String thirdLevel, String fourthLevel) {
		pitches = new ArrayList<Integer>();
		addPitch(firstLevel, secondLevel, thirdLevel, fourthLevel);
	}

	public void addPitch(String firstLevel, String secondLevel, String thirdLevel, String fourthLevel) {
		switch(firstLevel) {
		case "low": pitches.add(0); break;
		case "mid": pitches.add(6); break;
		case "high": pitches.add(12); break;
		}
		switch(secondLevel) {
		case "low": pitches.add(0); break;
		case "mid": pitches.add(6); break;
		case "high": pitches.add(12); break;
		}
		switch(thirdLevel) {
		case "low": pitches.add(0); break;
		case "mid": pitches.add(6); break;
		case "high": pitches.add(12); break;
		}
		switch(fourthLevel) {
		case "low": pitches.add(0); break;
		case "mid": pitches.add(6); break;
		case "high": pitches.add(12); break;
		}
	}

	public ArrayList<Integer> getPitches() { return pitches; }
}
