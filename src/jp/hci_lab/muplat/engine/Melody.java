package engine;

import java.util.ArrayList;

import gui.component.pianoroll.note.NoteModel;

public class Melody extends ArrayList<NoteModel> {
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
