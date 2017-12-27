package gui.component.base;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * ボタンの基底クラス
 * @author Shun Yamashita
 */
public class ButtonBase extends Button {
	public ButtonBase() {
		super();
	}

	public void setup(String text, Color color) {
		setText(text);
		setTextFill(color);
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
