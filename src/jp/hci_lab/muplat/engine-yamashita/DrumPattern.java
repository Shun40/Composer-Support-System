import static constants.DrumPatternConstants.*;

import java.util.ArrayList;

/**
 * ドラムパターンのクラス
 * @author Shun Yamashita
 */
public class DrumPattern extends ArrayList<NoteInformation> {
	public DrumPattern() {
		super();
	}

	public void addKick(int position, int velocity) {
		add(new NoteInformation(10, 1, KICK_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addStick(int position, int velocity) {
		add(new NoteInformation(10, 1, STICK_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addSnare(int position, int velocity) {
		add(new NoteInformation(10, 1, SNARE_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addLowTom2(int position, int velocity) {
		add(new NoteInformation(10, 1, L_TOM_2_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addLowTom1(int position, int velocity) {
		add(new NoteInformation(10, 1, L_TOM_1_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addMidTom2(int position, int velocity) {
		add(new NoteInformation(10, 1, M_TOM_2_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addMidTom1(int position, int velocity) {
		add(new NoteInformation(10, 1, M_TOM_1_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addHighTom2(int position, int velocity) {
		add(new NoteInformation(10, 1, H_TOM_2_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addHighTom1(int position, int velocity) {
		add(new NoteInformation(10, 1, H_TOM_1_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addCloseHihat(int position, int velocity) {
		add(new NoteInformation(10, 1, C_HIHAT_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addOpenHihat(int position, int velocity) {
		add(new NoteInformation(10, 1, O_HIHAT_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addCrashCymbal(int position, int velocity) {
		add(new NoteInformation(10, 1, C_CYMBAL_NOTE, position, DRUM_DURATION, velocity, null));
	}

	public void addRideCymbal(int position, int velocity) {
		add(new NoteInformation(10, 1, R_CYMBAL_NOTE, position, DRUM_DURATION, velocity, null));
	}
}
