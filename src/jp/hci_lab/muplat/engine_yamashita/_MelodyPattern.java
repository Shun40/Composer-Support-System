package engine_yamashita;

import java.util.ArrayList;

import gui.NoteInformation;

public class _MelodyPattern extends ArrayList<NoteInformation> {
	private String name;

	public _MelodyPattern(String name) {
		super();
		this.name = name;
	}

	public String getName() { return name; }
}
