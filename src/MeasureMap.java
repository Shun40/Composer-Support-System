import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class MeasureMap extends Group {
	public static final int MEASURE_MAP_WIDTH = 160;
	public static final int MEASURE_MAP_HEIGHT = 256;

	private int number;
	private BeatMap[] beatMap;
	private Rectangle region;

	public MeasureMap(int number, int x, int y) {
		super();
		this.number = number;
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.createBeatMap();
		this.createRegion();
	}

	public void createBeatMap() {
		int[] x = {0, 40, 80, 120};
		this.beatMap = new BeatMap[4];
		for(int n = 0; n < 4; n++) {
			this.beatMap[n] = new BeatMap(n + 1, x[n], 0);
			this.getChildren().add(this.beatMap[n]);
		}
	}

	public void createRegion() {
		this.region = new Rectangle(0, 0, MEASURE_MAP_WIDTH, MEASURE_MAP_HEIGHT);
		this.region.setStyle(
			"-fx-fill: null;"
			+ "-fx-stroke: #00AA00;"
			+ "-fx-stroke-type: outside;"
			+ "-fx-arc-width: 0;"
			+ "-fx-arc-height: 0;"
		);
		this.getChildren().add(this.region);
	}

	public int getNumber() { return this.number; }
}
