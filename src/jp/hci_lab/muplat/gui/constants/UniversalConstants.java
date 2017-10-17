package gui.constants;

import java.util.HashMap;

import javafx.scene.paint.Color;

/**
 * UIで全般的に使用する定数をまとめたクラス
 * @author Shun Yamashita
 */

public class UniversalConstants {
	private UniversalConstants() {};

	public static int[] TRACK_NUMBERS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	public static int[] PROGRAM_NUMBERS = { 82, 1, 5, 26, 28, 31, 49, 81, 34, 1 };
	public static String[] INSTRUMENTS = {
		"Synth",
		"A.Piano",
		"E.Piano",
		"A.Guitar",
		"E.Guitar (C)",
		"E.Guitar (D)",
		"Strings",
		"Synth",
		"E.Bass",
		"Drums"
	};
	public static final Color[] INSTRUMENTS_DARK_COLORS = {
			Color.RED,        // track 1
			Color.GREEN,      // track 2
			Color.BLUE,       // track 3
			Color.CHOCOLATE,  // track 4
			Color.DEEPPINK,   // track 5
			Color.DODGERBLUE, // track 6
			Color.CRIMSON,    // track 7
			Color.DARKGREEN,  // track 8
			Color.DARKBLUE,   // track 9
			Color.GOLDENROD,  // track 10
	};
	public static final Color[] INSTRUMENTS_LIGHT_COLORS = {
			Color.FUCHSIA,    // track 1
			Color.YELLOW,     // track 2
			Color.AQUA,       // track 3
			Color.CORAL,      // track 4
			Color.HOTPINK,    // track 5
			Color.SKYBLUE,    // track 6
			Color.LIGHTCORAL, // track 7
			Color.LIGHTGREEN, // track 8
			Color.LIGHTBLUE,  // track 9
			Color.KHAKI,      // track 10
	};
	public static final HashMap<String, Integer> MIDI_NUMBERS = new HashMap<String, Integer>() {
		{put("C", 0);} {put("C#", 1);} {put("D", 2);} {put("D#", 3);} {put("E", 4);}
		{put("F", 5);} {put("F#", 6);} {put("G", 7);} {put("G#", 8);} {put("A", 9);} {put("A#", 10);} {put("B", 11);}
	};
	public static final String[] INTERVALS = {"B", "A#", "A", "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C"};
	public static final String[] WHITE_KEY_INTERVALS = {"B", "A", "G", "F", "E", "D", "C"};
	public static final String[] BLACK_KEY_INTERVALS = {"A#", "G#", "F#", "D#", "C#"};
	public static final Integer[] CAN_USE_PITCHES = {
		55, 57, 59,
		60, 62, 64, 65, 67, 69, 71,
		72, 74, 76, 77, 79, 81, 83
	};

	public static enum Algorithm {
		PC,       // Prediction Conversion
		MS,       // Melody Structure
		PC_AND_MS // Prediction Conversion + Melody Structure
	};

	public static enum ChordFunction {
		TONIC,
		DOMINANT,
		SUBDOMINANT
	};

	public static enum DictionaryType {
		WORD,  // 単語辞書
		PHRASE // 例文辞書
	};

	public static enum SimilarityType {
		PITCH, // 音高パターン
		RHYTHM // リズムパターン
	};

	public static enum MelodyStructureType {
		ABAB, // A-B-A'-B'
		AABB, // A-A'-B-B'
		AABC, // A-A'-B-C
		ABCC, // A-B-C-C'
		ABCD  // A-B-C-D
	};

	public static final int PPQ = 480; // 4分音符のTick数
	public static final int DEFAULT_BPM = 120; // BPM値(デフォルト値)
	public static final int DEFAULT_MEASURE = 8; // 小節数
	public static final int DEFAULT_OCTAVE = 5; // オクターブ数
	public static final int MAX_OCTAVE = 6; // オクターブの最高値
	public static final int SHOW_MEASURE_COUNT = 4; // ピアノロール上に表示する小節数
	public static final int SHOW_OCTAVE_COUNT = 3; // ピアノロール上に表示するオクターブ数

	public static final int MEASURE_WIDTH = 160;
	public static final int MEASURE_HEIGHT = 144;
	public static final int BEAT_WIDTH = MEASURE_WIDTH / 4;
}
