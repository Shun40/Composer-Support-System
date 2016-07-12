import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class PianoRoll extends Group {
	public static final int WHITE_KEY_WIDTH = 160;
	public static final int WHITE_KEY_HEIGHT = 32;
	public static final int BLACK_KEY_WIDTH = 100;
	public static final int BLACK_KEY_HEIGHT = 16;

	private Rectangle[] whiteKey;
	private Rectangle[] blackKey;

	public PianoRoll(int x, int y) {
		super();
		this.setLayoutX(x);
		this.setLayoutY(y);
	}

	public void createWhiteKeyBoard() {
		int[] y = {0, 32, 64, 96, 128, 160, 192, 224};
		this.whiteKey = new Rectangle[8];
		for(int n = 0; n < 8; n++) {
			this.whiteKey[n] = new Rectangle(0, y[n], WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT);
			this.whiteKey[n].setStyle(
				"-fx-fill: #FFFFFF;"
				+ "-fx-stroke: #000000;"
				+ "-fx-stroke-type: outside;"
				+ "-fx-arc-width: 5;"
				+ "-fx-arc-height: 5;"
			);
			this.getChildren().add(this.whiteKey[n]);
		}
	}

	public void createBlackKeyBoard() {
		int[] y = {56, 88, 120, 184, 216};
		this.blackKey = new Rectangle[5];
		for(int n = 0; n < 5; n++) {
			this.blackKey[n] = new Rectangle(0, y[n], BLACK_KEY_WIDTH, BLACK_KEY_HEIGHT);
			this.blackKey[n].setStyle(
				"-fx-fill: #333333;"
				+ "-fx-stroke: #000000;"
				+ "-fx-stroke-type: outside;"
				+ "-fx-arc-width: 5;"
				+ "-fx-arc-height: 5;"
			);
			this.getChildren().add(this.blackKey[n]);
		}
	}
}
