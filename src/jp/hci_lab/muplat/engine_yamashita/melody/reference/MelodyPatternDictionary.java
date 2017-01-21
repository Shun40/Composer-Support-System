package engine_yamashita.melody.reference;

import java.util.ArrayList;
import java.util.HashMap;

public class MelodyPatternDictionary extends ArrayList<MelodyPatternDictionaryRecord> {
	public MelodyPatternDictionary() {
		super();
		//makeDictionary();
	}

	public void makeDictionary() {
		/*
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

		// 「天体観測」サビ8小節
		MelodyPattern p3_1 = new MelodyPattern();
		p3_1.add(1, 7, 0, 240);
		p3_1.add(0, 0, 240, 240);
		p3_1.add(0, 0, 480, 240);
		p3_1.add(0, 0, 720, 240);
		p3_1.add(0, 0, 960, 240);
		p3_1.add(0, 0, 1200, 240);
		p3_1.add(0, 0, 1440, 240);
		p3_1.add(-1, 2, 1680, 240);
		MelodyPattern p3_2 = new MelodyPattern();
		p3_2.add(1, 2, 0, 480);
		p3_2.add(1, 2, 480, 480);
		p3_2.add(-1, 4, 960, 960);
		MelodyPattern p3_3 = new MelodyPattern();
		p3_3.add(1, 2, 0, 480);
		p3_3.add(0, 0, 480, 480);
		p3_3.add(0, 0, 960, 480);
		p3_3.add(0, 0, 1440, 240);
		p3_3.add(-1, 2, 1680, 240);
		MelodyPattern p3_4 = new MelodyPattern();
		p3_4.add(1, 2, 0, 480);
		p3_4.add(1, 2, 480, 480);
		p3_4.add(-1, 4, 960, 960);
		MelodyPattern p3_5 = new MelodyPattern();
		p3_5.add(0, 0, 0, 240);
		p3_5.add(-1, 1, 240, 240);
		p3_5.add(-1, 2, 480, 240);
		p3_5.add(1, 2, 720, 240);
		p3_5.add(1, 1, 960, 480);
		p3_5.add(0, 0, 1440, 240);
		p3_5.add(-1, 1, 1680, 240);
		MelodyPattern p3_6 = new MelodyPattern();
		p3_6.add(-1, 2, 0, 240);
		p3_6.add(1, 2, 240, 240);
		p3_6.add(1, 1, 480, 480);
		p3_6.add(-1, 3, 960, 240);
		p3_6.add(1, 3, 1200, 480);
		p3_6.add(1, 4, 1680, 720);
		MelodyPattern p3_7 = new MelodyPattern();
		p3_7.add(1, 1, 480, 480);
		p3_7.add(-1, 1, 960, 240);
		p3_7.add(-1, 2, 1200, 240);
		p3_7.add(-1, 2, 1440, 240);
		p3_7.add(0, 0, 1680, 240);
		MelodyPattern p3_8 = new MelodyPattern();
		p3_8.add(1, 2, 0, 240);
		p3_8.add(-1, 7, 240, 240);
		p3_8.add(0, 0, 480, 240);
		p3_8.add(0, 0, 720, 1200);

		// 「チェリー」サビ8小節
		MelodyPattern p4_1 = new MelodyPattern();
		p4_1.add(-1, 2, 0, 720);
		p4_1.add(0, 0, 720, 240);
		p4_1.add(1, 4, 960, 480);
		p4_1.add(1, 3, 1440, 480);
		MelodyPattern p4_2 = new MelodyPattern();
		p4_2.add(1, 2, 0, 360);
		p4_2.add(-1, 2, 360, 240);
		p4_2.add(-1, 2, 600, 120);
		p4_2.add(1, 2, 720, 240);
		p4_2.add(0, 0, 960, 480);
		p4_2.add(-1, 3, 1440, 240);
		p4_2.add(-1, 2, 1680, 240);
		MelodyPattern p4_3 = new MelodyPattern();
		p4_3.add(-1, 2, 0, 720);
		p4_3.add(0, 0, 720, 240);
		p4_3.add(1, 4, 960, 480);
		p4_3.add(1, 3, 1440, 480);
		MelodyPattern p4_4 = new MelodyPattern();
		p4_4.add(-1, 2, 0, 360);
		p4_4.add(-1, 1, 360, 240);
		p4_4.add(-1, 2, 600, 120);
		p4_4.add(1, 3, 720, 240);
		p4_4.add(-1, 1, 960, 480);
		p4_4.add(0, 0, 1440, 240);
		p4_4.add(-1, 2, 1680, 240);
		MelodyPattern p4_5 = new MelodyPattern();
		p4_5.add(-1, 2, 0, 720);
		p4_5.add(0, 0, 720, 240);
		p4_5.add(1, 4, 960, 480);
		p4_5.add(1, 3, 1440, 480);
		MelodyPattern p4_6 = new MelodyPattern();
		p4_6.add(1, 2, 0, 360);
		p4_6.add(-1, 2, 360, 240);
		p4_6.add(-1, 2, 600, 120);
		p4_6.add(1, 2, 720, 240);
		p4_6.add(0, 0, 960, 480);
		p4_6.add(-1, 3, 1440, 240);
		p4_6.add(-1, 2, 1680, 240);
		MelodyPattern p4_7 = new MelodyPattern();
		p4_7.add(-1, 2, 0, 720);
		p4_7.add(0, 0, 720, 240);
		p4_7.add(1, 4, 960, 480);
		p4_7.add(1, 3, 1440, 480);
		MelodyPattern p4_8 = new MelodyPattern();
		p4_8.add(-1, 2, 0, 360);
		p4_8.add(-1, 1, 360, 360);
		p4_8.add(-1, 2, 720, 240);
		p4_8.add(-1, 2, 960, 960);

		// 「前前前世」サビ8小節
		MelodyPattern p5_1 = new MelodyPattern();
		p5_1.add(1, 7, 0, 480);
		p5_1.add(0, 0, 480, 480);
		p5_1.add(0, 0, 960, 480);
		p5_1.add(-1, 3, 1440, 240);
		p5_1.add(-1, 2, 1680, 240);
		MelodyPattern p5_2 = new MelodyPattern();
		p5_2.add(0, 0, 0, 240);
		p5_2.add(0, 0, 240, 480);
		p5_2.add(-1, 2, 720, 480);
		p5_2.add(0, 0, 1200, 240);
		p5_2.add(0, 0, 1440, 240);
		p5_2.add(0, 0, 1680, 240);
		MelodyPattern p5_3 = new MelodyPattern();
		p5_3.add(1, 7, 0, 240);
		p5_3.add(0, 0, 240, 480);
		p5_3.add(0, 0, 720, 240);
		p5_3.add(0, 0, 960, 240);
		p5_3.add(-1, 3, 1200, 240);
		p5_3.add(0, 0, 1440, 240);
		p5_3.add(-1, 2, 1680, 960);
		MelodyPattern p5_4 = new MelodyPattern();
		p5_4.add(-1, 2, 720, 240);
		p5_4.add(0, 0, 960, 240);
		p5_4.add(1, 2, 1200, 240);
		p5_4.add(1, 2, 1440, 240);
		p5_4.add(-1, 2, 1680, 480);
		MelodyPattern p5_5 = new MelodyPattern();
		p5_5.add(-1, 2, 240, 240);
		p5_5.add(0, 0, 480, 240);
		p5_5.add(0, 0, 720, 240);
		p5_5.add(0, 0, 960, 240);
		p5_5.add(1, 2, 1200, 240);
		p5_5.add(1, 2, 1440, 240);
		p5_5.add(-1, 2, 1680, 480);
		MelodyPattern p5_6 = new MelodyPattern();
		p5_6.add(-1, 2, 240, 240);
		p5_6.add(0, 0, 480, 240);
		p5_6.add(-1, 3, 720, 240);
		p5_6.add(1, 3, 960, 240);
		p5_6.add(-1, 3, 1200, 240);
		p5_6.add(1, 3, 1440, 480);
		MelodyPattern p5_7 = new MelodyPattern();
		p5_7.add(1, 7, 0, 480);
		p5_7.add(-1, 3, 480, 240);
		p5_7.add(1, 7, 720, 480);
		p5_7.add(-1, 3, 1200, 480);
		p5_7.add(0, 0, 1680, 240);
		MelodyPattern p5_8 = new MelodyPattern();
		p5_8.add(-1, 2, 0, 480);
		p5_8.add(-1, 2, 1200, 240);
		p5_8.add(0, 0, 1440, 240);
		p5_8.add(0, 0, 1680, 240);

		// とりあえず既存楽曲数曲のパターンを登録しておく
		add(new MelodyPatternDictionaryRecord(0, "BUMP OF CHICKEN 『天体観測』 サビ1小節目", p3_8, p3_1, 1));
		add(new MelodyPatternDictionaryRecord(1, "BUMP OF CHICKEN 『天体観測』 サビ2小節目", p3_1, p3_2, 1));
		add(new MelodyPatternDictionaryRecord(2, "BUMP OF CHICKEN 『天体観測』 サビ3小節目", p3_2, p3_3, 1));
		add(new MelodyPatternDictionaryRecord(3, "BUMP OF CHICKEN 『天体観測』 サビ4小節目", p3_3, p3_4, 1));
		add(new MelodyPatternDictionaryRecord(4, "BUMP OF CHICKEN 『天体観測』 サビ5小節目", p3_4, p3_5, 1));
		add(new MelodyPatternDictionaryRecord(5, "BUMP OF CHICKEN 『天体観測』 サビ6小節目", p3_5, p3_6, 1));
		add(new MelodyPatternDictionaryRecord(6, "BUMP OF CHICKEN 『天体観測』 サビ7小節目", p3_6, p3_7, 1));
		add(new MelodyPatternDictionaryRecord(7, "BUMP OF CHICKEN 『天体観測』 サビ8小節目", p3_7, p3_8, 1));
		add(new MelodyPatternDictionaryRecord(8, "RADWIMPS 『前前前世』 サビ1小節目", p5_8, p5_1, 1));
		add(new MelodyPatternDictionaryRecord(9, "RADWIMPS 『前前前世』 サビ2小節目", p5_1, p5_2, 1));
		add(new MelodyPatternDictionaryRecord(10, "RADWIMPS 『前前前世』 サビ3小節目", p5_2, p5_3, 1));
		add(new MelodyPatternDictionaryRecord(11, "RADWIMPS 『前前前世』 サビ4小節目", p5_3, p5_4, 1));
		add(new MelodyPatternDictionaryRecord(12, "RADWIMPS 『前前前世』 サビ5小節目", p5_4, p5_5, 1));
		add(new MelodyPatternDictionaryRecord(13, "RADWIMPS 『前前前世』 サビ6小節目", p5_5, p5_6, 1));
		add(new MelodyPatternDictionaryRecord(14, "RADWIMPS 『前前前世』 サビ7小節目", p5_6, p5_7, 1));
		add(new MelodyPatternDictionaryRecord(15, "RADWIMPS 『前前前世』 サビ8小節目", p5_7, p5_8, 1));
		add(new MelodyPatternDictionaryRecord(16, "KANA-BOON 『シルエット』 サビ1小節目", p2_8, p2_1, 1));
		add(new MelodyPatternDictionaryRecord(17, "KANA-BOON 『シルエット』 サビ2小節目", p2_1, p2_2, 1));
		add(new MelodyPatternDictionaryRecord(18, "KANA-BOON 『シルエット』 サビ3小節目", p2_2, p2_3, 1));
		add(new MelodyPatternDictionaryRecord(19, "KANA-BOON 『シルエット』 サビ4小節目", p2_3, p2_4, 1));
		add(new MelodyPatternDictionaryRecord(20, "KANA-BOON 『シルエット』 サビ5小節目", p2_4, p2_5, 1));
		add(new MelodyPatternDictionaryRecord(21, "KANA-BOON 『シルエット』 サビ6小節目", p2_5, p2_6, 1));
		add(new MelodyPatternDictionaryRecord(22, "KANA-BOON 『シルエット』 サビ7小節目", p2_6, p2_7, 1));
		add(new MelodyPatternDictionaryRecord(23, "KANA-BOON 『シルエット』 サビ8小節目", p2_7, p2_8, 1));
		add(new MelodyPatternDictionaryRecord(24, "スピッツ 『チェリー』 サビ1小節目", p4_8, p4_1, 1));
		add(new MelodyPatternDictionaryRecord(25, "スピッツ 『チェリー』 サビ2小節目", p4_1, p4_2, 1));
		add(new MelodyPatternDictionaryRecord(26, "スピッツ 『チェリー』 サビ3小節目", p4_2, p4_3, 1));
		add(new MelodyPatternDictionaryRecord(27, "スピッツ 『チェリー』 サビ4小節目", p4_3, p4_4, 1));
		add(new MelodyPatternDictionaryRecord(28, "スピッツ 『チェリー』 サビ5小節目", p4_4, p4_5, 1));
		add(new MelodyPatternDictionaryRecord(29, "スピッツ 『チェリー』 サビ6小節目", p4_5, p4_6, 1));
		add(new MelodyPatternDictionaryRecord(30, "スピッツ 『チェリー』 サビ7小節目", p4_6, p4_7, 1));
		add(new MelodyPatternDictionaryRecord(31, "スピッツ 『チェリー』 サビ8小節目", p4_7, p4_8, 1));
		add(new MelodyPatternDictionaryRecord(32, "初音ミク 『メルト』 サビ1小節目", p1_8, p1_1, 1));
		add(new MelodyPatternDictionaryRecord(33, "初音ミク 『メルト』 サビ2小節目", p1_1, p1_2, 1));
		add(new MelodyPatternDictionaryRecord(34, "初音ミク 『メルト』 サビ3小節目", p1_2, p1_3, 1));
		add(new MelodyPatternDictionaryRecord(35, "初音ミク 『メルト』 サビ4小節目", p1_3, p1_4, 1));
		add(new MelodyPatternDictionaryRecord(36, "初音ミク 『メルト』 サビ5小節目", p1_4, p1_5, 1));
		add(new MelodyPatternDictionaryRecord(37, "初音ミク 『メルト』 サビ6小節目", p1_5, p1_6, 1));
		add(new MelodyPatternDictionaryRecord(38, "初音ミク 『メルト』 サビ7小節目", p1_6, p1_7, 1));
		add(new MelodyPatternDictionaryRecord(39, "初音ミク 『メルト』 サビ8小節目", p1_7, p1_8, 1));
		*/
	}

	public void incPatternFrequency(int index) {
		get(index).incFrequency();
	}

	public void readDictionary(ArrayList<String> lines) {
		clear();
		int index = 0;
		HashMap<String, MelodyPattern> map = new HashMap<String, MelodyPattern>();
		for(int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String type = line.split(":")[0];
			String data = line.split(":")[1];
			switch(type) {
			case "pattern":
				map.put(data, new MelodyPattern());
				break;
			case "data":
				String patternName = data.split(",")[0];
				int variation = Integer.parseInt(data.split(",")[1]);
				int difference = Integer.parseInt(data.split(",")[2]);
				int position = Integer.parseInt(data.split(",")[3]);
				int duration = Integer.parseInt(data.split(",")[4]);
				map.get(patternName).add(variation, difference, position, duration);
				break;
			case "record":
				String recordName = data.split(",")[0];
				String transition = data.split(",")[1];
				String contextName = transition.split("-")[0];
				String wordName = transition.split("-")[1];
				int frequency = Integer.parseInt(data.split(",")[2]);
				add(new MelodyPatternDictionaryRecord(index, recordName, map.get(contextName), map.get(wordName), frequency));
				index++;
				break;
			}
		}
	}
}
