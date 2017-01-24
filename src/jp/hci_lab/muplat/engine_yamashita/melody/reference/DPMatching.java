package engine_yamashita.melody.reference;

public class DPMatching {
	public DPMatching() {
	}

	public double calcPitchSimilarity(MelodyPattern target, MelodyPattern pattern) {
		int targetSize = target.size();
		int patternSize = pattern.size();

		if(targetSize == 0 || patternSize == 0) return 0.0;

		double[][] miss = new double[targetSize][patternSize];
		double[][] cost = new double[targetSize][patternSize];

		// 総当りで距離計算
		for(int i = 0; i < targetSize; i++) {
			MelodyPatternData target_i = target.get(i);
			for(int j = 0; j < patternSize; j++) {
				MelodyPatternData pattern_j = pattern.get(j);
				// 音高パターン要素のユークリッド距離を計算
				//double diffVariation = Math.pow(target_i.getVariation() - pattern_j.getVariation(), 2);
				//double diffDifference = Math.pow(target_i.getDifference() - pattern_j.getDifference(), 2);
				//miss[i][j] = Math.sqrt(diffVariation + diffDifference);
				double diff = Math.pow(target_i.getVariation() * target_i.getDifference() - pattern_j.getVariation() * pattern_j.getDifference(), 2);
				miss[i][j] = Math.sqrt(diff);
			}
		}

		// コスト計算
		cost[0][0] = miss[0][0];
		for(int i = 1; i < targetSize; i++) {
			cost[i][0] = cost[i-1][0] + miss[i][0];
		}
		for(int j = 1; j < patternSize; j++) {
			cost[0][j] = cost[0][j-1] + miss[0][j];
		}
		for(int i = 1; i < targetSize; i++) {
			for(int j = 1; j < patternSize; j++) {
				double temp1 = cost[i-1][j-1] + 2.0 * miss[i][j];
				double temp2 = cost[i-1][j] + miss[i][j];
				double temp3 = cost[i][j-1] + miss[i][j];
				if(temp1 <= temp2 && temp1 <= temp3) {
					cost[i][j] = temp1;
				} else if(temp2 <= temp3) {
					cost[i][j] = temp2;
				} else {
					cost[i][j] = temp3;
				}
			}
		}
		double costMax = 0.0;
		for(int i = 0; i < targetSize; i++) {
			for(int j = 0; j < patternSize; j++) {
				if(cost[i][j] >= costMax) costMax = cost[i][j];
			}
		}
		return 1.0 - (cost[targetSize - 1][patternSize - 1] / costMax);
	}

	public double calcRhythmSimilarity(MelodyPattern target, MelodyPattern pattern) {
		int targetSize = target.size();
		int patternSize = pattern.size();

		if(targetSize == 0 || patternSize == 0) return 0.0;

		double[][] miss = new double[targetSize][patternSize];
		double[][] cost = new double[targetSize][patternSize];

		// 総当りで距離計算
		for(int i = 0; i < targetSize; i++) {
			MelodyPatternData target_i = target.get(i);
			for(int j = 0; j < patternSize; j++) {
				MelodyPatternData pattern_j = pattern.get(j);
				// リズムパターン要素のユークリッド距離を計算
				double diffPosition = Math.pow(target_i.getPosition() - pattern_j.getPosition(), 2);
				double diffDuration = Math.pow(target_i.getDuration() - pattern_j.getDuration(), 2);
				miss[i][j] = Math.sqrt(diffPosition + diffDuration);
			}
		}

		// コスト計算
		cost[0][0] = miss[0][0];
		for(int i = 1; i < targetSize; i++) {
			cost[i][0] = cost[i-1][0] + miss[i][0];
		}
		for(int j = 1; j < patternSize; j++) {
			cost[0][j] = cost[0][j-1] + miss[0][j];
		}
		for(int i = 1; i < targetSize; i++) {
			for(int j = 1; j < patternSize; j++) {
				double temp1 = cost[i-1][j-1] + 2.0 * miss[i][j];
				double temp2 = cost[i-1][j] + miss[i][j];
				double temp3 = cost[i][j-1] + miss[i][j];
				if(temp1 <= temp2 && temp1 <= temp3) {
					cost[i][j] = temp1;
				} else if(temp2 <= temp3) {
					cost[i][j] = temp2;
				} else {
					cost[i][j] = temp3;
				}
			}
		}
		double costMax = 0.0;
		for(int i = 0; i < targetSize; i++) {
			for(int j = 0; j < patternSize; j++) {
				if(cost[i][j] >= costMax) costMax = cost[i][j];
			}
		}
		return 1.0 - (cost[targetSize - 1][patternSize - 1] / costMax);
	}
}
