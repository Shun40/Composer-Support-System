package engine_yamashita.melody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import engine_yamashita.ArrangeInformation;
import engine_yamashita._MelodyPattern;
import engine_yamashita.melody.generation.MelodyLabel;
import engine_yamashita.melody.generation.MelodyMaker;
import engine_yamashita.melody.reference.MelodyPattern;
import engine_yamashita.melody.reference.MelodyPatternData;
import engine_yamashita.melody.reference.MelodyPatternDictionary;
import gui.NoteInformation;

/**
 * メロディを解析するクラス
 * @author Shun Yamashita
 */
public class MelodyAnalyzer {
	public MelodyAnalyzer() {
	}

	public ArrayList<_MelodyPattern> getMelodyPatterns(ArrangeInformation arrangeInformation) {
		ArrayList<_MelodyPattern> melodyPatterns = new ArrayList<_MelodyPattern>();
		ArrayList<NoteInformation> currentMelody = arrangeInformation.getCurrentMelody();   // 現在小節のメロディ
		ArrayList<NoteInformation> previousMelody = arrangeInformation.getPreviousMelody(); // 1小節前のメロディ

		MelodyPatternDictionary melodyPatternDictionary = new MelodyPatternDictionary();
		MelodyPattern currentMelodyPattern = getMelodyPattern(previousMelody, currentMelody);
		MelodyPattern previousMelodyPattern = getMelodyPattern(null, previousMelody);
		Similarity[] pitchSimilarities = new Similarity[melodyPatternDictionary.size()];
		Similarity[] rhythmSimilarities = new Similarity[melodyPatternDictionary.size()];
		int[] rank = new int[melodyPatternDictionary.size()];
		for(int n = 0; n < rank.length; n++) rank[n] = n;

		// 音高パターンの類似度計算
		for(int n = 0; n < melodyPatternDictionary.size(); n++) {
			// context部分の類似度計算
			MelodyPattern target = previousMelodyPattern;
			MelodyPattern pattern = melodyPatternDictionary.get(n).getContext();
			double contextSimilarity = calcPitchSimilarity(target, pattern);
			// word部分の類似度計算
			target = currentMelodyPattern;
			pattern = melodyPatternDictionary.get(n).getWord();
			double wordSimilarity = calcPitchSimilarity(target, pattern);
			pitchSimilarities[n] = new Similarity(n, contextSimilarity, wordSimilarity);
		}

		// リズムパターンの類似度計算
		for(int n = 0; n < melodyPatternDictionary.size(); n++) {
			// context部分の類似度計算
			MelodyPattern target = previousMelodyPattern;
			MelodyPattern pattern = melodyPatternDictionary.get(n).getContext();
			double contextSimilarity = calcRhythmSimilarity(target, pattern);
			// word部分の類似度計算
			target = currentMelodyPattern;
			pattern = melodyPatternDictionary.get(n).getWord();
			double wordSimilarity = calcRhythmSimilarity(target, pattern);
			rhythmSimilarities[n] = new Similarity(n, contextSimilarity, wordSimilarity);
		}

		// ソート
		for(int m = 0; m < melodyPatternDictionary.size(); m++) {
			for(int n = m; n < melodyPatternDictionary.size(); n++) {
				double sum_m = rhythmSimilarities[m].getScore() + pitchSimilarities[m].getScore();
				double sum_n = rhythmSimilarities[n].getScore() + pitchSimilarities[n].getScore();
				if(sum_m <= sum_n) {
					int temp = rank[m];
					rank[m] = rank[n];
					rank[n] = temp;
				}
			}
		}
		System.out.println(melodyPatternDictionary.get(rank[0]).getName());
		System.out.println(melodyPatternDictionary.get(rank[1]).getName());
		System.out.println(melodyPatternDictionary.get(rank[2]).getName());
		System.out.println(melodyPatternDictionary.get(rank[3]).getName());
		System.out.println(melodyPatternDictionary.get(rank[4]).getName());

		// リズムパターンと音高パターンを組み合わせてメロディを生成
		int justBeforeNotePitch = previousMelody.get(previousMelody.size() - 1).getNote();
		int justBeforeNotePosition = previousMelody.get(previousMelody.size() - 1).getPosition();
		int justBeforeNoteDuration = previousMelody.get(previousMelody.size() - 1).getDuration();
		NoteInformation justBeforeNote = new NoteInformation(1, 0, justBeforeNotePitch, justBeforeNotePosition, justBeforeNoteDuration, 100, null);


		ArrayList<ArrayList<MelodyLabel>> melodyLabels = new ArrayList<ArrayList<MelodyLabel>>();
		MelodyMaker melodyMaker = new MelodyMaker();
		for(int n = 0; n < melodyPatternDictionary.size(); n++) {
			MelodyPattern context = melodyPatternDictionary.get(rank[n]).getContext();
			MelodyPattern word = melodyPatternDictionary.get(rank[n]).getWord();
			ArrayList<String> chordProgression = arrangeInformation.getCurrentChordProgression();
			melodyLabels.add(melodyMaker.makeMelody(context, word, chordProgression, justBeforeNote));
		}

		// メロディを整形
		for(int m = 0; m < melodyLabels.size(); m++) {
			String name = melodyPatternDictionary.get(rank[m]).getName();
			_MelodyPattern melodyPattern = new _MelodyPattern(name);
			for(int n = 1; n < melodyLabels.get(m).size(); n++) { // 先頭には直前音符の情報が入っているので1から始める
				int track = 1;
				int program = 0;
				int pitch = melodyLabels.get(m).get(n).getPitch();
				int position = melodyLabels.get(m).get(n).getPosition();
				int duration = melodyLabels.get(m).get(n).getDuration();
				int velocity = 100;
				melodyPattern.add(new NoteInformation(track, program, pitch, position, duration, velocity, null));
			}
			melodyPatterns.add(melodyPattern);
		}

		return melodyPatterns;
	}

