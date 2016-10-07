package constants;

import java.util.HashMap;

/**
 * UIで全般的に使用する定数をまとめたクラス
 * @author Shun Yamashita
 */

public class UniversalConstants {
	private UniversalConstants() {};

	public static final HashMap<String, Integer> midiNumbers = new HashMap<String, Integer>() {
		{put("C", 0);} {put("C#", 1);} {put("D", 2);} {put("D#", 3);} {put("E", 4);}
		{put("F", 5);} {put("F#", 6);} {put("G", 7);} {put("G#", 8);} {put("A", 9);} {put("A#", 10);} {put("B", 11);}
	};
}
