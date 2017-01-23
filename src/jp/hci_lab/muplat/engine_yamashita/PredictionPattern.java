package engine_yamashita;

/**
 * アレンジパターンをまとめたクラス
 * @author Shun Yamashita
 */
public class PredictionPattern {
	private Melody melody;

	public PredictionPattern() {
		melody = new Melody(-1, "", "", 0);
	}

	public int getIndex() { return melody.getIndex(); }
	public String getId() { return melody.getId(); }
	public String getName() { return melody.getName(); }
	public int getFrequency() { return melody.getFrequency(); }

	public Melody getMelody() { return melody; }
	public void setMelody(Melody melody) { this.melody = melody; }
}
