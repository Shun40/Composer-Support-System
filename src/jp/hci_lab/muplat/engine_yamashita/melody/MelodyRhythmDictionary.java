package engine_yamashita.melody;

import java.util.ArrayList;

public class MelodyRhythmDictionary extends ArrayList<MelodyRhythmDictionaryRecord> {
	public MelodyRhythmDictionary() {
		super();
		makeDictionary();
	}

	public void makeDictionary() {
		MelodyRhythm r1 = new MelodyRhythm();
		r1.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(1));
		MelodyRhythm r2 = new MelodyRhythm();
		r2.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(2));
		r2.addRhythm(MelodyRhythm.calcPosition(3, 1), MelodyRhythm.calcDuration(2));
		MelodyRhythm r3 = new MelodyRhythm();
		r3.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(4));
		r3.addRhythm(MelodyRhythm.calcPosition(2, 1), MelodyRhythm.calcDuration(4));
		r3.addRhythm(MelodyRhythm.calcPosition(3, 1), MelodyRhythm.calcDuration(4));
		r3.addRhythm(MelodyRhythm.calcPosition(4, 1), MelodyRhythm.calcDuration(4));
		MelodyRhythm r4 = new MelodyRhythm();
		r4.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(8));
		r4.addRhythm(MelodyRhythm.calcPosition(1, 3), MelodyRhythm.calcDuration(8));
		r4.addRhythm(MelodyRhythm.calcPosition(2, 1), MelodyRhythm.calcDuration(8));
		r4.addRhythm(MelodyRhythm.calcPosition(2, 3), MelodyRhythm.calcDuration(8));
		r4.addRhythm(MelodyRhythm.calcPosition(3, 1), MelodyRhythm.calcDuration(8));
		r4.addRhythm(MelodyRhythm.calcPosition(3, 3), MelodyRhythm.calcDuration(8));
		r4.addRhythm(MelodyRhythm.calcPosition(4, 1), MelodyRhythm.calcDuration(8));
		r4.addRhythm(MelodyRhythm.calcPosition(4, 3), MelodyRhythm.calcDuration(8));
		MelodyRhythm r5 = new MelodyRhythm();
		r5.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(4));
		r5.addRhythm(MelodyRhythm.calcPosition(2, 1), MelodyRhythm.calcDuration(4));
		r5.addRhythm(MelodyRhythm.calcPosition(3, 1), MelodyRhythm.calcDuration(2));
		MelodyRhythm r6 = new MelodyRhythm();
		r6.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(2));
		r6.addRhythm(MelodyRhythm.calcPosition(3, 1), MelodyRhythm.calcDuration(4));
		r6.addRhythm(MelodyRhythm.calcPosition(4, 1), MelodyRhythm.calcDuration(4));
		MelodyRhythm r7 = new MelodyRhythm();
		r7.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(4) + MelodyRhythm.calcDuration(8));
		r7.addRhythm(MelodyRhythm.calcPosition(2, 3), MelodyRhythm.calcDuration(8));
		r7.addRhythm(MelodyRhythm.calcPosition(3, 1), MelodyRhythm.calcDuration(4) + MelodyRhythm.calcDuration(8));
		r7.addRhythm(MelodyRhythm.calcPosition(4, 3), MelodyRhythm.calcDuration(8));
		MelodyRhythm r8 = new MelodyRhythm();
		r8.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(4) + MelodyRhythm.calcDuration(8));
		r8.addRhythm(MelodyRhythm.calcPosition(2, 3), MelodyRhythm.calcDuration(8));
		r8.addRhythm(MelodyRhythm.calcPosition(3, 1), MelodyRhythm.calcDuration(4));
		r8.addRhythm(MelodyRhythm.calcPosition(4, 1), MelodyRhythm.calcDuration(4));
		MelodyRhythm r9 = new MelodyRhythm();
		r9.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(8));
		r9.addRhythm(MelodyRhythm.calcPosition(1, 3), MelodyRhythm.calcDuration(8));
		r9.addRhythm(MelodyRhythm.calcPosition(2, 1), MelodyRhythm.calcDuration(8));
		r9.addRhythm(MelodyRhythm.calcPosition(2, 3), MelodyRhythm.calcDuration(8));
		r9.addRhythm(MelodyRhythm.calcPosition(3, 1), MelodyRhythm.calcDuration(4));
		r9.addRhythm(MelodyRhythm.calcPosition(4, 1), MelodyRhythm.calcDuration(8));
		r9.addRhythm(MelodyRhythm.calcPosition(4, 3), MelodyRhythm.calcDuration(8));
		MelodyRhythm r10 = new MelodyRhythm();
		r10.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(4));
		r10.addRhythm(MelodyRhythm.calcPosition(2, 1), MelodyRhythm.calcDuration(8));
		r10.addRhythm(MelodyRhythm.calcPosition(2, 3), MelodyRhythm.calcDuration(4));
		r10.addRhythm(MelodyRhythm.calcPosition(3, 3), MelodyRhythm.calcDuration(8));
		r10.addRhythm(MelodyRhythm.calcPosition(4, 1), MelodyRhythm.calcDuration(4));
		MelodyRhythm r11 = new MelodyRhythm();
		r11.addRhythm(MelodyRhythm.calcPosition(1, 1), MelodyRhythm.calcDuration(4));
		r11.addRhythm(MelodyRhythm.calcPosition(2, 1), MelodyRhythm.calcDuration(8));
		r11.addRhythm(MelodyRhythm.calcPosition(2, 3), MelodyRhythm.calcDuration(2) + MelodyRhythm.calcDuration(8));

		// よく出そうなパターンの順に登録
		add(new MelodyRhythmDictionaryRecord("P4to1", r4, r1, 0));
		add(new MelodyRhythmDictionaryRecord("P9to1", r9, r1, 0));
		add(new MelodyRhythmDictionaryRecord("P7to1", r7, r1, 0));
		add(new MelodyRhythmDictionaryRecord("P10to1", r10, r1, 0));
		add(new MelodyRhythmDictionaryRecord("P6to1", r6, r1, 0));
		add(new MelodyRhythmDictionaryRecord("P8to1", r8, r1, 0));
		add(new MelodyRhythmDictionaryRecord("P2to1", r2, r1, 0));
		add(new MelodyRhythmDictionaryRecord("P11to1", r11, r1, 0));
		add(new MelodyRhythmDictionaryRecord("P1to1", r1, r1, 0));

		add(new MelodyRhythmDictionaryRecord("P4to2", r4, r2, 0));
		add(new MelodyRhythmDictionaryRecord("P9to2", r9, r2, 0));
		add(new MelodyRhythmDictionaryRecord("P7to2", r7, r2, 0));
		add(new MelodyRhythmDictionaryRecord("P10to2", r10, r2, 0));
		add(new MelodyRhythmDictionaryRecord("P6to2", r6, r2, 0));
		add(new MelodyRhythmDictionaryRecord("P8to2", r8, r2, 0));
		add(new MelodyRhythmDictionaryRecord("P2to2", r2, r2, 0));
		add(new MelodyRhythmDictionaryRecord("P11to2", r11, r2, 0));
		add(new MelodyRhythmDictionaryRecord("P1to2", r1, r2, 0));

		add(new MelodyRhythmDictionaryRecord("P4to3", r4, r3, 0));
		add(new MelodyRhythmDictionaryRecord("P9to3", r9, r3, 0));
		add(new MelodyRhythmDictionaryRecord("P7to3", r7, r3, 0));
		add(new MelodyRhythmDictionaryRecord("P10to3", r10, r3, 0));
		add(new MelodyRhythmDictionaryRecord("P6to3", r6, r3, 0));
		add(new MelodyRhythmDictionaryRecord("P8to3", r8, r3, 0));
		add(new MelodyRhythmDictionaryRecord("P2to3", r2, r3, 0));
		add(new MelodyRhythmDictionaryRecord("P11to3", r11, r3, 0));
		add(new MelodyRhythmDictionaryRecord("P1to3", r1, r3, 0));

		add(new MelodyRhythmDictionaryRecord("P4to4", r4, r4, 0));
		add(new MelodyRhythmDictionaryRecord("P9to4", r9, r4, 0));
		add(new MelodyRhythmDictionaryRecord("P7to4", r7, r4, 0));
		add(new MelodyRhythmDictionaryRecord("P10to4", r10, r4, 0));
		add(new MelodyRhythmDictionaryRecord("P6to4", r6, r4, 0));
		add(new MelodyRhythmDictionaryRecord("P8to4", r8, r4, 0));
		add(new MelodyRhythmDictionaryRecord("P2to4", r2, r4, 0));
		add(new MelodyRhythmDictionaryRecord("P11to4", r11, r4, 0));
		add(new MelodyRhythmDictionaryRecord("P1to4", r1, r4, 0));

		add(new MelodyRhythmDictionaryRecord("P4to5", r4, r5, 0));
		add(new MelodyRhythmDictionaryRecord("P9to5", r9, r5, 0));
		add(new MelodyRhythmDictionaryRecord("P7to5", r7, r5, 0));
		add(new MelodyRhythmDictionaryRecord("P10to5", r10, r5, 0));
		add(new MelodyRhythmDictionaryRecord("P6to5", r6, r5, 0));
		add(new MelodyRhythmDictionaryRecord("P8to5", r8, r5, 0));
		add(new MelodyRhythmDictionaryRecord("P2to5", r2, r5, 0));
		add(new MelodyRhythmDictionaryRecord("P11to5", r11, r5, 0));
		add(new MelodyRhythmDictionaryRecord("P1to5", r1, r5, 0));

		add(new MelodyRhythmDictionaryRecord("P4to6", r4, r6, 0));
		add(new MelodyRhythmDictionaryRecord("P9to6", r9, r6, 0));
		add(new MelodyRhythmDictionaryRecord("P7to6", r7, r6, 0));
		add(new MelodyRhythmDictionaryRecord("P10to6", r10, r6, 0));
		add(new MelodyRhythmDictionaryRecord("P6to6", r6, r6, 0));
		add(new MelodyRhythmDictionaryRecord("P8to6", r8, r6, 0));
		add(new MelodyRhythmDictionaryRecord("P2to6", r2, r6, 0));
		add(new MelodyRhythmDictionaryRecord("P11to6", r11, r6, 0));
		add(new MelodyRhythmDictionaryRecord("P1to6", r1, r6, 0));

		add(new MelodyRhythmDictionaryRecord("P4to7", r4, r7, 0));
		add(new MelodyRhythmDictionaryRecord("P9to7", r9, r7, 0));
		add(new MelodyRhythmDictionaryRecord("P7to7", r7, r7, 0));
		add(new MelodyRhythmDictionaryRecord("P10to7", r10, r7, 0));
		add(new MelodyRhythmDictionaryRecord("P6to7", r6, r7, 0));
		add(new MelodyRhythmDictionaryRecord("P8to7", r8, r7, 0));
		add(new MelodyRhythmDictionaryRecord("P2to7", r2, r7, 0));
		add(new MelodyRhythmDictionaryRecord("P11to7", r11, r7, 0));
		add(new MelodyRhythmDictionaryRecord("P1to7", r1, r7, 0));

		add(new MelodyRhythmDictionaryRecord("P4to8", r4, r8, 0));
		add(new MelodyRhythmDictionaryRecord("P9to8", r9, r8, 0));
		add(new MelodyRhythmDictionaryRecord("P7to8", r7, r8, 0));
		add(new MelodyRhythmDictionaryRecord("P10to8", r10, r8, 0));
		add(new MelodyRhythmDictionaryRecord("P6to8", r6, r8, 0));
		add(new MelodyRhythmDictionaryRecord("P8to8", r8, r8, 0));
		add(new MelodyRhythmDictionaryRecord("P2to8", r2, r8, 0));
		add(new MelodyRhythmDictionaryRecord("P11to8", r11, r8, 0));
		add(new MelodyRhythmDictionaryRecord("P1to8", r1, r8, 0));

		add(new MelodyRhythmDictionaryRecord("P4to9", r4, r9, 0));
		add(new MelodyRhythmDictionaryRecord("P9to9", r9, r9, 0));
		add(new MelodyRhythmDictionaryRecord("P7to9", r7, r9, 0));
		add(new MelodyRhythmDictionaryRecord("P10to9", r10, r9, 0));
		add(new MelodyRhythmDictionaryRecord("P6to9", r6, r9, 0));
		add(new MelodyRhythmDictionaryRecord("P8to9", r8, r9, 0));
		add(new MelodyRhythmDictionaryRecord("P2to9", r2, r9, 0));
		add(new MelodyRhythmDictionaryRecord("P11to9", r11, r9, 0));
		add(new MelodyRhythmDictionaryRecord("P1to9", r1, r9, 0));

		add(new MelodyRhythmDictionaryRecord("P4to10", r4, r10, 0));
		add(new MelodyRhythmDictionaryRecord("P9to10", r9, r10, 0));
		add(new MelodyRhythmDictionaryRecord("P7to10", r7, r10, 0));
		add(new MelodyRhythmDictionaryRecord("P10to10", r10, r10, 0));
		add(new MelodyRhythmDictionaryRecord("P6to10", r6, r10, 0));
		add(new MelodyRhythmDictionaryRecord("P8to10", r8, r10, 0));
		add(new MelodyRhythmDictionaryRecord("P2to10", r2, r10, 0));
		add(new MelodyRhythmDictionaryRecord("P11to10", r11, r10, 0));
		add(new MelodyRhythmDictionaryRecord("P1to10", r1, r10, 0));

		add(new MelodyRhythmDictionaryRecord("P4to11", r4, r11, 0));
		add(new MelodyRhythmDictionaryRecord("P9to11", r9, r11, 0));
		add(new MelodyRhythmDictionaryRecord("P7to11", r7, r11, 0));
		add(new MelodyRhythmDictionaryRecord("P10to11", r10, r11, 0));
		add(new MelodyRhythmDictionaryRecord("P6to11", r6, r11, 0));
		add(new MelodyRhythmDictionaryRecord("P8to11", r8, r11, 0));
		add(new MelodyRhythmDictionaryRecord("P2to11", r2, r11, 0));
		add(new MelodyRhythmDictionaryRecord("P11to11", r11, r11, 0));
		add(new MelodyRhythmDictionaryRecord("P1to11", r1, r11, 0));
	}
}
