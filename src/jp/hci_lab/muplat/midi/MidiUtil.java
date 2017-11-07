package midi;

import java.util.Arrays;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;

import system.AppConstants;

/**
 * MIDIに関するユーティリティクラス
 * @author Shun Yamashita
 */
public class MidiUtil {
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
		return ((position % getDurationOf1Measure()) / MidiConstants.PPQ) + 1;
	}

	/**
	 * ノートのティックを返す
	 * @param position ノートの発音位置
	 * @return ノートのティック
	 */
	public static int getTick(int position) {
		return ((position % MidiConstants.PPQ) / (MidiConstants.PPQ / 4)) + 1;
	}

	/**
	 * ノートの発音位置を返す
	 * @param measure ノートの小節
	 * @param beat ノートの拍
	 * @param tick ノートのティック
	 * @return ノートの発音位置
	 */
	public static int getPosition(int measure, int beat, int tick) {
		return (measure - 1) * getDurationOf1Measure() + (beat - 1) * MidiConstants.PPQ + tick;
	}

	/**
	 * 1小節分の時間長を返す
	 * @return 1小節分の時間長
	 */
	public static int getDurationOf1Measure() {
		return MidiConstants.PPQ * 4;
	}

	/**
	 * 2拍分の時間長を返す
	 * @return 2拍分の時間長
	 */
	public static int getDurationOf2Beats() {
		return MidiConstants.PPQ * 2;
	}

	/**
	 * 音程を返す
	 * @param pitch ノートのノート番号
	 * @return 音程
	 */
	public static String getInterval(int pitch) {
		String[] intervals = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
		return intervals[(pitch % 12)];
	}

	/**
	 * オクターブ表記付きで音程を返す
	 * @param pitch ノートのノート番号
	 * @return 音程
	 */
	public static String getIntervalWithOctave(int pitch) {
		String[] intervals = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
		int[] octaves = {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		String interval = intervals[pitch % 12];
		int octave = octaves[pitch / 12];
		return interval + octave;
	}

	/**
	 * 音程とオクターブからピッチを計算する
	 * @param interval 音程
	 * @param octave オクターブ
	 * @return ノート番号
	 */
	public static int calcPitch(String interval, int octave) {
		return 60 + (12 * (octave - 4)) + MidiConstants.PITCH_OFFSETS.get(interval); // 60 is midi number of C4
	}

	/**
	 * 鍵盤種類を返す
	 * @param interval 音程
	 * @return 鍵盤種類
	 */
	public static AppConstants.KeyType getKeyType(String interval) {
		if(interval.contains("#") || interval.contains("♭")) return AppConstants.KeyType.BLACK;
		else return AppConstants.KeyType.WHITE;
	}

	/**
	 * 和声音かどうか調べる
	 * @param chord コード
	 * @param pitch ノート番号
	 * @return 和声音ならばtrue, 非和声音ならばfalse
	 */
	public static boolean isChordTone(String chord, int pitch) {
		boolean isChordTone = false;
		switch(chord) {
		case "C":
			isChordTone = Arrays.asList(55, 60, 64, 67, 72, 76, 79).contains(pitch);
			break;
		case "Dm":
			isChordTone = Arrays.asList(57, 62, 65, 69, 74, 77, 81).contains(pitch);
			break;
		case "Em":
			isChordTone = Arrays.asList(55, 59, 64, 67, 71, 76, 79, 83).contains(pitch);
			break;
		case "F":
			isChordTone = Arrays.asList(57, 60, 65, 69, 72, 77, 81).contains(pitch);
			break;
		case "G":
			isChordTone = Arrays.asList(55, 59, 62, 67, 71, 74, 79, 83).contains(pitch);
			break;
		case "Am":
			isChordTone = Arrays.asList(57, 60, 64, 69, 72, 76, 81).contains(pitch);
			break;
		case "Bmb5":
			isChordTone = Arrays.asList(59, 62, 65, 71, 74, 77, 83).contains(pitch);
			break;
		}
		return isChordTone;
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

	/**
	 * 音を鳴らす
	 * @param track トラック番号
	 * @param program プログラム番号
	 * @param pitch ノート番号
	 * @param velocity ベロシティ
	 */
	public static void toneOn(int track, int program, int pitch, int velocity) {
		Synth.changeInstrument(track, program);
		Synth.toneOn(track, pitch, velocity);
	}

	/**
	 * 音を止める
	 * @param track トラック番号
	 */
	public static void toneOff(int track) {
		Synth.toneOff(track);
	}
}
