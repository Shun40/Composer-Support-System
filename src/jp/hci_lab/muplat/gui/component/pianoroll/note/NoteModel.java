package gui.component.pianoroll.note;

/**
 * ノートの情報のクラス
 * @author Shun Yamashita
 */
public class NoteModel {
	/** トラック番号（1 .. 10） */
	private int track;
	/** プログラム番号（1 .. 128） */
	private int program;
	/** ノート番号（0 .. 127） */
	private int pitch;
	/** 発音位置 */
	private int position;
	/** 発音時間長 */
	private int duration;
	/** ベロシティ（0 .. 127） */
	private int velocity;

	public NoteModel(int track, int program, int pitch, int position, int duration, int velocity) {
		this.track = track;
		this.program = program;
		this.pitch = pitch;
		this.position = position;
		this.duration = duration;
		this.velocity = velocity;
	}

	public int getTrack() {
		return track;
	}

	public int getProgram() {
		return program;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getVelocity() {
		return velocity;
	}
}
