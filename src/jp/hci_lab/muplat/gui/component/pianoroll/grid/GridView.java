package gui.component.pianoroll.grid;

import gui.GuiConstants;
import gui.component.base.RectangleBase;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import midi.MidiUtil;
import system.AppConstants;

public class GridView extends RectangleBase {
	private String color;
	private Grid owner;

	public GridView(AppConstants.AvailabilityType availabilityType, AppConstants.KeyType keyType, int x, int y, int width, int height, Grid owner) {
		super();
		this.owner = owner;
		setPoint(x + 0.5, y + 0.5);
		setSize(width, height);
		setupColor(availabilityType, keyType);
		setupEventListener(availabilityType);
	}

	private void setupColor(AppConstants.AvailabilityType availabilityType, AppConstants.KeyType keyType) {
		if(availabilityType == AppConstants.AvailabilityType.AVAILABLE) {
			switch(keyType) {
			case WHITE:
				color = GuiConstants.Grid.AVAILABLE_WHITE_COLORCODE;
				break;
			case BLACK:
				color = GuiConstants.Grid.AVAILABLE_BLACK_COLORCODE;
				break;
			}
		} else {
			switch(keyType) {
			case WHITE:
				color = GuiConstants.Grid.UNAVAILABLE_WHITE_COLORCODE;
				break;
			case BLACK:
				color = GuiConstants.Grid.UNAVAILABLE_BLACK_COLORCODE;
				break;
			}
		}
		setStyle("-fx-fill: " + color + ";" + GuiConstants.Grid.STYLE);
	}

	private void setupEventListener(AppConstants.AvailabilityType availabilityType) {
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

	private void press(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // 左クリック
			putNote(e);
		}
	}

	private void release(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // 左クリック
			MidiUtil.toneOff(owner.getCurrentTrack()); // 無発音
		}
	}

	private void putNote(MouseEvent e) {
		int putX = 0;
		int putY = (int)getY();
		int width = 0;
		int height = (int)getHeight();
		int clickX = (int)(e.getX() - getX());
		switch(owner.getModel().getResolution()) {
		case 4:
			width = GuiConstants.Pianoroll.BEAT_WIDTH;
			if(0 <= clickX && clickX < width) {
				putX = (int)getX();
			}
			break;
		case 8:
			width = GuiConstants.Pianoroll.BEAT_WIDTH / 2;
			if(0 <= clickX && clickX < width) {
				putX = (int)getX();
			}
			else if(width <= clickX && clickX < width * 2) {
				putX = (int)getX() + width;
			}
			break;
		case 16:
			width = GuiConstants.Pianoroll.BEAT_WIDTH / 4;
			if(0 <= clickX && clickX < width) {
				putX = (int)getX();
			}
			else if(width <= clickX && clickX < width * 2) {
				putX = (int)getX() + width;
			}
			else if(width * 2 <= clickX && clickX < width * 3) {
				putX = (int)getX() + width * 2;
			}
			else if(width * 3 <= clickX && clickX < width * 4) {
				putX = (int)getX() + width * 3;
			}
			break;
		}
		owner.putNote(putX, putY, width, height, true);
	}
}
