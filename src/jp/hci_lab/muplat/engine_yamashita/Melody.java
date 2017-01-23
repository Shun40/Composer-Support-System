package engine_yamashita;

import java.util.ArrayList;

import gui.Note;

public class Melody extends ArrayList<Note> {
	private int index;
	private String id;
	private String name;
	private int frequency;

	public Melody(int index, String id, String name, int frequency) {
		super();
		this.index = index;
		this.id = id;
		this.name = name;
		this.frequency = frequency;
	}

	public int getIndex() { return index; }
	public String getId() { return id; }
	public String getName() { return name; }
	public int getFrequency() { return frequency; }
}
