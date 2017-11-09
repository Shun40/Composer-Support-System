package engine.melody;

import java.util.ArrayList;

/**
 * 相対メロディのクラス
 * @author Shun Yamashita
 */
public class RelativeMelody extends ArrayList<RelativeNote> {
	private final String id;

	public RelativeMelody(String id) {
		super();
		this.id = id;
	}

	/*
	public void add(int variation, int difference, int position, int duration) {
		add(new RelativeNote(variation, difference, position, duration));
	}
	*/

	public String getId() {
		return id;
	}
}
