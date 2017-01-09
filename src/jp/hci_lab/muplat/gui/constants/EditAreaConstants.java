package gui.constants;

/**
 * EditAreaクラスで使用する定数クラス
 * @author Shun Yamashita
 */
public class EditAreaConstants {
	private EditAreaConstants() {}

	// NoteGrid
	public static final int NOTE_GRID_WIDTH = 40;
	public static final int NOTE_GRID_HEIGHT = 12;
	public static final int[] NOTE_GRID_X = {
		NOTE_GRID_WIDTH * 0,
		NOTE_GRID_WIDTH * 1,
		NOTE_GRID_WIDTH * 2,
		NOTE_GRID_WIDTH * 3,
	};
	public static final int[] NOTE_GRID_Y = {
		NOTE_GRID_HEIGHT * 0,
		NOTE_GRID_HEIGHT * 1,
		NOTE_GRID_HEIGHT * 2,
		NOTE_GRID_HEIGHT * 3,
		NOTE_GRID_HEIGHT * 4,
		NOTE_GRID_HEIGHT * 5,
		NOTE_GRID_HEIGHT * 6,
		NOTE_GRID_HEIGHT * 7,
		NOTE_GRID_HEIGHT * 8,
		NOTE_GRID_HEIGHT * 9,
		NOTE_GRID_HEIGHT * 10,
		NOTE_GRID_HEIGHT * 11
	};
	public static final int NOTE_GRID_X_OFFSET = 160; // 1 measure offset
	public static final int NOTE_GRID_Y_OFFSET = 144; // 1 octave offset
}
