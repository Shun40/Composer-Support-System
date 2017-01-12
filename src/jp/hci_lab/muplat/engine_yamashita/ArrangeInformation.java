package engine_yamashita;

import java.util.ArrayList;

import gui.NoteInformation;

public class ArrangeInformation {
	private ArrayList<NoteInformation> currentMelody;
	private ArrayList<NoteInformation> previousMelody;
	private ArrayList<String> currentChordProgression;
	private ArrayList<String> previousChordProgression;
	private ArrangeParameter arrangeParameter;

	public ArrangeInformation(
			ArrayList<NoteInformation> currentMelody,
			ArrayList<NoteInformation> previousMelody,
			ArrayList<String> currentChordProgression,
			ArrayList<String> previousChordProgression,
			ArrangeParameter arrangeParameter) {
		this.currentMelody = currentMelody;
		this.previousMelody = previousMelody;
		this.currentChordProgression = currentChordProgression;
		this.arrangeParameter = arrangeParameter;
	}

	public ArrayList<NoteInformation> getCurrentMelody() { return currentMelody; }
	public ArrayList<NoteInformation> getPreviousMelody() { return previousMelody; }
	public ArrayList<String> getCurrentChordProgression() { return currentChordProgression; }
	public ArrayList<String> getPreviousChordProgression() { return previousChordProgression; }
	public ArrangeParameter getArrangeParameter() { return arrangeParameter; }
}
