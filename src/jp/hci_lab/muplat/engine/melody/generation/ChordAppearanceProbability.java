package engine.melody.generation;

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
			probabilities[5] = probabilities[17] = 1.0; // C
			probabilities[7] = probabilities[19] = 1.0; // D
			probabilities[9] = probabilities[21] = 1.0; // E
			probabilities[10] = probabilities[22] = 1.0; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 1.0; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 1.0; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 1.0; // B
			for(int x = 0; x < probabilities.length; x++) {
				probabilities[x] /= 7.0; // 0.0~1.0に正規化
			}
			break;
		case "C":
			probabilities[5] = probabilities[17] = 5.0; // C
			probabilities[7] = probabilities[19] = 3.0; // D
			probabilities[9] = probabilities[21] = 5.0; // E
			probabilities[10] = probabilities[22] = 1.0; // F(アボイドノート)
			probabilities[0] = probabilities[12] = probabilities[24] = 5.0; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 2.0; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 4.0; // B
			for(int x = 0; x < probabilities.length; x++) {
				probabilities[x] /= 25.0; // 0.0~1.0に正規化
			}
			break;
		case "Dm":
			probabilities[5] = probabilities[17] = 4.0; // C
			probabilities[7] = probabilities[19] = 5.0; // D
			probabilities[9] = probabilities[21] = 3.0; // E
			probabilities[10] = probabilities[22] = 5.0; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 2.0; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 5.0; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 1.0; // B
			for(int x = 0; x < probabilities.length; x++) {
				probabilities[x] /= 25.0; // 0.0~1.0に正規化
			}
			break;
		case "Em":
			probabilities[5] = probabilities[17] = 1.5; // C(アボイドノート)
			probabilities[7] = probabilities[19] = 4.0; // D
			probabilities[9] = probabilities[21] = 5.0; // E
			probabilities[10] = probabilities[22] = 1.5; // F(アボイドノート)
			probabilities[0] = probabilities[12] = probabilities[24] = 5.0; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 3.0; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 5.0; // B
			for(int x = 0; x < probabilities.length; x++) {
				probabilities[x] /= 25.0; // 0.0~1.0に正規化
			}
			break;
		case "F":
			probabilities[5] = probabilities[17] = 5.0; // C
			probabilities[7] = probabilities[19] = 1.0; // D
			probabilities[9] = probabilities[21] = 4.0; // E
			probabilities[10] = probabilities[22] = 5.0; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 3.0; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 5.0; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 2.0; // B
			for(int x = 0; x < probabilities.length; x++) {
				probabilities[x] /= 25.0; // 0.0~1.0に正規化
			}
			break;
		case "G":
			probabilities[5] = probabilities[17] = 1.0; // C(アボイドノート)
			probabilities[7] = probabilities[19] = 5.0; // D
			probabilities[9] = probabilities[21] = 2.0; // E
			probabilities[10] = probabilities[22] = 4.0; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 5.0; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 3.0; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 5.0; // B
			for(int x = 0; x < probabilities.length; x++) {
				probabilities[x] /= 25.0; // 0.0~1.0に正規化
			}
			break;
		case "Am":
			probabilities[5] = probabilities[17] = 5.0; // C
			probabilities[7] = probabilities[19] = 2.0; // D
			probabilities[9] = probabilities[21] = 5.0; // E
			probabilities[10] = probabilities[22] = 1.0; // F(アボイドノート)
			probabilities[0] = probabilities[12] = probabilities[24] = 4.0; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 5.0; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 3.0; // B
			for(int x = 0; x < probabilities.length; x++) {
				probabilities[x] /= 25.0; // 0.0~1.0に正規化
			}
			break;
		case "Bmb5":
			probabilities[5] = probabilities[17] = 1.0; // C(アボイドノート)
			probabilities[7] = probabilities[19] = 5.0; // D
			probabilities[9] = probabilities[21] = 3.0; // E
			probabilities[10] = probabilities[22] = 5.0; // F
			probabilities[0] = probabilities[12] = probabilities[24] = 2.0; // G
			probabilities[2] = probabilities[14] = probabilities[26] = 4.0; // A
			probabilities[4] = probabilities[16] = probabilities[28] = 5.0; // B
			for(int x = 0; x < probabilities.length; x++) {
				probabilities[x] /= 25.0; // 0.0~1.0に正規化
			}
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
