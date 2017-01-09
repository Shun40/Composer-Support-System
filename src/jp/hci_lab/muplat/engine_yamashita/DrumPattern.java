package engine_yamashita;

import static engine_yamashita.constants.DrumPatternConstants.*;

import java.util.ArrayList;

import gui.NoteInformation;

/**
 * ドラムパターンのクラス
 * @author Shun Yamashita
 */
public class DrumPattern extends ArrayList<NoteInformation> {
	private String name;
	private double climax;
	private double speed;
	private double diffQueryClimax;
	private double diffQuerySpeed;

	public DrumPattern() {
		super();
		name = "";
		climax = 0.0;
		speed = 0.0;
		diffQueryClimax = 0.0;
		diffQuerySpeed = 0.0;
	}

	public void addKick(int position, int velocity) {
		NoteInformation kick = new NoteInformation(10, 1, KICK_NOTE, position, DRUM_DURATION, velocity, null);
		add(kick);
	}

	public void addSnare(int position, int velocity) {
		NoteInformation snare = new NoteInformation(10, 1, SNARE_NOTE, position, DRUM_DURATION, velocity, null);
		add(snare);
	}

	public void addLowTom2(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, L_TOM_2_NOTE, position, DRUM_DURATION, velocity, null);
		add(tom);
	}

	public void addLowTom1(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, L_TOM_1_NOTE, position, DRUM_DURATION, velocity, null);
		add(tom);
	}

	public void addMidTom2(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, M_TOM_2_NOTE, position, DRUM_DURATION, velocity, null);
		add(tom);
	}

	public void addMidTom1(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, M_TOM_1_NOTE, position, DRUM_DURATION, velocity, null);
		add(tom);
	}

	public void addHighTom2(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, H_TOM_2_NOTE, position, DRUM_DURATION, velocity, null);
		add(tom);
	}

	public void addHighTom1(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, H_TOM_1_NOTE, position, DRUM_DURATION, velocity, null);
		add(tom);
	}

	public void addCloseHihat(int position, int velocity) {
		NoteInformation hihat = new NoteInformation(10, 1, C_HIHAT_NOTE, position, DRUM_DURATION, velocity, null);
		add(hihat);
	}

	public void addOpenHihat(int position, int velocity) {
		NoteInformation hihat = new NoteInformation(10, 1, O_HIHAT_NOTE, position, DRUM_DURATION, velocity, null);
		add(hihat);
	}

	public void addCrashCymbal(int position, int velocity) {
		NoteInformation cymbal = new NoteInformation(10, 1, C_CYMBAL_NOTE, position, DRUM_DURATION, velocity, null);
		add(cymbal);
	}

	public void addRideCymbal(int position, int velocity) {
		NoteInformation cymbal = new NoteInformation(10, 1, R_CYMBAL_NOTE, position, DRUM_DURATION, velocity, null);
		add(cymbal);
	}

	public static int calcPosition(int beat, int position) {
		return 960 * (beat - 1) + (960 / 4) * (position - 1);
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public double getClimax() { return climax; }
	public void setClimax(double climax) { this.climax = climax; }
	public double getSpeed() { return speed; }
	public void setSpeed(double speed) { this.speed = speed; }
	public double getDiffQueryClimax() { return diffQueryClimax; }
	public void setDiffQueryClimax(double diffQueryClimax) { this.diffQueryClimax = diffQueryClimax; }
	public double getDiffQuerySpeed() { return diffQuerySpeed; }
	public void setDiffQuerySpeed(double diffQuerySpeed) { this.diffQuerySpeed = diffQuerySpeed; }
}
