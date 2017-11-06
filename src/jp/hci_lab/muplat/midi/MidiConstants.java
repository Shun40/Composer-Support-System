package midi;

import java.util.HashMap;
import java.util.Map;

/**
 * MIDIに関する定数クラス
 * @author Shun Yamashita
 */
public class MidiConstants {
	/** 4分音符のTick数 */
	public static final int PPQ = 480;
	/** トラック番号 */
	public static int[] TRACK_NUMBERS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	/** トラック番号に対応するプログラム番号 */
	public static int[] PROGRAM_NUMBERS = { 74, 1, 5, 26, 28, 31, 49, 81, 34, 1 };
	/** Cを基準とした時の各インターバルのオフセット値 */
	public static final Map<String, Integer> PITCH_OFFSETS = new HashMap<String, Integer>() {
		{put("C", 0);} {put("C#", 1);} {put("D", 2);} {put("D#", 3);} {put("E", 4);}
		{put("F", 5);} {put("F#", 6);} {put("G", 7);} {put("G#", 8);} {put("A", 9);} {put("A#", 10);} {put("B", 11);}
	};
}
