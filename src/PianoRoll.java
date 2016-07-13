/*
 * 1オクターブ分のピアノロールを表すクラス.
 */

import javafx.scene.Group;

public class PianoRoll extends Group {
	public static final int   WHITE_KEY_NUM    = 7;
	public static final int   BLACK_KEY_NUM    = 5;
	public static final int   WHITE_KEY_WIDTH  = 100;
	public static final int   BLACK_KEY_WIDTH  = 60;
	public static final int[] WHITE_KEY_HEIGHT = {21, 21, 21, 21, 20, 20, 20};
	public static final int[] BLACK_KEY_HEIGHT = {12, 12, 12, 12, 12};
	public static final int[] WHITE_KEY_Y      = {0, 21, 42, 63, 84, 104, 124};
	public static final int[] BLACK_KEY_Y      = {12, 36, 60, 96, 120};
	public static final String WHITE_KEY_COLOR = "#FFFFFF";
	public static final String BLACK_KEY_COLOR = "#333333";

	private PianoRollKey[] whiteKey;
	private PianoRollKey[] blackKey;

	public PianoRoll(int x, int y) {
		super();
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.createWhiteKeyBoard();
		this.createBlackKeyBoard();
	}

	public void createWhiteKeyBoard() {
		this.whiteKey = new PianoRollKey[WHITE_KEY_NUM];
		for(int n = 0; n < WHITE_KEY_NUM; n++) {
			this.whiteKey[n] = new PianoRollKey(WHITE_KEY_COLOR, 0, WHITE_KEY_Y[n], WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT[n]);
			this.getChildren().add(this.whiteKey[n]);
		}
	}

	public void createBlackKeyBoard() {
		this.blackKey = new PianoRollKey[BLACK_KEY_NUM];
		for(int n = 0; n < BLACK_KEY_NUM; n++) {
			this.blackKey[n] = new PianoRollKey(BLACK_KEY_COLOR, 0, BLACK_KEY_Y[n], BLACK_KEY_WIDTH, BLACK_KEY_HEIGHT[n]);
			this.getChildren().add(this.blackKey[n]);
		}
	}
}
