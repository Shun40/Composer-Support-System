package engine_yamashita;

import java.util.ArrayList;

import gui.Note;

public class ArrangeInformation {
	private ArrayList<Note> currentMelody;
	private ArrayList<Note> previousMelody;
	private ArrayList<String> currentChordProgression;
	private ArrayList<String> previousChordProgression;
	private ArrangeParameter arrangeParameter;

	public ArrangeInformation(
			ArrayList<Note> currentMelody,
			ArrayList<Note> previousMelody,
			ArrayList<String> currentChordProgression,
			ArrayList<String> previousChordProgression,
			ArrangeParameter arrangeParameter) {
		this.currentMelody = currentMelody;
		this.previousMelody = previousMelody;
		this.currentChordProgression = currentChordProgression;
		this.arrangeParameter = arrangeParameter;
	}

	public ArrayList<Note> getCurrentMelody() { return currentMelody; }
	public ArrayList<Note> getPreviousMelody() { return previousMelody; }
	public ArrayList<String> getCurrentChordProgression() { return currentChordProgression; }
	public ArrayList<String> getPreviousChordProgression() { return previousChordProgression; }
	public ArrangeParameter getArrangeParameter() { return arrangeParameter; }
}
