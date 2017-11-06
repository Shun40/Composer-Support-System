package gui.component.keyboard.key;

import midi.MidiConstants;
import midi.MidiUtil;

public class KeyModel {
	private int track;
	private int program;
	private final int pitch;
	private final String keyname;

	public KeyModel(int pitch) {
		track = 1;
		program = MidiConstants.PROGRAM_NUMBERS[track - 1];
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
