package engine.melody.generation;

import java.util.List;

import engine.melody.generation.probability.ChordAppearanceProbability;
import engine.melody.generation.probability.JumpTransitionProbability;
import engine.melody.generation.probability.RangeAppearanceProbability;
import engine.melody.generation.probability.RangeTransitionProbability;
import midi.MidiConstants;
import midi.MidiUtil;
import system.AppConstants;

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

	public void makeMelodyByDP(List<MelodyLabel> melodyLabels, List<AppConstants.Algorithm> algorithms) {
		int N = melodyLabels.size() - 1; // 先頭dummy分-1

		// 音高列X
		int[] X = new int[N+1];

		// Step.1 初期化
		double[][] delta = new double[N+1][numPitch];
		int[][] psi = new int[N+1][numPitch];
		for(int j = 0; j < numPitch; j++) {
			// 対象小節の(ユーザによって与えられた)直前音高を固定する
			// そのために直前音高と一致しない音高の出現確率を0として経路上に出現しないようにする
			if(melodyLabels.get(0).getPitch() == -1) {
				delta[0][j] = rangeAppearanceProbability.getProbability(j)
						* chordAppearanceProbability.getProbability(melodyLabels.get(0).getChord(), j);
				psi[0][j] = 0;
			} else {
				if(j == melodyLabels.get(0).getPitch() - minPitch) {
					delta[0][j] = 1.0;
					psi[0][j] = j;
				} else {
					delta[0][j] = 0.0;
					psi[0][j] = 0;
				}
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
					double restriction_coefficient = 0.5; // 経路制約に使う制限係数(0.0でその経路を禁止する)
					if(variation == 0) {
						if(j != k) a *= restriction_coefficient; // 音高の下降および上昇を抑制
					}
					if(variation == -1) {
						if(j <= k) a *= 0.0; // 音高の保持および上昇を抑制
						else {
							if(difference <= 2 && Math.abs(j - k) > 2) a *= restriction_coefficient;
							if(difference > 2 && Math.abs(j - k) <= 2) a *= restriction_coefficient;
						}
					}
					if(variation == 1) {
						if(j >= k) a *= 0.0; // 音高の保持および下降を抑制
						else {
							if(difference <= 2 && Math.abs(j - k) > 2) a *= restriction_coefficient;
							if(difference > 2 && Math.abs(j - k) <= 2) a *= restriction_coefficient;
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
				// 非和声音を考慮した出現確率補正
				if(algorithms.contains(AppConstants.Algorithm.MN)) {
					b *= correctNonChordToneAboutDuration(melodyLabels.get(i), k);
					b *= correctNonChordToneAboutStart(melodyLabels.get(i), k);
				}

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

	// 音価の大きい音に非和声音が出現しにくくなるよう補正する
	private double correctNonChordToneAboutDuration(MelodyLabel melodyLabel, int pitch) {
		String chord = melodyLabel.getChord();
		int duration = melodyLabel.getDuration();
		boolean isChordTone = MidiUtil.isChordTone(chord, minPitch + pitch);
		// 4分音符よりも長い音価に非和声音が割当てられるのを抑制
		if(!isChordTone && duration > MidiConstants.PPQ) {
			return 0.1;
		} else {
			return 1.0;
		}
	}

	// 小節の頭に非和声音が出現しにくくなるよう補正する
	private double correctNonChordToneAboutStart(MelodyLabel melodyLabel, int pitch) {
		String chord = melodyLabel.getChord();
		int position = melodyLabel.getPosition();
		boolean isChordTone = MidiUtil.isChordTone(chord, minPitch + pitch);
		// 小節の頭に非和声音が割当てられるのを抑制
		if(!isChordTone && (position % MidiUtil.getDurationOf1Measure()) == 0) {
			return 0.1;
		} else {
			return 1.0;
		}
	}
}
