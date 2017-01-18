package engine_yamashita.melody.reference;

import java.util.ArrayList;

public class RhythmPattern {
	private ArrayList<RhythmPatternData> rhythms;

	public RhythmPattern() {
		rhythms = new ArrayList<RhythmPatternData>();
	}

	public void addRhythm(int position, int duration) {
		rhythms.add(new RhythmPatternData(position, duration));
	}

	public static int calcPosition(int beat, int subPosition) {
		return 960 * (beat - 1) + (960 / 4) * (subPosition - 1);
	}

	public static int calcDuration(int separation) {
		return (960 * 4) / separation;
	}

	public ArrayList<RhythmPatternData> getRhythms() { return rhythms; }
}
