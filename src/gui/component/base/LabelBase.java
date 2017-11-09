package gui.component.base;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * ラベルの基底クラス
 * @author Shun Yamashita
 */
public class LabelBase extends Label {
	public LabelBase() {
		super();
	}

	public void setup(String text, Font font) {
		setText(text);
		setFont(font);
	}

	public void setup(String text, Font font, Color color) {
		setText(text);
		setFont(font);
		setTextFill(color);
	}

	public void setPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}
}
