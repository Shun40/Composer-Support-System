package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.stage.Stage;
import midi.Synth;
import system.AppConstants;

/**
 * システムのMainクラス
 * @author Shun Yamashita
 */
public class Main extends Application {
	private AppScene scene;

	@Override
	public void start(Stage stage) throws Exception {
		new Synth();

		Group root = new Group();
		int width = GuiConstants.AppSize.WIDTH;
		int height = GuiConstants.AppSize.HEIGHT;
		scene = new AppScene(root, width, height);

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
