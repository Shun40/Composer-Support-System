package engine_yamashita.melody.reference;

public class PitchPatternData {
	private int variation; // 前の音符と比べた音高の変化(-1 : 下降, 0 : 不変, 1 : 上昇)
	private int difference; // 音高変化値
	private int place; // この音符の音高値

	public PitchPatternData(int variation, int difference, int place) {
		this.variation = variation;
		this.difference = difference;
		this.place = place;
	}

	public int getVariation() { return variation; }
	public int getDifference() { return difference; }
	public int getPlace() { return place; }
}
