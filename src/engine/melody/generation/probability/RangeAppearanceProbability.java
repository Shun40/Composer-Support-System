package engine.melody.generation.probability;

import system.AppConstants;

/**
 * 音域による音高出現確率クラス
 * @author Shun Yamashita
 */
public class RangeAppearanceProbability {
	private double[] probabilities;

	public RangeAppearanceProbability() {
		probabilities = new double[29];

		// 音高の平均を求める
		int temp1 = 0; // 分子
		int temp2 = 0; // 分母
		double ave = 0.0; // 平均
		for(int x = 55; x <= 83; x++) {
			temp1 += x;
			temp2 += 1;
		}
		ave = (double)temp1 / (double)temp2;

		// 標準偏差を求める
		double temp3 = 0;
		double dev = 0.0;
		for(int x = 55; x <= 83; x++) {
			temp3 += Math.pow(x - ave, 2);
		}
		dev = Math.sqrt(temp3 / temp2);

		// 正規分布が出力する音高出現確率を正規化
		double max = 0.0;
		for(int x = 55; x <= 83; x++) {
			probabilities[x - AppConstants.Settings.AVAILABLE_MIN_PITCH] = (1.0 / (Math.sqrt(2.0 * Math.PI) * dev)) * Math.exp(-(Math.pow(x - ave, 2)) / (2.0 * Math.pow(dev, 2)));
			if(max <= probabilities[x - AppConstants.Settings.AVAILABLE_MIN_PITCH]) {
				max = probabilities[x - AppConstants.Settings.AVAILABLE_MIN_PITCH];
			}
		}
		for(int x = 55; x <= 83; x++) {
			probabilities[x - AppConstants.Settings.AVAILABLE_MIN_PITCH] /= max;
		}
		/*
		probabilities = new double[29];
		double sum = 0.0;
		for(int x = 55; x <= 83; x++) {
			double probability = 0.0;
			if(55 <= x && x <= 58) probability = 0.50;
			if(59 <= x && x <= 62) probability = 0.60;
			if(x == 63) probability = 0.70;
			if(x == 64) probability = 0.74;
			if(x == 65) probability = 0.78;
			if(x == 66) probability = 0.82;
			if(x == 67) probability = 0.86;
			if(68 <= x && x <= 70) probability = 0.9;
			if(x == 71) probability = 0.86;
			if(x == 72) probability = 0.82;
			if(x == 73) probability = 0.78;
			if(x == 74) probability = 0.74;
			if(x == 75) probability = 0.70;
			if(76 <= x && x <= 79) probability = 0.60;
			if(80 <= x && x <= 83) probability = 0.50;
			probabilities[x - AppConstants.Settings.AVAILABLE_MIN_PITCH] = probability * 100;
			sum += probabilities[x - AppConstants.Settings.AVAILABLE_MIN_PITCH];
		}
		// 正規化
		for(int x = 55; x <= 83; x++) {
			probabilities[x - AppConstants.Settings.AVAILABLE_MIN_PITCH] /= sum;
			System.out.println(x + ", " + probabilities[x - AppConstants.Settings.AVAILABLE_MIN_PITCH]);
		}
		*/
	}

	public double getProbability(int x) {
		return probabilities[x];
	}
}
