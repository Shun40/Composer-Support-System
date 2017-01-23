package engine_yamashita.melody.reference;

import java.util.ArrayList;

public class MelodyPattern extends ArrayList<MelodyPatternData> {
	private String id;

	public MelodyPattern(String id) {
		super();
		this.id = id;
	}

	public void add(int variation, int difference, int position, int duration) {
		add(new MelodyPatternData(variation, difference, position, duration));
	}

	public String getId() { return id; }
}
