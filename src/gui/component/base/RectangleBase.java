package gui.component.base;

import javafx.scene.shape.Rectangle;

/**
 * レクタングルの基底クラス
 * @author Shun Yamashita
 */
public class RectangleBase extends Rectangle {
	public RectangleBase() {
		super();
	}

	public void setup(String colorCode, String style) {
		setStyle("-fx-fill: " + colorCode + ";" + style);
	}

	public void setPoint(double x, double y) {
		setX(x);
		setY(y);
	}

	public void setSize(int width, int height) {
		setWidth(width);
		setHeight(height);
	}
}
