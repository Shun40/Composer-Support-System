package engine_yamashita.melody;

import java.util.ArrayList;

import engine_yamashita.ArrangeInformation;
import engine_yamashita.Melody;
import engine_yamashita.melody.generation.MelodyLabel;
import engine_yamashita.melody.generation.MelodyMaker;
import engine_yamashita.melody.reference.MelodyPattern;
import engine_yamashita.melody.reference.MelodyPatternData;
import engine_yamashita.melody.reference.MelodyPatternDictionary;
import gui.Note;

/**
 * メロディを解析するクラス
 * @author Shun Yamashita
 */
public class MelodyAnalyzer {
	public MelodyAnalyzer() {
	}

	public ArrayList<Melody> getMelodies(ArrangeInformation arrangeInformation) {
		ArrayList<Melody> melodies = new ArrayList<Melody>();
		ArrayList<Note> currentMelody = arrangeInformation.getCurrentMelody();   // 現在小節のメロディ
		ArrayList<Note> previousMelody = arrangeInformation.getPreviousMelody(); // 1小節前のメロディ

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
		int justBeforeNotePitch = previousMelody.get(previousMelody.size() - 1).getPitch();
		int justBeforeNotePosition = previousMelody.get(previousMelody.size() - 1).getPosition();
		int justBeforeNoteDuration = previousMelody.get(previousMelody.size() - 1).getDuration();
		Note justBeforeNote = new Note(1, 0, justBeforeNotePitch, justBeforeNotePosition, justBeforeNoteDuration, 100, null);


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
			String name =
				melodyPatternDictionary.get(rank[m]).getName()
				+ "(" + pitchSimilarities[rank[m]].getContextSimilarity()
				+ ", " + pitchSimilarities[rank[m]].getWordSimilarity()
				+ ", " + rhythmSimilarities[rank[m]].getContextSimilarity()
				+ ", " + rhythmSimilarities[rank[m]].getWordSimilarity()
				+ ")";
			Melody melody = new Melody(name);
			for(int n = 1; n < melodyLabels.get(m).size(); n++) { // 先頭には直前音符の情報が入っているので1から始める
				int track = 1;
				int program = 0;
				int pitch = melodyLabels.get(m).get(n).getPitch();
				int position = melodyLabels.get(m).get(n).getPosition();
				int duration = melodyLabels.get(m).get(n).getDuration();
				int velocity = 100;
				melody.add(new Note(track, program, pitch, position, duration, velocity, null));
			}
			melodies.add(melody);
		}

		return melodies;
	}

	public MelodyPattern getMelodyPattern(ArrayList<Note> previousMelody, ArrayList<Note> currentMelody) {
		// 音高情報を抽出
		int[] variations = new int[currentMelody.size()];
		int[] differences = new int[currentMelody.size()];
		for(int i = 0; i < currentMelody.size(); i++) {
			int previousPitch = 0;
			int currentPitch = currentMelody.get(i).getPitch();
			if(i <= 0) {
				if(previousMelody == null || previousMelody.size() <= 0) {
					previousPitch = currentMelody.get(i).getPitch();
				} else {
					previousPitch = previousMelody.get(previousMelody.size() - 1).getPitch();
				}
			} else {
				previousPitch = currentMelody.get(i-1).getPitch();
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
