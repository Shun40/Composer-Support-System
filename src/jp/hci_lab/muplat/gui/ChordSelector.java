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
	private ChordSelectorPair parent;

	public ChordSelector(int index, int x, int y, ChordSelectorPair parent) {
		super();
		this.index = index;
		this.parent = parent;
		setupComboBox();
		setupPoint(x, y);
		setupEventListener();
	}

	public void setupComboBox() {
		setItems(chords);
		setValue(initValue);
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends String> ov, String oldVal, String newVal)->{
				parent.makeAccompaniment(newVal, index);
			}
		);
	}
}
