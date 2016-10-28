import static constants.KeyConstants.*;
import static constants.UniversalConstants.*;

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
	private int track;
	private int program;
	private int note;
	private String position;
	private String normalColor;
	private String shadowColor;
	private Rectangle frame;

	public Key(String interval, int octave, int x, int y, int width, int height) {
		super();
		this.track = 1;
		this.program = PROGRAM_NUMBERS[track - 1];
		this.note = 60 + (12 * (octave - 4)) + MIDI_NUMBERS.get(interval); // 60 is midi number of C4
		this.position = interval + Integer.toString(octave);
		setupColor(interval);
		setupFrame(x, y, width, height);
		setupEventListener();
	}

	public void setupColor(String interval) {
		if(interval.contains("#") || interval.contains("♭")) {
			normalColor = BLACK_KEY_COLOR;
			shadowColor = BLACK_KEY_SHADOW_COLOR;
		} else {
			normalColor = WHITE_KEY_COLOR;
			shadowColor = WHITE_KEY_SHADOW_COLOR;
		}
	}

	public void setupFrame(int x, int y, int width, int height) {
		frame = new Rectangle(x + 0.5, y + 0.5, width, height);
		frame.setStyle("-fx-fill: " + normalColor + ";" + DEFAULT_STYLE);
		getChildren().add(frame);
	}

	public void showPositionLabel() {
		int x = (int)frame.getWidth() - 20;
		int y = (int)frame.getY() + 3;
		Label positionLabel = new Label(position);
		positionLabel.setFont(Font.font("Arial", 12));
		positionLabel.setTextFill(Color.web("#000000"));
		positionLabel.setLayoutX(x);
		positionLabel.setLayoutY(y);
		getChildren().add(positionLabel);
	}

	public void setupEventListener() {
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Key.this.press(e);
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Key.this.release(e);
			}
		});
	}

	public void press(MouseEvent e) {
		// press時の鍵盤グラフィック変化
		frame.setStyle("-fx-fill: " + shadowColor + ";" + DEFAULT_STYLE);
		// 発音
		toneOn();
	}

	public void release(MouseEvent e) {
		// release時の鍵盤グラフィック変化
		frame.setStyle("-fx-fill: " + normalColor + ";" + DEFAULT_STYLE);
		// 無発音
		toneOff();
	}

	public void toneOn() {
		UISynth.synth.getChannels()[track - 1].programChange(program - 1);
		UISynth.synth.getChannels()[track - 1].noteOn(note, 100);
	}

	public void toneOff() {
		UISynth.synth.getChannels()[track - 1].allNotesOff();
	}

	public void setTrack(int track) { this.track = track; }
	public void setProgram(int program) { this.program = program; }
}
