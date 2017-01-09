package gui;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * BPMを表示するラベルのクラス
 * @author Shun Yamashita
 */
public class BpmLabel extends Label {
	private Pianoroll parent;

	public BpmLabel(int bpm, int x, int y, Pianoroll parent) {
		super("BPM: " + Integer.toString(bpm));
		this.parent = parent;
		setFont(Font.font("Arial", 16));
		setTextFill(Color.web("#000000"));
		setupPoint(x, y);
		setupEventListener();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupEventListener() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				BpmLabel.this.click(e);
			}
		});
	}

	public void click(MouseEvent e) {
		int oldBpm = parent.getBpm();
		TextInputDialog dialog = new TextInputDialog(Integer.toString(oldBpm));
		dialog.setTitle("BPM設定");
		dialog.setHeaderText("BPMの値を設定して下さい. (20 ~ 200)");
		dialog.setContentText("BPM:");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()){
			int newBpm = (int)Float.parseFloat(result.get());
			if(20 <= newBpm && newBpm <= 200) {
				setText("BPM: " + newBpm);
				parent.setBpm(newBpm);
			} else {
				setText("BPM: " + oldBpm);
				parent.setBpm(oldBpm);
			}
		}
	}

	public void changeBpm(int bpm) {
		setText("BPM: " + bpm);
	}
}
