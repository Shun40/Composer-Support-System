package engine_yamashita.melody.generation;

import java.util.ArrayList;

public class DynamicProgramming {
	// DPに必要なパラメータや情報
	private RangeAppearanceProbability rangeAppearanceProbability;
	private ChordAppearanceProbability chordAppearanceProbability;
	private RangeTransitionProbability rangeTransitionProbability;
	private JumpTransitionProbability jumpTransitionProbability;
	private int minPitch;
	private int maxPitch;
	private int numPitch;

	public DynamicProgramming() {
		rangeAppearanceProbability = new RangeAppearanceProbability();
		chordAppearanceProbability = new ChordAppearanceProbability();
		rangeTransitionProbability = new RangeTransitionProbability();
		jumpTransitionProbability = new JumpTransitionProbability();
		minPitch = rangeAppearanceProbability.getMinPitch();
		maxPitch = rangeAppearanceProbability.getMaxPitch();
		numPitch = (maxPitch - minPitch) + 1;
	}

	public void makeMelodyByDP(ArrayList<MelodyLabel> melodyLabels) {
		int N = melodyLabels.size() - 1; // 先頭dummy分-1

		// 音後列X
		int[] X = new int[N+1];

		// Step.1 初期化
		double[][] delta = new double[N+1][numPitch];
		int[][] psi = new int[N+1][numPitch];
		for(int j = 0; j < numPitch; j++) {
			// 対象小節の(ユーザによって与えられた)直前音高を固定する
			// そのために直前音高と一致しない音高の出現確率を0として経路上に出現しないようにする
			if(j == melodyLabels.get(0).getPitch() - minPitch) {
				delta[0][j] = 1.0;
				psi[0][j] = j;
			} else {
				delta[0][j] = 0.0;
				psi[0][j] = 0;
			}
		}
		// Step.2 再帰計算
		for(int i = 1; i <= N; i++) {
			for(int k = 0; k < numPitch; k++) {
				double score = 0.0;
				double max_j = 0.0;
				int argmax_j = 0;
				for(int j = 0; j < numPitch; j++) {
					// 遷移確率計算
					double a = rangeTransitionProbability.getProbability(j, k) * jumpTransitionProbability.getProbability(j, k);
					// 音高概形による遷移制約
					int variation = melodyLabels.get(i).getVariation();
					int difference = melodyLabels.get(i).getDifference();
					if(variation == 0) {
						if(j != k) a *= 0.0; // 音高の下降および上昇を禁止
					}
					if(variation == -1) {
						if(j <= k) a *= 0.0; // 音高の保持および上昇を禁止
						else {
							if(difference <= 2 && Math.abs(j - k) > 2) a *= 0.0;
							if(difference > 2 && Math.abs(j - k) <= 2) a *= 0.0;
						}
					}
					if(variation == 1) {
						if(j >= k) a *= 0.0; // 音高の保持および下降を禁止
						else {
							if(difference <= 2 && Math.abs(j - k) > 2) a *= 0.0;
							if(difference > 2 && Math.abs(j - k) <= 2) a *= 0.0;
						}
					}

					score = delta[i-1][j] * a;
					if(max_j <= score) {
						max_j = score;
						argmax_j = j;
					}
				}
				// 出現確率計算
				double b =
						rangeAppearanceProbability.getProbability(k)
						* chordAppearanceProbability.getProbability(melodyLabels.get(i).getChord(), k);
				delta[i][k] = max_j * b;
				psi[i][k] = argmax_j;
			}
		}
		// Step.3 再帰計算の終了
		double score = 0.0;
		double max_k = 0.0;
		int argmax_k = 0;
		for(int k = 0; k < numPitch; k++) {
			score = delta[N][k];
			if(max_k <= score) {
				max_k = score;
				argmax_k = k;
			}
		}
		X[N] = argmax_k;
		// Step.4 バックトラック
		for(int i = N; i > 0; i--) {
			X[i-1] = psi[i][X[i]];
		}

		for(int i = 0; i < X.length; i++) {
			X[i] += minPitch;
			melodyLabels.get(i).setPitch(X[i]);
		}
	}
}
