/*
 * ピアノロールに含まれる鍵盤を表すクラス.
 */

import javafx.scene.shape.Rectangle;

public class PianoRollKey extends Rectangle {
	public static final String DEFAULT_STYLE = "-fx-stroke: #000000;-fx-stroke-type: outside;-fx-arc-width: 5;-fx-arc-height: 5;";

	public PianoRollKey(String color, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.fillColor(color);
	}

	public void fillColor(String color) {
		this.setStyle("-fx-fill: " + color + ";" + DEFAULT_STYLE);
	}
}
