package engine_yamashita.melody;

import static gui.constants.UniversalConstants.*;

import java.util.ArrayList;

import engine_yamashita.Melody;
import engine_yamashita.PredictionInformation;
import engine_yamashita.melody.generation.MelodyLabel;
import engine_yamashita.melody.generation.MelodyMaker;
import engine_yamashita.melody.reference.DPMatching;
import engine_yamashita.melody.reference.MelodyPattern;
import engine_yamashita.melody.reference.MelodyPatternDictionary;
import gui.ChordPair;
import gui.Note;

/**
 * メロディを解析するクラス
 * @author Shun Yamashita
 */
public class MelodyAnalyzer {
	private MelodyPatternDictionary melodyPatternDictionary;

	public MelodyAnalyzer() {
		melodyPatternDictionary = new MelodyPatternDictionary();
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

		MelodyPattern currentMelodyPattern = getMelodyPattern(getLastNote(previousNotes1), targetNotes);
		MelodyPattern previousMelodyPattern = getMelodyPattern(getLastNote(previousNotes2), previousNotes1);

		DPMatching dpMatching = new DPMatching();
		Similarity[] pitchSimilarities = new Similarity[melodyPatternDictionary.size()];
		Similarity[] rhythmSimilarities = new Similarity[melodyPatternDictionary.size()];
		int[] rank = new int[melodyPatternDictionary.size()];
		for(int n = 0; n < rank.length; n++) rank[n] = n;

		// 音高パターンの類似度計算
		for(int n = 0; n < melodyPatternDictionary.size(); n++) {
			// context部分の類似度計算
			MelodyPattern target = previousMelodyPattern;
			MelodyPattern pattern = melodyPatternDictionary.get(n).getContext();
			double contextSimilarity = dpMatching.calcPitchSimilarity(target, pattern);
			// word部分の類似度計算
			target = currentMelodyPattern;
			pattern = melodyPatternDictionary.get(n).getWord();
			double wordSimilarity = dpMatching.calcPitchSimilarity(target, pattern);
			pitchSimilarities[n] = new Similarity(contextSimilarity, wordSimilarity);
		}

		// リズムパターンの類似度計算
		for(int n = 0; n < melodyPatternDictionary.size(); n++) {
			// context部分の類似度計算
			MelodyPattern target = previousMelodyPattern;
			MelodyPattern pattern = melodyPatternDictionary.get(n).getContext();
			double contextSimilarity = dpMatching.calcRhythmSimilarity(target, pattern);
			// word部分の類似度計算
			target = currentMelodyPattern;
			pattern = melodyPatternDictionary.get(n).getWord();
			double wordSimilarity = dpMatching.calcRhythmSimilarity(target, pattern);
			rhythmSimilarities[n] = new Similarity(contextSimilarity, wordSimilarity);
		}

		// ソート
		for(int m = 0; m < melodyPatternDictionary.size(); m++) {
			for(int n = m; n < melodyPatternDictionary.size(); n++) {
				double sum_m = rhythmSimilarities[rank[m]].getScore() + pitchSimilarities[rank[m]].getScore();
				double sum_n = rhythmSimilarities[rank[n]].getScore() + pitchSimilarities[rank[n]].getScore();
				double frequency_m = melodyPatternDictionary.get(rank[m]).getFrequency();
				double frequency_n = melodyPatternDictionary.get(rank[n]).getFrequency();
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
		for(int n = 0; n < melodyPatternDictionary.size(); n++) {
			MelodyPattern context = melodyPatternDictionary.get(rank[n]).getContext();
			MelodyPattern word = melodyPatternDictionary.get(rank[n]).getWord();
			melodyLabels.add(melodyMaker.makeMelody(context, word, justBeforeNote, targetChordProgression, justBeforeChord));
		}

		// メロディを整形
		ArrayList<Melody> melodies = new ArrayList<Melody>();
		for(int m = 0; m < melodyLabels.size(); m++) {
			int index = melodyPatternDictionary.get(rank[m]).getIndex();
			String name = melodyPatternDictionary.get(rank[m]).getName();
			int frequency = melodyPatternDictionary.get(rank[m]).getFrequency();
			Melody melody = new Melody(index, name, frequency);
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
		MelodyPattern melodyPattern = new MelodyPattern();
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

	public void incMelodyPatternFrequency(int index) {
		melodyPatternDictionary.incPatternFrequency(index);
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
