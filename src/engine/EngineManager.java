package engine;

import java.util.ArrayList;
import java.util.List;

import engine.melody.CandidateMelody;
import engine.melody.ChordProgression;
import engine.melody.Melody;
import engine.melody.RelativeMelody;
import engine.melody.dictionary.Dictionary;
import engine.melody.dictionary.PhraseDictionary;
import engine.melody.dictionary.WordDictionary;
import engine.melody.generation.CandidateLabel;
import engine.melody.generation.MelodyMaker;
import engine.melody.sort.DPMatching;
import gui.component.pianoroll.note.NoteModel;
import midi.MidiConstants;
import midi.MidiUtil;
import system.AppConstants;

/**
 * エンジンの主な機能を管理するクラス
 * @author Shun Yamashita
 */
public class EngineManager {
	/** 単語辞書 */
	private Dictionary wordDictionary;
	/** 例文辞書 */
	private Dictionary phraseDictionary;

	public EngineManager() {
		wordDictionary = new WordDictionary();
		phraseDictionary = new PhraseDictionary();
	}

	public List<CandidateMelody> makeCandidateMelodies(PredictionParameter predictionParameter, AlgorithmParameter algorithmParameter) {
		// 候補メロディ生成に必要な情報を取得
		// 対象小節番号
		int targetMeasure = predictionParameter.getTargetMeasure();
		// 入力メロディ
		Melody melody = predictionParameter.getMelody();
		// 入力コード進行
		ChordProgression chordProgression = predictionParameter.getChordProgression();
		// 選択アルゴリズム
		List<AppConstants.Algorithm> selectedAlgorithms = algorithmParameter.getSelectedAlgorithms();
		// 選択メロディ構造パターン
		AppConstants.MelodyStructurePattern selectedMelodyStructurePattern = algorithmParameter.getSelectedMelodyStructurePattern();

		//showMelodyLine(melody);

		// 1. 下準備
		// 入力メロディのノートを発音位置が早い順にソート
		melody.sort((a, b) -> a.getPosition() - b.getPosition());
		// 対象小節の相対メロディを抽出
		RelativeMelody current = melody.extractRelativeMelodyIncludedInMeasure(targetMeasure);
		// 対象小節の1小節前の相対メロディを抽出
		RelativeMelody previous = melody.extractRelativeMelodyIncludedInMeasure(targetMeasure - 1);

		// 2. 辞書の選択
		Dictionary dictionary = (previous.isEmpty() ? wordDictionary : phraseDictionary);
		// ランクの初期化
		int[] rank = new int[dictionary.size()];
		for(int n = 0; n < rank.length; n++) {
			rank[n] = n;
		}

		// 3. 辞書登録済みパターンから候補メロディを生成
		NoteModel justBeforeNote = melody.getIncludedInMeasure(targetMeasure - 1).getLastPosition();
		String justBeforeChord = chordProgression.getIncludedInMeasure(targetMeasure - 1).getLast();
		ChordProgression targetChordProgression = chordProgression.getIncludedInMeasure(targetMeasure);
		List<List<CandidateLabel>> labelsList = new ArrayList<List<CandidateLabel>>();
		for(int n = 0; n < dictionary.size(); n++) {
			RelativeMelody context = dictionary.get(rank[n]).getContext(); // 単語辞書の時はnullになる
			RelativeMelody word = dictionary.get(rank[n]).getWord();
			labelsList.add(MelodyMaker.makeMelody(context, word, melody, justBeforeNote, targetChordProgression, justBeforeChord, selectedAlgorithms));
		}

		// 4. 類似度等のスコアを用いて候補メロディをソート
		// PCアルゴリズムの音高・リズムパターン類似度計算
		Similarity[] pcPitchSimilarities = calcPcSimilarities(dictionary, AppConstants.SimilarityType.PITCH, previous, current);
		Similarity[] pcRhythmSimilarities = calcPcSimilarities(dictionary, AppConstants.SimilarityType.RHYTHM, previous, current);
		// MSアルゴリズムのリズムパターン類似度計算
		Similarity[] msRhythmSimilarities = calcMsSimilarities(dictionary, AppConstants.SimilarityType.RHYTHM, selectedMelodyStructurePattern, targetMeasure, melody);
		// RBアルゴリズムのスコア計算
		double[] rbScores = calcRbScores(melody, labelsList);
		// ソート
		for(int m = 0; m < dictionary.size(); m++) {
			System.out.print((m + 1) + ": ");
			System.out.print("Freq: " + dictionary.get(m).getFrequency() + ", ");
			System.out.print("PC: " + (pcPitchSimilarities[m].getScore() + pcRhythmSimilarities[m].getScore()) + ", ");
			System.out.print("MS: " + (AppConstants.AlgorithmSettings.MS_WEIGHT * msRhythmSimilarities[m].getScore()) + ", ");
			System.out.println("RB: " + (AppConstants.AlgorithmSettings.RB_WEIGHT * rbScores[m]));
			for(int n = m; n < dictionary.size(); n++) {
				double sum_m = 0.0, sum_n = 0.0;
				if(selectedAlgorithms.contains(AppConstants.Algorithm.PC)) {
					sum_m += pcPitchSimilarities[rank[m]].getScore() + pcRhythmSimilarities[rank[m]].getScore();
					sum_n += pcPitchSimilarities[rank[n]].getScore() + pcRhythmSimilarities[rank[n]].getScore();
				}
				if(selectedAlgorithms.contains(AppConstants.Algorithm.MS)) {
					sum_m += AppConstants.AlgorithmSettings.MS_WEIGHT * msRhythmSimilarities[rank[m]].getScore();
					sum_n += AppConstants.AlgorithmSettings.MS_WEIGHT * msRhythmSimilarities[rank[n]].getScore();
				}
				if(selectedAlgorithms.contains(AppConstants.Algorithm.RB)) {
					sum_m += AppConstants.AlgorithmSettings.RB_WEIGHT * rbScores[rank[m]];
					sum_n += AppConstants.AlgorithmSettings.RB_WEIGHT * rbScores[rank[n]];
				}
				double score_m = sum_m * dictionary.get(rank[m]).getFrequency(); // 選択頻度を重みとして乗じる
				double score_n = sum_n * dictionary.get(rank[n]).getFrequency(); // 選択頻度を重みとして乗じる
				if(sum_m <= 0 && sum_n <= 0) { // 類似度が共に0以下の場合は選択頻度のみで大小比較する
					score_m = dictionary.get(rank[m]).getFrequency();
					score_n = dictionary.get(rank[n]).getFrequency();
				}
				if(score_m < score_n) {
					int temp = rank[m];
					rank[m] = rank[n];
					rank[n] = temp;
				}
			}
		}

		// 5. メロディを整形
		List<CandidateMelody> candidateMelodies = new ArrayList<CandidateMelody>();
		for(int m = 0; m < labelsList.size(); m++) {
			int index = dictionary.get(rank[m]).getIndex();
			String id = dictionary.get(rank[m]).getWord().getId();
			int frequency = dictionary.get(rank[m]).getFrequency();
			String name = dictionary.get(rank[m]).getName() + " (" + (dictionary.get(rank[m]).getFrequency() - 1) + "回)";
			CandidateMelody candidateMelody = new CandidateMelody(index, id, frequency, name);
			for(int n = 1; n < labelsList.get(rank[m]).size(); n++) { // 先頭には直前音符の情報が入っているので1から始める
				int track = AppConstants.MelodySettings.MELODY_TRACK;
				int program = AppConstants.MelodySettings.MELODY_PROGRAM;
				int pitch = labelsList.get(rank[m]).get(n).getPitch();
				int position = labelsList.get(rank[m]).get(n).getPosition() + (targetMeasure - 1) * MidiUtil.getDurationOf1Measure();
				int duration = labelsList.get(rank[m]).get(n).getDuration();
				int velocity = 100;
				candidateMelody.add(new NoteModel(track, program, pitch, position, duration, velocity));
			}
			candidateMelodies.add(candidateMelody);
		}
		return candidateMelodies;
	}

