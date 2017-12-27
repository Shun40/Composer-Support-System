package engine.melody.generation;

import engine.melody.RelativeNote;

/**
 * 候補メロディ生成時に使うラベル（音符の情報をまとめたもの）のクラス
 * @author Shun Yamashita
 */
public class CandidateLabel extends RelativeNote {
	private int pitch;
	private String chord;

	public CandidateLabel(int difference, int position, int duration, int pitch, String chord) {
		super(difference, position, duration);
		this.pitch = pitch;
		this.chord = chord;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public String getChord() {
		return chord;
	}

	public void setChord(String chord) {
		this.chord = chord;
	}
}
