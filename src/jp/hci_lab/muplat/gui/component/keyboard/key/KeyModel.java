package gui.component.keyboard.key;

import midi.MidiUtil;
import system.AppConstants;

public class KeyModel {
	private int track;
	private int program;
	private final int pitch;
	private final String keyname;

	public KeyModel(int pitch) {
		track = AppConstants.MelodySettings.MELODY_TRACK;
		program = AppConstants.MelodySettings.MELODY_PROGRAM;
		this.pitch = pitch;
		keyname = MidiUtil.getIntervalWithOctave(pitch);
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public int getProgram() {
		return program;
	}

	public void setProgram(int program) {
		this.program = program;
	}

	public int getPitch() {
		return pitch;
	}

	public String getKeyname() {
		return keyname;
	}
}
