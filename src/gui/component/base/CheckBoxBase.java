package gui.component.base;

import javafx.scene.control.CheckBox;

/**
 * チェックボックスの基底クラス
 * @author Shun Yamashita
 */
public class CheckBoxBase extends CheckBox {
	public CheckBoxBase() {
		super();
	}

	public void setPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}
}
