package constants;

import java.util.HashMap;

/**
 * UIで全般的に使用する定数をまとめたクラス
 * @author Shun Yamashita
 */

public class UniversalConstants {
	private UniversalConstants() {};

	public static final HashMap<String, Integer> MIDI_NUMBERS = new HashMap<String, Integer>() {
		{put("C", 0);} {put("C#", 1);} {put("D", 2);} {put("D#", 3);} {put("E", 4);}
		{put("F", 5);} {put("F#", 6);} {put("G", 7);} {put("G#", 8);} {put("A", 9);} {put("A#", 10);} {put("B", 11);}
	};
	public static final String[] INTERVALS = {"B", "A#", "A", "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C"};
	public static final String[] WHITE_KEY_INTERVALS = {"B", "A", "G", "F", "E", "D", "C"};
	public static final String[] BLACK_KEY_INTERVALS = {"A#", "G#", "F#", "D#", "C#"};

	public static final int DEFAULT_BPM = 120; // BPM値(デフォルト値)
	public static final int DEFAULT_MEASURE = 32; // 小節数
	public static final int DEFAULT_OCTAVE = 5; // オクターブ数
	public static final int MAX_OCTAVE = 6; // オクターブの最高値
	public static final int SHOW_MEASURE_COUNT = 4; // ピアノロール上に表示する小節数
	public static final int SHOW_OCTAVE_COUNT = 3; // ピアノロール上に表示するオクターブ数

	public static final int MEASURE_WIDTH = 160;
	public static final int MEASURE_HEIGHT = 144;
	public static final int BEAT_WIDTH = MEASURE_WIDTH / 4;
}
