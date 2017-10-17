package engine_yamashita.melody;

import static gui.constants.UniversalConstants.*;

import java.util.ArrayList;

import engine_yamashita.Melody;
import engine_yamashita.PredictionInformation;
import engine_yamashita.melody.generation.MelodyLabel;
import engine_yamashita.melody.generation.MelodyMaker;
import engine_yamashita.melody.reference.DPMatching;
import engine_yamashita.melody.reference.MelodyPattern;
import engine_yamashita.melody.reference.PhraseDictionary;
import engine_yamashita.melody.reference.WordDictionary;
import gui.ChordPair;
import gui.Note;
import gui.constants.UniversalConstants.Algorithm;
import gui.constants.UniversalConstants.DictionaryType;
import gui.constants.UniversalConstants.SimilarityType;
import system.UIController;

/**
 * メロディを解析するクラス
 * @author Shun Yamashita
 */
public class MelodyAnalyzer {
	private WordDictionary wordDictionary;
	private PhraseDictionary phraseDictionary;
	private Algorithm algorithm;
	private UIController uiController;

	public MelodyAnalyzer(UIController uiController) {
		wordDictionary = new WordDictionary();
		phraseDictionary = new PhraseDictionary(this);
		algorithm = Algorithm.PC;
		this.uiController = uiController;
	}

