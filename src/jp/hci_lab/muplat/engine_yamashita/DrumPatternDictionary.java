package engine_yamashita;

import java.util.ArrayList;

public class DrumPatternDictionary extends ArrayList<DrumPatternDictionaryRecord> {
	private static DrumPattern KICK_1000000010100000; // 1-1, 3-1, 3-3
	private static DrumPattern KICK_1000001000100000; // 1-1, 2-3, 3-3
	private static DrumPattern KICK_1000100010001000; // 1-1, 2-1, 3-1, 4-1
	private static DrumPattern KICK_1000100010000000; // 1-1, 2-1, 3-1
	private static DrumPattern KICK_1000001000000010; // 1-1, 2-3, 4-3
	private static DrumPattern KICK_0010100010100010; // 1-3, 2-1, 3-1, 3-3, 4-3
	private static DrumPattern KICK_0010001000100010; // 1-3, 2-3, 3-3, 4-3
	private static DrumPattern SNARE_0000100000001000; // 2-1, 4-1
	private static DrumPattern SNARE_1000100010001000; // 1-1, 2-1, 3-1, 4-1
	private static DrumPattern SNARE_0000000000001000; // 4-1
	private static DrumPattern SNARE_1000001000001000; // 1-1, 2-3, 4-1
	private static DrumPattern SNARE_0000100000001111; // 2-1, 4-1, 4-2, 4-3, 4-4
	private static DrumPattern SNARE_0000100000001011; // 2-1, 4-1, 4-3, 4-4
	private static DrumPattern SNARE_0000100000101111; // 2-1, 3-3, 4-1, 4-2, 4-3, 4-4
	private static DrumPattern SNARE_0000000010000000; // 3-1
	private static DrumPattern HIHAT_C0C0C0C0C0C0C0C0; // 1-1, 1-3, 2-1, 2-3, 3-1, 3-3, 4-1, 4-3 (all chihat)
	private static DrumPattern HIHAT_C000C000C000C000; // 1-1, 2-1, 3-1, 4-1 (all chihat)
	private static DrumPattern HIHAT_00C000C000C000C0; // 1-3, 2-3, 3-3, 4-3 (all chihat)
	private static DrumPattern HIHAT_C0CCC0CCC0CCC0CC; // 1-1, 1-3, 1-4, 2-1, 2-3, 2-4, 3-1, 3-3, 3-4, 4-1, 4-3, 4-4 (all chihat)
	private static DrumPattern HIHAT_O000O000O000O000; // 1-1, 2-1, 3-1, 4-1 (all ohihat)
	private static DrumPattern HIHAT_C0O0C0O0C0O0C0O0; // 1-1, 1-3, 2-1, 2-3, 3-1, 3-3, 4-1, 4-3 (-1 : chihat, -3 : ohihat)

	public DrumPatternDictionary() {
		super();
	}

