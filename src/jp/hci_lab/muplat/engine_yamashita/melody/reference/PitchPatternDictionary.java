package engine_yamashita.melody.reference;

import java.util.ArrayList;

public class PitchPatternDictionary extends ArrayList<PitchPatternDictionaryRecord> {
	public PitchPatternDictionary() {
		super();
		makeDictionary();
	}

	public void makeDictionary() {
		// メルトサビ
		PitchPattern p1_1 = new PitchPattern();
		p1_1.addPitch(1, 2, 76);
		p1_1.addPitch(-1, 4, 72);
		p1_1.addPitch(0, 0, 72);
		p1_1.addPitch(-1, 5, 67);
		PitchPattern p1_2 = new PitchPattern();
		p1_2.addPitch(1, 10, 77);
		p1_2.addPitch(-1, 1, 76);
		p1_2.addPitch(-1, 2, 74);
		p1_2.addPitch(-1, 2, 72);
		PitchPattern p1_3 = new PitchPattern();
		p1_3.addPitch(1, 2, 74);
		p1_3.addPitch(1, 2, 76);
		p1_3.addPitch(0, 0, 76);
		PitchPattern p1_4 = new PitchPattern();
		p1_4.addPitch(-1, 9, 67);
		p1_4.addPitch(1, 5, 72);
		p1_4.addPitch(-1, 1, 71);
		PitchPattern p1_5 = new PitchPattern();
		p1_5.addPitch(1, 1, 72);
		p1_5.addPitch(-1, 5, 67);
		p1_5.addPitch(0, 0, 67);
		p1_5.addPitch(0, 0, 67);
		PitchPattern p1_6 = new PitchPattern();
		p1_6.addPitch(1, 7, 74);
		p1_6.addPitch(-1, 2, 72);
		p1_6.addPitch(0, 0, 72);
		p1_6.addPitch(-1, 5, 67);
		PitchPattern p1_7 = new PitchPattern();
		p1_7.addPitch(1, 5, 72);
		p1_7.addPitch(1, 2, 74);
		PitchPattern p1_8 = new PitchPattern();
		p1_8.addPitch(-1, 3, 71);
		p1_8.addPitch(0, 0, 71);
		p1_8.addPitch(1, 1, 72);
		p1_8.addPitch(1, 2, 74);

		// よく出そうなパターンの順に登録 (とりあえず既存楽曲数曲を分析して出てきたパターンを登録しておく)
		add(new PitchPatternDictionaryRecord("「メルト」サビ, 1->2", p1_1, p1_2, 0));
		add(new PitchPatternDictionaryRecord("「メルト」サビ, 2->3", p1_2, p1_3, 0));
		add(new PitchPatternDictionaryRecord("「メルト」サビ, 3->4", p1_3, p1_4, 0));
		add(new PitchPatternDictionaryRecord("「メルト」サビ, 4->5", p1_4, p1_5, 0));
		add(new PitchPatternDictionaryRecord("「メルト」サビ, 5->6", p1_5, p1_6, 0));
		add(new PitchPatternDictionaryRecord("「メルト」サビ, 6->7", p1_6, p1_7, 0));
		add(new PitchPatternDictionaryRecord("「メルト」サビ, 7->8", p1_7, p1_8, 0));
		add(new PitchPatternDictionaryRecord("「メルト」サビ, 8->1", p1_8, p1_1, 0));
	}
}
