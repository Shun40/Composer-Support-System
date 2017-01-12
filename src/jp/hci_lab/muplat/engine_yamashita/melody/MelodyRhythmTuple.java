package engine_yamashita.melody;

public class MelodyRhythmTuple {
	private int position;
	private int duration;

	public MelodyRhythmTuple(int position, int duration) {
		this.position = position;
		this.duration = duration;
	}

	public int getPosition() { return position; }
	public int getDuration() { return duration; }
}
