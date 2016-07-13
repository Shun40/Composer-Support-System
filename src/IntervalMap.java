/*
 * 音程のマップを表すクラス.
 */

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class IntervalMap extends Group {
	public static final String DEFAULT_STYLE    = "-fx-stroke: #444444;-fx-stroke-type: outside;-fx-arc-width: 0;-fx-arc-height: 0;";
	public static final int INTERVAL_MAP_WIDTH  = 40;
	public static final int INTERVAL_MAP_HEIGHT = 12;

	private BeatMap parent;  // 自身が含まれている拍マップオブジェクトを親として持つ
	private String interval; // 自身の音程
	private Rectangle region;
	private Note note;

	public IntervalMap(BeatMap parent, String interval, String color, int x, int y) {
		super();
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.createRegion();
		this.fillColor(color);
		this.parent   = parent;
		this.interval = interval;
		this.note     = null;
	}

	public void createRegion() {
		this.region = new Rectangle(0, 0, INTERVAL_MAP_WIDTH, INTERVAL_MAP_HEIGHT);
		this.region.setStyle(DEFAULT_STYLE);

		// 音程マップへクリックイベント処理を追加
		this.region.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				IntervalMap.this.click(e);
			}
		});

		this.getChildren().add(this.region);
	}

	public void fillColor(String color) {
		this.region.setStyle("-fx-fill: " + color + ";" + DEFAULT_STYLE);
	}

	public void click(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // 左クリックされた時, マップ上にノートを配置
			if(this.note == null) this.putNote();
		} else {
		}
	}

	public void putNote() {
		this.note = new Note(this, 0, 0, INTERVAL_MAP_WIDTH, INTERVAL_MAP_HEIGHT);
		this.getChildren().add(this.note);
	}

	public void removeNote() {
		this.getChildren().remove(this.note);
		this.note = null;
	}

	public int getOctave()      { return this.parent.getOctave(); }
	public int getMeasure()     { return this.parent.getMeasure(); }
	public int getBeat()        { return this.parent.getBeat(); }
	public String getInterval() { return this.interval; }
}
