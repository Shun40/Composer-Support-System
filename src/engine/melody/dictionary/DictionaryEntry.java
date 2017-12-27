package engine.melody.dictionary;

import engine.melody.RelativeMelody;

public class DictionaryEntry {
	private final int index;
	private final String name;
	private final RelativeMelody context;
	private final RelativeMelody word;
	private int frequency;

	public DictionaryEntry(int index, String name, RelativeMelody context, RelativeMelody word, int frequency) {
		this.index = index;
		this.name = name;
		this.context = context;
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

	public RelativeMelody getContext() {
		return context;
	}

	public RelativeMelody getWord() {
		return word;
	}

	public int getFrequency() {
		return frequency;
	}
}
