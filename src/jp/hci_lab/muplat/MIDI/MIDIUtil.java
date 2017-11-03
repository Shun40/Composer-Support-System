package MIDI;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;

/**
 * MIDIに関するユーティリティクラス
 * @author Shun Yamashita
 */
public class MIDIUtil {
	/**
	 * ノートの小節を返す
	 * @param position ノートの発音位置
	 * @return ノートの小節
	 */
	public static int getMeasure(int position) {
		return (position / getDurationOf1Measure()) + 1;
	}

	/**
	 * ノートの拍を返す
	 * @param position ノートの発音位置
	 * @return ノートの拍
	 */
	public static int getBeat(int position) {
		return ((position % getDurationOf1Measure()) / MIDIConstants.PPQ) + 1;
	}

	/**
	 * ノートのティックを返す
	 * @param position ノートの発音位置
	 * @return ノートのティック
	 */
	public static int getTick(int position) {
		return ((position % MIDIConstants.PPQ) / (MIDIConstants.PPQ / 4)) + 1;
	}

	/**
	 * 1小節分の時間長を返す
	 * @return 1小節分の時間長
	 */
	public static int getDurationOf1Measure() {
		return MIDIConstants.PPQ * 4;
	}

	/**
	 * 2拍分の時間長を返す
	 * @return 2拍分の時間長
	 */
	public static int getDurationOf2Beats() {
		return MIDIConstants.PPQ * 2;
	}

	/**
	 * MIDIプログラムチェンジイベントを生成する
	 * @param track ノートのトラック番号
	 * @param program ノートのプログラム番号
	 * @param position ノートの発音位置
	 * @return プログラムチェンジイベント
	 */
	public static MidiEvent createProgramChangeEvent(int track, int program, int position) {
		ShortMessage programChangeMessage = null;
		MidiEvent programChangeEvent = null;
		try {
			programChangeMessage = new ShortMessage(ShortMessage.PROGRAM_CHANGE, track - 1, program - 1, 0);
			programChangeEvent = new MidiEvent(programChangeMessage, position);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return programChangeEvent;
	}

	/**
	 * MIDIノートオンイベントを生成する
	 * @param track ノートのトラック番号
	 * @param pitch ノートのノート番号
	 * @param position ノートの発音位置
	 * @param velocity ノートのべロシティ
	 * @return ノートオンイベント
	 */
	public static MidiEvent createNoteOnEvent(int track, int pitch, int position, int velocity) {
		ShortMessage noteOnMessage = null;
		MidiEvent noteOnEvent = null;
		try {
			noteOnMessage = new ShortMessage(ShortMessage.NOTE_ON, track - 1, pitch, velocity);
			noteOnEvent = new MidiEvent(noteOnMessage, position);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return noteOnEvent;
	}

	/**
	 * MIDIノートオフイベントを生成する
	 * @param track ノートのトラック番号
	 * @param pitch ノートのノート番号
	 * @param position ノートの発音位置
	 * @param duration ノートの発音時間長
	 * @return ノートオフイベント
	 */
	public static MidiEvent createNoteOffEvent(int track, int pitch, int position, int duration) {
		ShortMessage noteOffMessage = null;
		MidiEvent noteOffEvent = null;
		try {
			noteOffMessage = new ShortMessage(ShortMessage.NOTE_OFF, track - 1, pitch, 0);
			noteOffEvent = new MidiEvent(noteOffMessage, position + duration);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return noteOffEvent;
	}
}
