package gui.component.keyboard.key;

import gui.GuiConstants;
import gui.component.base.GroupBase;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import midi.MidiUtil;
import system.AppConstants;

public class KeyView extends GroupBase {
	private Key owner;
	private Rectangle rectangle;
	private Label label;
	private String releasedColor;
	private String pressedColor;

	public KeyView(AppConstants.AvailabilityType availabilityType, AppConstants.KeyType keyType, int x, int y, int width, int height, Key owner) {
		this.owner = owner;
		setupRectangle(x, y, width, height);
		setupLabel();
		setupColor(availabilityType, keyType);
		setupEventListener(availabilityType);
	}

	private void setupRectangle(int x, int y, int width, int height) {
		rectangle = new Rectangle(x + 0.5, y + 0.5, width, height);
		getChildren().add(rectangle);
	}

	private void setupLabel() {
		int x = (int)rectangle.getWidth() - 20;
		int y = (int)rectangle.getY() + 3;
		label = new Label(owner.getModel().getKeyname());
		label.setFont(Font.font("Arial", 12));
		label.setTextFill(Color.web("#000000"));
		label.setLayoutX(x);
		label.setLayoutY(y);
		label.setVisible(false);
		if(MidiUtil.getInterval(owner.getModel().getPitch()).equals("C")) { // 音程がCの鍵盤のみ鍵盤名を表示する
			label.setVisible(true);
		}
		getChildren().add(label);
	}

	private void setupColor(AppConstants.AvailabilityType availabilityType, AppConstants.KeyType keyType) {
		if(availabilityType == AppConstants.AvailabilityType.AVAILABLE) {
			switch(keyType) {
			case WHITE:
				releasedColor = GuiConstants.Key.AVAILABLE_WHITE_RELEASED_COLORCODE;
				pressedColor = GuiConstants.Key.AVAILABLE_WHITE_PRESSED_COLORCODE;
				break;
			case BLACK:
				releasedColor = GuiConstants.Key.AVAILABLE_BLACK_RELEASED_COLORCODE;
				pressedColor = GuiConstants.Key.AVAILABLE_BLACK_PRESSED_COLORCODE;
				break;
			}
		} else {
			switch(keyType) {
			case WHITE:
				releasedColor = GuiConstants.Key.UNAVAILABLE_WHITE_RELEASED_COLORCODE;
				pressedColor = GuiConstants.Key.UNAVAILABLE_WHITE_PRESSED_COLORCODE;
				break;
			case BLACK:
				releasedColor = GuiConstants.Key.UNAVAILABLE_BLACK_RELEASED_COLORCODE;
				pressedColor = GuiConstants.Key.UNAVAILABLE_BLACK_PRESSED_COLORCODE;
				break;
			}
		}
		setReleasedColor();
	}

	public void showLabel() {
		label.setVisible(true);
	}

	private void setReleasedColor() {
		rectangle.setStyle("-fx-fill: " + releasedColor + ";" + GuiConstants.Key.STYLE);
	}

	private void setPressedColor() {
		rectangle.setStyle("-fx-fill: " + pressedColor + ";" + GuiConstants.Key.STYLE);
	}

	public void setupEventListener(AppConstants.AvailabilityType availabilityType) {
		if(availabilityType == AppConstants.AvailabilityType.UNAVAILABLE) return;
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				press(e);
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				release(e);
			}
		});
	}

	public void press(MouseEvent e) {
		setPressedColor(); // 押下時の鍵盤グラフィック変化
		MidiUtil.toneOn(owner.getModel().getTrack(), owner.getModel().getProgram(), owner.getModel().getPitch(), 100); // 発音
	}

	public void release(MouseEvent e) {
		setReleasedColor(); // 無押下時の鍵盤グラフィック変化
		MidiUtil.toneOff(owner.getModel().getTrack()); // 無発音
	}
}
