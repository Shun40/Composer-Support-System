package gui;

import static gui.constants.UniversalConstants.*;

import java.util.ArrayList;

import engine_yamashita.PredictionPattern;
import javafx.scene.Group;

/**
 * パターン予測変換の編集領域のクラス
 * @author Shun Yamashita
 */
public class PatternArea extends Group {
	private PatternSelector patternSelector;
	private PredictionButton predictionButton;
	private DecisionButton decisionButton;
	private Pianoroll parent;

	public PatternArea(int x, int y, Pianoroll parent) {
		super();
		this.parent = parent;
		setupPoint(x, y);
		setupPatternSelector();
		setupPredictionButton();
		setupDecisionButton();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupPatternSelector() {
		patternSelector = new PatternSelector(0, 26, this);
		getChildren().add(patternSelector);
	}

	public void setupPredictionButton() {
		predictionButton = new PredictionButton(0, 0, this);
		getChildren().add(predictionButton);
	}

	public void setupDecisionButton() {
		decisionButton = new DecisionButton(80, 0, this);
		getChildren().add(decisionButton);
	}

	public void prediction() {
		patternSelector.getSelectionModel().clearSelection();
		patternSelector.getItems().clear(); // 一度リストの中身を空にする
		ArrayList<PredictionPattern> patterns = parent.prediction();
		for(PredictionPattern pattern : patterns) {
			patternSelector.addList(pattern);
		}
	}

	public void decision() {
		PredictionPattern pattern = patternSelector.getSelectionModel().getSelectedItem();
		if(pattern == null) return;
		parent.incPredictionPatternFrequency(pattern.getMelody().getIndex());
	}

	public void arrange(PredictionPattern pattern) {
		int targetMeasure = parent.getPredictionTargetMeasure();
		parent.removeNoteInMeasure(targetMeasure, 1); // メロディトラックのノートを消去
		ArrayList<Note> melody = pattern.getMelody();
		for(Note note : melody) {
			int track    = note.getTrack();
			int pitch    = note.getPitch();
			int position = note.getPosition() + (PPQ * 4) * (targetMeasure - 1);
			int duration = note.getDuration();
			parent.setCurrentTrack(track);
			parent.putNote(pitch, position, duration);
		}
		parent.setCurrentTrack(1);
	}
}
