package gui.component;
import java.util.ArrayList;
import java.util.List;

import gui.GuiConstants;
import gui.component.base.CheckBoxBase;
import gui.component.base.ChoiceBoxBase;
import gui.component.base.GroupBase;
import gui.component.base.LabelBase;
import javafx.collections.FXCollections;
import javafx.scene.text.Font;
import system.AppConstants;

/**
 * アルゴリズムに関するパラメータを選択するためのクラス
 * @author Shun Yamashita
 */
public class AlgorithmBar extends GroupBase {
	private CheckBoxBase[] checkBoxes;
	private ChoiceBoxBase<AppConstants.MelodyStructurePattern> choiceBox;

	public AlgorithmBar() {
		super();
		setPoint(GuiConstants.AlgorithmBar.X, GuiConstants.AlgorithmBar.Y);
		setupLabel();
		setupCheckBoxes();
		setupChoiceBox();
	}

	public void setupLabel() {
		LabelBase label = new LabelBase();
		label.setup("Algorithm", Font.font("Arial", 14));
		getChildren().add(label);
	}

	public void setupCheckBoxes() {
		checkBoxes = new CheckBoxBase[AppConstants.Algorithm.values().length];
		for(int n = 0; n < checkBoxes.length; n++) {
			checkBoxes[n] = new CheckBoxBase();
			checkBoxes[n].setText(AppConstants.Algorithm.values()[n].toString());
			checkBoxes[n].setUserData(AppConstants.Algorithm.values()[n]);
			checkBoxes[n].setPoint(50 * n, 25);
			if(AppConstants.Algorithm.values()[n] == AppConstants.Algorithm.PC) { // 最初はPCのみが選ばれている状態で初期化
				checkBoxes[n].setSelected(true);
			}
			getChildren().add(checkBoxes[n]);
		}
	}

	public void setupChoiceBox() {
		choiceBox = new ChoiceBoxBase<AppConstants.MelodyStructurePattern>();
		choiceBox.setup(FXCollections.observableArrayList(AppConstants.MelodyStructurePattern.values()), AppConstants.MelodyStructurePattern.ABCD);
		choiceBox.setPoint(150, 22);
		getChildren().add(choiceBox);
	}

	public List<AppConstants.Algorithm> getSelectedAlgorithms() {
		List<AppConstants.Algorithm> algorithms = new ArrayList<AppConstants.Algorithm>();
		for(CheckBoxBase checkBox : checkBoxes) {
			if(checkBox.isSelected()) {
				algorithms.add((AppConstants.Algorithm)checkBox.getUserData());
			}
		}
		return algorithms;
	}

	public AppConstants.MelodyStructurePattern getSelectedMelodyStructurePattern() {
		return choiceBox.getValue();
	}
}
