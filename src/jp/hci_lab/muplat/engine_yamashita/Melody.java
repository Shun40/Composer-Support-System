package engine_yamashita;

import java.util.ArrayList;

import gui.NoteInformation;

public class Melody extends ArrayList<NoteInformation> {
	private String name;

	public Melody(String name) {
		super();
		this.name = name;
	}

	public String getName() { return name; }
}
