package engine_yamashita;

import java.util.ArrayList;

import gui.constants.UniversalConstants.Algorithm;
import gui.constants.UniversalConstants.MelodyStructurePattern;

public class AlgorithmInformation {
	private ArrayList<Algorithm> selectedAlgorithms;
	private MelodyStructurePattern selectedMelodyStructurePattern;

	public AlgorithmInformation(ArrayList<Algorithm> selectedAlgorithms, MelodyStructurePattern selectedMelodyStructurePattern) {
		this.selectedAlgorithms = selectedAlgorithms;
		this.selectedMelodyStructurePattern = selectedMelodyStructurePattern;
	}

	public ArrayList<Algorithm> getSelectedAlgorithms() { return selectedAlgorithms; }
	public MelodyStructurePattern getSelectedMelodyStructurePattern() { return selectedMelodyStructurePattern; }
}
