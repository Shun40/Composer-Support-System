package engine_yamashita.melody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import engine_yamashita.ArrangeInformation;
import gui.NoteInformation;

/**
 * メロディを解析するクラス
 * @author Shun Yamashita
 */
public class MelodyAnalyzer {
	public MelodyAnalyzer() {
	}

	public ArrayList<ArrayList<NoteInformation>> getPredictedMelodies(ArrangeInformation arrangeInformation) {
		ArrayList<ArrayList<NoteInformation>> predictiedMelodies = new ArrayList<ArrayList<NoteInformation>>();
		ArrayList<NoteInformation> currentMelody = arrangeInformation.getCurrentMelody();
		ArrayList<NoteInformation> previousMelody = arrangeInformation.getPreviousMelody();

		MelodyRhythmDictionary melodyRhythmDictionary = new MelodyRhythmDictionary();
		MelodyRhythm currentMelodyRhythm = getRhythm(currentMelody);
		MelodyRhythm previousMelodyRhythm = getRhythm(previousMelody);

		MelodyPitchDictionary melodyPitchDictionary = new MelodyPitchDictionary();

		for(int n = 0; n < melodyRhythmDictionary.size(); n++) {
			MelodyRhythm target = null;
			MelodyRhythm pattern = null;
			target = previousMelodyRhythm;
			pattern = melodyRhythmDictionary.get(n).getContext();
			//System.out.println(melodyRhythmDictionary.get(n).getName() + " : " + calcMelodyRhythmSimilarity(target, pattern));
			target = currentMelodyRhythm;
			pattern = melodyRhythmDictionary.get(n).getWord();
			//System.out.println(melodyRhythmDictionary.get(n).getName() + " : " + calcMelodyRhythmSimilarity(target, pattern));
		}

		ArrayList<Integer> target = new ArrayList<Integer>();
		for(NoteInformation note : currentMelody) {
			target.add(note.getNote());
		}
		int lowest = getLowest(previousMelody).getNote();
		int highest = getHighest(previousMelody).getNote();
		int mid = lowest + ((highest - lowest) / 2);
		for(int i = 0; i < 3; i++) { // first
			for(int j = 0; j < 3; j++) { // second
				for(int k = 0; k < 3; k++) { // third
					for(int l = 0; l < 3; l++) { // fourth
						ArrayList<Integer> pattern = new ArrayList<Integer>();
						switch(i) {
						case 0: System.out.print("L(" + lowest + ")"); pattern.add(lowest); break;
						case 1: System.out.print("M(" + mid + ")"); pattern.add(mid); break;
						case 2: System.out.print("H(" + highest + ")"); pattern.add(highest); break;
						}
						switch(j) {
						case 0: System.out.print("L(" + lowest + ")"); pattern.add(lowest); break;
						case 1: System.out.print("M(" + mid + ")"); pattern.add(mid); break;
						case 2: System.out.print("H(" + highest + ")"); pattern.add(highest); break;
						}
						switch(k) {
						case 0: System.out.print("L(" + lowest + ")"); pattern.add(lowest); break;
						case 1: System.out.print("M(" + mid + ")"); pattern.add(mid); break;
						case 2: System.out.print("H(" + highest + ")"); pattern.add(highest); break;
						}
						switch(l) {
						case 0: System.out.print("L(" + lowest + ")"); pattern.add(lowest); break;
						case 1: System.out.print("M(" + mid + ")"); pattern.add(mid); break;
						case 2: System.out.print("H(" + highest + ")"); pattern.add(highest); break;
						}
						System.out.println(" : " + calcMelodyPitchSimilarity(target, pattern));
					}
				}
			}
		}

		return predictiedMelodies;
	}

	public MelodyRhythm getRhythm(ArrayList<NoteInformation> melody) {
		MelodyRhythm melodyRhythm = new MelodyRhythm();
		for(NoteInformation note : melody) {
			int position = note.getPosition() % (960 * 4);
			int duration = note.getDuration();
			melodyRhythm.addRhythm(position, duration);
		}
		return melodyRhythm;
	}

