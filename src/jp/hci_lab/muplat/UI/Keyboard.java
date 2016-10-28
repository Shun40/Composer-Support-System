import static constants.KeyboardConstants.*;
import static constants.UniversalConstants.*;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

/**
 * ピアノロール左側に表示されるキーボードのクラス
 * @author Shun Yamashita
 */

public class Keyboard extends Group{
	private int octaveCount;
	private Key[] whiteKeys;
	private Key[] blackKeys;

	public Keyboard(int octaveCount, int x, int y) {
		super();
		this.octaveCount = octaveCount;
		setClip(new Rectangle(WHITE_KEY_WIDTH + 0.5, MEASURE_HEIGHT * SHOW_OCTAVE_COUNT + 0.5)); // 実際に表示する領域サイズ
		setupPoint(x, y);
		setupWhiteKeyBoard();
		setupBlackKeyBoard();
		setupOptionalLines();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupWhiteKeyBoard() {
		whiteKeys = new Key[WHITE_KEY_COUNT * octaveCount];
		for(int m = 0; m < octaveCount; m++) {
			for(int n = 0; n < WHITE_KEY_COUNT; n++) {
				String interval = WHITE_KEY_INTERVALS[n];
				int octave = MAX_OCTAVE - m;
				int x = 0;
				int y = WHITE_KEY_Y[n] + KEYBOARD_Y_OFFSET * m;
				int w = WHITE_KEY_WIDTH;
				int h = WHITE_KEY_HEIGHT[n];
				whiteKeys[n + m * WHITE_KEY_COUNT] = new Key(interval, octave, x, y, w, h);
				if(n == WHITE_KEY_COUNT - 1) { // 各オクターブのCの鍵盤にラベルを表示
					whiteKeys[n + m * WHITE_KEY_COUNT].showPositionLabel();
				}
				getChildren().add(whiteKeys[n + m * WHITE_KEY_COUNT]);
			}
		}
	}

	public void setupBlackKeyBoard() {
		blackKeys = new Key[BLACK_KEY_COUNT * octaveCount];
		for(int m = 0; m < octaveCount; m++) {
			for(int n = 0; n < BLACK_KEY_COUNT; n++) {
				String interval = BLACK_KEY_INTERVALS[n];
				int octave = MAX_OCTAVE - m;
				int x = 0;
				int y = BLACK_KEY_Y[n] + KEYBOARD_Y_OFFSET * m;
				int w = BLACK_KEY_WIDTH;
				int h = BLACK_KEY_HEIGHT[n];
				blackKeys[n + m * BLACK_KEY_COUNT] = new Key(interval, octave, x, y, w, h);
				getChildren().add(blackKeys[n + m * BLACK_KEY_COUNT]);
			}
		}
	}

	public void setupOptionalLines() {
		int[] yArray = {0, MEASURE_HEIGHT * SHOW_OCTAVE_COUNT};
		int startX = 0;
		int endX = startX + WHITE_KEY_WIDTH;
		for(int n = 0; n < yArray.length; n++) {
			Line line = new Line(startX, yArray[n] + 0.5, endX, yArray[n] + 0.5);
			line.setStrokeLineCap(StrokeLineCap.BUTT);
			getChildren().add(line);
		}
	}

	public void translate(double move) {
		for(Key whiteKey : whiteKeys) {
			whiteKey.setTranslateY(move);
		}
		for(Key blackKey : blackKeys) {
			blackKey.setTranslateY(move);
		}
	}

	public void changeInstrument(int track, int program) {
		for(Key whiteKey : whiteKeys) {
			whiteKey.setTrack(track);
			whiteKey.setProgram(program);
		}
		for(Key blackKey : blackKeys) {
			blackKey.setTrack(track);
			blackKey.setProgram(program);
		}
	}
}
