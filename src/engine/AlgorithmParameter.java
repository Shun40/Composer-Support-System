package engine;

import java.util.List;

import system.AppConstants;

/**
 * 各種アルゴリズムの実行に必要なパラメータ情報を持つクラス
 * @author Shun Yamashita
 */
public class AlgorithmParameter {
	/** 選択されたアルゴリズム群 */
	private final List<AppConstants.Algorithm> selectedAlgorithms;
	/** 選択されたメロディ構造 */
	private final AppConstants.MelodyStructurePattern selectedMelodyStructurePattern;

	public AlgorithmParameter(List<AppConstants.Algorithm> selectedAlgorithms, AppConstants.MelodyStructurePattern selectedMelodyStructurePattern) {
		this.selectedAlgorithms = selectedAlgorithms;
		this.selectedMelodyStructurePattern = selectedMelodyStructurePattern;
	}

	public List<AppConstants.Algorithm> getSelectedAlgorithms() {
		return selectedAlgorithms;
	}

	public AppConstants.MelodyStructurePattern getSelectedMelodyStructurePattern() {
		return selectedMelodyStructurePattern;
	}
}
