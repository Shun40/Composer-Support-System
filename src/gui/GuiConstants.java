package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import system.AppConstants;

public final class GuiConstants {
	public static class AppSize {
		public static final int CLIENT_AREA_WIDTH = 1074;
		public static final int CLIENT_AREA_HEIGHT = 540;
		public static final int WINDOW_OFFSET_WIDTH = 8;
		public static final int WINDOW_OFFSET_HEIGHT = 34;
		public static final int WIDTH = CLIENT_AREA_WIDTH + WINDOW_OFFSET_WIDTH;
		public static final int HEIGHT = CLIENT_AREA_HEIGHT + WINDOW_OFFSET_HEIGHT;
	}

	public static class Menubar {
		public static final int WIDTH = AppSize.CLIENT_AREA_WIDTH + 18;
		public static final int HEIGHT = 10;
	}

	public static class ResolutionSelector {
		public static final int X = 110;
		public static final int Y = 40;
		public static final ObservableList<String> ITEMS = FXCollections.observableArrayList("1/4", "1/8", "1/16");
		public static final String DEFAULT_VALUE = ITEMS.get(0);
	}

	public static class BpmSelector {
		public static final int X = 200;
		public static final int Y = 42;
		public static final Font FONT = Font.font("Arial", 16);
		public static final Color COLOR = Color.web("#000000");
	}

	public static class StopButton {
		public static final int X = 310;
		public static final int Y = 40;
		public static final String TEXT = "■";
		public static final Color COLOR = Color.web("#0000AA");
	}

	public static class PlayButton {
		public static final int X = 346;
		public static final int Y = 40;
		public static final String TEXT = "▶";
		public static final Color COLOR = Color.web("#00AA00");
	}

	public static class Pianoroll {
		public static final int X = 110;
		public static final int Y = 120;
		public static final int MEASURE_WIDTH = 160;
		public static final int MEASURE_HEIGHT = 144;
		public static final int BEAT_WIDTH = MEASURE_WIDTH / 4;
	}

	public static class Grid {
		public static final int WIDTH = 40;
		public static final int HEIGHT = 12;
		public static final int[] X = {
				WIDTH * 0,
				WIDTH * 1,
				WIDTH * 2,
				WIDTH * 3,
			};
		public static final int[] Y = {
			HEIGHT * 0,
			HEIGHT * 1,
			HEIGHT * 2,
			HEIGHT * 3,
			HEIGHT * 4,
			HEIGHT * 5,
			HEIGHT * 6,
			HEIGHT * 7,
			HEIGHT * 8,
			HEIGHT * 9,
			HEIGHT * 10,
			HEIGHT * 11
		};
		public static final int X_OFFSET = 160; // 1 measure offset
		public static final int Y_OFFSET = 144; // 1 octave offset
		/** ノートグリッドのCSS描画スタイル */
		public static final String STYLE = "-fx-stroke: #888888;-fx-stroke-type: centered;";
		/** 有効な白鍵ノートグリッドのカラーコード */
		public static final String AVAILABLE_WHITE_COLORCODE = "#FFFFFF";
		/** 有効な黒鍵ノートグリッドのカラーコード */
		public static final String AVAILABLE_BLACK_COLORCODE = "#DDDDDD";
		/** 無効な白鍵ノートグリッドのカラーコード */
		public static final String UNAVAILABLE_WHITE_COLORCODE = "#BBBBBB";
		/** 無効な黒鍵ノートグリッドのカラーコード */
		public static final String UNAVAILABLE_BLACK_COLORCODE = "#999999";
	}

