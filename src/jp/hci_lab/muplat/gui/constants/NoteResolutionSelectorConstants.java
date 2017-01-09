package gui.constants;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * NoteResolutionSelectorクラスで使用する定数クラス
 * @author Shun Yamashita
 */
public class NoteResolutionSelectorConstants {
	private NoteResolutionSelectorConstants() {};

	public static final ObservableList<String> resolutions = FXCollections.observableArrayList(
		"1/4",
		"1/8",
		"1/16"
	);
	public static final String initValue = "1/4";
	public static final HashMap<String, Integer> values = new HashMap<String, Integer>() {
		{put("1/4", 4);}
		{put("1/8", 8);}
		{put("1/16", 16);}
	};
}
