package engine_yamashita;

import java.util.ArrayList;

import gui.Note;

public class Melody extends ArrayList<Note> {
	private String name;

	public Melody(String name) {
		super();
		this.name = name;
	}

	public String getName() { return name; }
}
