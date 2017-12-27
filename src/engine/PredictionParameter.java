package engine;

import engine.melody.ChordProgression;
import engine.melody.Melody;

/**
 * 候補メロディ生成に必要なパラメータ情報を持つクラス
 * @author Shun Yamashita
 */
public class PredictionParameter {
	/** 候補メロディ生成対象の小節 */
	private final int targetMeasure;
	/** ユーザによって入力されたメロディ */
	private final Melody melody;
	/** ユーザによって入力されたコード進行 */
	private final ChordProgression chordProgression;

	public PredictionParameter(int targetMeasure, Melody melody, ChordProgression chordProgression) {
		this.targetMeasure = targetMeasure;
		this.melody = melody;
		this.chordProgression = chordProgression;
	}

	public int getTargetMeasure() {
		return targetMeasure;
	}

	public Melody getMelody() {
		return melody;
	}

	public ChordProgression getChordProgression() {
		return chordProgression;
	}
}
