package gui;
import static gui.constants.NoteBlockConstants.*;
import static gui.constants.UniversalConstants.*;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/**
 * ノートのブロック（四角形）のクラス
 * @author Shun Yamashita
 */
public class NoteBlock extends Rectangle {
	private Note note;
	private float minX;
	private float maxX;
	private float minY;
	private float maxY;
	private String pressedPos;
	private boolean canTone; // 発音許可フラグ
	private EditArea parent;

	public NoteBlock(int x, int y, int width, int height, int minX, int maxX, int minY, int maxY, boolean canTone, EditArea parent) {
		super(x + 0.5, y + 0.5, width, height);
		this.minX = minX + 0.5f;
		this.maxX = maxX + 0.5f;
		this.minY = minY + 0.5f;
		this.maxY = maxY + 0.5f;
		this.canTone = canTone;
		this.parent = parent;
		setupColor();
		setupEventListener();
		setupNoteInformation();
	}

	public void setupNoteInformation() {
		int track    = parent.getCurrentTrack();
		int program  = PROGRAM_NUMBERS[track - 1];
		int pitch    = (int)((MAX_OCTAVE + 2) * 12 - ((getY() - 0.5) / getHeight()) - 1);
		int position = (int)((PPQ / 4) * ((getX() - 0.5) / 10));
		int duration = (int)((getWidth() / BEAT_WIDTH) * PPQ);
		int velocity = 100;
		note = new Note(track, program, pitch, position, duration, velocity, this);
		// 発音
		toneOn(track, program, pitch, velocity);
	}

	public void setupColor() {
		int trackNumber = parent.getCurrentTrack();
		Stop[] stops = new Stop[] { new Stop(0.0, INSTRUMENTS_DARK_COLORS[trackNumber - 1]), new Stop(1.0, INSTRUMENTS_LIGHT_COLORS[trackNumber - 1]) };
		LinearGradient grad = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
		setFill(grad);
		setStyle(DEFAULT_STYLE);
	}

	public void setupEventListener() {
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				NoteBlock.this.press(e);
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				NoteBlock.this.release(e);
			}
		});
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				NoteBlock.this.drag(e);
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
			toneOn(note.getTrack(), note.getProgram(), note.getPitch(), note.getVelocity());
		} else { // Right Click
			parent.removeNoteFromUi(this);
			parent.removeNoteFromEngine(this);
		}
	}

	public void release(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // Left click
			// 無発音
			toneOff(note.getTrack());
		} else {
		}
	}

	public void drag(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // Left click
			verticalDrug(e);
			horizontalDrug(e);
			note.updateNoteInfo();
			parent.addNoteToUi(this);
			parent.addNoteToEngine(this);
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

	public void horizontalDrug(MouseEvent e) {
		int resolution = parent.getResolution();
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

	public void updateView(int currentTrack) {
		int track = note.getTrack();
		if(track == currentTrack) {
			toFront();
			setOpacity(1.0);
			setDisable(false);
		} else {
			setOpacity(0.5);
			setDisable(true);
		}
	}

	public void toneOn(int track, int program, int pitch, int velocity) {
		if(!canTone) return;
		UISynth.changeInstrument(track, program);
		UISynth.toneOn(track, pitch, velocity);
	}

	public void toneOff(int track) {
		UISynth.toneOff(track);
	}

	public Note getNote() { return note; }
	public void setCanTone(boolean canTone) { this.canTone = canTone; }
}
