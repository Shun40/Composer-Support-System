package gui.component.pianoroll;

import gui.GuiConstants;
import gui.component.base.GroupBase;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import midi.MidiUtil;
import system.AppConstants;

/**
 * ノートを配置するキャンバスの役割を担うクラス
 * @author Shun
 *
 */
public class Sheet extends GroupBase {
	private Line[] gridLines;
	private Pianoroll owner;

	public Sheet(Pianoroll owner) {
		this.owner = owner;
		setupPitchRectangles();
		setupGridLines();
		setupBeatLines();
		setupMeasureLines();
		setupFrameLines();
		setupEventListener();
	}

	private void setupFrameLines() {
		int leftTopX = 0;
		int leftTopY = 0;
		int leftBottomX = 0;
		int leftBottomY = GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.OCTAVES;
		int rightTopX = GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.MEASURES;
		int rightTopY = 0;
		int rightBottomX = GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.MEASURES;
		int rightBottomY = GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.OCTAVES;
		getChildren().add(new Line(leftTopX + 0.5, leftTopY + 0.5, leftBottomX + 0.5, leftBottomY + 0.5));
		getChildren().add(new Line(rightTopX + 0.5, rightTopY + 0.5, rightBottomX + 0.5, rightBottomY + 0.5));
		getChildren().add(new Line(leftTopX + 0.5, leftTopY + 0.5, rightTopX + 0.5, rightTopY + 0.5));
		getChildren().add(new Line(leftBottomX + 0.5, leftBottomY + 0.5, rightBottomX + 0.5, rightBottomY + 0.5));
	}

	private void setupMeasureLines() {
		int startY = 0;
		int endY = startY + GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.OCTAVES;
		for(int n = 0; n < AppConstants.Settings.MEASURES; n++) {
			int x = GuiConstants.Pianoroll.MEASURE_WIDTH * n;
			Line line = new Line(x + 0.5, startY, x + 0.5, endY);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			getChildren().add(line);
		}
	}

	private void setupBeatLines() {
		int startY = 0;
		int endY = startY + GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.OCTAVES;
		for(int n = 0; n < AppConstants.Settings.MEASURES * 4; n++) {
			int x = GuiConstants.Pianoroll.BEAT_WIDTH * n;
			Line line = new Line(x + 0.5, startY, x + 0.5, endY);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			line.setStroke(Color.web("#888888"));
			getChildren().add(line);
		}
	}

	private void setupGridLines() {
		gridLines = new Line[AppConstants.Settings.MEASURES * 16];
		int startY = 0;
		int endY = startY + GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.OCTAVES;
		for(int n = 0; n < gridLines.length; n++) {
			int x = (GuiConstants.Pianoroll.BEAT_WIDTH / 4) * n;
			gridLines[n] = new Line(x + 0.5, startY, x + 0.5, endY);
			gridLines[n].setStrokeLineCap(StrokeLineCap.BUTT);
			gridLines[n].setStroke(Color.web("#888888"));
			gridLines[n].getStrokeDashArray().addAll(4.0, 2.0);
			gridLines[n].setVisible(false);
			getChildren().add(gridLines[n]);
		}
	}

	private void setupPitchRectangles() {
		for(int n = 0; n < AppConstants.Settings.OCTAVES * 12; n++) {
			int x = 0;
			int y = GuiConstants.Grid.Y[(int)(n % 12)] + GuiConstants.Grid.Y_OFFSET * (int)(n / 12);
			int width = GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.MEASURES;
			int height = GuiConstants.Pianoroll.MEASURE_HEIGHT / 12;
			Rectangle rectangle = new Rectangle(x + 0.5, y + 0.5, width, height);
			int pitch = calcPitch(y);
			if(AppConstants.Settings.AVAILABLE_PITCHES.contains(pitch)) {
				if(MidiUtil.getKeyType(MidiUtil.getInterval(pitch)) == AppConstants.KeyType.WHITE) {
					rectangle.setStyle("-fx-fill: " + "#FFFFFF" + ";" + "-fx-stroke: #888888;-fx-stroke-type: centered;");
				} else {
					rectangle.setStyle("-fx-fill: " + "#DDDDDD" + ";" + "-fx-stroke: #888888;-fx-stroke-type: centered;");
				}
			} else {
				if(MidiUtil.getKeyType(MidiUtil.getInterval(pitch)) == AppConstants.KeyType.WHITE) {
					rectangle.setStyle("-fx-fill: " + "#BBBBBB" + ";" + "-fx-stroke: #888888;-fx-stroke-type: centered;");
				} else {
					rectangle.setStyle("-fx-fill: " + "#999999" + ";" + "-fx-stroke: #888888;-fx-stroke-type: centered;");
				}
			}
			getChildren().add(rectangle);
		}
	}

	private void setupEventListener() {
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

	private int calcPitch(int y) {
		String[] intervals = {"B", "A#", "A", "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C"};
		String interval = intervals[y % GuiConstants.Pianoroll.MEASURE_HEIGHT / 12];
		int octave = AppConstants.Settings.MAX_OCTAVE - (y / GuiConstants.Pianoroll.MEASURE_HEIGHT);
		return MidiUtil.calcPitch(interval, octave);
	}

	private void putNote(MouseEvent e) {
		int pitch = calcPitch((int)e.getY());
		if(!AppConstants.Settings.AVAILABLE_PITCHES.contains(pitch)) return;
		int width = GuiConstants.Pianoroll.BEAT_WIDTH / (owner.getResolution() / 4);
		int height = GuiConstants.Pianoroll.MEASURE_HEIGHT / 12;
		int putX = (int)e.getX() / width * width;
		int putY = (int)e.getY() / height * height;
		owner.putNote(pitch, putX, putY, width, height, true);
	}

	public void updateResolution(int resolution) {
		int temp = resolution / (int)Math.pow(resolution / 4, 2);
		for(int n = 0; n < gridLines.length; n++) {
			if(n % temp == 0) {
				gridLines[n].setVisible(true);
			} else {
				gridLines[n].setVisible(false);
			}
		}
	}
}
