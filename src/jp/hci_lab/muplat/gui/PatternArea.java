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
	private PatternSelector[] patternSelectors;
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
		patternSelectors = new PatternSelector[parent.getMeasureCount()];
		for(int n = 0; n < patternSelectors.length; n++) {
			patternSelectors[n] = new PatternSelector(parent.getMeasureCount(), 0, 26, this);
		}
		getChildren().add(patternSelectors[0]);
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
		int targetMeasure = parent.getPredictionTargetMeasure();
		//patternSelector.getSelectionModel().clearSelection();
		patternSelectors[targetMeasure - 1].getItems().clear(); // 一度リストの中身を空にする
		ArrayList<PredictionPattern> patterns = parent.prediction();
		for(PredictionPattern pattern : patterns) {
			patternSelectors[targetMeasure - 1].addList(pattern);
		}
	}

	public void decision() {
		int targetMeasure = parent.getPredictionTargetMeasure();
		PredictionPattern pattern = patternSelectors[targetMeasure - 1].getSelectionModel().getSelectedItem();
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

	public void setPredictionPatternList(int targetMeasure) {
		for(int n = 0; n < patternSelectors.length; n++) {
			getChildren().remove(patternSelectors[n]);
		}
		getChildren().add(patternSelectors[targetMeasure - 1]);
	}

	public int getTargetMeasure() {
		return parent.getPredictionTargetMeasure();
	}
}
