/*
 * マップ上に配置されるノートを表すクラス.
 */

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class Note extends Rectangle {
	public static final String DEFAULT_STYLE = "-fx-stroke: #444444;-fx-stroke-type: outside;-fx-arc-width: 0;-fx-arc-height: 0;";
	public static final String DEFAULT_COLOR = "#FF0000";

	private IntervalMap parent; // 自身が配置されている音程マップオブジェクトを親として持つ

	public Note(IntervalMap parent, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.fillColor(DEFAULT_COLOR);
		this.parent = parent;

		// ノートへクリックイベント処理を追加
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Note.this.click(e);
			}
		});
	}

	public void fillColor(String color) {
		this.setStyle("-fx-fill: " + color + ";" + DEFAULT_STYLE);
	}

	public void click(MouseEvent e) {
		if(e.getButton() == MouseButton.SECONDARY) { // 右クリックされた時, マップ上からノートを削除
			this.parent.removeNote();
		} else { // 左クリックされた時, ノート情報をコンソール出力
			int octave      = this.parent.getOctave();
			int measure     = this.parent.getMeasure();
			int beat        = this.parent.getBeat();
			String interval = this.parent.getInterval();
			System.out.println(measure + ", " + beat + ", " + interval + octave);
		}
	}
}
