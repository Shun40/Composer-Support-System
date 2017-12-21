package engine.melody.generation;

import java.util.List;

import engine.melody.Melody;
import engine.melody.generation.probability.ChordAppearanceProbability;
import engine.melody.generation.probability.JumpTransitionProbability;
import engine.melody.generation.probability.RangeAppearanceProbability;
import engine.melody.generation.probability.RangeTransitionProbability;
import gui.component.pianoroll.note.NoteModel;
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
	private AppConstants.Version version;

	public DynamicProgramming(AppConstants.Version version) {
		rangeAppearanceProbability = new RangeAppearanceProbability(version);
		chordAppearanceProbability = new ChordAppearanceProbability(version);
		rangeTransitionProbability = new RangeTransitionProbability();
		jumpTransitionProbability = new JumpTransitionProbability();
		minPitch = AppConstants.Settings.AVAILABLE_MIN_PITCH;
		maxPitch = AppConstants.Settings.AVAILABLE_MAX_PITCH;
		numPitch = (maxPitch - minPitch) + 1;
		this.version = version;
	}

	public void makeMelody(List<CandidateLabel> labels, Melody melody, List<AppConstants.Algorithm> algorithms) {
		int N = labels.size() - 1; // 先頭dummy分-1

		// 音高列X
		int[] X = new int[N+1];

		// Step.1 初期化
		double[][] delta = new double[N+1][numPitch];
		int[][] psi = new int[N+1][numPitch];
		for(int j = 0; j < numPitch; j++) {
			// 対象小節の(ユーザによって与えられた)直前音高を固定する
			// そのために直前音高と一致しない音高の出現確率を0として経路上に出現しないようにする
			if(labels.get(0).getPitch() == -1) {
				delta[0][j] = rangeAppearanceProbability.getProbability(j)
						* chordAppearanceProbability.getProbability(labels.get(0).getChord(), j);
				psi[0][j] = 0;
			} else {
				if(j == labels.get(0).getPitch() - minPitch) {
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
					a *= correctRouteRestriction(labels.get(i), j, k);

					score = delta[i-1][j] * a;
					if(max_j <= score) {
						max_j = score;
						argmax_j = j;
					}
				}
				// 出現確率計算
				double b = rangeAppearanceProbability.getProbability(k) * chordAppearanceProbability.getProbability(labels.get(i).getChord(), k);
				// 非和声音を考慮した出現確率補正
				if(version == AppConstants.Version.NEW && algorithms.contains(AppConstants.Algorithm.MN)) {
					b *= correctNonChordToneAboutDuration(labels.get(i), k);
					b *= correctNonChordToneAboutStart(labels.get(i), k);
				}
				// 局所的な音域を考慮した出現確率補正
				if(version == AppConstants.Version.NEW && algorithms.contains(AppConstants.Algorithm.RB)) {
					b *= correctLocalRange(melody, k);
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
			labels.get(i).setPitch(X[i]);
		}
	}

	// 相対音高の形をなぞる経路となるよう補正する
	private double correctRouteRestriction(CandidateLabel label, int previousPitch, int currentPitch) {
		double coefficient = 1.0;
		int difference = label.getDifference();
		if(difference == 0) { // 音高を保持する経路
			if(previousPitch != currentPitch) {
				if(version == AppConstants.Version.OLD) {
					coefficient = 0.0; // 音高の下降および上昇を禁止
				} else {
					coefficient = 0.5; // 音高の下降および上昇を抑制
				}
			}
		}
		if(difference < 0) { // 音高が下降する経路
			if(previousPitch <= currentPitch) {
				coefficient = 0.0; // 音高の保持および上昇を禁止
			} else {
				if(Math.abs(difference) <= 2 && Math.abs(previousPitch - currentPitch) > 2) {
					if(version == AppConstants.Version.OLD) {
						coefficient = 0.0;
					} else {
						coefficient = 0.5;
					}
				}
				if(Math.abs(difference) > 2 && Math.abs(previousPitch - currentPitch) <= 2) {
					if(version == AppConstants.Version.OLD) {
						coefficient = 0.0;
					} else {
						coefficient = 0.5;
					}
				}
			}
		}
		if(difference > 0) { // 音高が上昇する経路
			if(previousPitch >= currentPitch) {
				coefficient = 0.0; // 音高の保持および下降を禁止
			} else {
				if(Math.abs(difference) <= 2 && Math.abs(previousPitch - currentPitch) > 2) {
					if(version == AppConstants.Version.OLD) {
						coefficient = 0.0;
					} else {
						coefficient = 0.5;
					}
				}
				if(Math.abs(difference) > 2 && Math.abs(previousPitch - currentPitch) <= 2) {
					if(version == AppConstants.Version.OLD) {
						coefficient = 0.0;
					} else {
						coefficient = 0.5;
					}
				}
			}
		}
		return coefficient;
	}

	// 音価の大きい音に非和声音が出現しにくくなるよう補正する
	private double correctNonChordToneAboutDuration(CandidateLabel label, int pitch) {
		String chord = label.getChord();
		int duration = label.getDuration();
		boolean isChordTone = MidiUtil.isChordTone(chord, minPitch + pitch);
		// 4分音符よりも長い音価に非和声音が割当てられるのを抑制
		if(!isChordTone && duration > MidiConstants.PPQ) {
			return 0.1;
		} else {
			return 1.0;
		}
	}

	// コードチェンジのタイミングで非和声音が出現しにくくなるよう補正する
	private double correctNonChordToneAboutStart(CandidateLabel label, int pitch) {
		String chord = label.getChord();
		int position = label.getPosition();
		boolean isChordTone = MidiUtil.isChordTone(chord, minPitch + pitch);
		// コードチェンジのタイミングで非和声音が割当てられるのを抑制
		int _position = position % MidiUtil.getDurationOf1Measure();
		if(!isChordTone && (_position == 0 || _position == MidiUtil.getDurationOf2Beats())) {
			return 0.1;
		} else {
			return 1.0;
		}
	}

	// 局所的な音域から大きく外れなくなるよう補正する
	private double correctLocalRange(Melody melody, int pitch) {
		if(melody.isEmpty()) {
			return 1.0;
		}
		// メロディ全体の音高の重み付き平均を求める
		int temp1 = 0; // 分子
		int temp2 = 0; // 分母
		double ave = 0.0; // 重み付き平均
		for(NoteModel note : melody) {
			// 分子計算
			int weight = note.getDuration() / (MidiConstants.PPQ / 4); // 発音時間長で重み付けする
			temp1 += note.getPitch() * weight;
			// 分母計算
			temp2 += weight;
		}
		ave = (double)temp1 / (double)temp2;

		// 標準偏差を求める
		double temp3 = 0;
		double dev = 0.0;
		for(NoteModel note : melody) {
			temp3 += Math.pow(note.getPitch() - ave, 2);
		}
		dev = Math.sqrt(temp3 / melody.size());

		// 標準偏差が小さい（音高の変化が小さい）と音高変化の大きい候補メロディが提示出来なくなる
		// よって標準偏差がしきい値よりも小さい場合は1.0を返し, 正規分布による影響を与えないようにする
		if(dev <= 3.0) {
			return 1.0;
		}

		// 正規分布が出力する音高出現確率を正規化して返す
		double[] prob = new double[numPitch];
		double max = 0.0;
		for(int n = 0; n < prob.length; n++) {
			prob[n] = (1.0 / (Math.sqrt(2.0 * Math.PI) * dev)) * Math.exp(-(Math.pow(minPitch + n - ave, 2)) / (2.0 * Math.pow(dev, 2)));
			if(max <= prob[n]) {
				max = prob[n];
			}
		}
		return prob[pitch] / max;
	}
}
