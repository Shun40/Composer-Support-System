package gui.component;

import gui.GuiManager;
import gui.GuiConstants;
import gui.component.base.ButtonBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * 楽曲再生停止ボタン
 * @author Shun Yamashita
 */
public class StopButton extends ButtonBase {
	private GuiManager owner;

	public StopButton(GuiManager owner) {
		super();
		this.owner = owner;
		setup(GuiConstants.StopButton.TEXT, GuiConstants.StopButton.COLOR);
		setPoint(GuiConstants.StopButton.X, GuiConstants.StopButton.Y);
		setDisable(true);
		setupEventListener();
	}

	public void setupEventListener() {
		setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.stop();
			}
		});
	}
}
