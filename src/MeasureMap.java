/*
 * 小節のマップを表すクラス.
 */

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class MeasureMap extends Group {
	public static final String DEFAULT_STYLE   = "-fx-fill: null;-fx-stroke: #00AA00;-fx-stroke-type: outside;-fx-arc-width: 0;-fx-arc-height: 0;";
	public static final int MEASURE_MAP_WIDTH  = 160;
	public static final int MEASURE_MAP_HEIGHT = 144;
	public static final int BEAT_NUM           = 4;
	public static final int[] BEAT_X           = {0, 40, 80, 120};

	private OctaveMap parent; // 自身が含まれている拍マップオブジェクトを親として持つ
	private int measure;      // 自身の小節番号
	private BeatMap[] beatMap;
	private Rectangle region;

	public MeasureMap(OctaveMap parent, int measure, int x, int y) {
		super();
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.createBeatMap();
		this.createRegion();
		this.parent  = parent;
		this.measure = measure;
	}

	public void createBeatMap() {
		this.beatMap = new BeatMap[BEAT_NUM];
		for(int n = 0; n < BEAT_NUM; n++) {
			this.beatMap[n] = new BeatMap(this, n + 1, BEAT_X[n], 0);
			this.getChildren().add(this.beatMap[n]);
		}
	}

	public void createRegion() {
		this.region = new Rectangle(0, 0, MEASURE_MAP_WIDTH, MEASURE_MAP_HEIGHT);
		this.region.setStyle(DEFAULT_STYLE);
		this.getChildren().add(this.region);
	}

	public int getOctave()  { return this.parent.getOctave(); }
	public int getMeasure() { return this.measure; }
}
