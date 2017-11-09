package gui.component.measurebar;

import gui.GuiConstants;
import gui.component.base.ChoiceBoxBase;
import javafx.beans.value.ObservableValue;

/**
 * コードを指定するチョイスボックスのクラス
 * @author Shun Yamashita
 */
public class ChordSelector extends ChoiceBoxBase<String> {
	private final int measure;
	private final int beat;
	private MeasureBar owner;

	public ChordSelector(int measure, int beat, int x, int y, MeasureBar owner) {
		super();
		this.measure = measure;
		this.beat = beat;
		this.owner = owner;
		setup(GuiConstants.ChordSelector.ITEMS, GuiConstants.ChordSelector.DEFAULT_VALUE);
		setPoint(x, y);
		setupEventListener();
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends String> ov, String oldVal, String newVal)->{
				owner.makeAccompaniment(newVal, measure, beat);
			}
		);
	}

	public String getChord() {
		return getValue();
	}

	public void setChord(String chord) {
		setValue(chord);
	}
}
