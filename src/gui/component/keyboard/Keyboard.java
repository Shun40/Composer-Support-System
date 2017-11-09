package gui.component.keyboard;

import java.util.ArrayList;
import java.util.List;

import gui.GuiConstants;
import gui.component.base.GroupBase;
import gui.component.keyboard.key.Key;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import midi.MidiUtil;
import system.AppConstants;

/**
 * ピアノロール左側に表示されるキーボード
 * @author Shun Yamashita
 */

public class Keyboard extends GroupBase {
	private List<Key> whiteKeys;
	private List<Key> blackKeys;

	public Keyboard() {
		super();
		setClip(new Rectangle(GuiConstants.Keyboard.WHITE_KEY_WIDTH + 0.5, GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.SHOW_OCTAVES + 0.5)); // 実際に表示する領域サイズ
		setPoint(GuiConstants.Keyboard.X, GuiConstants.Keyboard.Y);
		setupWhiteKeyBoard();
		setupBlackKeyBoard();
		setupFrameLines();
	}

	public void setupWhiteKeyBoard() {
		String[] intervals = {"B", "A", "G", "F", "E", "D", "C"};
		whiteKeys = new ArrayList<Key>();
		for(int m = 0; m < AppConstants.Settings.OCTAVES; m++) {
			for(int n = 0; n < GuiConstants.Keyboard.WHITE_KEY_COUNT; n++) {
				String interval = intervals[n];
				int octave = AppConstants.Settings.MAX_OCTAVE - m;
				int pitch = MidiUtil.calcPitch(interval, octave);
				AppConstants.AvailabilityType availabilityType = AppConstants.Settings.AVAILABLE_PITCHES.contains(pitch) ? AppConstants.AvailabilityType.AVAILABLE : AppConstants.AvailabilityType.UNAVAILABLE;
				AppConstants.KeyType keyType = AppConstants.KeyType.WHITE;
				int x = 0;
				int y = GuiConstants.Keyboard.WHITE_KEY_Y[n] + GuiConstants.Keyboard.Y_OFFSET * m;
				int width = GuiConstants.Keyboard.WHITE_KEY_WIDTH;
				int height = GuiConstants.Keyboard.WHITE_KEY_HEIGHT[n];
				Key key = new Key(pitch, availabilityType, keyType, x, y, width, height);
				getChildren().add(key.getView());
				whiteKeys.add(key);
			}
		}
	}

	public void setupBlackKeyBoard() {
		String[] intervals = {"A#", "G#", "F#", "D#", "C#"};
		blackKeys = new ArrayList<Key>();
		for(int m = 0; m < AppConstants.Settings.OCTAVES; m++) {
			for(int n = 0; n < GuiConstants.Keyboard.BLACK_KEY_COUNT; n++) {
				String interval = intervals[n];
				int octave = AppConstants.Settings.MAX_OCTAVE - m;
				int pitch = MidiUtil.calcPitch(interval, octave);
				AppConstants.AvailabilityType availabilityType = AppConstants.Settings.AVAILABLE_PITCHES.contains(pitch) ? AppConstants.AvailabilityType.AVAILABLE : AppConstants.AvailabilityType.UNAVAILABLE;
				AppConstants.KeyType keyType = AppConstants.KeyType.BLACK;
				int x = 0;
				int y = GuiConstants.Keyboard.BLACK_KEY_Y[n] + GuiConstants.Keyboard.Y_OFFSET * m;
				int w = GuiConstants.Keyboard.BLACK_KEY_WIDTH;
				int h = GuiConstants.Keyboard.BLACK_KEY_HEIGHT[n];
				Key key = new Key(pitch, availabilityType, keyType, x, y, w, h);
				getChildren().add(key.getView());
				blackKeys.add(key);
			}
		}
	}

	public void setupFrameLines() {
		int[] yArray = {0, GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.SHOW_OCTAVES};
		int startX = 0;
		int endX = startX + GuiConstants.Keyboard.WHITE_KEY_WIDTH;
		for(int n = 0; n < yArray.length; n++) {
			Line line = new Line(startX, yArray[n] + 0.5, endX, yArray[n] + 0.5);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			getChildren().add(line);
		}
	}

	public void translate(double move) {
		for(Key whiteKey : whiteKeys) {
			whiteKey.getView().setTranslateY(move);
		}
		for(Key blackKey : blackKeys) {
			blackKey.getView().setTranslateY(move);
		}
	}

	public void changeInstrument(int track, int program) {
		for(Key whiteKey : whiteKeys) {
			whiteKey.getModel().setTrack(track);
			whiteKey.getModel().setProgram(program);
		}
		for(Key blackKey : blackKeys) {
			blackKey.getModel().setTrack(track);
			blackKey.getModel().setProgram(program);
		}
	}
}
