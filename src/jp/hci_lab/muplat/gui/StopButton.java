package gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * 楽曲再生停止ボタンのクラス
 * @author Shun Yamashita
 */
public class StopButton extends Button {
	private Pianoroll parent;

	public StopButton(int x, int y, Pianoroll parent) {
		super("■");
		this.parent = parent;
		setTextFill(Color.web("#0000AA"));
		setDisable(true);
		setupPoint(x, y);
		setupEventListener();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupEventListener() {
		setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.stop();
			}
		});
	}
}
