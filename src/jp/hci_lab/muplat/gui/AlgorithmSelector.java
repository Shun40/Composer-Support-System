package gui;
import java.util.ArrayList;

import gui.constants.UniversalConstants.Algorithm;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * アルゴリズムを選択するためのチェックボックス群のクラス
 * @author Shun Yamashita
 */
public class AlgorithmSelector extends Group {
	private CheckBox[] checkBoxes;

	public AlgorithmSelector(Algorithm algorithm, int x, int y) {
		super();
		setupPoint(x, y);
		setupLabel();
		setupCheckBoxes();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupLabel() {
		Label label = new Label("Algorithm");
		label.setFont(Font.font("Arial", 14));
		getChildren().add(label);
	}

	public void setupCheckBoxes() {
		checkBoxes = new CheckBox[Algorithm.values().length];
		for(int n = 0; n < checkBoxes.length; n++) {
			checkBoxes[n] = new CheckBox();
			checkBoxes[n].setText(Algorithm.values()[n].toString());
			checkBoxes[n].setUserData(Algorithm.values()[n]);
			checkBoxes[n].setLayoutX(50 * n);
			checkBoxes[n].setLayoutY(25);
			if(Algorithm.values()[n] == Algorithm.PC) { // 最初はPCのみが選ばれている状態で初期化
				checkBoxes[n].setSelected(true);
			}
			getChildren().add(checkBoxes[n]);
		}
	}

	public ArrayList<Algorithm> getSelectedAlgorithms() {
		ArrayList<Algorithm> algorithms = new ArrayList<Algorithm>();
		for(CheckBox checkBox : checkBoxes) {
			if(checkBox.isSelected()) {
				algorithms.add((Algorithm)checkBox.getUserData());
			}
		}
		return algorithms;
	}
}
