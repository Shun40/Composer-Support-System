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
		"C",
		"Dm",
		"Em",
		"F",
		"G",
		"Am",
		"Bdim"
	);
	public static final String initValue = "N.C.";
}
