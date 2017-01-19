package engine_yamashita;

import java.util.ArrayList;

import gui.NoteInformation;

public class Accompaniment {
	private ArrayList<NoteInformation> pianoPart;
	private ArrayList<NoteInformation> bassPart;
	private ArrayList<NoteInformation> drumPart;

	public Accompaniment() {
		pianoPart = new ArrayList<NoteInformation>();
		bassPart = new ArrayList<NoteInformation>();
		drumPart = new ArrayList<NoteInformation>();
	}

	public ArrayList<NoteInformation> getPianoPart() { return pianoPart; }
	public ArrayList<NoteInformation> getBassPart() { return bassPart; }
	public ArrayList<NoteInformation> getDrumPart() { return drumPart; }
	public void setPianoPart(ArrayList<NoteInformation> pianoPart) { this.pianoPart = pianoPart; }
	public void setBassPart(ArrayList<NoteInformation> bassPart) { this.bassPart = bassPart; }
	public void setDrumPart(ArrayList<NoteInformation> drumPart) { this.drumPart = drumPart; }
}
