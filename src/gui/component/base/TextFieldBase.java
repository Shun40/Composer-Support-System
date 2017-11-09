package gui.component.base;

import javafx.scene.control.TextField;

/**
 * テキストフィールドの基底クラス
 * @author Shun Yamashita
 */
public class TextFieldBase extends TextField {
	public TextFieldBase() {
		super();
	}

	public void setPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setSize(int width, int height) {
		setPrefWidth(width);
		setPrefHeight(height);
	}
}
