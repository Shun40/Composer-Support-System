package engine_yamashita.melody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import engine_yamashita.ArrangeInformation;
import engine_yamashita.melody.generation.MelodyLabel;
import engine_yamashita.melody.generation.MelodyMaker;
import engine_yamashita.melody.reference.PitchPattern;
import engine_yamashita.melody.reference.PitchPatternData;
import engine_yamashita.melody.reference.PitchPatternDictionary;
import engine_yamashita.melody.reference.PitchPatternDictionaryRecord;
import engine_yamashita.melody.reference.RhythmPattern;
import engine_yamashita.melody.reference.RhythmPatternData;
import engine_yamashita.melody.reference.RhythmPatternDictionary;
import engine_yamashita.melody.reference.RhythmPatternDictionaryRecord;
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

		RhythmPatternDictionary rhythmPatternDictionary = new RhythmPatternDictionary();
		RhythmPattern currentMelodyRhythmPattern = getRhythmPattern(currentMelody);
		RhythmPattern previousMelodyRhythmPattern = getRhythmPattern(previousMelody);
		RhythmPatternDictionaryRecord bestMelodyRhythmPatternDictionaryRecord = null;

		PitchPatternDictionary pitchPatternDictionary = new PitchPatternDictionary();
		PitchPattern currentMelodyPitchPattern = getPitchPattern(previousMelody, currentMelody);
		PitchPattern previousMelodyPitchPattern = getPitchPattern(null, previousMelody);
		PitchPatternDictionaryRecord bestMelodyPitchPatternDictionaryRecord = null;

		double rhythmPatternMax = 0.0;
		double rhythmPatternContextSimilarity = 0.0;
		double rhythmPatternWordSimilarity = 0.0;
		for(int n = 0; n < rhythmPatternDictionary.size(); n++) {
			RhythmPattern target = previousMelodyRhythmPattern;
			RhythmPattern pattern = rhythmPatternDictionary.get(n).getContext();
			rhythmPatternContextSimilarity = calcRhythmPatternSimilarity(target, pattern);
			target = currentMelodyRhythmPattern;
			pattern = rhythmPatternDictionary.get(n).getWord();
			rhythmPatternWordSimilarity = calcRhythmPatternSimilarity(target, pattern);
			System.out.println(rhythmPatternDictionary.get(n).getName() + " : " + rhythmPatternContextSimilarity);
			System.out.println(rhythmPatternDictionary.get(n).getName() + " : " + rhythmPatternWordSimilarity);
			if(rhythmPatternMax <= rhythmPatternContextSimilarity + rhythmPatternWordSimilarity) {
				rhythmPatternMax = rhythmPatternContextSimilarity + rhythmPatternWordSimilarity;
				bestMelodyRhythmPatternDictionaryRecord = rhythmPatternDictionary.get(n);
			}
		}
		System.out.println(bestMelodyRhythmPatternDictionaryRecord.getName());
		System.out.println();

		double pitchPatternMax = 0.0;
		double pitchPatternContextSimilarity = 0.0;
		double pitchPatternWordSimilarity = 0.0;
		for(int n = 0; n < pitchPatternDictionary.size(); n++) {
			PitchPattern target = previousMelodyPitchPattern;
			PitchPattern pattern = pitchPatternDictionary.get(n).getContext();
			pitchPatternContextSimilarity = calcPitchPatternSimilarity(target, pattern);
			target = currentMelodyPitchPattern;;
			pattern = pitchPatternDictionary.get(n).getWord();
			pitchPatternWordSimilarity = calcPitchPatternSimilarity(target, pattern);
			System.out.println(pitchPatternDictionary.get(n).getName() + " : " + pitchPatternContextSimilarity);
			System.out.println(pitchPatternDictionary.get(n).getName() + " : " + pitchPatternWordSimilarity);
			if(pitchPatternMax <= pitchPatternContextSimilarity + pitchPatternWordSimilarity) {
				pitchPatternMax = pitchPatternContextSimilarity + pitchPatternWordSimilarity;
				bestMelodyPitchPatternDictionaryRecord = pitchPatternDictionary.get(n);
			}
		}
		System.out.println(bestMelodyPitchPatternDictionaryRecord.getName());
		System.out.println();

		MelodyMaker melodyMaker = new MelodyMaker();
		RhythmPattern bestRhythmPatternWord = bestMelodyRhythmPatternDictionaryRecord.getWord();
		PitchPattern bestPitchPatternWord = bestMelodyPitchPatternDictionaryRecord.getWord();
		RhythmPattern bestRhythmPatternContext = bestMelodyRhythmPatternDictionaryRecord.getContext();
		PitchPattern bestPitchPatternContext = bestMelodyPitchPatternDictionaryRecord.getContext();
		ArrayList<String> chordProgression = arrangeInformation.getCurrentChordProgression();
		ArrayList<MelodyLabel> melodyLabels = melodyMaker.makeMelody(bestRhythmPatternWord, bestPitchPatternWord, bestRhythmPatternContext, bestPitchPatternContext, chordProgression);

		return predictiedMelodies;
	}

	public RhythmPattern getRhythmPattern(ArrayList<NoteInformation> melody) {
		RhythmPattern rhythmPattern = new RhythmPattern();
		for(NoteInformation note : melody) {
			int position = note.getPosition() % (960 * 4);
			int duration = note.getDuration();
			rhythmPattern.addRhythm(position, duration);
		}
		return rhythmPattern;
	}

	public double calcRhythmPatternSimilarity(RhythmPattern target, RhythmPattern pattern) {
		ArrayList<RhythmPatternData> targetRhythms = target.getRhythms();
		ArrayList<RhythmPatternData> patternRhythms = pattern.getRhythms();
		int targetSize = targetRhythms.size();
		int patternSize = patternRhythms.size();

		if(targetSize == 0 || patternSize == 0) return 0.0;

		double[][] miss = new double[targetSize][patternSize];
		double[][] cost = new double[targetSize][patternSize];

		// 総当りで距離計算
		for(int i = 0; i < targetSize; i++) {
			RhythmPatternData target_i = targetRhythms.get(i);
			for(int j = 0; j < patternSize; j++) {
				RhythmPatternData pattern_j = patternRhythms.get(j);
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

	public PitchPattern getPitchPattern(ArrayList<NoteInformation> previousMelody, ArrayList<NoteInformation> currentMelody) {
		PitchPattern pitchPattern = new PitchPattern();
		for(int n = 0; n < currentMelody.size(); n++) {
			int previousNotePitch = 0;
			int currentNotePitch = currentMelody.get(n).getNote();
			if(n == 0) {
				if(previousMelody == null || previousMelody.size() <= 0) {
					pitchPattern.addPitch(0, 0, currentNotePitch);
				} else {
					previousNotePitch = previousMelody.get(previousMelody.size() - 1).getNote();
					if(previousNotePitch == currentNotePitch) {
						pitchPattern.addPitch(0, 0, currentNotePitch);
					} else if(previousNotePitch > currentNotePitch) {
						pitchPattern.addPitch(-1, Math.abs(previousNotePitch - currentNotePitch), currentNotePitch);
					} else {
						pitchPattern.addPitch(1, Math.abs(previousNotePitch - currentNotePitch), currentNotePitch);
					}
				}
			} else {
				previousNotePitch = currentMelody.get(n - 1).getNote();
				if(previousNotePitch == currentNotePitch) {
					pitchPattern.addPitch(0, 0, currentNotePitch);
				} else if(previousNotePitch > currentNotePitch) {
					pitchPattern.addPitch(-1, Math.abs(previousNotePitch - currentNotePitch), currentNotePitch);
				} else {
					pitchPattern.addPitch(1, Math.abs(previousNotePitch - currentNotePitch), currentNotePitch);
				}
			}
		}
		return pitchPattern;
	}

	public double calcPitchPatternSimilarity(PitchPattern target, PitchPattern pattern) {
		ArrayList<PitchPatternData> targetPitches = target.getPitches();
		ArrayList<PitchPatternData> patternPitches = pattern.getPitches();
		int targetSize = targetPitches.size();
		int patternSize = patternPitches.size();

		if(targetSize == 0 || patternSize == 0) return 0.0;

		double[][] miss = new double[targetSize][patternSize];
		double[][] cost = new double[targetSize][patternSize];

		// 総当りで距離計算
		for(int i = 0; i < targetSize; i++) {
			PitchPatternData target_i = targetPitches.get(i);
			for(int j = 0; j < patternSize; j++) {
				PitchPatternData pattern_j = patternPitches.get(j);
				// 音高パターン要素のユークリッド距離を計算
				double diffVariation = Math.pow(target_i.getVariation() - pattern_j.getVariation(), 2);
				double diffDifference = Math.pow(target_i.getDifference() - pattern_j.getDifference(), 2);
				double diffPlace = Math.pow(target_i.getPlace() - pattern_j.getPlace(), 2);
				miss[i][j] = Math.sqrt(diffVariation + diffDifference);
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