	private Similarity[] calcPcSimilarities(Dictionary dictionary, AppConstants.SimilarityType similarityType, RelativeMelody previous, RelativeMelody current) {
		Similarity[] similarities = new Similarity[dictionary.size()];
		RelativeMelody target, pattern;
		double contextSimilarity = 0.0, wordSimilarity = 0.0;

		for(int n = 0; n < similarities.length; n++) {
			target = previous;
			pattern = dictionary.get(n).getContext();
			switch(similarityType) {
			case PITCH:
				contextSimilarity = DPMatching.calcPitchSimilarity(target, pattern); // 単語辞書の時は0.0になる
				break;
			case RHYTHM:
				contextSimilarity = DPMatching.calcRhythmSimilarity(target, pattern); // 単語辞書の時は0.0になる
				break;
			}
			target = current;
			pattern = dictionary.get(n).getWord();
			switch(similarityType) {
			case PITCH:
				wordSimilarity = DPMatching.calcPitchSimilarity(target, pattern);
				break;
			case RHYTHM:
				wordSimilarity = DPMatching.calcRhythmSimilarity(target, pattern);
				break;
			}
			similarities[n] = new Similarity(contextSimilarity, wordSimilarity);
		}
		return similarities;
	}

	private Similarity[] calcMsSimilarities(Dictionary dictionary, AppConstants.SimilarityType similarityType, AppConstants.MelodyStructurePattern melodyStructurePattern, int targetMeasure, Melody melody) {
		Similarity[] similarities = new Similarity[dictionary.size()];
		RelativeMelody reference = null, target, pattern;
		double similarity = 0.0;

		switch(melodyStructurePattern) {
		case ABCD:
			break;
		case ABAB:
			if(targetMeasure >= 3) {
				// (targetMeasure - 2)小節目のメロディ情報を抽出
				reference = melody.extractRelativeMelodyIncludedInMeasure(targetMeasure - 2);
			}
			break;
		case AABB:
			if(targetMeasure == 2 || targetMeasure == 4) {
				// (targetMeasure - 1)小節目のメロディ情報を抽出
				reference = melody.extractRelativeMelodyIncludedInMeasure(targetMeasure - 1);
			}
			break;
		case AABC:
			if(targetMeasure == 2) {
				// (targetMeasure - 1)小節目のメロディ情報を抽出
				reference = melody.extractRelativeMelodyIncludedInMeasure(targetMeasure - 1);
			}
			break;
		case ABCC:
			if(targetMeasure == 4) {
				// (targetMeasure - 1)小節目のメロディ情報を抽出
				reference = melody.extractRelativeMelodyIncludedInMeasure(targetMeasure - 1);
			}
			break;
		}

		if(reference == null) {
			for(int n = 0; n < similarities.length; n++) {
				similarities[n] = new Similarity(0.0, 0.0);
			}
		} else {
			for(int n = 0; n < similarities.length; n++) {
				target = reference;
				pattern = dictionary.get(n).getWord();
				switch(similarityType) {
				case PITCH:
					similarity = DPMatching.calcPitchSimilarity(target, pattern);
					break;
				case RHYTHM:
					similarity = DPMatching.calcRhythmSimilarity(target, pattern);
					break;
				}
				similarities[n] = new Similarity(0.0, similarity);
			}
		}
		return similarities;
	}

