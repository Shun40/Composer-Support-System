package engine_yamashita.melody;

import java.util.ArrayList;

public class MelodyRhythm {
	private ArrayList<MelodyRhythmTuple> rhythms;

	public MelodyRhythm() {
		rhythms = new ArrayList<MelodyRhythmTuple>();
	}

	public void addRhythm(int position, int duration) {
		rhythms.add(new MelodyRhythmTuple(position, duration));
	}

	public static int calcPosition(int beat, int subPosition) {
		return 960 * (beat - 1) + (960 / 4) * (subPosition - 1);
	}

	public static int calcDuration(int separation) {
		return (960 * 4) / separation;
	}

	public ArrayList<MelodyRhythmTuple> getRhythms() { return rhythms; }
}
