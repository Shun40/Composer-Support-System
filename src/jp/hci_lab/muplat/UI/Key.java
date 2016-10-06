import static constants.KeyConstants.*;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * キーボードの鍵盤のクラス
 * @author Shun Yamashita
 */
public class Key extends Group {
	private String position;
	private Rectangle frame;
	private Label positionLabel;

	public Key(String position, int x, int y, int width, int height) {
		super();
		this.position = position;
		setupFrame(x, y, width, height);
		setupPositionLabel();
		setupEventListener();
	}

	public void setupFrame(int x, int y, int width, int height) {
		frame = new Rectangle(x + 0.5, y + 0.5, width, height);
		if(position.contains("#") || position.contains("♭")) {
			frame.setStyle("-fx-fill: " + BLACK_KEY_COLOR + ";" + DEFAULT_STYLE);
		} else {
			frame.setStyle("-fx-fill: " + WHITE_KEY_COLOR + ";" + DEFAULT_STYLE);
		}
		getChildren().add(frame);
	}

	public void setupPositionLabel() {
		int x = (int)frame.getWidth() - 20;
		int y = (int)frame.getY() + 3;
		positionLabel = new Label(position);
		positionLabel.setFont(Font.font("Arial", 12));
		positionLabel.setTextFill(Color.web("#000000"));
		positionLabel.setLayoutX(x);
		positionLabel.setLayoutY(y);
		positionLabel.setVisible(false);
		getChildren().add(positionLabel);
	}

	public void showPositionLabel() {
		positionLabel.setVisible(true);
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
