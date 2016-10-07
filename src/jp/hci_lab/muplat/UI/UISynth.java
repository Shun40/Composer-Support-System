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
}
