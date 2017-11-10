package engine.melody.generation.probability;

import system.AppConstants;

/**
 * 音域による音高遷移確率クラス
 * @author Shun Yamashita
 */
public class RangeTransitionProbability {
	private double[][] probabilities;

	public RangeTransitionProbability() {
		probabilities = new double[29][29];
		for(int Xn_1 = 55; Xn_1 <= 83; Xn_1++) {
			for(int Xn = 55; Xn <= 83; Xn++) {
				double probability = 0.0;
				if(55 <= Xn && Xn <= 58) probability = 0.005;
				if(59 <= Xn && Xn <= 62) probability = 0.01;
				if(Xn == 63) probability = 0.02;
				if(Xn == 64) probability = 0.03;
				if(Xn == 65) probability = 0.07;
				if(Xn == 66) probability = 0.08;
				if(Xn == 67) probability = 0.09;
				if(68 <= Xn && Xn <= 70) probability = 0.12;
				if(Xn == 71) probability = 0.09;
				if(Xn == 72) probability = 0.08;
				if(Xn == 73) probability = 0.07;
				if(Xn == 74) probability = 0.03;
				if(Xn == 75) probability = 0.02;
				if(76 <= Xn && Xn <= 79) probability = 0.01;
				if(80 <= Xn && Xn <= 83) probability = 0.005;
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
