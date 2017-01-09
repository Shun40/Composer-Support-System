package engine_yamashita;

import static engine_yamashita.constants.DrumPatternConstants.*;

import java.util.ArrayList;

import gui.NoteInformation;

/**
 * ドラムパターンのクラス
 * @author Shun Yamashita
 */
public class DrumPattern {
	private String name;
	private double climax;
	private double speed;
	private double diffQueryClimax;
	private double diffQuerySpeed;
	private ArrayList<NoteInformation> kicks;
	private ArrayList<NoteInformation> snares;
	private ArrayList<NoteInformation> toms;
	private ArrayList<NoteInformation> hihats;
	private ArrayList<NoteInformation> ccymbals;
	private ArrayList<NoteInformation> rcymbals;

	public DrumPattern() {
		name = "";
		climax = 0.0;
		speed = 0.0;
		diffQueryClimax = 0.0;
		diffQuerySpeed = 0.0;
		kicks = new ArrayList<NoteInformation>();
		snares = new ArrayList<NoteInformation>();
		toms = new ArrayList<NoteInformation>();
		hihats = new ArrayList<NoteInformation>();
		ccymbals = new ArrayList<NoteInformation>();
		rcymbals = new ArrayList<NoteInformation>();
		for(int n = 0; n < 16; n++) {
			kicks.add(null);
			snares.add(null);
			toms.add(null);
			hihats.add(null);
			ccymbals.add(null);
			rcymbals.add(null);
		}
	}

	public void addKick(int position, int velocity) {
		NoteInformation kick = new NoteInformation(10, 1, KICK_NOTE, position, DRUM_DURATION, velocity, null);
		kicks.set(calcIndex(position), kick);
	}

	public void removeKick(int position) {
		kicks.set(calcIndex(position), null);
	}

	public void addSnare(int position, int velocity) {
		NoteInformation snare = new NoteInformation(10, 1, SNARE_NOTE, position, DRUM_DURATION, velocity, null);
		snares.set(calcIndex(position), snare);
	}

	public void removeSnare(int position) {
		snares.set(calcIndex(position), null);
	}

	public void addLowTom2(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, L_TOM_2_NOTE, position, DRUM_DURATION, velocity, null);
		toms.set(calcIndex(position), tom);
	}

	public void removeLowTom2(int position) {
		toms.set(calcIndex(position), null);
	}

	public void addLowTom1(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, L_TOM_1_NOTE, position, DRUM_DURATION, velocity, null);
		toms.set(calcIndex(position), tom);
	}

	public void removeLowTom1(int position) {
		toms.set(calcIndex(position), null);
	}

	public void addMidTom2(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, M_TOM_2_NOTE, position, DRUM_DURATION, velocity, null);
		toms.set(calcIndex(position), tom);
	}

	public void removeMidTom2(int position) {
		toms.set(calcIndex(position), null);
	}

	public void addMidTom1(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, M_TOM_1_NOTE, position, DRUM_DURATION, velocity, null);
		toms.set(calcIndex(position), tom);
	}

	public void removeMidTom1(int position) {
		toms.set(calcIndex(position), null);
	}

	public void addHighTom2(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, H_TOM_2_NOTE, position, DRUM_DURATION, velocity, null);
		toms.set(calcIndex(position), tom);
	}

	public void removeHighTom2(int position) {
		toms.set(calcIndex(position), null);
	}

	public void addHighTom1(int position, int velocity) {
		NoteInformation tom = new NoteInformation(10, 1, H_TOM_1_NOTE, position, DRUM_DURATION, velocity, null);
		toms.set(calcIndex(position), tom);
	}

	public void removeHighTom1(int position) {
		toms.set(calcIndex(position), null);
	}

	public void addCloseHihat(int position, int velocity) {
		NoteInformation hihat = new NoteInformation(10, 1, C_HIHAT_NOTE, position, DRUM_DURATION, velocity, null);
		hihats.set(calcIndex(position), hihat);
	}

	public void removeCloseHihat(int position) {
		hihats.set(calcIndex(position), null);
	}

	public void addOpenHihat(int position, int velocity) {
		NoteInformation hihat = new NoteInformation(10, 1, O_HIHAT_NOTE, position, DRUM_DURATION, velocity, null);
		hihats.set(calcIndex(position), hihat);
	}

	public void removeOpenHihat(int position) {
		hihats.set(calcIndex(position), null);
	}

	public void addCrashCymbal1(int position, int velocity) {
		NoteInformation cymbal = new NoteInformation(10, 1, C_CYMBAL_1_NOTE, position, DRUM_DURATION, velocity, null);
		ccymbals.set(calcIndex(position), cymbal);
	}

	public void removeCrashCymbal1(int position) {
		ccymbals.set(calcIndex(position), null);
	}

	public void addCrashCymbal2(int position, int velocity) {
		NoteInformation cymbal = new NoteInformation(10, 1, C_CYMBAL_2_NOTE, position, DRUM_DURATION, velocity, null);
		ccymbals.set(calcIndex(position), cymbal);
	}

	public void removeCrashCymbal2(int position) {
		ccymbals.set(calcIndex(position), null);
	}

	public void addRideCymbal(int position, int velocity) {
		NoteInformation cymbal = new NoteInformation(10, 1, R_CYMBAL_NOTE, position, DRUM_DURATION, velocity, null);
		rcymbals.set(calcIndex(position), cymbal);
	}

	public void removeRideCymbal(int position) {
		rcymbals.set(calcIndex(position), null);
	}

	public ArrayList<NoteInformation> getDrumPatternNotes() {
		ArrayList<NoteInformation> drumPattern = new ArrayList<NoteInformation>();
		for(int n = 0; n < 16; n++) {
			if(kicks.get(n) != null) drumPattern.add(kicks.get(n));
			if(snares.get(n) != null) drumPattern.add(snares.get(n));
			if(toms.get(n) != null) drumPattern.add(toms.get(n));
			if(hihats.get(n) != null) drumPattern.add(hihats.get(n));
			if(ccymbals.get(n) != null) drumPattern.add(ccymbals.get(n));
			if(rcymbals.get(n) != null) drumPattern.add(rcymbals.get(n));
		}
		return drumPattern;
	}

	public static int calcPosition(int beat, int subPosition) {
		return 960 * (beat - 1) + (960 / 4) * (subPosition - 1);
	}

	public static int calcIndex(int position) {
		return 4 * (position / 960) + (position % 960 / 240);
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
	public ArrayList<NoteInformation> getKicks() { return kicks; }
	public void setKicks(ArrayList<NoteInformation> kicks) { this.kicks = kicks; }
	public ArrayList<NoteInformation> getSnares() { return snares; }
	public void setSnares(ArrayList<NoteInformation> snares) { this.snares = snares; }
	public ArrayList<NoteInformation> getToms() { return toms; }
	public void setToms(ArrayList<NoteInformation> toms) { this.toms = toms; }
	public ArrayList<NoteInformation> getHihats() { return hihats; }
	public void setHihats(ArrayList<NoteInformation> hihats) { this.hihats = hihats; }
	public ArrayList<NoteInformation> getCcymbals() { return ccymbals; }
	public void setCcymbals(ArrayList<NoteInformation> ccymbals) { this.ccymbals = ccymbals; }
	public ArrayList<NoteInformation> getRcymbals() { return rcymbals; }
	public void setRcymbals(ArrayList<NoteInformation> rcymbals) { this.rcymbals = rcymbals; }
}
