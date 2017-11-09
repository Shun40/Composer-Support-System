package gui;

import midi.MidiConstants;
import midi.MidiUtil;
import system.AppConstants;

/**
 * GUIに関するユーティリティクラス
 * @author Shun Yamashita
 *
 */
public class GuiUtil {
	/**
	 * 発音位置からx座標を計算する
	 * @param position 発音位置
	 * @return x座標
	 */
	public static int calcX(int position) {
		return (GuiConstants.Pianoroll.BEAT_WIDTH / 4) * (position / (MidiConstants.PPQ / 4));
	}

	/**
	 * ノート番号からy座標を計算する
	 * @param pitch ノート番号
	 * @return y座標
	 */
	public static int calcY(int pitch) {
		return GuiConstants.Grid.HEIGHT * ((AppConstants.Settings.MAX_OCTAVE + 2) * 12 - pitch - 1);
	}

	/**
	 * 発音時間長から横幅を計算する
	 * @param duration 発音時間長
	 * @return 横幅
	 */
	public static int calcWidth(int duration) {
		return (GuiConstants.Pianoroll.BEAT_WIDTH / 4) * (duration / (MidiConstants.PPQ / 4));
	}

	/**
	 * y座標からノート番号を計算する
	 * @param y y座標
	 * @return ノート番号
	 */
	public static int calcPitch(int y) {
		String[] intervals = {"B", "A#", "A", "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C"};
		String interval = intervals[y % GuiConstants.Pianoroll.MEASURE_HEIGHT / 12];
		int octave = AppConstants.Settings.MAX_OCTAVE - (y / GuiConstants.Pianoroll.MEASURE_HEIGHT);
		return MidiUtil.calcPitch(interval, octave);
	}

	/**
	 * x座標から発音位置を計算する
	 * @param x x座標
	 * @return 発音位置
	 */
	public static int calcPosition(int x) {
		return (MidiConstants.PPQ / 4) * (x / (GuiConstants.Pianoroll.BEAT_WIDTH / 4));
	}

	/**
	 * 横幅から発音時間長を計算する
	 * @param width 横幅
	 * @return 発音時間長
	 */
	public static int calcDuration(int width) {
		return (MidiConstants.PPQ / 4) * (width / (GuiConstants.Pianoroll.BEAT_WIDTH / 4));
	}
}
