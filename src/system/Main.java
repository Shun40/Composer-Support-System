package system;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Mainクラス
 * @author Shun Yamashita
 */
public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		new AppManager(stage);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