	public double calcMelodyRhythmSimilarity(MelodyRhythm target, MelodyRhythm pattern) {
		ArrayList<MelodyRhythmTuple> targetRhythms = target.getRhythms();
		ArrayList<MelodyRhythmTuple> patternRhythms = pattern.getRhythms();
		int targetSize = targetRhythms.size();
		int patternSize = patternRhythms.size();

		if(targetSize == 0 || patternSize == 0) return 0.0;

		double[][] miss = new double[targetSize][patternSize];
		double[][] cost = new double[targetSize][patternSize];

		// 総当りで距離計算
		for(int i = 0; i < targetSize; i++) {
			MelodyRhythmTuple target_i = targetRhythms.get(i);
			for(int j = 0; j < patternSize; j++) {
				MelodyRhythmTuple pattern_j = patternRhythms.get(j);
				// positionとdurationのユークリッド距離を計算
				miss[i][j] = Math.sqrt(Math.pow(target_i.getPosition() - pattern_j.getPosition(), 2) + Math.pow(target_i.getDuration() - pattern_j.getDuration(), 2));
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

	public double calcMelodyPitchSimilarity(ArrayList<Integer> target, ArrayList<Integer> pattern) {
		int targetSize = target.size();
		int patternSize = pattern.size();

		if(targetSize == 0 || patternSize == 0) return 0.0;

		double[][] miss = new double[targetSize][patternSize];
		double[][] cost = new double[targetSize][patternSize];

		// 総当りで距離計算
		for(int i = 0; i < targetSize; i++) {
			int target_i = target.get(i);
			for(int j = 0; j < patternSize; j++) {
				int pattern_j = pattern.get(j);
				// 音高のユークリッド距離を計算
				miss[i][j] = Math.sqrt(Math.pow(target_i - pattern_j, 2));
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

	public ArrayList<Double> getAccentScores(ArrangeInformation arrangeInformation) {
		ArrayList<Double> accentScores = new ArrayList<Double>();
		// 元の配列に影響を与えないようディープコピー
		ArrayList<NoteInformation> _melody = new ArrayList<NoteInformation>(arrangeInformation.getCurrentMelody());

		if(_melody.size() <= 0) {
			return null;
		} else {
			ArrayList<Boolean> silhouetteAccents = getSilhouetteAccents(_melody);
			ArrayList<Boolean> longAccents = getLongAccents(_melody);
			ArrayList<Boolean> isolationAccents = getIsolationAccents(_melody);

			for(int n = 0; n < _melody.size(); n++) {
				double accentScore = 0.0;
				if(silhouetteAccents.get(n)) accentScore += 0.25;
				if(longAccents.get(n)) accentScore += 0.5;
				if(isolationAccents.get(n)) accentScore += 0.25;
				accentScore /= 1.0;
				accentScores.add(accentScore);
			}
			return accentScores;
		}
	}

	/*
	 * ノートの個数を返す
	 */
	public int getNumber(ArrayList<NoteInformation> melody) {
		return melody.size();
	}

	/*
	 * 最も音高が高いノートの情報を返す
	 */
	public NoteInformation getHighest(ArrayList<NoteInformation> melody) {
		// 元の配列に影響を与えないようディープコピー
		ArrayList<NoteInformation> _melody = new ArrayList<NoteInformation>(melody);
		// ノート番号で降順ソート
		Collections.sort(_melody, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e2.getNote() - e1.getNote();
			}
		});
		return _melody.get(0);
	}

	/*
	 * 最も音高が低いノートの情報を返す
	 */
	public NoteInformation getLowest(ArrayList<NoteInformation> melody) {
		// 元の配列に影響を与えないようディープコピー
			ArrayList<NoteInformation> _melody = new ArrayList<NoteInformation>(melody);
		// ノート番号で昇順ソート
		Collections.sort(_melody, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e1.getNote() - e2.getNote();
			}
		});
		return _melody.get(0);
	}

	/*
	 * 最も長いノートの情報を返す
	 */
	public NoteInformation getLongest(ArrayList<NoteInformation> melody) {
		// 元の配列に影響を与えないようディープコピー
			ArrayList<NoteInformation> _melody = new ArrayList<NoteInformation>(melody);
		// デュレーションで降順ソート
		Collections.sort(_melody, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e2.getDuration() - e1.getDuration();
			}
		});
		return _melody.get(0);
	}

	/*
	 * 最も短いノートの情報を返す
	 */
	public NoteInformation getShortest(ArrayList<NoteInformation> melody) {
		// 元の配列に影響を与えないようディープコピー
		ArrayList<NoteInformation> _melody = new ArrayList<NoteInformation>(melody);
		// デュレーションで降順ソート
		Collections.sort(_melody, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e1.getDuration() - e2.getDuration();
			}
		});
		return _melody.get(0);
	}

	/*
	 * 輪郭アクセントと見なせるノート群を返す
	 */
	public ArrayList<Boolean> getSilhouetteAccents(ArrayList<NoteInformation> melody) {
		ArrayList<Boolean> accents = new ArrayList<Boolean>();
		if(melody.size() == 1) {
			accents.add(false);
			return accents;
		}
		for(int n = 0; n < melody.size(); n++) {
			if(n == 0) {
				if(melody.get(n).getNote() > melody.get(n+1).getNote()) {
					accents.add(true);
				} else {
					accents.add(false);
				}
			} else if(n == melody.size() - 1) {
				if(melody.get(n-1).getNote() < melody.get(n).getNote()) {
					accents.add(true);
				} else {
					accents.add(false);
				}
			} else {
				if(melody.get(n-1).getNote() < melody.get(n).getNote() && melody.get(n).getNote() > melody.get(n+1).getNote()) {
					accents.add(true);
				} else {
					accents.add(false);
				}
			}
		}
		return accents;
	}

	/*
	 * 長音アクセントと見なせるノート群を返す
	 */
	public ArrayList<Boolean> getLongAccents(ArrayList<NoteInformation> melody) {
		ArrayList<Boolean> accents = new ArrayList<Boolean>();
		int shortestDuration = getShortest(melody).getDuration();
		for(int n = 0; n < melody.size(); n++) {
			if(melody.get(n).getDuration() > shortestDuration) {
				accents.add(true);
			} else {
				accents.add(false);
			}
		}
		return accents;
	}

	/*
	 * 隔離アクセントと見なせるノートを調べる
	 */
	public ArrayList<Boolean> getIsolationAccents(ArrayList<NoteInformation> melody) {
		ArrayList<Boolean> accents = new ArrayList<Boolean>();
		for(int n = 0; n < melody.size(); n++) {
			if(n == melody.size() - 1) {
				if((melody.get(n).getPosition() + melody.get(n).getDuration()) % (960 * 4) < 960 * 4) {
					accents.add(true);
				} else {
					accents.add(false);
				}
			} else {
				if(melody.get(n).getPosition() + melody.get(n).getDuration() < melody.get(n+1).getPosition()) {
					accents.add(true);
				} else {
					accents.add(false);
				}
			}
		}
		return accents;
	}
}
