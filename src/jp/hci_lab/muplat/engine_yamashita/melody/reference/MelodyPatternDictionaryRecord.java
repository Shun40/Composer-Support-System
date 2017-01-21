package engine_yamashita.melody.reference;

public class MelodyPatternDictionaryRecord {
	private int index;
	private String name;
	private MelodyPattern context;
	private MelodyPattern word;
	private int frequency;

	public MelodyPatternDictionaryRecord(int index, String name, MelodyPattern context, MelodyPattern word, int frequency) {
		this.index = index;
		this.name = name;
		this.context = context;
		this.word = word;
		this.frequency = frequency;
	}

	public void incFrequency() {
		frequency++;
	}

	public int getIndex() { return index; }
	public String getName() { return name; }
	public MelodyPattern getContext() { return context; }
	public MelodyPattern getWord() { return word; }
	public int getFrequency() { return frequency; }
}
