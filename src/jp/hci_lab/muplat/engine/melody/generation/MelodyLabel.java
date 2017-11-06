package engine.melody.generation;

import midi.MidiConstants;

public class MelodyLabel {
	private int pitch;
	private String chord;
	private int variation;
	private int difference;
	private int position;
	private int duration;

	public MelodyLabel(int pitch, String chord, int variation, int difference, int position, int duration) {
		this.pitch = pitch;
		this.chord = chord;
		this.variation = variation;
		this.difference = difference;
		this.position = position;
		this.duration = duration;
	}

	public MelodyLabel(int pitch, String chord, int variation, int difference, int measure, int beat, int subPosition, int separation) {
		this.pitch = pitch;
		this.chord = chord;
		this.variation = variation;
		this.difference = difference;
		this.position = (MidiConstants.PPQ * 4) * (measure - 1) + MidiConstants.PPQ * (beat - 1) + (MidiConstants.PPQ / 4) * (subPosition - 1);
		this.duration = (MidiConstants.PPQ * 4) / separation;
	}

	public int getPitch() { return pitch; }
	public String getChord() { return chord; }
	public int getVariation() { return variation; }
	public int getDifference() { return difference; }
	public int getPosition() { return position; }
	public int getDuration() { return duration; }
	public void setPitch(int pitch) { this.pitch = pitch; }
	public void setChord(String chord) { this.chord = chord; }
	public void setVariation(int variation) { this.variation = variation; }
	public void setDifference(int difference) { this.difference = difference; }
	public void setPosition(int position) { this.position = position; }
	public void setDuration(int duration) { this.duration = duration; }
}
