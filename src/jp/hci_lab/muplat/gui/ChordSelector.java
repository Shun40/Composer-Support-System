package gui;
import static gui.constants.ChordSelectorConstants.*;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;

/**
 * コードを指定するコンボボックスのクラス
 * @author Shun Yamashita
 */
public class ChordSelector extends ChoiceBox<String> {
	private int index;
	private boolean setupFinished;
	private ChordSelectorPair parent;

	public ChordSelector(int index, int x, int y, ChordSelectorPair parent) {
		super();
		this.index = index;
		this.setupFinished = false;
		this.parent = parent;
		setupComboBox();
		setupPoint(x, y);
		setupEventListener();
	}

	public void setupComboBox() {
		setItems(chords);
		setValue(initValue);
		setChord(initValue, index);
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends String> ov, String oldVal, String newVal)->{
				setChord(newVal, index);
			}
		);
		setupFinished = true;
	}

	public void setChord(String chord, int index) {
		parent.setChord(chord, index);
		if(setupFinished) {
			parent.makeAccompaniment(chord, index);
		}
	}
}
