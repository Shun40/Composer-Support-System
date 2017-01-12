package engine_yamashita;

public class DrumPatternDictionaryRecord {
	private DrumPattern context;
	private DrumPattern word;
	private int frequency;

	public DrumPatternDictionaryRecord(DrumPattern context, DrumPattern word, int frequency) {
		this.context = context;
		this.word = word;
		this.frequency = frequency;
	}

	public DrumPattern getContext() { return context; }
	public DrumPattern getWord() { return word; }
	public int getFrequency() { return frequency; }
}
