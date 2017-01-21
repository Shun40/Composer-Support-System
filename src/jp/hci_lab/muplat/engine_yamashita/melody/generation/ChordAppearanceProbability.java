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
		case "N.C.":
			probabilities[5] = probabilities[17] = 0.143; // C
			probabilities[7] = probabilities[19] = 0.143; // D
			probabilities[9] = probabilities[21] = 0.143; // E
			probabilities[10] = probabilities[22] = 0.143; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.143; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.143; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.143; // B
			break;
		case "C":
			probabilities[5] = probabilities[17] = 0.23; // C(1.0)
			probabilities[7] = probabilities[19] = 0.11; // D(0.5)
			probabilities[9] = probabilities[21] = 0.16; // E(0.75)
			probabilities[10] = probabilities[22] = 0.05; // F(0.25)
			probabilities[0] = probabilities[12] = probabilities[24] = 0.18; // G(0.85)
			probabilities[2] = probabilities[14] = probabilities[26] = 0.13; // A(0.6)
			probabilities[4] = probabilities[16] = probabilities[28] = 0.14; // B(0.65)
			break;
		case "Dm":
			probabilities[5] = probabilities[17] = 0.14; // C
			probabilities[7] = probabilities[19] = 0.23; // D
			probabilities[9] = probabilities[21] = 0.11; // E
			probabilities[10] = probabilities[22] = 0.16; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.05; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.18; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.13; // B
			break;
		case "Em":
			probabilities[5] = probabilities[17] = 0.13; // C
			probabilities[7] = probabilities[19] = 0.14; // D
			probabilities[9] = probabilities[21] = 0.23; // E
			probabilities[10] = probabilities[22] = 0.11; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.16; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.05; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.18; // B
			break;
		case "F":
			probabilities[5] = probabilities[17] = 0.18; // C
			probabilities[7] = probabilities[19] = 0.13; // D
			probabilities[9] = probabilities[21] = 0.14; // E
			probabilities[10] = probabilities[22] = 0.23; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.11; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.16; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.05; // B
			break;
		case "G":
			probabilities[5] = probabilities[17] = 0.05; // C
			probabilities[7] = probabilities[19] = 0.18; // D
			probabilities[9] = probabilities[21] = 0.13; // E
			probabilities[10] = probabilities[22] = 0.14; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.23; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.11; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.16; // B
			break;
		case "Am":
			probabilities[5] = probabilities[17] = 0.16; // C
			probabilities[7] = probabilities[19] = 0.05; // D
			probabilities[9] = probabilities[21] = 0.18; // E
			probabilities[10] = probabilities[22] = 0.13; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.14; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.23; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.11; // B
			break;
		case "Bmb5":
			probabilities[5] = probabilities[17] = 0.11; // C
			probabilities[7] = probabilities[19] = 0.16; // D
			probabilities[9] = probabilities[21] = 0.05; // E
			probabilities[10] = probabilities[22] = 0.18; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.13; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.14; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.23; // B
			break;
		case "CM7":
			probabilities[5] = probabilities[17] = 0.20; // C(1.0)
			probabilities[7] = probabilities[19] = 0.10; // D(0.5)
			probabilities[9] = probabilities[21] = 0.16; // E(0.75)
			probabilities[10] = probabilities[22] = 0.05; // F(0.25)
			probabilities[0] = probabilities[12] = probabilities[24] = 0.18; // G(0.85)
			probabilities[2] = probabilities[14] = probabilities[26] = 0.13; // A(0.6)
			probabilities[4] = probabilities[16] = probabilities[28] = 0.18; // B(0.85)
			break;
		case "Dm7":
			probabilities[5] = probabilities[17] = 0.18; // C(1.0)
			probabilities[7] = probabilities[19] = 0.20; // D(0.5)
			probabilities[9] = probabilities[21] = 0.10; // E(0.75)
			probabilities[10] = probabilities[22] = 0.16; // F(0.25)
			probabilities[0] = probabilities[12] = probabilities[24] = 0.05; // G(0.85)
			probabilities[2] = probabilities[14] = probabilities[26] = 0.18; // A(0.6)
			probabilities[4] = probabilities[16] = probabilities[28] = 0.13; // B(0.85)
			break;
		case "Em7":
			probabilities[5] = probabilities[17] = 0.13; // C(1.0)
			probabilities[7] = probabilities[19] = 0.18; // D(0.5)
			probabilities[9] = probabilities[21] = 0.20; // E(0.75)
			probabilities[10] = probabilities[22] = 0.10; // F(0.25)
			probabilities[0] = probabilities[12] = probabilities[24] = 0.16; // G(0.85)
			probabilities[2] = probabilities[14] = probabilities[26] = 0.05; // A(0.6)
			probabilities[4] = probabilities[16] = probabilities[28] = 0.18; // B(0.85)
			break;
		case "FM7":
			probabilities[5] = probabilities[17] = 0.18; // C
			probabilities[7] = probabilities[19] = 0.13; // D
			probabilities[9] = probabilities[21] = 0.18; // E
			probabilities[10] = probabilities[22] = 0.20; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.10; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.16; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.05; // B
			break;
		case "G7":
			probabilities[5] = probabilities[17] = 0.05; // C
			probabilities[7] = probabilities[19] = 0.18; // D
			probabilities[9] = probabilities[21] = 0.13; // E
			probabilities[10] = probabilities[22] = 0.18; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.20; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.10; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.16; // B
			break;
		case "Am7":
			probabilities[5] = probabilities[17] = 0.16; // C
			probabilities[7] = probabilities[19] = 0.05; // D
			probabilities[9] = probabilities[21] = 0.18; // E
			probabilities[10] = probabilities[22] = 0.13; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.18; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.20; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.10; // B
			break;
		case "Bm7b5":
			probabilities[5] = probabilities[17] = 0.10; // C
			probabilities[7] = probabilities[19] = 0.16; // D
			probabilities[9] = probabilities[21] = 0.05; // E
			probabilities[10] = probabilities[22] = 0.18; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 0.13; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 0.18; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 0.20; // B
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
