import static constants.UniversalConstants.*;

import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

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
		setupRadioButtons();
		setupEventListener();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupRadioButtons() {
		radioButtons = new RadioButton[INSTRUMENTS.length];
		for(int n = 0; n < INSTRUMENTS.length; n++) {
			radioButtons[n] = new RadioButton("[" + Integer.toString(n + 1) + "] " + INSTRUMENTS[n]);
			radioButtons[n].setUserData(CHANNEL_NUMBERS[n]);
			radioButtons[n].setTextFill(INSTRUMENTS_DARK_COLORS[n]);
			radioButtons[n].setLayoutX(0);
			radioButtons[n].setLayoutY(30 * n);
			radioButtons[n].setToggleGroup(toggleGroup);
			if(n == 0) radioButtons[n].setSelected(true);
			getChildren().add(radioButtons[n]);
		}
	}

	public void setupEventListener() {
		toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) -> {
			parent.setCurrentChannel((int)toggleGroup.getSelectedToggle().getUserData());
		});
	}

	public void setSelectedChannel(int selectedChannel) {
		toggleGroup.selectToggle(radioButtons[selectedChannel - 1]);
	}
}
