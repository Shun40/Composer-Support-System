package engine_yamashita;

/**
 * アレンジパターンをまとめたクラス
 * @author Shun Yamashita
 */
public class PredictionPattern {
	private String name;
	private Melody melody;

	public PredictionPattern(String name) {
		this.name = name;
		melody = new Melody("");
	}

	public String getName() { return name; }
	public Melody getMelody() { return melody; }
	public void setMelody(Melody melody) { this.melody = melody; }
}
