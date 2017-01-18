package engine_yamashita.melody.reference;

public class PitchPatternDictionaryRecord {
	private String name;
	private PitchPattern context;
	private PitchPattern word;
	private int frequency;

	public PitchPatternDictionaryRecord(String name, PitchPattern context, PitchPattern word, int frequency) {
		this.name = name;
		this.context = context;
		this.word = word;
		this.frequency = frequency;
	}

	public String getName() { return name; }
	public PitchPattern getContext() { return context; }
	public PitchPattern getWord() { return word; }
	public int getFrequency() { return frequency; }
}
