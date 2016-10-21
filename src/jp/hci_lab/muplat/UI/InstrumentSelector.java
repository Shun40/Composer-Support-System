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
		for(int n = 0; n < INSTRUMENTS.length; n++) {
			RadioButton rb = new RadioButton("[" + Integer.toString(n + 1) + "] " + INSTRUMENTS[n]);
			rb.setUserData(CHANNEL_NUMBERS[n]);
			rb.setTextFill(INSTRUMENTS_DARK_COLORS[n]);
			rb.setLayoutX(0);
			rb.setLayoutY(30 * n);
			rb.setToggleGroup(toggleGroup);
			if(n == 0) rb.setSelected(true);
			getChildren().add(rb);
		}
	}

	public void setupEventListener() {
		toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) -> {
			parent.setCurrentChannel((int)toggleGroup.getSelectedToggle().getUserData());
		});
	}
}
