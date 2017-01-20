package gui;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

/**
 * UIで音を鳴らす時に使うSynthesizerインスタンスを提供するクラス
 * サウンドエンジンを使うまでもないちょっとした音色発音(ex. 鍵盤を押した時)向け
 * グローバル変数的に色々なところで使いまわせる
 * @author Shun Yamashita
 */

public class UISynth {
	public static Synthesizer synth;

	public UISynth() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeInstrument(int track, int program) {
		synth.getChannels()[track - 1].programChange(program - 1);
	}

	public static void toneOn(int track, int pitch, int velocity) {
		synth.getChannels()[track - 1].noteOn(pitch, velocity);
	}

	public static void toneOff(int track) {
		synth.getChannels()[track - 1].allNotesOff();
	}

	public static void close() {
		synth.close();
	}
}
