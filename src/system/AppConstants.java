package system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppConstants {
	/** アプリケーション名 */
	public static final String APP_NAME = "Composer-Support-System";
	/** デフォルトで読み込む単語辞書ファイルパス */
	public static final String DEFAULT_WORD_DICTIONARY_FILE = "./word.dic";
	/** デフォルトで読み込む例文辞書ファイルパス */
	public static final String DEFAULT_PHRASE_DICTIONARY_FILE = "./phrase.dic";

	/** 鍵盤種類 */
	public static enum KeyType {
		WHITE,
		BLACK
	};

	/** 有効種類 */
	public static enum AvailabilityType {
		AVAILABLE,
		UNAVAILABLE
	};

	/** 辞書書類 */
	public static enum DictionaryType {
		/** 単語辞書 */
		WORD,
		/** 例文辞書 */
		PHRASE
	};

	/** 類似度種類 */
	public static enum SimilarityType {
		PITCH,
		RHYTHM
	};

	/** アルゴリズム */
	public static enum Algorithm {
		/** Prediction Conversion */
		PC,
		/** Melody Structure */
		MS,
		/** Musical Naturality */
		MN,
		/** Range Balance */
		RB
	};

	/** メロディ構造パターン */
	public static enum MelodyStructurePattern {
		ABCD, // A-B-C-D
		ABAB, // A-B-A'-B'
		AABB, // A-A'-B-B'
		AABC, // A-A'-B-C
		ABCC  // A-B-C-C'
	};

	/** コード進行のプリセット */
	public static enum ChordProgression {
		C_C_F_F_G_G_C_C,
		F_F_G_G_Em_Em_Am_Am,
		Am_Am_F_F_G_G_C_C,
		F_F_G_G_Am_Am_Am_Am,
		C_C_Am_Am_Dm_Dm_G_G,
		Am_Am_G_G_F_G_C_C
	};

	public static class Settings {
		/** 小節数 */
		public static final int MEASURES = 8;
		/** GUIで表示する小節数 */
		public static final int SHOW_MEASURES = 4;
		/** オクターブ数 */
		public static final int OCTAVES = 5;
		/** オクターブ最大値 */
		public static final int MAX_OCTAVE = 6;
		/** GUIで表示するオクターブ数 */
		public static final int SHOW_OCTAVES = 3;
		/** BPM初期値 */
		public static final int DEFAULT_BPM = 120;
		/** BPM最大値 */
		public static final int MAX_BPM = 200;
		/** BPM最小値 */
		public static final int MIN_BPM = 20;
		/** 有効なノート番号群 */
		public static final List<Integer> AVAILABLE_PITCHES = new ArrayList<Integer>(Arrays.asList(
				// G3, A3, B3
				55, 57, 59,
				// C4, D4, E4, F4, G4, A4, B4
				60, 62, 64, 65, 67, 69, 71,
				// C5, D5, E5, F5, G5, A5, B5
				72, 74, 76, 77, 79, 81, 83
		));
		/** 有効なノート番号の最大値 */
		public static final int AVAILABLE_MAX_PITCH = AVAILABLE_PITCHES.get(AVAILABLE_PITCHES.size() - 1);
		/** 有効なノート番号の最小値 */
		public static final int AVAILABLE_MIN_PITCH = AVAILABLE_PITCHES.get(0);
	}

	public static class MelodySettings {
		/** メロディパートのトラック番号 */
		public static final int MELODY_TRACK = 1;
		/** メロディパートのプログラム番号 */
		public static final int MELODY_PROGRAM = 74;
	}

	public static class AccompanimentSettings {
		/** コードパートのトラック番号 */
		public static final int CHORD_TRACK = 2;
		/** コードパートのプログラム番号 */
		public static final int CHORD_PROGRAM = 1;
		/** ベースパートのトラック番号 */
		public static final int BASS_TRACK = 9;
		/** ベースパートのプログラム番号 */
		public static final int BASS_PROGRAM = 34;
		/** ドラムパートのトラック番号 */
		public static final int DRUM_TRACK = 10;
		/** ドラムパートのプログラム番号 */
		public static final int DRUM_PROGRAM = 128; // 1以上の値ならなんでもOK
	}

	public static class AlgorithmSettings {
		/** MSアルゴリズムの重み */
		public static final double MS_WEIGHT = 1.0;
		/** RBアルゴリズムの重み */
		public static final double RB_WEIGHT = 1.0;
	}
}
