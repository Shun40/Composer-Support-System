import java.util.ArrayList;

public class HihatPatternPreset extends ArrayList<DrumPattern> {
	public HihatPatternPreset() {
		DrumPattern preset1 = new DrumPattern();
		preset1.setName("Preset1");
		preset1.setSpeed(0.0);
		add(preset1);

		DrumPattern preset2 = new DrumPattern();
		preset2.addCloseHihat(DrumPattern.calcPosition(1, 1), 100);
		preset2.addCloseHihat(DrumPattern.calcPosition(2, 1), 100);
		preset2.addCloseHihat(DrumPattern.calcPosition(3, 1), 100);
		preset2.addCloseHihat(DrumPattern.calcPosition(4, 1), 100);
		preset2.setName("Preset2");
		preset2.setSpeed(0.15);
		add(preset2);

		DrumPattern preset3 = new DrumPattern();
		preset3.addCloseHihat(DrumPattern.calcPosition(1, 3), 100);
		preset3.addCloseHihat(DrumPattern.calcPosition(2, 3), 100);
		preset3.addCloseHihat(DrumPattern.calcPosition(3, 3), 100);
		preset3.addCloseHihat(DrumPattern.calcPosition(4, 3), 100);
		preset3.setName("Preset3");
		preset3.setSpeed(0.35);
		add(preset3);

		DrumPattern preset4 = new DrumPattern();
		preset4.addCloseHihat(DrumPattern.calcPosition(1, 1), 100);
		preset4.addCloseHihat(DrumPattern.calcPosition(1, 3), 100);
		preset4.addCloseHihat(DrumPattern.calcPosition(2, 1), 100);
		preset4.addCloseHihat(DrumPattern.calcPosition(2, 3), 100);
		preset4.addCloseHihat(DrumPattern.calcPosition(3, 1), 100);
		preset4.addCloseHihat(DrumPattern.calcPosition(3, 3), 100);
		preset4.addCloseHihat(DrumPattern.calcPosition(4, 1), 100);
		preset4.addCloseHihat(DrumPattern.calcPosition(4, 3), 100);
		preset4.setName("Preset4");
		preset4.setSpeed(0.55);
		add(preset4);

		DrumPattern preset5 = new DrumPattern();
		preset5.addOpenHihat(DrumPattern.calcPosition(1, 1), 100);
		preset5.addOpenHihat(DrumPattern.calcPosition(2, 1), 100);
		preset5.addOpenHihat(DrumPattern.calcPosition(3, 1), 100);
		preset5.addOpenHihat(DrumPattern.calcPosition(4, 1), 100);
		preset5.setName("Preset5");
		preset5.setSpeed(0.75);
		add(preset5);

		DrumPattern preset6 = new DrumPattern();
		preset6.addCloseHihat(DrumPattern.calcPosition(1, 1), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(1, 3), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(1, 4), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(2, 1), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(2, 3), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(2, 4), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(3, 1), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(3, 3), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(3, 4), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(4, 1), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(4, 3), 100);
		preset6.addCloseHihat(DrumPattern.calcPosition(4, 4), 100);
		preset6.setName("Preset6");
		preset6.setSpeed(0.9);
		add(preset6);

		DrumPattern preset7 = new DrumPattern();
		preset7.addCloseHihat(DrumPattern.calcPosition(1, 1), 100);
		preset7.addOpenHihat(DrumPattern.calcPosition(1, 3), 100);
		preset7.addCloseHihat(DrumPattern.calcPosition(2, 1), 100);
		preset7.addOpenHihat(DrumPattern.calcPosition(2, 3), 100);
		preset7.addCloseHihat(DrumPattern.calcPosition(3, 1), 100);
		preset7.addOpenHihat(DrumPattern.calcPosition(3, 3), 100);
		preset7.addCloseHihat(DrumPattern.calcPosition(4, 1), 100);
		preset7.addOpenHihat(DrumPattern.calcPosition(4, 3), 100);
		preset7.setName("Preset7");
		preset7.setSpeed(1.0);
		add(preset7);
	}
}
