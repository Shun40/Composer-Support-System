package engine_yamashita.melody.reference;

public class WordDictionaryEntry {
	private int index;
	private String name;
	private MelodyPattern word;
	private int frequency;

	public WordDictionaryEntry(int index, String name, MelodyPattern word, int frequency) {
		this.index = index;
		this.name = name;
		this.word = word;
		this.frequency = frequency;
	}

	public void incFrequency() {
		frequency++;
	}

	public int getIndex() { return index; }
	public String getName() { return name; }
	public MelodyPattern getWord() { return word; }
	public int getFrequency() { return frequency; }
}
