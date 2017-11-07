package gui.component;

import java.util.Optional;

import gui.GuiManager;
import gui.GuiConstants;
import gui.component.base.LabelBase;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import system.AppConstants;

/**
 * BPMを指定するラベル
 * @author Shun Yamashita
 */
public class BpmSelector extends LabelBase {
	private int bpm;
	private GuiManager owner;

	public BpmSelector(GuiManager owner) {
		super();
		bpm = AppConstants.Settings.DEFAULT_BPM;
		this.owner = owner;
		setup(("BPM: " + bpm), GuiConstants.BpmSelector.FONT, GuiConstants.BpmSelector.COLOR);
		setPoint(GuiConstants.BpmSelector.X, GuiConstants.BpmSelector.Y);
		setupEventListener();
	}

	public void setupEventListener() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				BpmSelector.this.click(e);
			}
		});
	}

	public void click(MouseEvent e) {
		TextInputDialog dialog = new TextInputDialog(Integer.toString(bpm));
		dialog.setTitle("BPM設定");
		dialog.setHeaderText("BPMの値を設定して下さい. (20 ~ 200)");
		dialog.setContentText("BPM:");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()){
			int newBpm = (int)Float.parseFloat(result.get());
			if(AppConstants.Settings.MIN_BPM <= newBpm && newBpm <= AppConstants.Settings.MAX_BPM) {
				bpm = newBpm;
				setText("BPM: " + bpm);
				owner.setBpmToSequencer(newBpm);
			} else { // 指定されたBPMが適切な範囲外だったら変更しない
			}
		}
	}

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		setText("BPM: " + bpm);
	}
}
