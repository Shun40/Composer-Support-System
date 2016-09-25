import static constants.KeyConstants.*;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * キーボードの鍵盤のクラス
 * @author Shun Yamashita
 */
public class Key extends Rectangle {
	private boolean isWhite;

	public Key(boolean isWhite, int x, int y, int width, int height) {
		super(x + 0.5, y + 0.5, width, height);
		this.isWhite = isWhite;
		setupColor();
		setupEventListener();
	}

	public void setupColor() {
		if(isWhite) {
			setStyle("-fx-fill: " + WHITE_KEY_COLOR + ";" + DEFAULT_STYLE);
		} else {
			setStyle("-fx-fill: " + BLACK_KEY_COLOR + ";" + DEFAULT_STYLE);
		}
	}

	public void setupEventListener() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Key.this.click(e);
			}
		});
	}

	public void click(MouseEvent e) {
		System.out.println("Key was clicked.");
	}
}
