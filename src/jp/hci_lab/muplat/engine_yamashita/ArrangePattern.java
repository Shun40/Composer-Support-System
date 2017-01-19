package engine_yamashita;

/**
 * アレンジパターンをまとめたクラス
 * @author Shun Yamashita
 */
public class ArrangePattern {
	private String patternName;
	private Melody melody;

	public ArrangePattern(String patternName) {
		this.patternName = patternName;
		this.melody = new Melody("");
	}

	public String getPatternName() { return patternName; }
	public Melody getMelody() { return melody; }
	public void setMelody(Melody melody) { this.melody = melody; }
}
