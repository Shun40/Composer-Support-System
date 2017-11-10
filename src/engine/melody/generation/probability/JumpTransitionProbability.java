package engine.melody.generation.probability;

import system.AppConstants;

/**
 * 2音間の跳躍音高差による音高遷移確率クラス
 * @author Shun Yamashita
 */
public class JumpTransitionProbability {
	private double[][] probabilities;

	public JumpTransitionProbability() {
		probabilities = new double[29][29];
		for(int Xn_1 = 55; Xn_1 <= 83; Xn_1++) {
			for(int Xn = 55; Xn <= 83; Xn++) {
				double probability = 0.0;
				if(Xn_1 == 55) { // 9
					if(55 <= Xn && Xn <= 60) probability = 0.105;
					if(62 <= Xn && Xn <= 64) probability = 0.105;
					if(Xn == Xn_1 + 12) probability = 0.055;
				}
				if(Xn_1 == 56) { // 10
					if(55 <= Xn && Xn <= 61) probability = 0.096;
					if(63 <= Xn && Xn <= 65) probability = 0.096;
					if(Xn == Xn_1 + 12) probability = 0.04;
				}
				if(Xn_1 == 57) { // 11
					if(55 <= Xn && Xn <= 62) probability = 0.088;
					if(64 <= Xn && Xn <= 66) probability = 0.088;
					if(Xn == Xn_1 + 12) probability = 0.032;
				}
				if(Xn_1 == 58) { // 12
					if(55 <= Xn && Xn <= 63) probability = 0.081;
					if(65 <= Xn && Xn <= 67) probability = 0.081;
					if(Xn == Xn_1 + 12) probability = 0.028;
				}
				if(Xn_1 == 59) { // 13
					if(55 <= Xn && Xn <= 64) probability = 0.075;
					if(66 <= Xn && Xn <= 68) probability = 0.075;
					if(Xn == Xn_1 + 12) probability = 0.025;
				}
				if(60 <= Xn_1 && Xn_1 <= 61) { // 14
					if(Xn_1 - 5 <= Xn && Xn <= Xn_1 + 5) probability = 0.07;
					if(Xn_1 + 7 <= Xn && Xn <= Xn_1 + 9) probability = 0.07;
					if(Xn == Xn_1 + 12) probability = 0.02;
				}
				if(Xn_1 == 62) { // 15
					if(Xn == 55) probability = 0.0655;
					if(57 <= Xn && Xn <= 67) probability = 0.0655;
					if(69 <= Xn && Xn <= 71) probability = 0.0655;
					if(Xn == Xn_1 + 12) probability = 0.0175;
				}
				if(Xn_1 == 63) { // 16
					if(55 <= Xn && Xn <= 56) probability = 0.0615;
					if(58 <= Xn && Xn <= 68) probability = 0.0615;
					if(70 <= Xn && Xn <= 72) probability = 0.0615;
					if(Xn == Xn_1 + 12) probability = 0.016;
				}
				if(64 <= Xn_1 && Xn_1 <= 66) {
					if(Xn_1 - 9 <= Xn && Xn <= Xn_1 - 7) probability = 0.058;
					if(Xn_1 - 5 <= Xn && Xn <= Xn_1 + 5) probability = 0.058;
					if(Xn_1 + 7 <= Xn && Xn <= Xn_1 + 9) probability = 0.058;
					if( Xn == Xn_1 + 12) probability = 0.014;
				}
				if(67 <= Xn_1 && Xn_1 <= 71) {
					if(Xn == Xn_1 - 12) probability = 0.01125;
					if(Xn_1 - 9 <= Xn && Xn <= Xn_1 - 7) probability = 0.0575;
					if(Xn_1 - 5 <= Xn && Xn <= Xn_1 + 5) probability = 0.0575;
					if(Xn_1 + 7 <= Xn && Xn <= Xn_1 + 9) probability = 0.0575;
					if( Xn == Xn_1 + 12) probability = 0.01125;
				}
				if(72 <= Xn_1 && Xn_1 <= 74) {
					if( Xn == Xn_1 - 12) probability = 0.014;
					if(Xn_1 - 9 <= Xn && Xn <= Xn_1 - 7) probability = 0.058;
					if(Xn_1 - 5 <= Xn && Xn <= Xn_1 + 5) probability = 0.058;
					if(Xn_1 + 7 <= Xn && Xn <= Xn_1 + 9) probability = 0.058;
				}
				if(Xn_1 == 75) { // 16
					if(Xn == Xn_1 - 12) probability = 0.016;
					if(66 <= Xn && Xn <= 68) probability = 0.0615;
					if(70 <= Xn && Xn <= 80) probability = 0.0615;
					if(82 <= Xn && Xn <= 83) probability = 0.0615;
				}
				if(Xn_1 == 76) { // 15
					if(Xn == Xn_1 - 12) probability = 0.0175;
					if(67 <= Xn && Xn <= 69) probability = 0.0655;
					if(71 <= Xn && Xn <= 81) probability = 0.0655;
					if(Xn == 83) probability = 0.0655;
				}
				if(77 <= Xn_1 && Xn_1 <= 78) { // 14
					if(Xn == Xn_1 - 12) probability = 0.02;
					if(Xn_1 - 9 <= Xn && Xn <= Xn_1 - 7) probability = 0.07;
					if(Xn_1 - 5 <= Xn && Xn <= Xn_1 + 5) probability = 0.07;
				}
				if(Xn_1 == 79) { // 13
					if(Xn == Xn_1 - 12) probability = 0.025;
					if(70 <= Xn && Xn <= 72) probability = 0.075;
					if(74 <= Xn && Xn <= 83) probability = 0.075;
				}
				if(Xn_1 == 80) { // 12
					if(Xn == Xn_1 - 12) probability = 0.028;
					if(71 <= Xn && Xn <= 73) probability = 0.081;
					if(75 <= Xn && Xn <= 83) probability = 0.081;
				}
				if(Xn_1 == 81) { // 11
					if(Xn == Xn_1 - 12) probability = 0.032;
					if(72 <= Xn && Xn <= 74) probability = 0.088;
					if(76 <= Xn && Xn <= 83) probability = 0.088;
				}
				if(Xn_1 == 82) { // 10
					if(Xn == Xn_1 - 12) probability = 0.04;
					if(73 <= Xn && Xn <= 75) probability = 0.096;
					if(77 <= Xn && Xn <= 83) probability = 0.096;
				}
				if(Xn_1 == 83) { // 9
					if(Xn == Xn_1 - 12) probability = 0.055;
					if(74 <= Xn && Xn <= 76) probability = 0.105;
					if(78 <= Xn && Xn <= 83) probability = 0.105;
				}
				probabilities[Xn_1 - AppConstants.Settings.AVAILABLE_MIN_PITCH][Xn - AppConstants.Settings.AVAILABLE_MIN_PITCH] = probability;
			}
		}
	}

	public void printProbability() {
		for(int Xn_1 = AppConstants.Settings.AVAILABLE_MAX_PITCH; Xn_1 >= AppConstants.Settings.AVAILABLE_MIN_PITCH; Xn_1--) {
			System.out.print(Xn_1 + "|");
			for(int Xn = AppConstants.Settings.AVAILABLE_MIN_PITCH; Xn <= AppConstants.Settings.AVAILABLE_MAX_PITCH; Xn++) {
				System.out.print(String.format("%f", getProbability(Xn_1, Xn)) + "  ");
			}
			System.out.println();
		}
	}

	public double getProbability(int Xn_1, int Xn) {
		return probabilities[Xn_1][Xn];
	}
}
