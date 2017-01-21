package gui;
import static gui.constants.NoteGridConstants.*;
import static gui.constants.UniversalConstants.*;

import java.util.Arrays;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * ノートが配置される格子のクラス
 * @author Shun Yamashita
 */
public class NoteGrid extends Group {
	private int resolution;
	private String color;
	private Rectangle frame;
	private EditArea parent;

	public NoteGrid(int resolution, String interval, int octave, int x, int y, int width, int height, EditArea parent) {
		super();
		this.resolution = resolution;
		this.parent = parent;
		setupColor(interval, octave);
		setupFrame(x, y, width, height);
		setupEventListener(interval, octave);
	}

	public void setupColor(String interval, int octave) {
		int pitch = 60 + (12 * (octave - 4)) + MIDI_NUMBERS.get(interval); // 60 is midi number of C4
		if(interval.contains("#") || interval.contains("♭")) {
			if(Arrays.asList(CAN_USE_PITCHES).contains(pitch)) {
				color = BLACK_GRID_COLOR;
			} else {
				color = CANNOT_USE_BLACK_GRID_COLOR;
			}
		} else {
			if(Arrays.asList(CAN_USE_PITCHES).contains(pitch)) {
				color = WHITE_GRID_COLOR;
			} else {
				color = CANNOT_USE_WHITE_GRID_COLOR;
			}
		}
	}

	public void setupFrame(int x, int y, int width, int height) {
		frame = new Rectangle(x + 0.5, y + 0.5, width, height);
		frame.setStyle(DEFAULT_STYLE);
		frame.setStyle("-fx-fill: " + color + ";" + DEFAULT_STYLE);
		getChildren().add(frame);
	}

	public void setupEventListener(String interval, int octave) {
		int pitch = 60 + (12 * (octave - 4)) + MIDI_NUMBERS.get(interval); // 60 is midi number of C4
		if(!Arrays.asList(CAN_USE_PITCHES).contains(pitch)) return;
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				NoteGrid.this.press(e);
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				NoteGrid.this.release(e);
			}
		});
	}

	public void press(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // Left click
			putNote(e);
		} else {
		}
	}

	public void release(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // Left click
			// 無発音
			toneOff();
		} else {
		}
	}

	public void putNote(MouseEvent e) {
		int putX = 0;
		int putY = (int)frame.getY();
		int width = 0;
		int height = (int)frame.getHeight();
		int clickX = (int)(e.getX() - frame.getX());
		if(resolution == 4) {
			width = BEAT_WIDTH;
			if(0 <= clickX && clickX < width) putX = (int)frame.getX();
		}
		if(resolution == 8) {
			width = BEAT_WIDTH / 2;
			if(0 <= clickX && clickX < width) putX = (int)frame.getX();
			else if(width <= clickX && clickX < width * 2) putX = (int)frame.getX() + width;
		}
		if(resolution == 16) {
			width = BEAT_WIDTH / 4;
			if(0 <= clickX && clickX < width) putX = (int)frame.getX();
			else if(width <= clickX && clickX < width * 2) putX = (int)frame.getX() + width;
			else if(width * 2 <= clickX && clickX < width * 3) putX = (int)frame.getX() + width * 2;
			else if(width * 3 <= clickX && clickX < width * 4) putX = (int)frame.getX() + width * 3;
		}
		parent.putNote(putX, putY, width, height, true);
	}

	public void toneOff() {
		UISynth.toneOff(parent.getCurrentTrack());
	}

	public void setResolution(int resolution) { this.resolution = resolution; }
}
