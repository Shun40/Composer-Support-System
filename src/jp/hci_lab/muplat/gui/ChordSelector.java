package gui;
import static gui.constants.ChordSelectorConstants.*;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

/**
 * コードを指定するコンボボックスのクラス
 * @author Shun Yamashita
 */
public class ChordSelector extends ComboBox<String> {
	private int measure;
	private int beat;
	private MeasureArea parent;

	public ChordSelector(int measure, int beat, int x, int y, MeasureArea parent) {
		super();
		this.measure = measure;
		this.beat = beat;
		this.parent = parent;
		setupComboBox();
		setupPoint(x, y);
		setupEventListener();
	}

	public void setupComboBox() {
		setItems(chords);
		setValue(initValue);
		setEditable(false);
		setChord(initValue, measure, beat);
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends String> ov, String oldVal, String newVal)->{
				setChord(newVal, measure, beat);
			}
		);
	}

	public void setChord(String chord, int measure, int beat) {
		parent.setChord(chord, measure, beat);
	}
}