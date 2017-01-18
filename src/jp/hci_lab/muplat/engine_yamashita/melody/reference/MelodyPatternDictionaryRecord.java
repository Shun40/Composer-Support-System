package engine_yamashita.melody.reference;

public class MelodyPatternDictionaryRecord {
	private String name;
	private MelodyPattern context;
	private MelodyPattern word;
	private int frequency;

	public MelodyPatternDictionaryRecord(String name, MelodyPattern context, MelodyPattern word, int frequency) {
		this.name = name;
		this.context = context;
		this.word = word;
		this.frequency = frequency;
	}

	public String getName() { return name; }
	public MelodyPattern getContext() { return context; }
	public MelodyPattern getWord() { return word; }
	public int getFrequency() { return frequency; }
}
