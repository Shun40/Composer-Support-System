package system;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Mainクラス
 * @author Shun Yamashita
 */
public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parameters parameters = getParameters();
		List<String> args = parameters.getRaw();

		AppConstants.Version version = AppConstants.Version.NEW;
		if(args.size() > 0) {
			if(args.get(0).equals("old")) version = AppConstants.Version.OLD;
			if(args.get(0).equals("new")) version = AppConstants.Version.NEW;
		}
		System.out.println("Application version: " + version);

		new AppManager(stage, version);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
