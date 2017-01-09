package gui;
import java.util.ArrayList;

import engine_yamashita.ArrangePattern;
import javafx.scene.Group;

/**
 * パターン予測変換の編集領域のクラス
 * @author Shun Yamashita
 */
public class PatternArea extends Group {
	private PatternSelector patternSelector;
	private PredictionButton predictionButton;
	private Pianoroll parent;

	public PatternArea(int x, int y, Pianoroll parent) {
		super();
		this.parent = parent;
		setupPoint(x, y);
		setupPatternSelector();
		setupPredictionButton();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupPatternSelector() {
		patternSelector = new PatternSelector(0, 32, this);
		getChildren().add(patternSelector);
	}

	public void setupPredictionButton() {
		predictionButton = new PredictionButton(0, 140, this);
		getChildren().add(predictionButton);
	}

	public void prediction() {
		ArrayList<ArrangePattern> patterns = parent.prediction();
		for(ArrangePattern pattern : patterns) {
			patternSelector.addList(pattern);
		}
	}

	public void arrange(ArrangePattern pattern) {
		for(NoteInformation noteInformation : pattern.getDrumPattern()) {
			int track    = noteInformation.getTrack();
			int note     = noteInformation.getNote();
			int position = noteInformation.getPosition();
			int duration = noteInformation.getDuration();
			parent.setCurrentTrack(track);
			parent.putNote(note, position, duration);
		}
		parent.setCurrentTrack(1);
	}
}
