package engine_yamashita;

import java.util.ArrayList;

import gui.Note;

public class Accompaniment {
	private ArrayList<Note> pianoPart;
	private ArrayList<Note> bassPart;
	private ArrayList<Note> drumPart;

	public Accompaniment() {
		pianoPart = new ArrayList<Note>();
		bassPart = new ArrayList<Note>();
		drumPart = new ArrayList<Note>();
	}

	public ArrayList<Note> getPianoPart() { return pianoPart; }
	public ArrayList<Note> getBassPart() { return bassPart; }
	public ArrayList<Note> getDrumPart() { return drumPart; }
	public void setPianoPart(ArrayList<Note> pianoPart) { this.pianoPart = pianoPart; }
	public void setBassPart(ArrayList<Note> bassPart) { this.bassPart = bassPart; }
	public void setDrumPart(ArrayList<Note> drumPart) { this.drumPart = drumPart; }
}
