package gui.component;

import gui.GuiConstants;
import gui.GuiManager;
import gui.component.base.ChoiceBoxBase;
import javafx.beans.value.ObservableValue;

/**
 * ピアノロールスナップの分解能を指定するコンボボックス
 * @author Shun Yamashita
 */
public class ResolutionSelector extends ChoiceBoxBase<String> {
	private GuiManager owner;

	public ResolutionSelector(GuiManager owner) {
		super();
		this.owner = owner;
		setup(GuiConstants.ResolutionSelector.ITEMS, GuiConstants.ResolutionSelector.DEFAULT_VALUE);
		setPoint(GuiConstants.ResolutionSelector.X, GuiConstants.ResolutionSelector.Y);
		setupEventListener();
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends String> ov, String oldVal, String newVal)->{
				owner.changeResolution(Integer.parseInt(newVal.split("/")[1]));
			}
		);
	}

	public int getResolution() {
		return Integer.parseInt(getValue().split("/")[1]);
	}
}
