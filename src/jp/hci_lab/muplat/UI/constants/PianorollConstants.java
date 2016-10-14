package constants;

/**
 * Pianorollクラスで使用する定数クラス
 * @author Shun Yamashita
 */
public class PianorollConstants {
	private PianorollConstants() {}

	public static final int KEYBOARD_X = 10;
	public static final int KEYBOARD_Y = 90;

	public static final int MEASURE_AREA_X = 110;
	public static final int MEASURE_AREA_Y = 73;

	public static final int EDIT_AREA_X = 110;
	public static final int EDIT_AREA_Y = 90;


	public static final int PIANOROLL_WIDTH  = 640;
	public static final int PIANOROLL_HEIGHT = 144;
	// NoteResolutionComboBox
	public static final int NOTE_RESOLUTION_SELECTOR_X = 110;
	public static final int NOTE_RESOLUTION_SELECTOR_Y = 40;
	// BpmLabel
	public static final int BPM_LABEL_X = 200;
	public static final int BPM_LABEL_Y = 42;
	// StopButton
	public static final int STOP_BUTTON_X = 310;
	public static final int STOP_BUTTON_Y = 40;
	// PlayButton
	public static final int PLAY_BUTTON_X = 346;
	public static final int PLAY_BUTTON_Y = 40;
	// SnapResolutionSelector
	public static final int SNAP_RESOLUTION_COMBO_BOX_X = 100;
	public static final int SNAP_RESOLUTION_COMBO_BOX_Y = 0;
	// MeasureLabel
	public static final int MEASURE_LABEL_X = 4;
	public static final int MEASURE_LABEL_Y = 0;
	// Measure
	public static final int MEASURE_WIDTH = 160;
	public static final int MEASURE_HEIGHT = 144;
	public static final int[] MEASURE_X = {0, 160, 320, 480};
	public static final int MEASURE_Y = 46;
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
