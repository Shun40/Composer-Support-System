import static constants.KeyConstants.*;

import java.util.HashMap;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

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
	private static final HashMap<String, Integer> midiNumbers = new HashMap<String, Integer>() {
		{put("C", 0);} {put("C#", 1);} {put("D", 2);} {put("D#", 3);} {put("E", 4);}
		{put("F", 5);} {put("F#", 6);} {put("G", 7);} {put("G#", 8);} {put("A", 9);} {put("A#", 10);} {put("B", 11);}
	};

	private String interval;
	private int octave;
	private Synthesizer synth;
	private Rectangle frame;
	private Label positionLabel;

	public Key(String interval, int octave, int x, int y, int width, int height) {
		super();
		this.interval = interval;
		this.octave = octave;
		setupSynth();
		setupFrame(x, y, width, height);
		setupPositionLabel();
		setupEventListener();
	}

	public void setupSynth() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
		} catch(Exception err) {
			err.printStackTrace();
		}
		synth.getChannels()[0].programChange(81);
	}

	public void setupFrame(int x, int y, int width, int height) {
		frame = new Rectangle(x + 0.5, y + 0.5, width, height);
		if(interval.contains("#") || interval.contains("♭")) {
			frame.setStyle("-fx-fill: " + BLACK_KEY_COLOR + ";" + DEFAULT_STYLE);
		} else {
			frame.setStyle("-fx-fill: " + WHITE_KEY_COLOR + ";" + DEFAULT_STYLE);
		}
		getChildren().add(frame);
	}

	public void setupPositionLabel() {
		int x = (int)frame.getWidth() - 20;
		int y = (int)frame.getY() + 3;
		positionLabel = new Label(interval + Integer.toString(octave));
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
		if(interval.contains("#") || interval.contains("♭")) {
			frame.setStyle("-fx-fill: " + BLACK_KEY_SHADOW_COLOR + ";" + DEFAULT_STYLE);
		} else {
			frame.setStyle("-fx-fill: " + WHITE_KEY_SHADOW_COLOR + ";" + DEFAULT_STYLE);
		}
		// 発音
		int noteNo = 60 + (12 * (octave - 4)) + midiNumbers.get(interval);
		synth.getChannels()[0].noteOn(noteNo, 100);
	}

	public void release(MouseEvent e) {
		// release時の鍵盤グラフィック変化
		if(interval.contains("#") || interval.contains("♭")) {
			frame.setStyle("-fx-fill: " + BLACK_KEY_COLOR + ";" + DEFAULT_STYLE);
		} else {
			frame.setStyle("-fx-fill: " + WHITE_KEY_COLOR + ";" + DEFAULT_STYLE);
		}
		// 無発音
		synth.getChannels()[0].allNotesOff();
	}
}
