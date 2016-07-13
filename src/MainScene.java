/*
 * シーン(画面全体)を表すクラス.
 * この中にピアノロール等のコンポーネントを配置する.
 */

import javafx.scene.Group;
import javafx.scene.Scene;

public class MainScene extends Scene {
	public static final int PIANO_ROLL_X = 10;
	public static final int PIANO_ROLL_Y = 20;
	public static final int OCTAVE_MAP_X = 120;
	public static final int OCTAVE_MAP_Y = 20;

	public MainScene(Group root, int width, int height) {
		super(root, width, height);

		PianoRoll pianoRoll1 = new PianoRoll(PIANO_ROLL_X, PIANO_ROLL_Y);
		PianoRoll pianoRoll2 = new PianoRoll(PIANO_ROLL_X, PIANO_ROLL_Y + 144);
		root.getChildren().add(pianoRoll1);
		root.getChildren().add(pianoRoll2);

		OctaveMap octaveMap1 = new OctaveMap(4, OCTAVE_MAP_X, OCTAVE_MAP_Y);
		OctaveMap octaveMap2 = new OctaveMap(3, OCTAVE_MAP_X, OCTAVE_MAP_Y + 144);
		root.getChildren().add(octaveMap1);
		root.getChildren().add(octaveMap2);
	}
}
