import static constants.UniversalConstants.*;

import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 楽器を選択するためのラジオボタン群のクラス
 * @author Shun Yamashita
 */
public class InstrumentSelector extends Group {
	private final ToggleGroup toggleGroup;
	private RadioButton[] radioButtons;
	private Pianoroll parent;

	public InstrumentSelector(int x, int y, Pianoroll parent) {
		super();
		this.toggleGroup = new ToggleGroup();
		this.parent = parent;
		setupPoint(x, y);
		setupLabel();
		setupRadioButtons();
		setupEventListener();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupLabel() {
		Label label = new Label("Instruments");
		label.setFont(Font.font("Arial", 16));
		label.setTextFill(Color.web("#000000"));
		getChildren().add(label);
	}

	public void setupRadioButtons() {
		radioButtons = new RadioButton[INSTRUMENTS.length];
		for(int n = 0; n < INSTRUMENTS.length; n++) {
			radioButtons[n] = new RadioButton("[" + Integer.toString(n + 1) + "] " + INSTRUMENTS[n]);
			radioButtons[n].setUserData(TRACK_NUMBERS[n]);
			radioButtons[n].setTextFill(INSTRUMENTS_DARK_COLORS[n]);
			radioButtons[n].setLayoutX(0);
			radioButtons[n].setLayoutY(30 * n + 30);
			radioButtons[n].setToggleGroup(toggleGroup);
			if(n == 0) radioButtons[n].setSelected(true);
			getChildren().add(radioButtons[n]);
		}
	}

	public void setupEventListener() {
		toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) -> {
			parent.setCurrentTrack((int)toggleGroup.getSelectedToggle().getUserData());
		});
	}

	public void setSelectedTrack(int selectedTrack) {
		toggleGroup.selectToggle(radioButtons[selectedTrack - 1]);
	}
}
