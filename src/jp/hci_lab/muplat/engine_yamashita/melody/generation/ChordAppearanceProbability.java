package engine_yamashita.melody.generation;

public class ChordAppearanceProbability {
	private double[] probabilities;
	private int minPitch;
	private int maxPitch;

	public ChordAppearanceProbability() {
		probabilities = new double[29];
		minPitch = 55;
		maxPitch = 83;
		resetProbability();
	}

	public void setProbabilityForChord(String chord) {
		resetProbability();
		switch(chord) {
		case "C":
			probabilities[5] = probabilities[17] = 1.0; // C
			probabilities[7] = probabilities[19] = 0.5; // D
			probabilities[9] = probabilities[21] = 0.65; // E
			probabilities[10] = probabilities[22] = 0.25; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.8; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.6; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.75; // B
			break;
		case "Cadd9":
			probabilities[5] = probabilities[17] = 1.0; // C
			probabilities[7] = probabilities[19] = 0.95; // D
			probabilities[9] = probabilities[21] = 0.65; // E
			probabilities[10] = probabilities[22] = 0.25; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.8; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.6; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.75; // B
			break;
		case "Dm":
			probabilities[5] = probabilities[17] = 0.7; // C
			probabilities[7] = probabilities[19] = 1.0; // D
			probabilities[9] = probabilities[21] = 0.5; // E
			probabilities[10] = probabilities[22] = 0.65; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.25; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.8; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.6; // B
			break;
		case "Em":
			probabilities[5] = probabilities[17] = 0.6; // C
			probabilities[7] = probabilities[19] = 0.75; // D
			probabilities[9] = probabilities[21] = 1.0; // E
			probabilities[10] = probabilities[22] = 0.5; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.65; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.25; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.8; // B
			break;
		case "F":
			probabilities[5] = probabilities[17] = 0.8; // C
			probabilities[7] = probabilities[19] = 0.6; // D
			probabilities[9] = probabilities[21] = 0.75; // E
			probabilities[10] = probabilities[22] = 1.0; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.5; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.65; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.25; // B
			break;
		case "FM7":
			probabilities[5] = probabilities[17] = 0.8; // C
			probabilities[7] = probabilities[19] = 0.6; // D
			probabilities[9] = probabilities[21] = 0.95; // E
			probabilities[10] = probabilities[22] = 1.0; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.5; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.65; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.25; // B
			break;
		case "G":
			probabilities[5] = probabilities[17] = 0.25; // C
			probabilities[7] = probabilities[19] = 0.8; // D
			probabilities[9] = probabilities[21] = 0.6; // E
			probabilities[10] = probabilities[22] = 0.75; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 1.0; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.5; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.65; // B
			break;
		case "G7":
			probabilities[5] = probabilities[17] = 0.25; // C
			probabilities[7] = probabilities[19] = 0.8; // D
			probabilities[9] = probabilities[21] = 0.6; // E
			probabilities[10] = probabilities[22] = 0.95; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 1.0; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.5; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.65; // B
			break;
		case "Am":
			probabilities[5] = probabilities[17] = 0.65; // C
			probabilities[7] = probabilities[19] = 0.25; // D
			probabilities[9] = probabilities[21] = 0.8; // E
			probabilities[10] = probabilities[22] = 0.6; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.75; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 1.0; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.5; // B
			break;
		case "Am7":
			probabilities[5] = probabilities[17] = 0.65; // C
			probabilities[7] = probabilities[19] = 0.25; // D
			probabilities[9] = probabilities[21] = 0.8; // E
			probabilities[10] = probabilities[22] = 0.6; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.95; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 1.0; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.5; // B
			break;
		case "Bdim":
			probabilities[5] = probabilities[17] = 0.5; // C
			probabilities[7] = probabilities[19] = 0.65; // D
			probabilities[9] = probabilities[21] = 0.25; // E
			probabilities[10] = probabilities[22] = 0.8; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.6; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.75; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 1.0; // B
			break;
		}
	}

	public void resetProbability() {
		for(int x = 0; x < probabilities.length; x++) {
			probabilities[x] = 0.0;
		}
	}

	public double getProbability(String chord, int x) {
		setProbabilityForChord(chord);
		return probabilities[x];
	}
	public int getMinPitch() { return minPitch; }
	public int getMaxPitch() { return maxPitch; }
}
