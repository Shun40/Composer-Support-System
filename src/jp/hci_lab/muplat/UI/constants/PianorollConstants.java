package constants;

/**
 * Pianorollクラスで使用する定数クラス
 * @author Shun Yamashita
 */
public class PianorollConstants {
	private PianorollConstants() {}

	public static final int PIANOROLL_WIDTH  = 640;
	public static final int PIANOROLL_HEIGHT = 144;
	// NoteResolutionComboBox
	public static final int NOTE_RESOLUTION_SELECTOR_X = 0;
	public static final int NOTE_RESOLUTION_SELECTOR_Y = 0;
	// BpmLabel
	public static final int BPM_LABEL_X = 90;
	public static final int BPM_LABEL_Y = 2;
	// StopButton
	public static final int STOP_BUTTON_X = 200;
	public static final int STOP_BUTTON_Y = 0;
	// PlayButton
	public static final int PLAY_BUTTON_X = 236;
	public static final int PLAY_BUTTON_Y = 0;
	// SnapResolutionSelector
	public static final int SNAP_RESOLUTION_COMBO_BOX_X = 100;
	public static final int SNAP_RESOLUTION_COMBO_BOX_Y = 0;
	// MeasureLabel
	public static final int MEASURE_LABEL_X = 4;
	public static final int MEASURE_LABEL_Y = 31;
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
		NOTE_GRID_HEIGHT * 0 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 1 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 2 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 3 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 4 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 5 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 6 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 7 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 8 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 9 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 10 + MEASURE_Y,
		NOTE_GRID_HEIGHT * 11 + MEASURE_Y
	};
	public static final int NOTE_GRID_X_OFFSET = 160; // 1 measure offset
	public static final int NOTE_GRID_Y_OFFSET = 144; // 1 octave offset
}
