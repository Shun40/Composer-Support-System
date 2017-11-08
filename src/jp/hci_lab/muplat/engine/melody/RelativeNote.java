package engine.melody;

/**
 * 相対音高とリズムから構成される相対音符のクラス
 * @author Shun Yamashita
 */
public class RelativeNote {
	private final int variation; // 音高に関するデータ
	private final int difference; // 音高に関するデータ
	private final int position; // リズムに関するデータ
	private final int duration; // リズムに関するデータ

	public RelativeNote(int variation, int difference, int position, int duration) {
		this.variation = variation;
		this.difference = difference;
		this.position = position;
		this.duration = duration;
	}

	public int getVariation() {
		return variation;
	}

	public int getDifference() {
		return difference;
	}

	public int getPosition() {
		return position;
	}

	public int getDuration() {
		return duration;
	}
}
