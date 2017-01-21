package gui.constants;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ChordSelectorクラスで使用する定数クラス
 * @author Shun Yamashita
 */
public class ChordSelectorConstants {
	private ChordSelectorConstants() {};

	public static final ObservableList<String> chords = FXCollections.observableArrayList(
		"N.C.",
		"C", "CM7",
		"Dm", "Dm7",
		"Em", "Em7",
		"F", "FM7",
		"G", "G7",
		"Am", "Am7",
		"Bmb5", "Bm7b5"
	);
	public static final String initValue = "N.C.";
}
