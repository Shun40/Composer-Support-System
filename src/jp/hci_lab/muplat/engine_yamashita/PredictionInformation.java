package engine_yamashita;

import java.util.ArrayList;

import gui.ChordPair;
import gui.Note;
import gui.constants.UniversalConstants.Algorithm;

public class PredictionInformation {
	private int targetMeasure;
	private ArrayList<Note> melodyNotes;
	private ChordPair[] chordProgression;
	private ArrayList<Algorithm> algorithms;

	public PredictionInformation(
			int targetMeasure,
			ArrayList<Note> melodyNotes,
			ChordPair[] chordProgression,
			ArrayList<Algorithm> algorithms) {
		this.targetMeasure = targetMeasure;
		this.melodyNotes = melodyNotes;
		this.chordProgression = chordProgression;
		this.algorithms = algorithms;
	}

	public int getTargetMeasure() { return targetMeasure; }
	public ArrayList<Note> getMelodyNotes() { return melodyNotes; }
	public ChordPair[] getChordProgression() { return chordProgression; }
	public ArrayList<Algorithm> getAlgorithms() { return algorithms; }
}
