import javafx.scene.Group;
import javafx.scene.Scene;

public class MainScene extends Scene {
	public static final int PIANOROLL_X = 10;
	public static final int PIANOROLL_Y = 50;

	public MainScene(Group root, int width, int height) {
		super(root, width, height);

		PianoRoll pianoRoll = new PianoRoll(PIANOROLL_X, PIANOROLL_Y);
		pianoRoll.createWhiteKeyBoard();
		pianoRoll.createBlackKeyBoard();
		root.getChildren().add(pianoRoll);
	}
}
