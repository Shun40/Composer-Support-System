/*
 * 拍のマップを表すクラス.
 */

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class BeatMap extends Group {
	public static final String DEFAULT_STYLE = "-fx-fill: null;-fx-stroke: #888888;-fx-stroke-type: outside;-fx-arc-width: 0;-fx-arc-height: 0;";
	public static final int BEAT_MAP_WIDTH   = 40;
	public static final int BEAT_MAP_HEIGHT  = 144;
	public static final int INTERVAL_NUM     = 12;
	public static final int[] INTERVAL_Y     = {0, 12, 24, 36, 48, 60, 72, 84, 96, 108, 120, 132};
	public static final String[] INTERVAL    = {"B", "A#", "A", "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C"};

	private MeasureMap parent; // 自身が含まれている小節マップオブジェクトを親として持つ
	private int beat;          // 自身の拍番号
	private IntervalMap[] intervalMap;
	private Rectangle region;

	public BeatMap(MeasureMap parent, int beat, int x, int y) {
		super();
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.createIntervalMap();
		this.createRegion();
		this.parent = parent;
		this.beat   = beat;
	}

	public void createIntervalMap() {
		this.intervalMap = new IntervalMap[INTERVAL_NUM];
		for(int n = 0; n < INTERVAL_NUM; n++) {
			if(INTERVAL[n].contains("#") || INTERVAL[n].contains("♭")) { // 黒鍵の音程
				this.intervalMap[n] = new IntervalMap(this, INTERVAL[n], "#DDDDDD", 0, INTERVAL_Y[n]);
			} else { // 白鍵の音程
				this.intervalMap[n] = new IntervalMap(this, INTERVAL[n], "#FFFFFF", 0, INTERVAL_Y[n]);
			}
			this.getChildren().add(this.intervalMap[n]);
		}
	}

	public void createRegion() {
		this.region = new Rectangle(0, 0, BEAT_MAP_WIDTH, BEAT_MAP_HEIGHT);
		this.region.setStyle(DEFAULT_STYLE);
		this.getChildren().add(this.region);
	}

	public int getOctave()  { return this.parent.getOctave(); }
	public int getMeasure() { return this.parent.getMeasure(); }
	public int getBeat()    { return this.beat; }
}
