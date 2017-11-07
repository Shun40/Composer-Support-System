package system;

import gui.GuiConstants;
import gui.GuiManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.stage.Stage;
import midi.Synth;

/**
 * Mainクラス
 * @author Shun Yamashita
 */
public class Main extends Application {
	private GuiManager scene;

	@Override
	public void start(Stage stage) throws Exception {
		new Synth();

		scene = new GuiManager(new Group(), GuiConstants.AppSize.WIDTH, GuiConstants.AppSize.HEIGHT);

		stage.setTitle(AppConstants.APP_NAME);
		stage.setResizable(false);
		stage.setOnCloseRequest(req -> close());
		stage.setScene(scene);
		stage.show();
	}

	public void close() {
		scene.close();
		Synth.close();
		Platform.exit();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