	public static class Note {
		public static final float MIN_X = 0 + 0.5f;
		public static final float MIN_Y = 0 + 0.5f;
		public static final float MAX_X = GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.MEASURES + 0.5f;
		public static final float MAX_Y = GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.OCTAVES + 0.5f;
		public static final String STYLE = "-fx-stroke: #000000;-fx-stroke-type: centered;";
		public static final Color[] LIGHT_COLORS = {
			Color.FUCHSIA, // track 1
			Color.YELLOW, // track 2
			Color.AQUA, // track 3
			Color.CORAL, // track 4
			Color.HOTPINK, // track 5
			Color.SKYBLUE, // track 6
			Color.LIGHTCORAL, // track 7
			Color.LIGHTGREEN, // track 8
			Color.LIGHTBLUE, // track 9
			Color.KHAKI, // track 10
		};
		public static final Color[] DARK_COLORS = {
			Color.RED, // track 1
			Color.GREEN, // track 2
			Color.BLUE, // track 3
			Color.CHOCOLATE, // track 4
			Color.DEEPPINK, // track 5
			Color.DODGERBLUE, // track 6
			Color.CRIMSON, // track 7
			Color.DARKGREEN, // track 8
			Color.DARKBLUE, // track 9
			Color.GOLDENROD, // track 10
		};
	}

	public static class Keyboard {
		public static final int X = 10;
		public static final int Y = 120;
		public static final int Y_OFFSET = Pianoroll.MEASURE_HEIGHT; // 1 octave offset
		public static final int[] WHITE_KEY_Y = {0, 21, 42, 63, 84, 104, 124};
		public static final int[] BLACK_KEY_Y = {12, 36, 60, 96, 120};
		public static final int WHITE_KEY_WIDTH = 100;
		public static final int BLACK_KEY_WIDTH = 60;
		public static final int[] WHITE_KEY_HEIGHT = {21, 21, 21, 21, 20, 20, 20};
		public static final int[] BLACK_KEY_HEIGHT = {12, 12, 12, 12, 12};
		public static final int WHITE_KEY_COUNT = 7;
		public static final int BLACK_KEY_COUNT = 5;
	}

	public static class Key {
		/** 鍵盤のCSS描画スタイル */
		public static final String STYLE = "-fx-stroke: #000000;-fx-stroke-type: centered;-fx-arc-width: 5;-fx-arc-height: 5;";
		/** 有効な白鍵のカラーコード（無押下時） */
		public static final String AVAILABLE_WHITE_RELEASED_COLORCODE = "#FFFFFF";
		/** 有効な白鍵のカラーコード（押下時） */
		public static final String AVAILABLE_WHITE_PRESSED_COLORCODE = "#CCCCCC";
		/** 有効な黒鍵のカラーコード（無押下時） */
		public static final String AVAILABLE_BLACK_RELEASED_COLORCODE = "#999999";
		/** 有効な黒鍵のカラーコード（押下時） */
		public static final String AVAILABLE_BLACK_PRESSED_COLORCODE = "#666666";
		/** 無効な白鍵のカラーコード（無押下時） */
		public static final String UNAVAILABLE_WHITE_RELEASED_COLORCODE = "#999999";
		/** 無効な白盤のカラーコード（押下時） */
		public static final String UNAVAILABLE_WHITE_PRESSED_COLORCODE = "#999999";
		/** 無効な黒鍵のカラーコード（無押下時） */
		public static final String UNAVAILABLE_BLACK_RELEASED_COLORCODE = "#000000";
		/** 無効な黒盤のカラーコード（押下時） */
		public static final String UNAVAILABLE_BLACK_PRESSED_COLORCODE = "#000000";
	}

	public static class Measurebar {
		public static final int X = 110;
		public static final int Y = 73;
		public static final String STYLE = "-fx-stroke: #000000;-fx-stroke-type: centered;";
		/** 小節選択時のカラーコード */
		public static final String SELECTED_COLORCODE = "#7FFFD4";
		/** 小節非選択時のカラーコード */
		public static final String UNSELECTED_COLORCODE = "#FFFFFF";
	}

	public static class ChordSelector {
		public static final ObservableList<String> ITEMS = FXCollections.observableArrayList(
				"N.C.",
				"C",
				"Dm",
				"Em",
				"F",
				"G",
				"Am",
				"Bmb5"
		);
		public static final String DEFAULT_VALUE = ITEMS.get(0);
	}

	public static class AlgorithmBar {
		public static final int X = 780;
		public static final int Y = 60;
	}

	public static class PatternBar {
		public static final int X = 780;
		public static final int Y = 90;
	}
}
