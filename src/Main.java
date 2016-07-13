/*
 * システムを実行するMainクラス.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int CLIENT_AREA_WIDTH    = 860;
	public static final int CLIENT_AREA_HEIGHT   = 300;
	public static final int WINDOW_OFFSET_WIDTH  = 8;
	public static final int WINDOW_OFFSET_HEIGHT = 34;

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		MainScene mainScene = new MainScene(root, CLIENT_AREA_WIDTH + WINDOW_OFFSET_WIDTH, CLIENT_AREA_HEIGHT + WINDOW_OFFSET_HEIGHT);

		stage.setTitle("muplat");
		stage.setScene(mainScene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