	public void makeKickBeat() {
		KICK_1000000010100000 = new DrumPattern(); // 1-1, 3-1, 3-3
		KICK_1000000010100000.addKick(DrumPattern.calcPosition(1, 1), 100);
		KICK_1000000010100000.addKick(DrumPattern.calcPosition(3, 1), 100);
		KICK_1000000010100000.addKick(DrumPattern.calcPosition(3, 3), 100);
		KICK_1000001000100000 = new DrumPattern(); // 1-1, 2-3, 3-3
		KICK_1000001000100000.addKick(DrumPattern.calcPosition(1, 1), 100);
		KICK_1000001000100000.addKick(DrumPattern.calcPosition(2, 3), 100);
		KICK_1000001000100000.addKick(DrumPattern.calcPosition(3, 3), 100);
		KICK_1000100010001000 = new DrumPattern(); // 1-1, 2-1, 3-1, 4-1
		KICK_1000100010001000.addKick(DrumPattern.calcPosition(1, 1), 100);
		KICK_1000100010001000.addKick(DrumPattern.calcPosition(2, 1), 100);
		KICK_1000100010001000.addKick(DrumPattern.calcPosition(3, 1), 100);
		KICK_1000100010001000.addKick(DrumPattern.calcPosition(4, 1), 100);
		KICK_1000100010000000 = new DrumPattern(); // 1-1, 2-1, 3-1
		KICK_1000100010000000.addKick(DrumPattern.calcPosition(1, 1), 100);
		KICK_1000100010000000.addKick(DrumPattern.calcPosition(2, 1), 100);
		KICK_1000100010000000.addKick(DrumPattern.calcPosition(3, 1), 100);
		KICK_1000001000000010 = new DrumPattern(); // 1-1, 2-3, 4-3
		KICK_1000001000000010.addKick(DrumPattern.calcPosition(1, 1), 100);
		KICK_1000001000000010.addKick(DrumPattern.calcPosition(2, 3), 100);
		KICK_1000001000000010.addKick(DrumPattern.calcPosition(4, 3), 100);
		KICK_0010100010100010 = new DrumPattern(); // 1-3, 2-1, 3-1, 3-3, 4-3
		KICK_0010100010100010.addKick(DrumPattern.calcPosition(1, 3), 100);
		KICK_0010100010100010.addKick(DrumPattern.calcPosition(2, 1), 100);
		KICK_0010100010100010.addKick(DrumPattern.calcPosition(3, 1), 100);
		KICK_0010100010100010.addKick(DrumPattern.calcPosition(3, 3), 100);
		KICK_0010100010100010.addKick(DrumPattern.calcPosition(4, 3), 100);
		KICK_0010001000100010 = new DrumPattern(); // 1-3, 2-3, 3-3, 4-3
		KICK_0010001000100010.addKick(DrumPattern.calcPosition(1, 3), 100);
		KICK_0010001000100010.addKick(DrumPattern.calcPosition(2, 3), 100);
		KICK_0010001000100010.addKick(DrumPattern.calcPosition(3, 3), 100);
		KICK_0010001000100010.addKick(DrumPattern.calcPosition(4, 3), 100);
	}

	public void makeSnareBeat() {
		SNARE_0000100000001000 = new DrumPattern(); // 2-1, 4-1
		SNARE_0000100000001000.addSnare(DrumPattern.calcPosition(2, 1), 100);
		SNARE_0000100000001000.addSnare(DrumPattern.calcPosition(4, 1), 100);
		SNARE_1000100010001000 = new DrumPattern(); // 1-1, 2-1, 3-1, 4-1
		SNARE_1000100010001000.addSnare(DrumPattern.calcPosition(1, 1), 100);
		SNARE_1000100010001000.addSnare(DrumPattern.calcPosition(2, 1), 100);
		SNARE_1000100010001000.addSnare(DrumPattern.calcPosition(3, 1), 100);
		SNARE_1000100010001000.addSnare(DrumPattern.calcPosition(4, 1), 100);
		SNARE_0000000000001000 = new DrumPattern(); // 4-1
		SNARE_0000000000001000.addSnare(DrumPattern.calcPosition(4, 1), 100);
		SNARE_1000001000001000 = new DrumPattern(); // 1-1, 2-3, 4-1
		SNARE_1000001000001000.addSnare(DrumPattern.calcPosition(1, 1), 100);
		SNARE_1000001000001000.addSnare(DrumPattern.calcPosition(2, 3), 100);
		SNARE_1000001000001000.addSnare(DrumPattern.calcPosition(4, 1), 100);
		SNARE_0000100000001111 = new DrumPattern(); // 2-1, 4-1, 4-2, 4-3, 4-4
		SNARE_0000100000001111.addSnare(DrumPattern.calcPosition(2, 1), 100);
		SNARE_0000100000001111.addSnare(DrumPattern.calcPosition(4, 1), 100);
		SNARE_0000100000001111.addSnare(DrumPattern.calcPosition(4, 2), 100);
		SNARE_0000100000001111.addSnare(DrumPattern.calcPosition(4, 3), 100);
		SNARE_0000100000001111.addSnare(DrumPattern.calcPosition(4, 4), 100);
		SNARE_0000100000001011 = new DrumPattern(); // 2-1, 4-1, 4-3, 4-4
		SNARE_0000100000001011.addSnare(DrumPattern.calcPosition(2, 1), 100);
		SNARE_0000100000001011.addSnare(DrumPattern.calcPosition(4, 1), 100);
		SNARE_0000100000001011.addSnare(DrumPattern.calcPosition(4, 3), 100);
		SNARE_0000100000001011.addSnare(DrumPattern.calcPosition(4, 4), 100);
		SNARE_0000100000101111 = new DrumPattern(); // 2-1, 3-3, 4-1, 4-2, 4-3, 4-4
		SNARE_0000100000101111.addSnare(DrumPattern.calcPosition(2, 1), 100);
		SNARE_0000100000101111.addSnare(DrumPattern.calcPosition(3, 3), 100);
		SNARE_0000100000101111.addSnare(DrumPattern.calcPosition(4, 1), 100);
		SNARE_0000100000101111.addSnare(DrumPattern.calcPosition(4, 2), 100);
		SNARE_0000100000101111.addSnare(DrumPattern.calcPosition(4, 3), 100);
		SNARE_0000100000101111.addSnare(DrumPattern.calcPosition(4, 4), 100);
		SNARE_0000000010000000 = new DrumPattern(); // 3-1
		SNARE_0000000010000000.addSnare(DrumPattern.calcPosition(3, 1), 100);
	}

