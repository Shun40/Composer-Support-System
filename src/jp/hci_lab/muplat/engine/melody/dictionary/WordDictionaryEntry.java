package engine.melody.dictionary;

import engine.melody.RelativeMelody;

public class WordDictionaryEntry {
	private final int index;
	private final String name;
	private final RelativeMelody word;
	private int frequency;

	public WordDictionaryEntry(int index, String name, RelativeMelody word, int frequency) {
		this.index = index;
		this.name = name;
		this.word = word;
		this.frequency = frequency;
	}

	public void incFrequency() {
		frequency++;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public RelativeMelody getWord() {
		return word;
	}

	public int getFrequency() {
		return frequency;
	}
}
