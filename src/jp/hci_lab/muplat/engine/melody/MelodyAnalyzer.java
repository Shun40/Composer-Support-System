package engine.melody;

import java.util.ArrayList;
import java.util.List;

import engine.AlgorithmInformation;
import engine.Melody;
import engine.PredictionInformation;
import engine.melody.dictionary.PhraseDictionary;
import engine.melody.dictionary.WordDictionary;
import engine.melody.generation.MelodyLabel;
import engine.melody.generation.MelodyMaker;
import engine.melody.reference.DPMatching;
import engine.melody.reference.MelodyPattern;
import gui.component.pianoroll.note.Note;
import gui.component.pianoroll.note.NoteModel;
import midi.MidiConstants;
import system.AppConstants;

/**
 * メロディを解析するクラス
 * @author Shun Yamashita
 */
public class MelodyAnalyzer {
	private WordDictionary wordDictionary;
	private PhraseDictionary phraseDictionary;

	public MelodyAnalyzer() {
		wordDictionary = new WordDictionary();
		phraseDictionary = new PhraseDictionary(this);
	}

	public List<Melody> getMelodies(PredictionInformation predictionInformation, AlgorithmInformation algorithmInformation) {
		int targetMeasure = predictionInformation.getTargetMeasure(); // 対象小節番号
		List<Note> melodyNotes = predictionInformation.getMelodyNotes(); // メロディの音符
		List<String> chordProgression = predictionInformation.getChordProgression(); // コード進行
		List<AppConstants.Algorithm> selectedAlgorithms = algorithmInformation.getSelectedAlgorithms(); // 選択アルゴリズム
		AppConstants.MelodyStructurePattern selectedMelodyStructurePattern = algorithmInformation.getSelectedMelodyStructurePattern(); // 選択メロディ構造パターン

		// 下準備
		melodyNotes.sort((a, b) -> a.getModel().getPosition() - b.getModel().getPosition()); // positionが小さい順にソート
		List<Note> targetNotes = getInMeasureNotes(targetMeasure, melodyNotes);
		List<Note> previousNotes1 = getInMeasureNotes(targetMeasure - 1, melodyNotes);
		List<Note> previousNotes2 = getInMeasureNotes(targetMeasure - 2, melodyNotes);

		MelodyPattern previous = getMelodyPattern(getLastNote(previousNotes2), previousNotes1);
		MelodyPattern current = getMelodyPattern(getLastNote(previousNotes1), targetNotes);

		double msWeight = 1.0; // MSアルゴリズムのスコアの重み

		if(previous.isEmpty()) { // 単語辞書を検索する場合
			int[] rank = new int[wordDictionary.size()];
			for(int n = 0; n < rank.length; n++) {
				rank[n] = n;
			}

			// 1. 辞書登録済みパターンからメロディを生成
			Note justBeforeNote = getLastNote(previousNotes1);
			List<String> targetChordProgression = getInMeasureChordProgression(targetMeasure, chordProgression);
			List<String> previousChordProgression = getInMeasureChordProgression(targetMeasure - 1, chordProgression);
			String justBeforeChord = getLastChord(previousChordProgression);
			List<List<MelodyLabel>> melodyLabels = new ArrayList<List<MelodyLabel>>();
			MelodyMaker melodyMaker = new MelodyMaker();
			for(int n = 0; n < wordDictionary.size(); n++) {
				MelodyPattern context = null;
				MelodyPattern word = wordDictionary.get(rank[n]).getWord();
				melodyLabels.add(melodyMaker.makeMelody(context, word, justBeforeNote, targetChordProgression, justBeforeChord, selectedAlgorithms));
			}

			// 2. 類似度等のスコアを用いて候補メロディをソート
			// PCアルゴリズムの音高・リズムパターン類似度計算
			Similarity[] pcPitchSimilarities = calcPcSimilarities(AppConstants.DictionaryType.WORD, AppConstants.SimilarityType.PITCH, previous, current);
			Similarity[] pcRhythmSimilarities = calcPcSimilarities(AppConstants.DictionaryType.WORD, AppConstants.SimilarityType.RHYTHM, previous, current);
			// MSアルゴリズムのリズムパターン類似度計算
			Similarity[] msRhythmSimilarities = calcMsSimilarities(selectedMelodyStructurePattern, AppConstants.SimilarityType.RHYTHM, previous, current, targetMeasure, melodyNotes);
			// ソート
			for(int m = 0; m < wordDictionary.size(); m++) {
				for(int n = m; n < wordDictionary.size(); n++) {
					double sum_m = 0.0, sum_n = 0.0;
					if(selectedAlgorithms.contains(AppConstants.Algorithm.PC)) {
						sum_m += pcPitchSimilarities[rank[m]].getScore() + pcRhythmSimilarities[rank[m]].getScore();
						sum_n += pcPitchSimilarities[rank[n]].getScore() + pcRhythmSimilarities[rank[n]].getScore();
					}
					if(selectedAlgorithms.contains(AppConstants.Algorithm.MS)) {
						sum_m += msWeight * msRhythmSimilarities[rank[m]].getScore();
						sum_n += msWeight * msRhythmSimilarities[rank[n]].getScore();
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

			// 3. メロディを整形
			List<Melody> melodies = new ArrayList<Melody>();
			for(int m = 0; m < melodyLabels.size(); m++) {
				int index = wordDictionary.get(rank[m]).getIndex();
				String id = wordDictionary.get(rank[m]).getWord().getId();
				String name = wordDictionary.get(rank[m]).getName() + " (" + (wordDictionary.get(rank[m]).getFrequency() - 1) + "回)";
				int frequency = wordDictionary.get(rank[m]).getFrequency();
				Melody melody = new Melody(index, id, name, frequency);
				for(int n = 1; n < melodyLabels.get(rank[m]).size(); n++) { // 先頭には直前音符の情報が入っているので1から始める
					int track = 1;
					int program = 0;
					int pitch = melodyLabels.get(rank[m]).get(n).getPitch();
					int position = melodyLabels.get(rank[m]).get(n).getPosition();
					int duration = melodyLabels.get(rank[m]).get(n).getDuration();
					int velocity = 100;
					melody.add(new NoteModel(track, program, pitch, position, duration, velocity));
				}
				melodies.add(melody);
			}
			return melodies;
		} else { // 例文辞書を検索する場合
			int[] rank = new int[phraseDictionary.size()];
			for(int n = 0; n < rank.length; n++) {
				rank[n] = n;
			}

			// 1. 辞書登録済みパターンからメロディを生成
			Note justBeforeNote = getLastNote(previousNotes1);
			List<String> targetChordProgression = getInMeasureChordProgression(targetMeasure, chordProgression);
			List<String> previousChordProgression = getInMeasureChordProgression(targetMeasure - 1, chordProgression);
			String justBeforeChord = getLastChord(previousChordProgression);
			List<List<MelodyLabel>> melodyLabels = new ArrayList<List<MelodyLabel>>();
			MelodyMaker melodyMaker = new MelodyMaker();
			for(int n = 0; n < phraseDictionary.size(); n++) {
				MelodyPattern context = phraseDictionary.get(rank[n]).getContext();
				MelodyPattern word = phraseDictionary.get(rank[n]).getWord();
				melodyLabels.add(melodyMaker.makeMelody(context, word, justBeforeNote, targetChordProgression, justBeforeChord, selectedAlgorithms));
			}

			// 2. 類似度等のスコアを用いて候補メロディをソート
			// PCアルゴリズムの音高・リズムパターン類似度計算
			Similarity[] pcPitchSimilarities = calcPcSimilarities(AppConstants.DictionaryType.PHRASE, AppConstants.SimilarityType.PITCH, previous, current);
			Similarity[] pcRhythmSimilarities = calcPcSimilarities(AppConstants.DictionaryType.PHRASE, AppConstants.SimilarityType.RHYTHM, previous, current);
			// MSアルゴリズムの音高・リズムパターン類似度計算
			Similarity[] msRhythmSimilarities = calcMsSimilarities(selectedMelodyStructurePattern, AppConstants.SimilarityType.RHYTHM, previous, current, targetMeasure, melodyNotes);
			// ソート
			for(int m = 0; m < phraseDictionary.size(); m++) {
				for(int n = m; n < phraseDictionary.size(); n++) {
					double sum_m = 0.0, sum_n = 0.0;
					if(selectedAlgorithms.contains(AppConstants.Algorithm.PC)) {
						sum_m += pcPitchSimilarities[rank[m]].getScore() + pcRhythmSimilarities[rank[m]].getScore();
						sum_n += pcPitchSimilarities[rank[n]].getScore() + pcRhythmSimilarities[rank[n]].getScore();
						System.out.println("PC: " + sum_m + ", " + sum_n);
					}
					if(selectedAlgorithms.contains(AppConstants.Algorithm.MS)) {
						sum_m += msWeight * msRhythmSimilarities[rank[m]].getScore();
						sum_n += msWeight * msRhythmSimilarities[rank[n]].getScore();
						System.out.println("MS: " + sum_m + ", " + sum_n);
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

			// 3. メロディを整形
			List<Melody> melodies = new ArrayList<Melody>();
			for(int m = 0; m < melodyLabels.size(); m++) {
				int index = phraseDictionary.get(rank[m]).getIndex();
				String id = phraseDictionary.get(rank[m]).getWord().getId();
				String name = phraseDictionary.get(rank[m]).getName() + " (" + (phraseDictionary.get(rank[m]).getFrequency() - 1) + "回)";
				int frequency = phraseDictionary.get(rank[m]).getFrequency();
				Melody melody = new Melody(index, id, name, frequency);
				for(int n = 1; n < melodyLabels.get(rank[m]).size(); n++) { // 先頭には直前音符の情報が入っているので1から始める
					int track = 1;
					int program = 0;
					int pitch = melodyLabels.get(rank[m]).get(n).getPitch();
					int position = melodyLabels.get(rank[m]).get(n).getPosition();
					int duration = melodyLabels.get(rank[m]).get(n).getDuration();
					int velocity = 100;
					melody.add(new NoteModel(track, program, pitch, position, duration, velocity));
				}
				melodies.add(melody);
			}
			return melodies;
		}
	}

	private Similarity[] calcPcSimilarities(AppConstants.DictionaryType dictionaryType, AppConstants.SimilarityType similarityType, MelodyPattern previous, MelodyPattern current) {
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

	private Similarity[] calcMsSimilarities(AppConstants.MelodyStructurePattern melodyStructurePattern, AppConstants.SimilarityType similarityType, MelodyPattern previous, MelodyPattern current, int targetMeasure, List<Note> melodyNotes) {
		Similarity[] similarities = new Similarity[wordDictionary.size()];
		MelodyPattern reference = null, target, pattern;
		double similarity;

		switch(melodyStructurePattern) {
		case ABCD:
			break;
		case ABAB:
			if(targetMeasure >= 3) {
				// (targetMeasure - 2)小節目のメロディ情報を抽出
				reference = getMelodyPattern(getLastNote(getInMeasureNotes(targetMeasure - 3, melodyNotes)), getInMeasureNotes(targetMeasure - 2, melodyNotes));
			}
			break;
		case AABB:
			if(targetMeasure == 2 || targetMeasure == 4) {
				// (targetMeasure - 1)小節目のメロディ情報を抽出
				reference = getMelodyPattern(getLastNote(getInMeasureNotes(targetMeasure - 2, melodyNotes)), getInMeasureNotes(targetMeasure - 1, melodyNotes));
			}
			break;
		case AABC:
			if(targetMeasure == 2) {
				// (targetMeasure - 1)小節目のメロディ情報を抽出
				reference = getMelodyPattern(getLastNote(getInMeasureNotes(targetMeasure - 2, melodyNotes)), getInMeasureNotes(targetMeasure - 1, melodyNotes));
			}
			break;
		case ABCC:
			if(targetMeasure == 4) {
				// (targetMeasure - 1)小節目のメロディ情報を抽出
				reference = getMelodyPattern(getLastNote(getInMeasureNotes(targetMeasure - 2, melodyNotes)), getInMeasureNotes(targetMeasure - 1, melodyNotes));
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
		}

		return similarities;
	}

	public MelodyPattern getMelodyPattern(Note justBeforeNote, List<Note> melody) {
		// 音高情報を抽出
		int[] variations = new int[melody.size()];
		int[] differences = new int[melody.size()];
		for(int i = 0; i < melody.size(); i++) {
			int previousPitch = 0;
			int currentPitch = melody.get(i).getModel().getPitch();
			if(i <= 0) {
				if(justBeforeNote == null) {
					previousPitch = melody.get(i).getModel().getPitch();
				} else {
					previousPitch = justBeforeNote.getModel().getPitch();
				}
			} else {
				previousPitch = melody.get(i-1).getModel().getPitch();
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
			positions[i] = melody.get(i).getModel().getPosition() % (MidiConstants.PPQ * 4);
			durations[i] = melody.get(i).getModel().getDuration();
		}

		// メロディパターン生成
		MelodyPattern melodyPattern = new MelodyPattern("");
		for(int i = 0; i < melody.size(); i++) {
			melodyPattern.add(variations[i], differences[i], positions[i], durations[i]);
		}
		return melodyPattern;
	}

	public ArrayList<Note> getInMeasureNotes(int targetMeasure, List<Note> melodyNotes) {
		ArrayList<Note> inMeasureNotes = new ArrayList<Note>();
		for(int i = 0; i < melodyNotes.size(); i++) {
			int position = melodyNotes.get(i).getModel().getPosition();
			if((position / (MidiConstants.PPQ * 4)) + 1 == targetMeasure) {
				inMeasureNotes.add(melodyNotes.get(i));
			}
		}
		if(!inMeasureNotes.isEmpty()) {
			inMeasureNotes.sort((a, b) -> a.getModel().getPosition() - b.getModel().getPosition());
		}
		return inMeasureNotes;
	}

	public List<String> getInMeasureChordProgression(int targetMeasure, List<String> chordProgression) {
		List<String> inMeasureChordProgression = new ArrayList<String>();
		if(targetMeasure > 0) {
			inMeasureChordProgression.add(chordProgression.get((targetMeasure - 1) * 2 + 0)); // 1~2拍目
			inMeasureChordProgression.add(chordProgression.get((targetMeasure - 1) * 2 + 1)); // 3~4拍目
		}
		return inMeasureChordProgression;
	}

	public Note getLastNote(List<Note> melodyNotes) {
		Note lastNote = null;
		if(!melodyNotes.isEmpty()) {
			melodyNotes.sort((a, b) -> a.getModel().getPosition() - b.getModel().getPosition());
			lastNote = melodyNotes.get(melodyNotes.size() - 1);
		}
		return lastNote;
	}

	public String getLastChord(List<String> chordProgression) {
		if(chordProgression.isEmpty()) return "N.C.";
		else return chordProgression.get(chordProgression.size() - 1);
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

	public WordDictionary getWordDictionary() {
		return wordDictionary;
	}

	public PhraseDictionary getPhraseDictionary() {
		return phraseDictionary;
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
