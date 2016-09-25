import static constants.MainSceneConstants.*;

/**
 * コンポーネントを配置するシーンのクラス
 * @author Shun Yamashita
 */
import javafx.scene.Group;
import javafx.scene.Scene;

public class MainScene extends Scene {
	public MainScene(Group root, int width, int height) {
		super(root, width, height);

		setupKeyboard(root);
		setupPianoroll(root);
	}

	public void setupKeyboard(Group root) {
		Keyboard keyboard = new Keyboard(2, KEYBOARD_X, KEYBOARD_Y);
		root.getChildren().add(keyboard);
	}

	public void setupPianoroll(Group root) {
		Pianoroll pianoroll = new Pianoroll(8, 2, 128, PIANOROLL_X, PIANOROLL_Y);
		root.getChildren().add(pianoroll);
	}
}
