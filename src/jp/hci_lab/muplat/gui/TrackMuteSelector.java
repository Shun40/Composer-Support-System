package gui;
import static gui.constants.UniversalConstants.*;

import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 各トラックのミュートOn/Offチェックボックス群のクラス
 * @author Shun Yamashita
 */
public class TrackMuteSelector extends Group {
	private CheckBox[] checkBoxes;
	private Pianoroll parent;

	public TrackMuteSelector(int x, int y, Pianoroll parent) {
		super();
		this.parent = parent;
		setupPoint(x, y);
		setupLabel();
		setupCheckBoxes();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupLabel() {
		Label label = new Label("Mute");
		label.setFont(Font.font("Arial", 16));
		label.setTextFill(Color.web("#000000"));
		getChildren().add(label);
	}

	public void setupCheckBoxes() {
		checkBoxes = new CheckBox[INSTRUMENTS.length];
		for(int n = 0; n < INSTRUMENTS.length; n++) {
			CheckBox cb = new CheckBox();
			cb.setUserData(TRACK_NUMBERS[n]);
			cb.setLayoutX(0);
			cb.setLayoutY(30 * n + 30);
			cb.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
				parent.setTrackMute((int)cb.getUserData(), cb.isSelected());
			});
			checkBoxes[n] = cb;
			getChildren().add(checkBoxes[n]);
		}
	}
}
