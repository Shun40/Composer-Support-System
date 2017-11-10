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

	public String getId() {
		return id;
	}
}
