import static constants.KeyboardConstants.*;

import javafx.scene.Group;

/**
 * ピアノロール左側に表示されるキーボードのクラス
 * @author Shun Yamashita
 */

public class Keyboard extends Group{
	private int octaveCount;
	private Key[] whiteKey;
	private Key[] blackKey;

	public Keyboard(int octaveCount, int x, int y) {
		super();
		this.octaveCount = octaveCount;
		setupPoint(x, y);
		setupWhiteKeyBoard();
		setupBlackKeyBoard();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupWhiteKeyBoard() {
		String[] intervals = {"B", "A", "G", "F", "E", "D", "C"};
		whiteKey = new Key[WHITE_KEY_COUNT * octaveCount];
		for(int m = 0; m < octaveCount; m++) {
			for(int n = 0; n < WHITE_KEY_COUNT; n++) {
				String interval = intervals[n];
				int octave = 5 - m;
				int x = 0;
				int y = WHITE_KEY_Y[n] + KEYBOARD_Y_OFFSET * m;
				int w = WHITE_KEY_WIDTH;
				int h = WHITE_KEY_HEIGHT[n];
				whiteKey[n] = new Key(interval, octave, x, y, w, h);
				if(n == WHITE_KEY_COUNT - 1) { // 各オクターブのCの鍵盤にラベルを表示
					whiteKey[n].showPositionLabel();
				}
				getChildren().add(whiteKey[n]);
			}
		}
	}

	public void setupBlackKeyBoard() {
		String[] intervals = {"A#", "G#", "F#", "D#", "C#"};
		blackKey = new Key[BLACK_KEY_COUNT * octaveCount];
		for(int m = 0; m < octaveCount; m++) {
			for(int n = 0; n < BLACK_KEY_COUNT; n++) {
				String interval = intervals[n];
				int octave = 5 - m;
				int x = 0;
				int y = BLACK_KEY_Y[n] + KEYBOARD_Y_OFFSET * m;
				int w = BLACK_KEY_WIDTH;
				int h = BLACK_KEY_HEIGHT[n];
				blackKey[n] = new Key(interval, octave, x, y, w, h);
				getChildren().add(blackKey[n]);
			}
		}
	}
}
