package engine_yamashita.melody.reference;

public class RhythmPatternDictionaryRecord {
	private String name;
	private RhythmPattern context;
	private RhythmPattern word;
	private int frequency;

	public RhythmPatternDictionaryRecord(String name, RhythmPattern context, RhythmPattern word, int frequency) {
		this.name = name;
		this.context = context;
		this.word = word;
		this.frequency = frequency;
	}

	public String getName() { return name; }
	public RhythmPattern getContext() { return context; }
	public RhythmPattern getWord() { return word; }
	public int getFrequency() { return frequency; }
}
