package engine;

import java.util.List;

import gui.component.pianoroll.note.NoteModel;

/**
 * 候補メロディ生成に必要なパラメータ情報を持つクラス
 * @author Shun Yamashita
 */
public class PredictionParameter {
	/** 候補メロディ生成対象の小節 */
	private final int targetMeasure;
	/** ユーザによって入力されたメロディ */
	private final List<NoteModel> melody;
	/** ユーザによって入力されたコード進行 */
	private final List<String> chordProgression;

	public PredictionParameter(int targetMeasure, List<NoteModel> melody, List<String> chordProgression) {
		this.targetMeasure = targetMeasure;
		this.melody = melody;
		this.chordProgression = chordProgression;
	}

	public int getTargetMeasure() {
		return targetMeasure;
	}

	public List<NoteModel> getMelody() {
		return melody;
	}

	public List<String> getChordProgression() {
		return chordProgression;
	}
}