	public void makeHihatBeat() {
		HIHAT_C0C0C0C0C0C0C0C0 = new DrumPattern(); // 1-1, 1-3, 2-1, 2-3, 3-1, 3-3, 4-1, 4-3 (all chihat)
		HIHAT_C0C0C0C0C0C0C0C0.addCloseHihat(DrumPattern.calcPosition(1, 1), 100);
		HIHAT_C0C0C0C0C0C0C0C0.addCloseHihat(DrumPattern.calcPosition(1, 3), 100);
		HIHAT_C0C0C0C0C0C0C0C0.addCloseHihat(DrumPattern.calcPosition(2, 1), 100);
		HIHAT_C0C0C0C0C0C0C0C0.addCloseHihat(DrumPattern.calcPosition(2, 3), 100);
		HIHAT_C0C0C0C0C0C0C0C0.addCloseHihat(DrumPattern.calcPosition(3, 1), 100);
		HIHAT_C0C0C0C0C0C0C0C0.addCloseHihat(DrumPattern.calcPosition(3, 3), 100);
		HIHAT_C0C0C0C0C0C0C0C0.addCloseHihat(DrumPattern.calcPosition(4, 1), 100);
		HIHAT_C0C0C0C0C0C0C0C0.addCloseHihat(DrumPattern.calcPosition(4, 3), 100);
		HIHAT_C000C000C000C000 = new DrumPattern(); // 1-1, 2-1, 3-1, 4-1 (all chihat)
		HIHAT_C000C000C000C000.addCloseHihat(DrumPattern.calcPosition(1, 1), 100);
		HIHAT_C000C000C000C000.addCloseHihat(DrumPattern.calcPosition(2, 1), 100);
		HIHAT_C000C000C000C000.addCloseHihat(DrumPattern.calcPosition(3, 1), 100);
		HIHAT_C000C000C000C000.addCloseHihat(DrumPattern.calcPosition(4, 1), 100);
		HIHAT_00C000C000C000C0 = new DrumPattern(); // 1-3, 2-3, 3-3, 4-3 (all chihat)
		HIHAT_00C000C000C000C0.addCloseHihat(DrumPattern.calcPosition(1, 3), 100);
		HIHAT_00C000C000C000C0.addCloseHihat(DrumPattern.calcPosition(2, 3), 100);
		HIHAT_00C000C000C000C0.addCloseHihat(DrumPattern.calcPosition(3, 3), 100);
		HIHAT_00C000C000C000C0.addCloseHihat(DrumPattern.calcPosition(4, 3), 100);
		HIHAT_C0CCC0CCC0CCC0CC = new DrumPattern(); // 1-1, 1-3, 1-4, 2-1, 2-3, 2-4, 3-1, 3-3, 3-4, 4-1, 4-3, 4-4 (all chihat)
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(1, 1), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(1, 3), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(1, 4), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(2, 1), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(2, 3), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(2, 4), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(3, 1), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(3, 3), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(3, 4), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(4, 1), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(4, 3), 100);
		HIHAT_C0CCC0CCC0CCC0CC.addCloseHihat(DrumPattern.calcPosition(4, 4), 100);
		HIHAT_O000O000O000O000 = new DrumPattern(); // 1-1, 2-1, 3-1, 4-1 (all ohihat)
		HIHAT_O000O000O000O000.addOpenHihat(DrumPattern.calcPosition(1, 1), 100);
		HIHAT_O000O000O000O000.addOpenHihat(DrumPattern.calcPosition(2, 1), 100);
		HIHAT_O000O000O000O000.addOpenHihat(DrumPattern.calcPosition(3, 1), 100);
		HIHAT_O000O000O000O000.addOpenHihat(DrumPattern.calcPosition(4, 1), 100);
		HIHAT_C0O0C0O0C0O0C0O0 = new DrumPattern(); // 1-1, 1-3, 2-1, 2-3, 3-1, 3-3, 4-1, 4-3 (-1 : chihat, -3 : ohihat)
		HIHAT_C0O0C0O0C0O0C0O0.addCloseHihat(DrumPattern.calcPosition(1, 1), 100);
		HIHAT_C0O0C0O0C0O0C0O0.addOpenHihat(DrumPattern.calcPosition(1, 3), 100);
		HIHAT_C0O0C0O0C0O0C0O0.addCloseHihat(DrumPattern.calcPosition(2, 1), 100);
		HIHAT_C0O0C0O0C0O0C0O0.addOpenHihat(DrumPattern.calcPosition(2, 3), 100);
		HIHAT_C0O0C0O0C0O0C0O0.addCloseHihat(DrumPattern.calcPosition(3, 1), 100);
		HIHAT_C0O0C0O0C0O0C0O0.addOpenHihat(DrumPattern.calcPosition(3, 3), 100);
		HIHAT_C0O0C0O0C0O0C0O0.addCloseHihat(DrumPattern.calcPosition(4, 1), 100);
		HIHAT_C0O0C0O0C0O0C0O0.addOpenHihat(DrumPattern.calcPosition(4, 3), 100);
	}

	public void makeDictionary() {
		makeKickBeat();
		makeSnareBeat();
		makeHihatBeat();

		DrumPattern eightBeat1 = new DrumPattern();
		eightBeat1.setKicks(KICK_1000000010100000.getKicks());
		eightBeat1.setSnares(SNARE_0000100000001000.getKicks());
		eightBeat1.setHihats(HIHAT_C0C0C0C0C0C0C0C0.getHihats());

		DrumPattern eightBeat2 = new DrumPattern();
		eightBeat2.setKicks(KICK_1000001000100000.getKicks());
		eightBeat2.setSnares(SNARE_0000100000001000.getKicks());
		eightBeat2.setHihats(HIHAT_C0C0C0C0C0C0C0C0.getHihats());

		DrumPattern eightBeat3 = new DrumPattern();
		eightBeat3.setKicks(KICK_1000000010100000.getKicks());
		eightBeat3.setSnares(SNARE_0000100000001000.getKicks());
		eightBeat3.setHihats(HIHAT_O000O000O000O000.getHihats());

		DrumPattern eightBeat4 = new DrumPattern();
		eightBeat4.setKicks(KICK_1000001000100000.getKicks());
		eightBeat4.setSnares(SNARE_0000100000001000.getKicks());
		eightBeat4.setHihats(HIHAT_O000O000O000O000.getHihats());

		DrumPattern eightBeat5 = new DrumPattern();
		eightBeat5.setKicks(KICK_1000000010100000.getKicks());
		eightBeat5.setSnares(SNARE_0000100000001000.getKicks());
		eightBeat5.setHihats(HIHAT_C0O0C0O0C0O0C0O0.getHihats());

		DrumPattern eightBeat6 = new DrumPattern();
		eightBeat6.setKicks(KICK_1000001000100000.getKicks());
		eightBeat6.setSnares(SNARE_0000100000001000.getKicks());
		eightBeat6.setHihats(HIHAT_C0O0C0O0C0O0C0O0.getHihats());

		DrumPattern sixteenBeat1 = new DrumPattern();
		sixteenBeat1.setKicks(KICK_1000000010100000.getKicks());
		sixteenBeat1.setSnares(SNARE_0000100000001000.getKicks());
		sixteenBeat1.setHihats(HIHAT_C0CCC0CCC0CCC0CC.getHihats());

		DrumPattern sixteenBeat2 = new DrumPattern();
		sixteenBeat2.setKicks(KICK_1000001000100000.getKicks());
		sixteenBeat2.setSnares(SNARE_0000100000001000.getKicks());
		sixteenBeat2.setHihats(HIHAT_C0CCC0CCC0CCC0CC.getHihats());

		DrumPattern fourBeat1 = new DrumPattern();
		fourBeat1.setKicks(KICK_1000100010001000.getKicks());

		DrumPattern fourBeat2 = new DrumPattern();
		fourBeat2.setKicks(KICK_1000100010001000.getKicks());
		fourBeat2.setHihats(HIHAT_C000C000C000C000.getHihats());

		DrumPattern fourBeat3 = new DrumPattern();
		fourBeat3.setKicks(KICK_1000100010001000.getKicks());
		fourBeat3.setHihats(HIHAT_C0C0C0C0C0C0C0C0.getHihats());

		DrumPattern fourBeat4 = new DrumPattern();
		fourBeat4.setKicks(KICK_1000100010001000.getKicks());
		fourBeat4.setHihats(HIHAT_C0CCC0CCC0CCC0CC.getHihats());

		DrumPattern fourBeat5 = new DrumPattern();
		fourBeat5.setKicks(KICK_1000100010001000.getKicks());
		fourBeat5.setHihats(HIHAT_C0O0C0O0C0O0C0O0.getHihats());

		DrumPattern fourBeat6 = new DrumPattern();
		fourBeat6.setKicks(KICK_1000100010001000.getKicks());
		fourBeat6.setSnares(SNARE_0000100000001000.getKicks());
		fourBeat6.setHihats(HIHAT_C0O0C0O0C0O0C0O0.getHihats());

		DrumPattern fourBeat7 = new DrumPattern();
		fourBeat7.setKicks(KICK_1000100010000000.getKicks());
		fourBeat7.setSnares(SNARE_0000000000001000.getKicks());
		fourBeat7.setHihats(HIHAT_C000C000C000C000.getHihats());

		DrumPattern fourBeat8 = new DrumPattern();
		fourBeat8.setKicks(KICK_1000100010000000.getKicks());
		fourBeat8.setSnares(SNARE_0000000000001000.getKicks());
		fourBeat8.setHihats(HIHAT_C0C0C0C0C0C0C0C0.getHihats());

		DrumPattern otherBeat1 = new DrumPattern();
		otherBeat1.setKicks(KICK_1000001000000010.getKicks());
		otherBeat1.setSnares(SNARE_0000000010000000.getKicks());
		otherBeat1.setHihats(HIHAT_C000C000C000C000.getHihats());

		DrumPattern otherBeat2 = new DrumPattern();
		otherBeat2.setKicks(KICK_1000001000000010.getKicks());
		otherBeat2.setSnares(SNARE_0000000010000000.getKicks());
		otherBeat2.setHihats(HIHAT_O000O000O000O000.getHihats());

		DrumPattern otherBeat3 = new DrumPattern();
		otherBeat3.setKicks(KICK_0010001000100010.getKicks());
		otherBeat3.setSnares(SNARE_1000100010001000.getKicks());
		otherBeat3.setHihats(HIHAT_O000O000O000O000.getHihats());

		// よく出そうなパターンの順に登録
		add(new DrumPatternDictionaryRecord(eightBeat1, eightBeat1, 0));
		add(new DrumPatternDictionaryRecord(eightBeat2, eightBeat1, 0));
		add(new DrumPatternDictionaryRecord(fourBeat8, eightBeat1, 0));
		add(new DrumPatternDictionaryRecord(fourBeat7, eightBeat1, 0));
		add(new DrumPatternDictionaryRecord(fourBeat2, eightBeat1, 0));
		add(new DrumPatternDictionaryRecord(eightBeat1, eightBeat2, 0));
		add(new DrumPatternDictionaryRecord(eightBeat2, eightBeat2, 0));
		add(new DrumPatternDictionaryRecord(fourBeat8, eightBeat2, 0));
		add(new DrumPatternDictionaryRecord(fourBeat7, eightBeat2, 0));
		add(new DrumPatternDictionaryRecord(fourBeat2, eightBeat2, 0));
		add(new DrumPatternDictionaryRecord(eightBeat3, eightBeat3, 0));
		add(new DrumPatternDictionaryRecord(eightBeat4, eightBeat3, 0));
		add(new DrumPatternDictionaryRecord(fourBeat8, eightBeat3, 0));
		add(new DrumPatternDictionaryRecord(fourBeat7, eightBeat3, 0));
		add(new DrumPatternDictionaryRecord(fourBeat2, eightBeat3, 0));
		add(new DrumPatternDictionaryRecord(eightBeat3, eightBeat4, 0));
		add(new DrumPatternDictionaryRecord(eightBeat4, eightBeat4, 0));
		add(new DrumPatternDictionaryRecord(fourBeat8, eightBeat4, 0));
		add(new DrumPatternDictionaryRecord(fourBeat7, eightBeat4, 0));
		add(new DrumPatternDictionaryRecord(fourBeat2, eightBeat4, 0));

		add(new DrumPatternDictionaryRecord(eightBeat3, eightBeat3, 0));
		add(new DrumPatternDictionaryRecord(eightBeat3, eightBeat4, 0));
		add(new DrumPatternDictionaryRecord(eightBeat5, eightBeat5, 0));
		add(new DrumPatternDictionaryRecord(eightBeat5, eightBeat6, 0));
		add(new DrumPatternDictionaryRecord(sixteenBeat1, sixteenBeat1, 0));
		add(new DrumPatternDictionaryRecord(sixteenBeat1, sixteenBeat2, 0));
		add(new DrumPatternDictionaryRecord(fourBeat6, fourBeat6, 0));
		add(new DrumPatternDictionaryRecord(fourBeat5, fourBeat5, 0));
		add(new DrumPatternDictionaryRecord(fourBeat5, fourBeat6, 0));
		add(new DrumPatternDictionaryRecord(fourBeat1, fourBeat1, 0));
		add(new DrumPatternDictionaryRecord(fourBeat3, fourBeat3, 0));
		add(new DrumPatternDictionaryRecord(fourBeat2, fourBeat7, 0));
		add(new DrumPatternDictionaryRecord(fourBeat2, fourBeat2, 0));
		add(new DrumPatternDictionaryRecord(fourBeat4, fourBeat4, 0));
		add(new DrumPatternDictionaryRecord(otherBeat1, otherBeat1, 0));
		add(new DrumPatternDictionaryRecord(otherBeat2, otherBeat2, 0));
		add(new DrumPatternDictionaryRecord(otherBeat3, otherBeat3, 0));
	}
}
