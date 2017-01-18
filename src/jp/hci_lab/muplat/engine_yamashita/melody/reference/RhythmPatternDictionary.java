package engine_yamashita.melody.reference;

import java.util.ArrayList;

public class RhythmPatternDictionary extends ArrayList<RhythmPatternDictionaryRecord> {
	public RhythmPatternDictionary() {
		super();
		makeDictionary();
	}

	public void makeDictionary() {
		// メルトサビ
		RhythmPattern r1_1 = new RhythmPattern();
		r1_1.addRhythm(RhythmPattern.calcPosition(1, 1), RhythmPattern.calcDuration(4) + RhythmPattern.calcDuration(8));
		r1_1.addRhythm(RhythmPattern.calcPosition(2, 3), RhythmPattern.calcDuration(8));
		r1_1.addRhythm(RhythmPattern.calcPosition(3, 1), RhythmPattern.calcDuration(4));
		r1_1.addRhythm(RhythmPattern.calcPosition(4, 1), RhythmPattern.calcDuration(4));
		RhythmPattern r1_2 = new RhythmPattern();
		r1_2.addRhythm(RhythmPattern.calcPosition(1, 1), RhythmPattern.calcDuration(4));
		r1_2.addRhythm(RhythmPattern.calcPosition(2, 1), RhythmPattern.calcDuration(4));
		r1_2.addRhythm(RhythmPattern.calcPosition(3, 1), RhythmPattern.calcDuration(4));
		r1_2.addRhythm(RhythmPattern.calcPosition(4, 1), RhythmPattern.calcDuration(4));
		RhythmPattern r1_3 = new RhythmPattern();
		r1_3.addRhythm(RhythmPattern.calcPosition(1, 1), RhythmPattern.calcDuration(4));
		r1_3.addRhythm(RhythmPattern.calcPosition(2, 1), RhythmPattern.calcDuration(8));
		r1_3.addRhythm(RhythmPattern.calcPosition(2, 3), RhythmPattern.calcDuration(8) + RhythmPattern.calcDuration(2));
		RhythmPattern r1_4 = new RhythmPattern();
		r1_4.addRhythm(RhythmPattern.calcPosition(2, 1), RhythmPattern.calcDuration(4));
		r1_4.addRhythm(RhythmPattern.calcPosition(3, 1), RhythmPattern.calcDuration(4));
		r1_4.addRhythm(RhythmPattern.calcPosition(4, 1), RhythmPattern.calcDuration(4));
		RhythmPattern r1_5 = new RhythmPattern();
		r1_5.addRhythm(RhythmPattern.calcPosition(1, 1), RhythmPattern.calcDuration(4));
		r1_5.addRhythm(RhythmPattern.calcPosition(2, 1), RhythmPattern.calcDuration(8));
		r1_5.addRhythm(RhythmPattern.calcPosition(2, 3), RhythmPattern.calcDuration(4) + RhythmPattern.calcDuration(8));
		r1_5.addRhythm(RhythmPattern.calcPosition(4, 1), RhythmPattern.calcDuration(4));
		RhythmPattern r1_6 = new RhythmPattern();
		r1_6.addRhythm(RhythmPattern.calcPosition(1, 1), RhythmPattern.calcDuration(4));
		r1_6.addRhythm(RhythmPattern.calcPosition(2, 1), RhythmPattern.calcDuration(8));
		r1_6.addRhythm(RhythmPattern.calcPosition(2, 3), RhythmPattern.calcDuration(4) + RhythmPattern.calcDuration(8));
		r1_6.addRhythm(RhythmPattern.calcPosition(4, 1), RhythmPattern.calcDuration(4));
		RhythmPattern r1_7 = new RhythmPattern();
		r1_7.addRhythm(RhythmPattern.calcPosition(1, 1), RhythmPattern.calcDuration(2));
		r1_7.addRhythm(RhythmPattern.calcPosition(3, 1), RhythmPattern.calcDuration(2));
		RhythmPattern r1_8 = new RhythmPattern();
		r1_8.addRhythm(RhythmPattern.calcPosition(1, 1), RhythmPattern.calcDuration(4));
		r1_8.addRhythm(RhythmPattern.calcPosition(2, 1), RhythmPattern.calcDuration(4));
		r1_8.addRhythm(RhythmPattern.calcPosition(3, 1), RhythmPattern.calcDuration(4));
		r1_8.addRhythm(RhythmPattern.calcPosition(4, 1), RhythmPattern.calcDuration(4));

		// よく出そうなパターンの順に登録 (とりあえず既存楽曲数曲を分析して出てきたパターンを登録しておく)
		add(new RhythmPatternDictionaryRecord("「メルト」サビ, 1->2", r1_1, r1_2, 0));
		add(new RhythmPatternDictionaryRecord("「メルト」サビ, 2->3", r1_2, r1_3, 0));
		add(new RhythmPatternDictionaryRecord("「メルト」サビ, 3->4", r1_3, r1_4, 0));
		add(new RhythmPatternDictionaryRecord("「メルト」サビ, 4->5", r1_4, r1_5, 0));
		add(new RhythmPatternDictionaryRecord("「メルト」サビ, 5->6", r1_5, r1_6, 0));
		add(new RhythmPatternDictionaryRecord("「メルト」サビ, 6->7", r1_6, r1_7, 0));
		add(new RhythmPatternDictionaryRecord("「メルト」サビ, 7->8", r1_7, r1_8, 0));
		add(new RhythmPatternDictionaryRecord("「メルト」サビ, 8->1", r1_8, r1_1, 0));
	}
}
