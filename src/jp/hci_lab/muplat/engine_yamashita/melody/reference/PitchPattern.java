package engine_yamashita.melody.reference;

import java.util.ArrayList;

public class PitchPattern {
	private ArrayList<PitchPatternData> pitches;

	public PitchPattern() {
		pitches = new ArrayList<PitchPatternData>();
	}

	public void addPitch(int variation, int difference, int place) {
		pitches.add(new PitchPatternData(variation, difference, place));
	}

	public ArrayList<PitchPatternData> getPitches() { return pitches; }
}
