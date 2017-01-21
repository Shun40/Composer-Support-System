package gui;
import static gui.constants.NoteGridConstants.*;
import static gui.constants.UniversalConstants.*;

import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;

/**
 * 小節の表示領域(小節番号ラベルやコード選択ボタンが置かれた領域)のクラス
 * @author Shun Yamashita
 */

public class MeasureArea extends Group {
	private int measureCount;
	private ChordPair chords[];
	private Rectangle[] measureFrames;
	private Label[] measureLabels;
	private final ToggleGroup toggleGroup;
	private RadioButton[] predictionTargetButtons;
	private ChordSelectorPair[] chordSelectorPairs;
	private Line[] vFrameLines;
	private Pianoroll parent;

	public MeasureArea(int measureCount, int x, int y, Pianoroll parent) {
		super();
		this.measureCount = measureCount;
		this.toggleGroup = new ToggleGroup();
		this.parent = parent;
		setClip(new Rectangle(MEASURE_WIDTH * SHOW_MEASURE_COUNT + 0.5, 64 + 0.5)); // 実際に表示する領域サイズ
		setupPoint(x, y);
		setupChords();
		setupMeasureFrames();
		setupMeasureLabels();
		setupPredictionTargetButtons();
		setupChordSelectorPairs();
		setupFrameLines();
		setupOptionalLines();
		setupEventListener();
		setPredictionTarget(1);
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupChords() {
		chords = new ChordPair[measureCount];
		for(int measure = 0; measure < measureCount; measure++) {
			chords[measure] = new ChordPair();
		}
	}

	public void setupMeasureFrames() {
		measureFrames = new Rectangle[measureCount];
		for(int n = 0; n < measureCount; n++) {
			int x = MEASURE_WIDTH * n;
			int y = 0;
			int w = MEASURE_WIDTH;
			int h = 48;
			measureFrames[n] = new Rectangle(x + 0.5, y + 0.5, w, h);
			measureFrames[n].setStyle("-fx-stroke: #FFFFFF;-fx-stroke-type: centered;");
			measureFrames[n].setStyle("-fx-fill: #FFFFFF;" + DEFAULT_STYLE);
			getChildren().add(measureFrames[n]);
		}
	}

	public void setupMeasureLabels() {
		measureLabels = new Label[measureCount];
		for(int n = 0; n < measureCount; n++) {
			int x = 4 + MEASURE_WIDTH * n;
			int y = 2;
			measureLabels[n] = new Label(Integer.toString(n + 1));
			measureLabels[n].setFont(Font.font("Arial", 12));
			measureLabels[n].setTextFill(Color.web("#000000"));
			measureLabels[n].setLayoutX(x);
			measureLabels[n].setLayoutY(y);
			getChildren().add(measureLabels[n]);
		}
	}

	public void setupPredictionTargetButtons() {
		predictionTargetButtons = new RadioButton[measureCount];
		for(int n = 0; n < measureCount; n++) {
			int x = 140 + MEASURE_WIDTH * n;
			int y = 2;
			predictionTargetButtons[n] = new RadioButton("");
			predictionTargetButtons[n].setUserData(n+1);
			predictionTargetButtons[n].setLayoutX(x);
			predictionTargetButtons[n].setLayoutY(y);
			predictionTargetButtons[n].setToggleGroup(toggleGroup);
			getChildren().add(predictionTargetButtons[n]);
		}
	}

	public void setupEventListener() {
		toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) -> {
			if(oldToggle != null) resetPredictionTarget((int)oldToggle.getUserData());
			setPredictionTarget((int)newToggle.getUserData());
		});
	}

	public void setupChordSelectorPairs() {
		chordSelectorPairs = new ChordSelectorPair[measureCount];
		for(int measure = 0; measure < measureCount; measure++) {
			int x = 3 + MEASURE_WIDTH * measure;
			int y = 20;
			chordSelectorPairs[measure] = new ChordSelectorPair(measure + 1, x, y, this);
			getChildren().add(chordSelectorPairs[measure]);
		}
	}

	public void setupFrameLines() {
		// 垂直方向のライン
		vFrameLines = new Line[measureCount + 1];
		int vStartY = 0;
		int vEndY = vStartY + 47;
		for(int n = 0; n < measureCount + 1; n++) {
			int x = MEASURE_WIDTH * n;
			vFrameLines[n] = new Line(x + 0.5, vStartY, x + 0.5, vEndY);
			vFrameLines[n].setStrokeLineCap(StrokeLineCap.BUTT);
			getChildren().add(vFrameLines[n]);
		}
		// 水平方向のライン
		int hStartX = 0;
		int hEndX = MEASURE_WIDTH * measureCount;
		int[] hStartY = {0, 47};
		int[] hEndY = hStartY;
		for(int n = 0; n < hStartY.length; n++) {
			Line line = new Line(hStartX, hStartY[n] + 0.5, hEndX, hEndY[n] + 0.5);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			getChildren().add(line);
		}
	}

	public void setupOptionalLines() {
		int[] xArray = {0, MEASURE_WIDTH * SHOW_MEASURE_COUNT};
		int startY = 0;
		int endY = startY + 47;
		for(int n = 0; n < xArray.length; n++) {
			Line line = new Line(xArray[n] + 0.5, startY, xArray[n] + 0.5, endY);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			getChildren().add(line);
		}
	}

	public void translate(int move) {
		for(Rectangle measureFrame : measureFrames) {
			measureFrame.setTranslateX(move);
		}
		for(Label measureLabel : measureLabels) {
			measureLabel.setTranslateX(move);
		}
		for(ToggleButton arrangeTargetButton : predictionTargetButtons) {
			arrangeTargetButton.setTranslateX(move);
		}
		for(ChordSelectorPair chordSelectoPair : chordSelectorPairs) {
			chordSelectoPair.setTranslateX(move);
		}
		for(Line vFrameLine : vFrameLines) {
			vFrameLine.setTranslateX(move);
		}
	}

	public void setPredictionTarget(int targetMeasure) {
		toggleGroup.selectToggle(predictionTargetButtons[targetMeasure - 1]);
		measureFrames[targetMeasure - 1].setStyle("-fx-stroke: #7FFFD4;-fx-stroke-type: centered;");
		measureFrames[targetMeasure - 1].setStyle("-fx-fill: #7FFFD4;" + DEFAULT_STYLE);
		parent.setPredictionPatternList(targetMeasure);
	}

	public void resetPredictionTarget(int targetMeasure) {
		measureFrames[targetMeasure - 1].setStyle("-fx-stroke: #FFFFFF;-fx-stroke-type: centered;");
		measureFrames[targetMeasure - 1].setStyle("-fx-fill: #FFFFFF;" + DEFAULT_STYLE);
	}

	public int getPredictionTarget() {
		return (int)toggleGroup.getSelectedToggle().getUserData();
	}

	public void makeAccompaniment(String chord, int measure, int index) {
		parent.makeAccompaniment(chord, measure, index);
	}

	public String getChord(int measure, int index) {
		return chords[measure - 1].getChord(index);
	}
	public void setChord(String chord, int measure, int index) {
		this.chords[measure - 1].setChord(chord, index);
	}
}
