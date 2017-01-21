package engine_yamashita;

import java.util.ArrayList;

import gui.ChordPair;
import gui.Note;

public class PredictionInformation {
	private int targetMeasure;
	private ArrayList<Note> melodyNotes;
	private ChordPair[] chordProgression;

	public PredictionInformation(
			int targetMeasure,
			ArrayList<Note> melodyNotes,
			ChordPair[] chordProgression) {
		this.targetMeasure = targetMeasure;
		this.melodyNotes = melodyNotes;
		this.chordProgression = chordProgression;
	}

	public int getTargetMeasure() { return targetMeasure; }
	public ArrayList<Note> getMelodyNotes() { return melodyNotes; }
	public ChordPair[] getChordProgression() { return chordProgression; }
}
