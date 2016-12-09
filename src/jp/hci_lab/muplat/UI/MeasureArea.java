import static constants.UniversalConstants.*;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;

/**
 * 小節番号の表示領域(小節番号ラベルや小節線が置かれた領域)のクラス
 * @author Shun Yamashita
 */

public class MeasureArea extends Group {
	private int measureCount;
	private String chords[];
	private Label[] measureLabels;
	private ChordSelector[] chordSelectors;
	private Line[] vFrameLines;

	public MeasureArea(int measureCount, int x, int y) {
		super();
		this.measureCount = measureCount;
		setClip(new Rectangle(MEASURE_WIDTH * SHOW_MEASURE_COUNT + 0.5, 64 + 0.5)); // 実際に表示する領域サイズ
		setupPoint(x, y);
		setupChords();
		setupMeasureLabels();
		setupChordSelector();
		setupFrameLines();
		setupOptionalLines();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupChords() {
		chords = new String[measureCount * 2];
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

	public void setupChordSelector() {
		chordSelectors = new ChordSelector[measureCount * 2];
		for(int measure = 0; measure < measureCount; measure++) {
			for(int beat = 0; beat < 2; beat++) {
				int x = 3 + MEASURE_WIDTH * measure + (MEASURE_WIDTH / 2) * beat;
				int y = 20;
				chordSelectors[measure * 2 + beat] = new ChordSelector(measure + 1, beat + 1, x, y, this);
				getChildren().add(chordSelectors[measure * 2 + beat]);
			}
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
		for(Label measureLabel : measureLabels) {
			measureLabel.setTranslateX(move);
		}
		for(ChordSelector chordSelector : chordSelectors) {
			chordSelector.setTranslateX(move);
		}
		for(Line vFrameLine : vFrameLines) {
			vFrameLine.setTranslateX(move);
		}
	}

	public String getChord(int measure, int beat) {
		return chords[(measure - 1) * 2 + (beat - 1)];
	}
	public void setChord(String chord, int measure, int beat) {
		this.chords[(measure - 1) * 2 + (beat - 1)] = chord;
	}
}
