package engine_yamashita.melody.reference;

import java.util.ArrayList;

public class MelodyPatternDictionary extends ArrayList<MelodyPatternDictionaryRecord> {
	public MelodyPatternDictionary() {
		super();
		makeDictionary();
	}

	public void makeDictionary() {
		// 「メルト」サビ8小節
		MelodyPattern p1_1 = new MelodyPattern();
		p1_1.add(1, 2, 0, 720);
		p1_1.add(-1, 4, 720, 240);
		p1_1.add(0, 0, 960, 480);
		p1_1.add(-1, 5, 1440, 480);
		MelodyPattern p1_2 = new MelodyPattern();
		p1_2.add(1, 10, 0, 480);
		p1_2.add(-1, 1, 480, 480);
		p1_2.add(-1, 2, 960, 480);
		p1_2.add(-1, 2, 1440, 480);
		MelodyPattern p1_3 = new MelodyPattern();
		p1_3.add(1, 2, 0, 480);
		p1_3.add(1, 2, 480, 240);
		p1_3.add(0, 0, 720, 1200);
		MelodyPattern p1_4 = new MelodyPattern();
		p1_4.add(-1, 9, 480, 480);
		p1_4.add(1, 5, 960, 480);
		p1_4.add(-1, 1, 1440, 480);
		MelodyPattern p1_5 = new MelodyPattern();
		p1_5.add(1, 1, 0, 480);
		p1_5.add(-1, 5, 480, 240);
		p1_5.add(0, 0, 720, 720);
		p1_5.add(0, 0, 1440, 480);
		MelodyPattern p1_6 = new MelodyPattern();
		p1_6.add(1, 7, 0, 480);
		p1_6.add(-1, 2, 480, 240);
		p1_6.add(0, 0, 720, 720);
		p1_6.add(-1, 5, 1440, 480);
		MelodyPattern p1_7 = new MelodyPattern();
		p1_7.add(1, 5, 0, 960);
		p1_7.add(1, 2, 960, 960);
		MelodyPattern p1_8 = new MelodyPattern();
		p1_8.add(-1, 3, 0, 480);
		p1_8.add(0, 0, 480, 480);
		p1_8.add(1, 1, 960, 480);
		p1_8.add(1, 2, 1440, 480);

		// 「シルエット」サビ8小節
		MelodyPattern p2_1 = new MelodyPattern();
		p2_1.add(-1, 2, 0, 480);
		p2_1.add(-1, 5, 480, 480);
		p2_1.add(1, 5, 960, 480);
		p2_1.add(1, 4, 1440, 480);
		MelodyPattern p2_2 = new MelodyPattern();
		p2_2.add(0, 0, 0, 480);
		p2_2.add(-1, 2, 480, 240);
		p2_2.add(-1, 2, 720, 240);
		p2_2.add(1, 2, 960, 960);
		MelodyPattern p2_3 = new MelodyPattern();
		p2_3.add(0, 0, 0, 480);
		p2_3.add(-1, 7, 480, 480);
		p2_3.add(1, 4, 960, 480);
		p2_3.add(1, 3, 1440, 480);
		MelodyPattern p2_4 = new MelodyPattern();
		p2_4.add(1, 3, 0, 480);
		p2_4.add(-1, 1, 480, 240);
		p2_4.add(-1, 2, 720, 240);
		p2_4.add(1, 2, 960, 480);
		p2_4.add(-1, 2, 1440, 480);
		MelodyPattern p2_5 = new MelodyPattern();
		p2_5.add(-1, 2, 0, 960);
		p2_5.add(1, 9, 960, 480);
		p2_5.add(-1, 2, 1440, 480);
		MelodyPattern p2_6 = new MelodyPattern();
		p2_6.add(-1, 7, 0, 960);
		p2_6.add(1, 9, 960, 480);
		p2_6.add(-1, 2, 1440, 480);
		MelodyPattern p2_7 = new MelodyPattern();
		p2_7.add(-1, 7, 0, 960);
		p2_7.add(0, 0, 960, 960);
		MelodyPattern p2_8 = new MelodyPattern();
		p2_8.add(-1, 1, 0, 480);
		p2_8.add(1, 1, 480, 480);
		p2_8.add(1, 2, 960, 480);
		p2_8.add(1, 2, 1440, 480);

		// よく出そうなパターンの順に登録 (とりあえず既存楽曲数曲を分析して出てきたパターンを登録しておく)
		add(new MelodyPatternDictionaryRecord("「メルト」サビ, (1->)2", p1_1, p1_2, 0));
		add(new MelodyPatternDictionaryRecord("「メルト」サビ, (2->)3", p1_2, p1_3, 0));
		add(new MelodyPatternDictionaryRecord("「メルト」サビ, (3->)4", p1_3, p1_4, 0));
		add(new MelodyPatternDictionaryRecord("「メルト」サビ, (4->)5", p1_4, p1_5, 0));
		add(new MelodyPatternDictionaryRecord("「メルト」サビ, (5->)6", p1_5, p1_6, 0));
		add(new MelodyPatternDictionaryRecord("「メルト」サビ, (6->)7", p1_6, p1_7, 0));
		add(new MelodyPatternDictionaryRecord("「メルト」サビ, (7->)8", p1_7, p1_8, 0));
		add(new MelodyPatternDictionaryRecord("「メルト」サビ, (8->)1", p1_8, p1_1, 0));
		add(new MelodyPatternDictionaryRecord("「シルエット」サビ, (1->)2", p2_1, p2_2, 0));
		add(new MelodyPatternDictionaryRecord("「シルエット」サビ, (2->)3", p2_2, p2_3, 0));
		add(new MelodyPatternDictionaryRecord("「シルエット」サビ, (3->)4", p2_3, p2_4, 0));
		add(new MelodyPatternDictionaryRecord("「シルエット」サビ, (4->)5", p2_4, p2_5, 0));
		add(new MelodyPatternDictionaryRecord("「シルエット」サビ, (5->)6", p2_5, p2_6, 0));
		add(new MelodyPatternDictionaryRecord("「シルエット」サビ, (6->)7", p2_6, p2_7, 0));
		add(new MelodyPatternDictionaryRecord("「シルエット」サビ, (7->)8", p2_7, p2_8, 0));
		add(new MelodyPatternDictionaryRecord("「シルエット」サビ, (8->)1", p2_8, p2_1, 0));
	}
}
