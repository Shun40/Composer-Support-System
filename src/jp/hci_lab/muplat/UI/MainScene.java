import static constants.MainSceneConstants.*;

/**
 * コンポーネントを配置するシーンのクラス
 * @author Shun Yamashita
 */
import javafx.scene.Group;
import javafx.scene.Scene;

public class MainScene extends Scene {
	private UIController uiController;

	public MainScene(Group root, int width, int height) {
		super(root, width, height);
		this.uiController = new UIController();
		setupKeyboard(root);
		setupPianoroll(root);
	}

	public void setupKeyboard(Group root) {
		Keyboard keyboard = new Keyboard(2, KEYBOARD_X, KEYBOARD_Y);
		root.getChildren().add(keyboard);
	}

	public void setupPianoroll(Group root) {
		Pianoroll pianoroll = new Pianoroll(8, 2, 128, PIANOROLL_X, PIANOROLL_Y, this);
		root.getChildren().add(pianoroll);
	}

	public void setBpm(int bpm) {
		uiController.setBpm(bpm);
	}

	public void addNote(Note note) {
		uiController.addNote(note);
	}

	public void play() {
		uiController.play();
	}

	public void stop() {
		uiController.stop();
	}
}
