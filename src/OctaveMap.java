/*
 * オクターブのマップを表すクラス.
 * このクラスの配下に小節, 拍, 音程を表すクラスがある.
 */

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class OctaveMap extends Group {
	public static final String DEFAULT_STYLE = "-fx-fill: null;-fx-stroke: #000000;-fx-stroke-type: outside;-fx-arc-width: 0;-fx-arc-height: 0;";
	public static final int MUSIC_MAP_WIDTH  = 640;
	public static final int MUSIC_MAP_HEIGHT = 144;
	public static final int MEASURE_NUM      = 4;
	public static final int[] MEASURE_X      = {0, 160, 320, 480};

	private int octave; // 自身のオクターブ番号
	private MeasureMap[] measureMap;
	private Rectangle region;

	public OctaveMap(int octave, int x, int y) {
		super();
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.createMeasureMap();
		this.createRegion();
		this.octave = octave;
	}

	public void createMeasureMap() {
		this.measureMap = new MeasureMap[MEASURE_NUM];
		for(int n = 0; n < MEASURE_NUM; n++) {
			this.measureMap[n] = new MeasureMap(this, n + 1, MEASURE_X[n], 0);
			this.getChildren().add(this.measureMap[n]);
		}
	}

	public void createRegion() {
		this.region = new Rectangle(0, 0, MUSIC_MAP_WIDTH, MUSIC_MAP_HEIGHT);
		this.region.setStyle(DEFAULT_STYLE);
		this.getChildren().add(this.region);
	}

	public int getOctave() { return this.octave; }
}
