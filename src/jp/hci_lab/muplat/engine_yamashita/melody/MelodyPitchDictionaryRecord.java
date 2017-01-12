package engine_yamashita.melody;

public class MelodyPitchDictionaryRecord {
	private String name;
	private MelodyPitch context;
	private MelodyPitch word;
	private int frequency;

	public MelodyPitchDictionaryRecord(String name, MelodyPitch context, MelodyPitch word, int frequency) {
		this.name = name;
		this.context = context;
		this.word = word;
		this.frequency = frequency;
	}

	public String getName() { return name; }
	public MelodyPitch getContext() { return context; }
	public MelodyPitch getWord() { return word; }
	public int getFrequency() { return frequency; }
}
