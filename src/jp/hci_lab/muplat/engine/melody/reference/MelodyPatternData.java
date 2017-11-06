package engine.melody.reference;

public class MelodyPatternData {
	private int variation; // 音高に関するデータ
	private int difference; // 音高に関するデータ
	private int position; // リズムに関するデータ
	private int duration; // リズムに関するデータ

	public MelodyPatternData(int variation, int difference, int position, int duration) {
		this.variation = variation;
		this.difference = difference;
		this.position = position;
		this.duration = duration;
	}

	public int getVariation() { return variation; }
	public int getDifference() { return difference; }
	public int getPosition() { return position; }
	public int getDuration() { return duration; }
}
