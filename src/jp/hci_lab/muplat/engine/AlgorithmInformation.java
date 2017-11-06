package engine;

import java.util.List;

import system.AppConstants;

public class AlgorithmInformation {
	private List<AppConstants.Algorithm> selectedAlgorithms;
	private AppConstants.MelodyStructurePattern selectedMelodyStructurePattern;

	public AlgorithmInformation(List<AppConstants.Algorithm> selectedAlgorithms, AppConstants.MelodyStructurePattern selectedMelodyStructurePattern) {
		this.selectedAlgorithms = selectedAlgorithms;
		this.selectedMelodyStructurePattern = selectedMelodyStructurePattern;
	}

	public List<AppConstants.Algorithm> getSelectedAlgorithms() { return selectedAlgorithms; }
	public AppConstants.MelodyStructurePattern getSelectedMelodyStructurePattern() { return selectedMelodyStructurePattern; }
}
