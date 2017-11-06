package engine;

import java.util.List;

import gui.component.pianoroll.note.Note;

public class PredictionInformation {
	private int targetMeasure;
	private List<Note> melodyNotes;
	private List<String> chordProgression;

	public PredictionInformation(int targetMeasure, List<Note> melodyNotes, List<String> chordProgression) {
		this.targetMeasure = targetMeasure;
		this.melodyNotes = melodyNotes;
		this.chordProgression = chordProgression;
	}

	public int getTargetMeasure() {
		return targetMeasure;
	}

	public List<Note> getMelodyNotes() {
		return melodyNotes;
	}

	public List<String> getChordProgression() {
		return chordProgression;
	}
}
