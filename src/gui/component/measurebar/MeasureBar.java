package gui.component.measurebar;
import gui.GuiConstants;
import gui.GuiManager;
import gui.component.base.GroupBase;
import javafx.beans.value.ObservableValue;
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
import system.AppConstants;

/**
 * 小節の表示領域(小節番号ラベルやコード選択ボタンが置かれた領域)のクラス
 * @author Shun Yamashita
 */

public class MeasureBar extends GroupBase {
	private Rectangle[] measureRectangles;
	private Label[] measureLabels;
	private ToggleGroup toggleGroup;
	private RadioButton[] predictionTargetButtons;
	private ChordSelector[] chordSelectors;
	private GuiManager owner;

	public MeasureBar(GuiManager owner) {
		super();
		this.owner = owner;
		setPoint(GuiConstants.Measurebar.X, GuiConstants.Measurebar.Y);
		setupClip();
		setupMeasureRectangles();
		setupMeasureLabels();
		setupPredictionTargetButtons();
		setupChordSelectors();
		setupFrameLines();
		updatePredictionTarget(1);
	}

	/**
	 * 実際に表示する領域のサイズを設定する
	 */
	private void setupClip() {
		double width = GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.SHOW_MEASURES + 0.5;
		double height = 64 + 0.5;
		setClip(new Rectangle(width, height));
	}

	public void setupMeasureRectangles() {
		measureRectangles = new Rectangle[AppConstants.Settings.MEASURES];
		for(int n = 0; n < measureRectangles.length; n++) {
			int x = GuiConstants.Pianoroll.MEASURE_WIDTH * n;
			int y = 0;
			int width = GuiConstants.Pianoroll.MEASURE_WIDTH;
			int height = 47;
			measureRectangles[n] = new Rectangle(x + 0.5, y + 0.5, width, height);
			measureRectangles[n].setStyle("-fx-stroke: #FFFFFF;-fx-stroke-type: centered;");
			measureRectangles[n].setStyle("-fx-fill: #FFFFFF;" + GuiConstants.Measurebar.STYLE);
			getChildren().add(measureRectangles[n]);
		}
	}

	public void setupMeasureLabels() {
		measureLabels = new Label[AppConstants.Settings.MEASURES];
		for(int n = 0; n < AppConstants.Settings.MEASURES; n++) {
			int x = 4 + GuiConstants.Pianoroll.MEASURE_WIDTH * n;
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
		toggleGroup = new ToggleGroup();
		predictionTargetButtons = new RadioButton[AppConstants.Settings.MEASURES];
		for(int n = 0; n < AppConstants.Settings.MEASURES; n++) {
			int x = 140 + GuiConstants.Pianoroll.MEASURE_WIDTH * n;
			int y = 2;
			predictionTargetButtons[n] = new RadioButton("");
			predictionTargetButtons[n].setUserData(n+1);
			predictionTargetButtons[n].setLayoutX(x);
			predictionTargetButtons[n].setLayoutY(y);
			predictionTargetButtons[n].setToggleGroup(toggleGroup);
			getChildren().add(predictionTargetButtons[n]);
		}
		toggleGroup.selectedToggleProperty().addListener(
			(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) -> {
				if(oldToggle != null) resetPredictionTarget((int)oldToggle.getUserData());
				updatePredictionTarget((int)newToggle.getUserData());
			}
		);
	}

	public void setupChordSelectors() {
		chordSelectors = new ChordSelector[AppConstants.Settings.MEASURES * 2];
		for(int measure = 0; measure < AppConstants.Settings.MEASURES; measure++) {
			// 1拍目のコードのコンボボックス生成
			int x1 = 3 + GuiConstants.Pianoroll.MEASURE_WIDTH * measure;
			int y1 = 20;
			chordSelectors[measure * 2] = new ChordSelector(measure + 1, 1, x1, y1, this);
			getChildren().add(chordSelectors[measure * 2]);
			// 2拍目のコードのコンボボックス生成
			int x2 = x1 + (GuiConstants.Pianoroll.MEASURE_WIDTH / 2);
			int y2 = 20;
			chordSelectors[measure * 2 + 1] = new ChordSelector(measure + 1, 3, x2, y2, this);
			getChildren().add(chordSelectors[measure * 2 + 1]);
		}
	}

	public void setupFrameLines() {
		int[] xArray = {0, GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.SHOW_MEASURES};
		int startY = 0;
		int endY = startY + 47;
		for(int n = 0; n < xArray.length; n++) {
			Line line = new Line(xArray[n] + 0.5, startY, xArray[n] + 0.5, endY);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			getChildren().add(line);
		}
	}

	public void translate(int move) {
		for(Rectangle measureFrame : measureRectangles) {
			measureFrame.setTranslateX(move);
		}
		for(Label measureLabel : measureLabels) {
			measureLabel.setTranslateX(move);
		}
		for(ToggleButton arrangeTargetButton : predictionTargetButtons) {
			arrangeTargetButton.setTranslateX(move);
		}
		for(ChordSelector chordSelector : chordSelectors) {
			chordSelector.setTranslateX(move);
		}
	}

	public void updatePredictionTarget(int targetMeasure) {
		toggleGroup.selectToggle(predictionTargetButtons[targetMeasure - 1]);
		measureRectangles[targetMeasure - 1].setStyle("-fx-fill: " + GuiConstants.Measurebar.SELECTED_COLORCODE + ";" + GuiConstants.Measurebar.STYLE);
		owner.setPredictionPatternList(targetMeasure);
		owner.updatePlayPosition(targetMeasure);
	}

	public void resetPredictionTarget(int targetMeasure) {
		measureRectangles[targetMeasure - 1].setStyle("-fx-fill: " + GuiConstants.Measurebar.UNSELECTED_COLORCODE + ";" + GuiConstants.Measurebar.STYLE);
	}

	public int getPredictionTarget() {
		return (int)toggleGroup.getSelectedToggle().getUserData();
	}

	public void makeAccompaniment(String chord, int measure, int beat) {
		owner.makeAccompaniment(chord, measure, beat);
	}

	public void setChord(String chord, int measure, int beat) {
		switch(beat) {
		case 1:
			chordSelectors[(measure - 1) * 2 + 0].setChord(chord);
			break;
		case 3:
			chordSelectors[(measure - 1) * 2 + 1].setChord(chord);
			break;
		}
	}

	public String getChord(int measure, int beat) {
		String chord = null;
		switch(beat) {
		case 1:
			chord = chordSelectors[(measure - 1) * 2 + 0].getChord();
			break;
		case 3:
			chord = chordSelectors[(measure - 1) * 2 + 1].getChord();
			break;
		}
		return chord;
	}
}
