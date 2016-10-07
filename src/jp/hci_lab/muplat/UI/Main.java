import static constants.MainConstants.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 * システムを実行するMainクラス
 * @author Shun Yamashita
 */
public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		new UISynth();

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
