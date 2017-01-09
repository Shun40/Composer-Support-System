package engine_yamashita;

import java.util.ArrayList;

public class KickAndSnarePatternPreset extends ArrayList<DrumPattern> {
	public KickAndSnarePatternPreset() {
		DrumPattern preset1 = new DrumPattern();
		preset1.setName("Preset1");
		preset1.setClimax(0.0);
		add(preset1);

		DrumPattern preset2 = new DrumPattern();
		preset2.addKick(DrumPattern.calcPosition(1, 1), 100);
		preset2.addKick(DrumPattern.calcPosition(2, 1), 100);
		preset2.addKick(DrumPattern.calcPosition(3, 1), 100);
		preset2.addKick(DrumPattern.calcPosition(4, 1), 100);
		preset2.setName("Preset2");
		preset2.setClimax(0.1);
		add(preset2);

		DrumPattern preset3 = new DrumPattern();
		preset3.addKick(DrumPattern.calcPosition(1, 1), 100);
		preset3.addKick(DrumPattern.calcPosition(2, 1), 100);
		preset3.addKick(DrumPattern.calcPosition(3, 1), 100);
		preset3.addSnare(DrumPattern.calcPosition(4, 1), 100);
		preset3.setName("Preset3");
		preset3.setClimax(0.2);
		add(preset3);

		DrumPattern preset4 = new DrumPattern();
		preset4.addKick(DrumPattern.calcPosition(1, 1), 100);
		preset4.addKick(DrumPattern.calcPosition(2, 3), 100);
		preset4.addKick(DrumPattern.calcPosition(4, 3), 100);
		preset4.addSnare(DrumPattern.calcPosition(3, 1), 100);
		preset4.setName("Preset4");
		preset4.setClimax(0.4);
		add(preset4);

		DrumPattern preset5 = new DrumPattern();
		preset5.addKick(DrumPattern.calcPosition(1, 1), 100);
		preset5.addKick(DrumPattern.calcPosition(3, 1), 100);
		preset5.addKick(DrumPattern.calcPosition(3, 3), 100);
		preset5.addSnare(DrumPattern.calcPosition(2, 1), 100);
		preset5.addSnare(DrumPattern.calcPosition(4, 3), 100);
		preset5.setName("Preset5");
		preset5.setClimax(0.5);
		add(preset5);

		DrumPattern preset6 = new DrumPattern();
		preset6.addKick(DrumPattern.calcPosition(1, 1), 100);
		preset6.addKick(DrumPattern.calcPosition(3, 1), 100);
		preset6.addKick(DrumPattern.calcPosition(3, 3), 100);
		preset6.addSnare(DrumPattern.calcPosition(2, 1), 100);
		preset6.addSnare(DrumPattern.calcPosition(4, 1), 100);
		preset6.setName("Preset6");
		preset6.setClimax(0.75);
		add(preset6);

		DrumPattern preset7 = new DrumPattern();
		preset7.addKick(DrumPattern.calcPosition(1, 1), 100);
		preset7.addKick(DrumPattern.calcPosition(2, 3), 100);
		preset7.addKick(DrumPattern.calcPosition(3, 3), 100);
		preset7.addSnare(DrumPattern.calcPosition(2, 1), 100);
		preset7.addSnare(DrumPattern.calcPosition(4, 1), 100);
		preset7.setName("Preset7");
		preset7.setClimax(0.75);
		add(preset7);

		DrumPattern preset8 = new DrumPattern();
		preset8.addKick(DrumPattern.calcPosition(1, 2), 100);
		preset8.addKick(DrumPattern.calcPosition(2, 1), 100);
		preset8.addKick(DrumPattern.calcPosition(3, 1), 100);
		preset8.addKick(DrumPattern.calcPosition(3, 3), 100);
		preset8.addKick(DrumPattern.calcPosition(4, 3), 100);
		preset8.addSnare(DrumPattern.calcPosition(1, 1), 100);
		preset8.addSnare(DrumPattern.calcPosition(2, 3), 100);
		preset8.addSnare(DrumPattern.calcPosition(4, 1), 100);
		preset8.setName("Preset8");
		preset8.setClimax(0.85);
		add(preset8);

		DrumPattern preset9 = new DrumPattern();
		preset9.addKick(DrumPattern.calcPosition(1, 3), 100);
		preset9.addKick(DrumPattern.calcPosition(2, 3), 100);
		preset9.addKick(DrumPattern.calcPosition(3, 3), 100);
		preset9.addKick(DrumPattern.calcPosition(4, 3), 100);
		preset9.addSnare(DrumPattern.calcPosition(1, 1), 100);
		preset9.addSnare(DrumPattern.calcPosition(2, 1), 100);
		preset9.addSnare(DrumPattern.calcPosition(3, 1), 100);
		preset9.addSnare(DrumPattern.calcPosition(4, 1), 100);
		preset9.setName("Preset9");
		preset9.setClimax(1.0);
		add(preset9);
	}
}
