package engine_yamashita;

import java.util.ArrayList;

import gui.Note;

public class Melody extends ArrayList<Note> {
	private int index;
	private String name;
	private int frequency;

	public Melody(int index, String name, int frequency) {
		super();
		this.index = index;
		this.name = name;
		this.frequency = frequency;
	}

	public int getIndex() { return index; }
	public String getName() { return name; }
	public int getFrequency() { return frequency; }
}
