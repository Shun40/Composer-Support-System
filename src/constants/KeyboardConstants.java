package constants;

/**
 * Keyboardクラスで使用する定数クラス
 * @author Shun Yamashita
 */
public class KeyboardConstants {
	private KeyboardConstants() {};

	public static final int WHITE_KEY_COUNT = 7;
	public static final int BLACK_KEY_COUNT = 5;
	public static final int WHITE_KEY_WIDTH = 100;
	public static final int BLACK_KEY_WIDTH = 60;
	public static final int[] WHITE_KEY_HEIGHT = {21, 21, 21, 21, 20, 20, 20};
	public static final int[] BLACK_KEY_HEIGHT = {12, 12, 12, 12, 12};
	public static final int[] WHITE_KEY_Y = {0, 21, 42, 63, 84, 104, 124};
	public static final int[] BLACK_KEY_Y = {12, 36, 60, 96, 120};
	public static final int KEYBOARD_Y_OFFSET = 144; // 1 octave offset
}
