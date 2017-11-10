package engine.melody;

/**
 * エンジンによって生成された候補メロディのクラス
 * @author Shun Yamashita
 */
public class CandidateMelody extends Melody {
	/** 参照元辞書エントリのインデックス */
	private final int index;
	/** 参照元辞書エントリのID */
	private final String id;
	/** 参照元辞書エントリの選択頻度 */
	private final int frequency;
	/** GUI側でリスト表示される際の名前 */
	private final String name;

	public CandidateMelody(int index, String id, int frequency, String name) {
		super();
		this.index = index;
		this.id = id;
		this.frequency = frequency;
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public String getId() {
		return id;
	}

	public int getFrequency() {
		return frequency;
	}

	public String getName() {
		return name;
	}
}
