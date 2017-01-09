package engine_yamashita;

import java.util.ArrayList;

import gui.NoteInformation;

public class ArrangeInformation {
	private ArrayList<NoteInformation> melody;
	private ArrayList<String> chordProgression;
	private ArrangeParameter arrangeParameter;

	public ArrangeInformation(ArrayList<NoteInformation> melody, ArrayList<String> chordProgression, ArrangeParameter arrangeParameter) {
		this.melody = melody;
		this.chordProgression = chordProgression;
		this.arrangeParameter = arrangeParameter;
	}

	public ArrayList<NoteInformation> getMelody() { return melody; }
	public ArrayList<String> getChordProgression() { return chordProgression; }
	public ArrangeParameter getArrangeParameter() { return arrangeParameter; }
}
