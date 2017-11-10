package engine.melody;

import java.util.ArrayList;

/**
 * コード進行のクラス
 * @author Shun Yamashita
 */
public class ChordProgression extends ArrayList<String> {
	public ChordProgression() {
		super();
	}

	/**
	 * 指定した小節に含まれるコード進行を返す
	 * @param measure 小節
	 * @return 指定小節に含まれるコード
	 */
	public ChordProgression getIncludedInMeasure(int measure) {
		ChordProgression chordProgression = new ChordProgression();
		if(measure > 0) {
			chordProgression.add(get((measure - 1) * 2 + 0)); // 1~2拍目
			chordProgression.add(get((measure - 1) * 2 + 1)); // 3~4拍目
		}
		return chordProgression;
	}

	/**
	 * 最後のコードを返す
	 * @return 最後のコード
	 */
	public String getLast() {
		if(isEmpty()) {
			return "N.C.";
		} else {
			return get(size() - 1);
		}
	}
}
