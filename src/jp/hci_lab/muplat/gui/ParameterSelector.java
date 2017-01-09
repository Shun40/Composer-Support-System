package gui;

import static gui.constants.UniversalConstants.*;

import engine_yamashita.DrumPatternParameter;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * パラメータを調整するためのスライダー群のクラス
 * @author Shun Yamashita
 */
public class ParameterSelector extends Group {
	private Slider[] sliders;
	private Pianoroll parent;

	public ParameterSelector(int x, int y, Pianoroll parent) {
		super();
		this.parent = parent;
		setupPoint(x, y);
		setupLabel();
		setupSliders();
		setupEventListener();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupLabel() {
		Label label = new Label("Parameters");
		label.setFont(Font.font("Arial", 16));
		label.setTextFill(Color.web("#000000"));
		getChildren().add(label);
	}

	public void setupSliders() {
		sliders = new Slider[PARAMETERS.length];
		for(int n = 0; n < PARAMETERS.length; n++) {
			Label label = new Label(PARAMETERS[n]);
			label.setFont(Font.font("Arial", 12));
			label.setTextFill(Color.web("#000000"));
			label.setLayoutX(0);
			label.setLayoutY(80 * n + 30);
			getChildren().add(label);
			sliders[n] = new Slider();
			sliders[n].setUserData(n);
			sliders[n].setMin(0.0);
			sliders[n].setMax(1.0);
			sliders[n].setValue(0.5);
			sliders[n].setShowTickLabels(true);
			sliders[n].setShowTickMarks(true);
			sliders[n].setMajorTickUnit(0.5);
			sliders[n].setMinorTickCount(9);
			sliders[n].setBlockIncrement(0.05);
			//sliders[n].setSnapToTicks(true);
			//sliders[n].setSnapToPixel(true);
			sliders[n].setLayoutX(0);
			sliders[n].setLayoutY(80 * n + 60);
			getChildren().add(sliders[n]);
		}
	}

	public void setupEventListener() {
		for(Slider slider : sliders) {
			slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
			});
		}
	}

	public DrumPatternParameter getParameter() {
		double climax = sliders[0].getValue();
		double speed = sliders[1].getValue();
		double rhythm = sliders[2].getValue();
		return new DrumPatternParameter(climax, speed, rhythm);
	}
}