	public ArrayList<Melody> getMelodies(PredictionInformation predictionInformation) {
		int targetMeasure = predictionInformation.getTargetMeasure(); // 対象小節番号
		ArrayList<Note> melodyNotes = predictionInformation.getMelodyNotes();   // メロディの音符
		ChordPair[] chordProgression = predictionInformation.getChordProgression(); // コード進行

		// 下準備
		melodyNotes.sort((a, b) -> a.getPosition() - b.getPosition()); // positionが小さい順にソート
		ArrayList<Note> targetNotes = getInMeasureNotes(targetMeasure, melodyNotes);
		ArrayList<Note> previousNotes1 = getInMeasureNotes(targetMeasure - 1, melodyNotes);
		ArrayList<Note> previousNotes2 = getInMeasureNotes(targetMeasure - 2, melodyNotes);

		MelodyPattern previous = getMelodyPattern(getLastNote(previousNotes2), previousNotes1);
		MelodyPattern current = getMelodyPattern(getLastNote(previousNotes1), targetNotes);

		double msWeight = 2.0; // メロディ構造のマッチングスコアの重み

		if(previous.isEmpty()) { // 単語辞書を検索する場合
			Similarity[] pcPitchSimilarities, pcRhythmSimilarities, msRhythmSimilarities;
			int[] rank = new int[wordDictionary.size()];
			for(int n = 0; n < rank.length; n++) {
				rank[n] = n;
			}

			// 類似度計算
			// PCアルゴリズムの音高パターン類似度計算
			pcPitchSimilarities = calcPcSimilarities(DictionaryType.WORD, SimilarityType.PITCH, previous, current);
			// PCアルゴリズムのリズムパターン類似度計算
			pcRhythmSimilarities = calcPcSimilarities(DictionaryType.WORD, SimilarityType.RHYTHM, previous, current);
			// MSアルゴリズムのリズムパターン類似度計算
			msRhythmSimilarities = calcMsSimilarities(SimilarityType.RHYTHM, previous, current, targetMeasure, melodyNotes);

			// ソート
			for(int m = 0; m < wordDictionary.size(); m++) {
				for(int n = m; n < wordDictionary.size(); n++) {
					double sum_m = 0.0, sum_n = 0.0;
					switch(algorithm) {
					case PC:
						sum_m = pcPitchSimilarities[rank[m]].getScore() + pcRhythmSimilarities[rank[m]].getScore();
						sum_n = pcPitchSimilarities[rank[n]].getScore() + pcRhythmSimilarities[rank[n]].getScore();
						break;
					case MS:
						sum_m = msRhythmSimilarities[rank[m]].getScore();
						sum_n = msRhythmSimilarities[rank[n]].getScore();
						break;
					case PC_AND_MS:
						sum_m = pcPitchSimilarities[rank[m]].getScore() + pcRhythmSimilarities[rank[m]].getScore() + (msWeight * msRhythmSimilarities[rank[m]].getScore());
						sum_n = pcPitchSimilarities[rank[n]].getScore() + pcRhythmSimilarities[rank[n]].getScore() + (msWeight * msRhythmSimilarities[rank[n]].getScore());
						break;
					}
					double frequency_m = wordDictionary.get(rank[m]).getFrequency();
					double frequency_n = wordDictionary.get(rank[n]).getFrequency();
					double score_m = sum_m * frequency_m; // 選択頻度を重みとして乗じる
					double score_n = sum_n * frequency_n; // 選択頻度を重みとして乗じる
					if(sum_m <= 0 && sum_n <= 0) { // 類似度が共に0以下の場合は選択頻度のみで大小比較する
						score_m = frequency_m;
						score_n = frequency_n;
					}
					if(score_m < score_n) {
						int temp = rank[m];
						rank[m] = rank[n];
						rank[n] = temp;
					}
				}
			}

			// リズムパターンと音高パターンを組み合わせてメロディを生成
			Note justBeforeNote = getLastNote(previousNotes1);
			ArrayList<String> targetChordProgression = getInMeasureChordProgression(targetMeasure, chordProgression);
			ArrayList<String> previousChordProgression = getInMeasureChordProgression(targetMeasure - 1, chordProgression);
			String justBeforeChord = getLastChord(previousChordProgression);

			ArrayList<ArrayList<MelodyLabel>> melodyLabels = new ArrayList<ArrayList<MelodyLabel>>();
			MelodyMaker melodyMaker = new MelodyMaker();
			for(int n = 0; n < wordDictionary.size(); n++) {
				MelodyPattern context = null;
				MelodyPattern word = wordDictionary.get(rank[n]).getWord();
				melodyLabels.add(melodyMaker.makeMelody(context, word, justBeforeNote, targetChordProgression, justBeforeChord));
			}

			// メロディを整形
			ArrayList<Melody> melodies = new ArrayList<Melody>();
			for(int m = 0; m < melodyLabels.size(); m++) {
				int index = wordDictionary.get(rank[m]).getIndex();
				String id = wordDictionary.get(rank[m]).getWord().getId();
				String name = wordDictionary.get(rank[m]).getName() + " (" + (wordDictionary.get(rank[m]).getFrequency() - 1) + "回)";
				int frequency = wordDictionary.get(rank[m]).getFrequency();
				Melody melody = new Melody(index, id, name, frequency);
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
		} else { // 例文辞書を検索する場合
			Similarity[] pcPitchSimilarities, pcRhythmSimilarities, msRhythmSimilarities;
			int[] rank = new int[phraseDictionary.size()];
			for(int n = 0; n < rank.length; n++) {
				rank[n] = n;
			}

			// 類似度計算
			// PCアルゴリズムの音高パターン類似度計算
			pcPitchSimilarities = calcPcSimilarities(DictionaryType.PHRASE, SimilarityType.PITCH, previous, current);
			// PCアルゴリズムのリズムパターン類似度計算
			pcRhythmSimilarities = calcPcSimilarities(DictionaryType.PHRASE, SimilarityType.RHYTHM, previous, current);
			// MSアルゴリズムのリズムパターン類似度計算
			msRhythmSimilarities = calcMsSimilarities(SimilarityType.RHYTHM, previous, current, targetMeasure, melodyNotes);

			// ソート
			for(int m = 0; m < phraseDictionary.size(); m++) {
				for(int n = m; n < phraseDictionary.size(); n++) {
					double sum_m = 0.0, sum_n = 0.0;
					switch(algorithm) {
					case PC:
						sum_m = pcPitchSimilarities[rank[m]].getScore() + pcRhythmSimilarities[rank[m]].getScore();
						sum_n = pcPitchSimilarities[rank[n]].getScore() + pcRhythmSimilarities[rank[n]].getScore();
						break;
					case MS:
						sum_m = msRhythmSimilarities[rank[m]].getScore();
						sum_n = msRhythmSimilarities[rank[n]].getScore();
						break;
					case PC_AND_MS:
						sum_m = pcPitchSimilarities[rank[m]].getScore() + pcRhythmSimilarities[rank[m]].getScore() + (msWeight * msRhythmSimilarities[rank[m]].getScore());
						sum_n = pcPitchSimilarities[rank[n]].getScore() + pcRhythmSimilarities[rank[n]].getScore() + (msWeight * msRhythmSimilarities[rank[n]].getScore());
						break;
					}
					double frequency_m = phraseDictionary.get(rank[m]).getFrequency();
					double frequency_n = phraseDictionary.get(rank[n]).getFrequency();
					double score_m = sum_m * frequency_m; // 選択頻度を重みとして乗じる
					double score_n = sum_n * frequency_n; // 選択頻度を重みとして乗じる
					if(sum_m <= 0 && sum_n <= 0) { // 類似度が共に0以下の場合は選択頻度のみで大小比較する
						score_m = frequency_m;
						score_n = frequency_n;
					}
					if(score_m < score_n) {
						int temp = rank[m];
						rank[m] = rank[n];
						rank[n] = temp;
					}
				}
			}
			// リズムパターンと音高パターンを組み合わせてメロディを生成
			Note justBeforeNote = getLastNote(previousNotes1);
			ArrayList<String> targetChordProgression = getInMeasureChordProgression(targetMeasure, chordProgression);
			ArrayList<String> previousChordProgression = getInMeasureChordProgression(targetMeasure - 1, chordProgression);
			String justBeforeChord = getLastChord(previousChordProgression);

			ArrayList<ArrayList<MelodyLabel>> melodyLabels = new ArrayList<ArrayList<MelodyLabel>>();
			MelodyMaker melodyMaker = new MelodyMaker();
			for(int n = 0; n < phraseDictionary.size(); n++) {
				MelodyPattern context = phraseDictionary.get(rank[n]).getContext();
				MelodyPattern word = phraseDictionary.get(rank[n]).getWord();
				melodyLabels.add(melodyMaker.makeMelody(context, word, justBeforeNote, targetChordProgression, justBeforeChord));
			}

			// メロディを整形
			ArrayList<Melody> melodies = new ArrayList<Melody>();
			for(int m = 0; m < melodyLabels.size(); m++) {
				int index = phraseDictionary.get(rank[m]).getIndex();
				String id = phraseDictionary.get(rank[m]).getWord().getId();
				String name = phraseDictionary.get(rank[m]).getName() + " (" + (phraseDictionary.get(rank[m]).getFrequency() - 1) + "回)";
				int frequency = phraseDictionary.get(rank[m]).getFrequency();
				Melody melody = new Melody(index, id, name, frequency);
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
	}

	private Similarity[] calcPcSimilarities(DictionaryType dictionaryType, SimilarityType similarityType, MelodyPattern previous, MelodyPattern current) {
		Similarity[] similarities;
		MelodyPattern target, pattern;
		double contextSimilarity, wordSimilarity;
		switch(dictionaryType) {
		case WORD:
			similarities = new Similarity[wordDictionary.size()];
			for(int n = 0; n < similarities.length; n++) {
				target = current;
				pattern = wordDictionary.get(n).getWord();
				switch(similarityType) {
				case PITCH:
					wordSimilarity = DPMatching.calcPitchSimilarity(target, pattern);
					break;
				case RHYTHM:
					wordSimilarity = DPMatching.calcRhythmSimilarity(target, pattern);
					break;
				default:
					wordSimilarity = 0.0;
					break;
				}
				similarities[n] = new Similarity(0.0, wordSimilarity);
			}
			break;
		case PHRASE:
			similarities = new Similarity[phraseDictionary.size()];
			for(int n = 0; n < similarities.length; n++) {
				target = previous;
				pattern = phraseDictionary.get(n).getContext();
				switch(similarityType) {
				case PITCH:
					contextSimilarity = DPMatching.calcPitchSimilarity(target, pattern);
					break;
				case RHYTHM:
					contextSimilarity = DPMatching.calcRhythmSimilarity(target, pattern);
					break;
				default:
					contextSimilarity = 0.0;
					break;
				}

				target = current;
				pattern = phraseDictionary.get(n).getWord();
				switch(similarityType) {
				case PITCH:
					wordSimilarity = DPMatching.calcPitchSimilarity(target, pattern);
					break;
				case RHYTHM:
					wordSimilarity = DPMatching.calcRhythmSimilarity(target, pattern);
					break;
				default:
					wordSimilarity = 0.0;
					break;
				}

				similarities[n] = new Similarity(contextSimilarity, wordSimilarity);
			}
			break;
		default:
			similarities = new Similarity[wordDictionary.size()];
			for(int n = 0; n < similarities.length; n++) {
				similarities[n] = new Similarity(0.0, 0.0);
			}
			break;
		}
		return similarities;
	}

	private Similarity[] calcMsSimilarities(SimilarityType similarityType, MelodyPattern previous, MelodyPattern current, int targetMeasure, ArrayList<Note> melodyNotes) {
		Similarity[] similarities = new Similarity[wordDictionary.size()];
		MelodyPattern target, pattern;
		double similarity;
		if(targetMeasure == 3 || targetMeasure == 4) {
			// (targetMeasure - 2)小節目のメロディ情報を抽出
			MelodyPattern reference = getMelodyPattern(getLastNote(getInMeasureNotes(targetMeasure - 3, melodyNotes)), getInMeasureNotes(targetMeasure - 2, melodyNotes));
			for(int n = 0; n < similarities.length; n++) {
				target = reference;
				pattern = wordDictionary.get(n).getWord();
				switch(similarityType) {
				case PITCH:
					similarity = DPMatching.calcPitchSimilarity(target, pattern);
					break;
				case RHYTHM:
					similarity = DPMatching.calcRhythmSimilarity(target, pattern);
					break;
				default:
					similarity = 0.0;
					break;
				}
				similarities[n] = new Similarity(0.0, similarity);
			}
		} else {
			for(int n = 0; n < similarities.length; n++) {
				similarities[n] = new Similarity(0.0, 0.0);
			}
		}
		return similarities;
	}

	public MelodyPattern getMelodyPattern(Note justBeforeNote, ArrayList<Note> melody) {
		// 音高情報を抽出
		int[] variations = new int[melody.size()];
		int[] differences = new int[melody.size()];
		for(int i = 0; i < melody.size(); i++) {
			int previousPitch = 0;
			int currentPitch = melody.get(i).getPitch();
			if(i <= 0) {
				if(justBeforeNote == null) {
					previousPitch = melody.get(i).getPitch();
				} else {
					previousPitch = justBeforeNote.getPitch();
				}
			} else {
				previousPitch = melody.get(i-1).getPitch();
			}
			if(previousPitch == currentPitch) variations[i] = 0;
			if(previousPitch > currentPitch) variations[i] = -1;
			if(previousPitch < currentPitch) variations[i] = 1;
			differences[i] = Math.abs(previousPitch - currentPitch);
		}

		// リズム情報を抽出
		int[] positions = new int[melody.size()];
		int[] durations = new int[melody.size()];
		for(int i = 0; i < melody.size(); i++) {
			positions[i] = melody.get(i).getPosition() % (PPQ * 4);
			durations[i] = melody.get(i).getDuration();
		}

		// メロディパターン生成
		MelodyPattern melodyPattern = new MelodyPattern("");
		for(int i = 0; i < melody.size(); i++) {
			melodyPattern.add(variations[i], differences[i], positions[i], durations[i]);
		}
		return melodyPattern;
	}

	public ArrayList<Note> getInMeasureNotes(int targetMeasure, ArrayList<Note> melodyNotes) {
		ArrayList<Note> inMeasureNotes = new ArrayList<Note>();
		for(int i = 0; i < melodyNotes.size(); i++) {
			int position = melodyNotes.get(i).getPosition();
			if((position / (PPQ * 4)) + 1 == targetMeasure) {
				inMeasureNotes.add(melodyNotes.get(i));
			}
		}
		if(!inMeasureNotes.isEmpty()) {
			inMeasureNotes.sort((a, b) -> a.getPosition() - b.getPosition());
		}
		return inMeasureNotes;
	}

	public ArrayList<String> getInMeasureChordProgression(int targetMeasure, ChordPair[] chordProgression) {
		ArrayList<String> inMeasureChordProgression = new ArrayList<String>();
		if(targetMeasure > 0) {
			inMeasureChordProgression.add(chordProgression[targetMeasure - 1].getChord(0));
			inMeasureChordProgression.add(chordProgression[targetMeasure - 1].getChord(1));
		}
		return inMeasureChordProgression;
	}

	public Note getLastNote(ArrayList<Note> melodyNotes) {
		Note lastNote = null;
		if(!melodyNotes.isEmpty()) {
			melodyNotes.sort((a, b) -> a.getPosition() - b.getPosition());
			lastNote = melodyNotes.get(melodyNotes.size() - 1);
		}
		return lastNote;
	}

	public String getLastChord(ArrayList<String> chordProgression) {
		if(chordProgression.isEmpty()) return "N.C.";
		else return chordProgression.get(chordProgression.size() - 1);
	}

	public void incWordDictionaryFrequency(String wordId) {
		wordDictionary.incPatternFrequency(wordId);
	}

	public void incPhraseDictionaryFrequency(String contextId, String wordId) {
		phraseDictionary.incPatternFrequency(contextId, wordId);
	}

	public void readWordDictionary(ArrayList<String> lines) {
		wordDictionary.readDictionary(lines);
	}

	public void readPhraseDictionary(ArrayList<String> lines) {
		phraseDictionary.readDictionary(lines);
	}

	public WordDictionary getWordDictionary() {
		return wordDictionary;
	}

	public PhraseDictionary getPhraseDictionary() {
		return phraseDictionary;
	}

	public String getNewPhraseEntryName(MelodyPattern context, MelodyPattern word) {
		return uiController.getNewPhraseEntryName(context, word);
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
		System.out.println("Algorithm is " + this.algorithm);
	}

	public class Similarity {
		private double contextSimilarity;
		private double wordSimilarity;
		public Similarity(double contextSimilarity, double wordSimilarity) {
			this.contextSimilarity = contextSimilarity;
			this.wordSimilarity = wordSimilarity;
		}
		public double getScore() { return contextSimilarity + wordSimilarity; }
		public double getContextSimilarity() { return contextSimilarity; }
		public double getWordSimilarity() { return wordSimilarity; }
	}
}
