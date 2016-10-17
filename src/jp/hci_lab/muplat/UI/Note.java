import static constants.NoteConstants.*;
import static constants.UniversalConstants.*;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/**
 * ノートのクラス
 * @author Shun Yamashita
 */
public class Note extends Rectangle {
	private int progNumber;
	private int resolution;
	private float minX;
	private float maxX;
	private float minY;
	private float maxY;
	private String pressedPos;
	private int measure;
	private int beat;
	private int place;
	private int duration;
	private String interval;
	private int octave;
	private boolean canTone; // 発音許可フラグ
	private EditArea parent;

	public Note(int x, int y, int width, int height, int resolution, int minX, int maxX, int minY, int maxY, boolean canTone, EditArea parent) {
		super(x + 0.5, y + 0.5, width, height);
		this.progNumber = 0;
		this.resolution = resolution;
		this.minX = minX + 0.5f;
		this.maxX = maxX + 0.5f;
		this.minY = minY + 0.5f;
		this.maxY = maxY + 0.5f;
		this.canTone = canTone;
		this.parent = parent;
		setupColor();
		setupEventListener();
		updateNoteInfo();
	}

	public void setupColor() {
		Stop[] stops = new Stop[] { new Stop(0.0, Color.BLUE), new Stop(1.0, Color.AQUAMARINE) };
		LinearGradient grad = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
		setFill(grad);
		setStyle(DEFAULT_STYLE);
	}

	public void setupEventListener() {
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Note.this.press(e);
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Note.this.release(e);
			}
		});
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Note.this.drag(e);
			}
		});
	}

	public void press(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // Left click
			int pressX = (int)(e.getX() - getX());
			if(0 <= pressX && pressX < getWidth() / 3) {
				pressedPos = "left";
			}
			if(getWidth() / 3 <= pressX && pressX < (getWidth() / 3) * 2) {
				pressedPos = "center";
			}
			if((getWidth() / 3) * 2 <= pressX && pressX < getWidth()) {
				pressedPos = "right";
			}
			// 発音
			toneOn();
		} else { // Right Click
			removeNote();
		}
	}

	public void release(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // Left click
			// 無発音
			toneOff();
		} else {
		}
	}

	public void drag(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // Left click
			verticalDrug(e);
			horizontalDrug2(e);
			updateNoteInfo();
		}
	}

	public void verticalDrug(MouseEvent e) {
		for(int n = 0; n < 12 * parent.getOctaveCount(); n++) {
			float min = minY + 12 * n;
			float max = min + 12;
			if(min <= e.getY() && e.getY() < max) {
				if(minY <= e.getY() && e.getY() < maxY) {
					setY(min);
				}
			}
		}
	}

	public void horizontalDrug2(MouseEvent e) {
		int moveX = (int)(e.getX() - getX());
		int resolutionWidth = MEASURE_WIDTH / resolution;
		if(pressedPos.equals("right") && 0 <= moveX) {
			for(int n = 0; n < resolution; n++) {
				int min = resolutionWidth * n;
				int max = min + resolutionWidth;
				if(min <= moveX && moveX < max) {
					if(e.getX() < maxX) {
						setWidth(resolutionWidth * (n + 1));
					}
				}
			}
		} else if(pressedPos.equals("left") && moveX <= 0) {
			/*
			for(int n = 0; n < resolution; n++) {
				int min = -resolutionWidth * n;
				int max = min + resolutionWidth;
				if(min <= moveX && moveX < max) {
					if(minX <= e.getX()) {
						setWidth(resolutionWidth * (n + 1));
					}
				}
			}
			*/
		}
	}

	public void removeNote() {
		parent.removeNote(this);
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	public void updateNoteInfo() {
		int x = (int)(getX() - 0.5);
		int y = (int)(getY() - 0.5);
		String oldInterval = interval;
		String newInterval = INTERVALS[(y % MEASURE_HEIGHT / 12)];
		measure = parent.getCurrentMeasure() + (x / MEASURE_WIDTH);
		beat = (x % MEASURE_WIDTH) / BEAT_WIDTH + 1;
		place = (int)((x % BEAT_WIDTH * 0.1) * 240); // 240 is number of tick of 1/16 musical note
		duration = (int)((getWidth() / BEAT_WIDTH) * 960); // 960 is number of tick of 1 measure
		interval = INTERVALS[(y % MEASURE_HEIGHT / 12)];
		octave = MAX_OCTAVE - (y / MEASURE_HEIGHT);
		// 音高が変化したら再発音
		if(oldInterval != newInterval) {
			// 無発音
			toneOff();
			// 発音
			toneOn();
		}
	}

	public void toneOn() {
		if(!canTone) return;
		int noteNumber = 60 + (12 * (octave - 4)) + MIDI_NUMBERS.get(interval);
		UISynth.synth.getChannels()[0].programChange(progNumber);
		UISynth.synth.getChannels()[0].noteOn(noteNumber, 100);
	}

	public void toneOff() {
		UISynth.synth.getChannels()[0].allNotesOff();
	}

	public int getMeasure() { return measure; };
	public int getBeat() { return beat; }
	public int getPlace() { return place; }
	public int getDuration() { return duration; }
	public String getInterval() { return interval; }
	public int getOctave() { return octave; }
	public void setCanTone(boolean canTone) { this.canTone = canTone; }
}
