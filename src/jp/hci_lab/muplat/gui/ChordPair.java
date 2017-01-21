package gui;

public class ChordPair {
	private String[] chords;

	public ChordPair() {
		chords = new String[2];
	}

	public String getChord(int index) { return chords[index]; }
	public void setChord(String chord, int index) { this.chords[index] = chord; }
}
