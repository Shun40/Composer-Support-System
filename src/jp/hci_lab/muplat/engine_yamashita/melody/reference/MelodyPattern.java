package engine_yamashita.melody.reference;

import java.util.ArrayList;

public class MelodyPattern extends ArrayList<MelodyPatternData> {
	public MelodyPattern() {
		super();
	}

	public void add(int variation, int difference, int position, int duration) {
		add(new MelodyPatternData(variation, difference, position, duration));
	}
}
