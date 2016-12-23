/**
 * アレンジパターンをまとめたクラス
 * @author Shun Yamashita
 */
public class ArrangePattern {
	private String patternName;
	private DrumPattern drumPattern;

	public ArrangePattern(String patternName) {
		this.patternName = patternName;
		this.drumPattern = new DrumPattern();
	}

	public String getPatternName() { return patternName; }
	public DrumPattern getDrumPattern() { return drumPattern; }
}
