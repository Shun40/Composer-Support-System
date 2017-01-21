package gui;

import static gui.constants.UniversalConstants.*;

import javafx.scene.Group;

public class ChordSelectorPair extends Group {
	private int measure;
	private ChordSelector[] chordSelectors;
	private MeasureArea parent;

	public ChordSelectorPair(int measure, int x, int y, MeasureArea parent) {
		this.measure = measure;
		this.parent = parent;
		setupPoint(x, y);
		setupChordSelector();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupChordSelector() {
		chordSelectors = new ChordSelector[2];
		for(int index = 0; index < chordSelectors.length; index++) {
			int x = (MEASURE_WIDTH / 2) * index;
			int y = 0;
			chordSelectors[index] = new ChordSelector(index, x, y, this);
			getChildren().add(chordSelectors[index]);
		}
	}

	public void setChord(String chord, int index) {
		parent.setChord(chord, measure, index);
	}

	public void makeAccompaniment(String chord, int index) {
		parent.makeAccompaniment(chord, measure, index);
	}
}
