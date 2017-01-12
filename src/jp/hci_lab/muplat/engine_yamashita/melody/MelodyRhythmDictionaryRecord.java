package engine_yamashita.melody;

public class MelodyRhythmDictionaryRecord {
	private String name;
	private MelodyRhythm context;
	private MelodyRhythm word;
	private int frequency;

	public MelodyRhythmDictionaryRecord(String name, MelodyRhythm context, MelodyRhythm word, int frequency) {
		this.name = name;
		this.context = context;
		this.word = word;
		this.frequency = frequency;
	}

	public String getName() { return name; }
	public MelodyRhythm getContext() { return context; }
	public MelodyRhythm getWord() { return word; }
	public int getFrequency() { return frequency; }
}
