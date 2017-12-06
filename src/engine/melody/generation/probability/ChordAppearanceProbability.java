package engine.melody.generation.probability;

import system.AppConstants;

/**
 * コードによる和声音・非和声音の音高出現確率クラス
 * @author Shun Yamashita
 */
public class ChordAppearanceProbability {
	private double[] probabilities;
	private AppConstants.Version version;

	public ChordAppearanceProbability(AppConstants.Version version) {
		probabilities = new double[29];
		this.version = version;
		resetProbability();
	}

	public void setProbabilityForChord(String chord) {
		resetProbability();
		if(version == AppConstants.Version.OLD) {
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
			}
		} else {
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
}
