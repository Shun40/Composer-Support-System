package gui.component;

import gui.GuiManager;
import gui.GuiConstants;
import gui.component.base.ButtonBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * 楽曲再生ボタン
 * @author Shun Yamashita
 */
public class PlayButton extends ButtonBase {
	private GuiManager owner;

	public PlayButton(GuiManager owner) {
		super();
		this.owner = owner;
		setup(GuiConstants.PlayButton.TEXT, GuiConstants.PlayButton.COLOR);
		setPoint(GuiConstants.PlayButton.X, GuiConstants.PlayButton.Y);
		setDisable(false);
		setupEventListener();
	}

	public void setupEventListener() {
		setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.play();
			}
		});
	}
}
