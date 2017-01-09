package gui;
import static gui.constants.NoteResolutionSelectorConstants.*;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

/**
 * ピアノロールスナップの分解能を指定するコンボボックスのクラス
 * @author Shun Yamashita
 */
public class NoteResolutionSelector extends ComboBox<String> {
	private Pianoroll parent;

	public NoteResolutionSelector(int x, int y, Pianoroll parent) {
		super();
		this.parent = parent;
		setupComboBox();
		setupPoint(x, y);
		setupEventListener();
	}

	public void setupComboBox() {
		setItems(resolutions);
		setValue(initValue);
		setEditable(false);
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends String> ov, String oldVal, String newVal)->{
				parent.changedNoteResolution(values.get(newVal));
			}
		);
	}

	public int getIntValue() {
		return values.get(getValue());
	}
}