	public MelodyPattern getMelodyPattern(ArrayList<NoteInformation> previousMelody, ArrayList<NoteInformation> currentMelody) {
		// 音高情報を抽出
		int[] variations = new int[currentMelody.size()];
		int[] differences = new int[currentMelody.size()];
		for(int i = 0; i < currentMelody.size(); i++) {
			int previousPitch = 0;
			int currentPitch = currentMelody.get(i).getNote();
			if(i <= 0) {
				if(previousMelody == null || previousMelody.size() <= 0) {
					previousPitch = currentMelody.get(i).getNote();
				} else {
					previousPitch = previousMelody.get(previousMelody.size() - 1).getNote();
				}
			} else {
				previousPitch = currentMelody.get(i-1).getNote();
			}
			if(previousPitch == currentPitch) variations[i] = 0;
			if(previousPitch > currentPitch) variations[i] = -1;
			if(previousPitch < currentPitch) variations[i] = 1;
			differences[i] = Math.abs(previousPitch - currentPitch);
		}

		// リズム情報を抽出
		int[] positions = new int[currentMelody.size()];
		int[] durations = new int[currentMelody.size()];
		for(int i = 0; i < currentMelody.size(); i++) {
			positions[i] = currentMelody.get(i).getPosition();
			durations[i] = currentMelody.get(i).getDuration();
		}

		// メロディパターン生成
		MelodyPattern melodyPattern = new MelodyPattern();
		for(int i = 0; i < currentMelody.size(); i++) {
			melodyPattern.add(variations[i], differences[i], positions[i], durations[i]);
		}

		return melodyPattern;
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
				double diffVariation = Math.pow(target_i.getVariation() - pattern_j.getVariation(), 2);
				double diffDifference = Math.pow(target_i.getDifference() - pattern_j.getDifference(), 2);
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

	public class Similarity {
		private int index;
		private double contextSimilarity;
		private double wordSimilarity;
		public Similarity(int index, double contextSimilarity, double wordSimilarity) {
			this.index = index;
			this.contextSimilarity = contextSimilarity;
			this.wordSimilarity = wordSimilarity;
		}
		public double getScore() { return contextSimilarity + wordSimilarity; }
		public int getIndex() { return index; }
		public double getContextSimilarity() { return contextSimilarity; }
		public double getWordSimilarity() { return wordSimilarity; }
	}
}
