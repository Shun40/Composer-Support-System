package gui;

import static gui.constants.PatternAreaConstants.*;

import java.util.ArrayList;

import MIDI.MIDIConstants;
import engine_yamashita.PredictionPattern;
import javafx.scene.Group;
import javafx.scene.control.TextField;

/**
 * パターン予測変換の編集領域のクラス
 * @author Shun Yamashita
 */
public class PatternArea extends Group {
	private PatternSelector[] patternSelectors;
	private PredictionButton predictionButton;
	private DecisionButton decisionButton;
	private String[] lastDecidedId;
	private TextField[] lastDecidedName;
	private Pianoroll parent;

	public PatternArea(int x, int y, Pianoroll parent) {
		super();
		this.parent = parent;
		setupPoint(x, y);
		setupPatternSelector();
		setupPredictionButton();
		setupDecisionButton();
		setupLastDecidedId();
		setupLastDecidedName();
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

	public void setupLastDecidedId() {
		lastDecidedId = new String[parent.getMeasureCount()];
		for(int n = 0; n < lastDecidedId.length; n++) {
			lastDecidedId[n] = null;
		}
	}

	public void setupLastDecidedName() {
		lastDecidedName = new TextField[parent.getMeasureCount()];
		for(int n = 0; n < lastDecidedName.length; n++) {
			lastDecidedName[n] = new TextField("");
			lastDecidedName[n].setLayoutX(0);
			lastDecidedName[n].setLayoutY(436);
			lastDecidedName[n].setPrefWidth(TEXT_FIELD_WIDTH);
			lastDecidedName[n].setPrefHeight(TEXT_FIELD_HEIGHT);
			lastDecidedName[n].setDisable(true);
			lastDecidedName[n].setPromptText("最後に決定したパターン");
			getChildren().add(lastDecidedName[n]);
		}
	}

	public void prediction() {
		int targetMeasure = parent.getPredictionTargetMeasure();
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

		String wordId = pattern.getId();
		if(targetMeasure == 1) { // 単語辞書の頻度更新
			parent.incWordDictionaryFrequency(wordId);
		} else {
			String contextId = lastDecidedId[targetMeasure - 1 - 1];
			if(contextId == null) { // 単語辞書の頻度更新
				parent.incWordDictionaryFrequency(wordId);
			} else { // 例文辞書の頻度更新
				parent.incPhraseDictionaryFrequency(contextId, wordId);
			}
		}
		lastDecidedId[targetMeasure - 1] = wordId;
		lastDecidedName[targetMeasure - 1].setText(pattern.getName());
	}

	public void arrange(PredictionPattern pattern) {
		int targetMeasure = parent.getPredictionTargetMeasure();
		parent.removeNoteInMeasure(targetMeasure, 1); // メロディトラックのノートを消去
		ArrayList<Note> melody = pattern.getMelody();
		for(Note note : melody) {
			int track    = note.getTrack();
			int pitch    = note.getPitch();
			int position = note.getPosition() + (MIDIConstants.PPQ * 4) * (targetMeasure - 1);
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
		for(int n = 0; n < lastDecidedName.length; n++) {
			getChildren().remove(lastDecidedName[n]);
		}
		getChildren().add(lastDecidedName[targetMeasure - 1]);
	}

	public int getTargetMeasure() {
		return parent.getPredictionTargetMeasure();
	}
}