	private double[] calcRbScores(Melody melody, List<List<CandidateLabel>> labelsList) {
		if(melody.isEmpty()) {
			double[] scores = new double[labelsList.size()];
			for(int n = 0; n < scores.length; n++) {
				scores[n] = 0.0;
			}
			return scores;
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
		// よって標準偏差がしきい値よりも小さい場合は0.0を返し, 正規分布による影響を与えないようにする
		if(dev <= 3.0) {
			double[] scores = new double[labelsList.size()];
			for(int n = 0; n < scores.length; n++) {
				scores[n] = 0.0;
			}
			return scores;
		}

		double[] scores = new double[labelsList.size()];
		double max = 0.0;
		for(int n = 0; n < scores.length; n++) {
			temp1 = 0;
			temp2 = 0;
			List<CandidateLabel> labels = labelsList.get(n);
			for(CandidateLabel label : labels) {
				// 分子計算
				int weight = label.getDuration() / (MidiConstants.PPQ / 4); // 発音時間長で重み付けする
				temp1 += label.getPitch() * weight;
				// 分母計算
				temp2 += weight;
			}
			double _ave = (double)temp1 / (double)temp2;
			scores[n] = (1.0 / (Math.sqrt(2.0 * Math.PI) * dev)) * Math.exp(-(Math.pow(_ave - ave, 2)) / (2.0 * Math.pow(dev, 2)));
			if(max <= scores[n]) {
				max = scores[n];
			}
		}

		for(int n = 0; n < scores.length; n++) {
			scores[n] /= max;
		}
		return scores;
	}

	private void showMelodyLine(Melody melody) {
		int min = melody.getMinPitch();
		int max = melody.getMaxPitch();
		if(min == 128 || max == -1) {
			return;
		}
		for(int y = max; y >= min; y--) {
			for(int x = 0; x < melody.size(); x++) {
				System.out.print("|");
				if(melody.get(x).getPitch() == y) {
					for(int d = 0; d < melody.get(x).getDuration() / (MidiConstants.PPQ / 4); d++) {
						System.out.print("*");
					}
				} else {
					for(int d = 0; d < melody.get(x).getDuration() / (MidiConstants.PPQ / 4); d++) {
						System.out.print("_");
					}
				}
			}
			System.out.println();
		}
	}

	public void readWordDictionary() {
		wordDictionary.readDictionary();
	}

	public void readWordDictionary(String filename) {
		wordDictionary.readDictionary(filename);
	}

	public void readPhraseDictionary() {
		phraseDictionary.readDictionary();
	}

	public void readPhraseDictionary(String filename) {
		phraseDictionary.readDictionary(filename);
	}

	public void incFrequencyOfWordDictionary(String wordId) {
		wordDictionary.incPatternFrequency(wordId);
	}

	public void incFrequencyOfPhraseDictionary(String contextId, String wordId) {
		phraseDictionary.incPatternFrequency(contextId, wordId);
	}

	public void showWordDictionary() {
		wordDictionary.showDictionary();
	}

	public void showPhraseDictionary() {
		phraseDictionary.showDictionary();
	}

	public Dictionary getWordDictionary() {
		return wordDictionary;
	}

	public Dictionary getPhraseDictionary() {
		return phraseDictionary;
	}

	private class Similarity {
		private double contextSimilarity;
		private double wordSimilarity;
		public Similarity(double contextSimilarity, double wordSimilarity) {
			this.contextSimilarity = contextSimilarity;
			this.wordSimilarity = wordSimilarity;
		}
		public double getScore() {
			return contextSimilarity + wordSimilarity;
		}
	}
}
