package gui;
import static gui.constants.MainConstants.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 * システムを実行するMainクラス
 * @author Shun Yamashita
 */
public class Main extends Application {
	private MainScene scene;

	@Override
	public void start(Stage stage) throws Exception {
		new UISynth();

		Group root = new Group();
		scene = new MainScene(root, CLIENT_AREA_WIDTH + WINDOW_OFFSET_WIDTH, CLIENT_AREA_HEIGHT + WINDOW_OFFSET_HEIGHT);

		stage.setTitle("muplat");
		stage.setResizable(false);
		stage.setOnCloseRequest(req -> close());
		stage.setScene(scene);
		stage.show();
	}

	public void close() {
		scene.close();
		UISynth.close();
		Platform.exit();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
