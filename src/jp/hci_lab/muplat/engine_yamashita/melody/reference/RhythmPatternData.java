package engine_yamashita.melody.reference;

public class RhythmPatternData {
	private int position;
	private int duration;

	public RhythmPatternData(int position, int duration) {
		this.position = position;
		this.duration = duration;
	}

	public int getPosition() { return position; }
	public int getDuration() { return duration; }
}
