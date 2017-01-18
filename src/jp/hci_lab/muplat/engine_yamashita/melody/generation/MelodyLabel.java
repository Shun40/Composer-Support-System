package engine_yamashita.melody.generation;

import engine_yamashita.melody.reference.PitchPatternData;

public class MelodyLabel {
	private int pitch;
	private String chord;
	private int position;
	private int duration;
	private PitchPatternData pitchPatternData;

	public MelodyLabel(int pitch, String chord, int position, int duration, PitchPatternData pitchPatternData) {
		this.pitch = pitch;
		this.chord = chord;
		this.position = position;
		this.duration = duration;
		this.pitchPatternData = pitchPatternData;
	}

	public MelodyLabel(int pitch, String chord, int measure, int beat, int subPosition, int separation, PitchPatternData pitchPatternData) {
		this.pitch = pitch;
		this.chord = chord;
		this.position = (480 * 4) * (measure - 1) + 480 * (beat - 1) + 120 * (subPosition - 1);
		this.duration = (480 * 4) / separation;
		this.pitchPatternData = pitchPatternData;
	}

	public int getPitch() { return pitch; }
	public String getChord() { return chord; }
	public int getPosition() { return position; }
	public int getDuration() { return duration; }
	public PitchPatternData getPitchPatternData() { return pitchPatternData; }
	public void setPitch(int pitch) { this.pitch = pitch; }
	public void setChord(String chord) { this.chord = chord; }
	public void setPosition(int position) { this.position = position; }
	public void setDuration(int duration) { this.duration = duration; }
	public void setPitchPatternData(PitchPatternData pitchPatternData) { this.pitchPatternData = pitchPatternData; }
}
