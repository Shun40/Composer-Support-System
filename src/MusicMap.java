import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class MusicMap extends Group {
	public static final int MUSIC_MAP_WIDTH = 640;
	public static final int MUSIC_MAP_HEIGHT = 256;

	private MeasureMap[] measureMap;
	private Rectangle region;

	public MusicMap(int x, int y) {
		super();
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.createMeasureMap();
		this.createRegion();
	}

	public void createMeasureMap() {
		int[] x = {0, 160, 320, 480};
		this.measureMap = new MeasureMap[4];
		for(int n = 0; n < 4; n++) {
			this.measureMap[n] = new MeasureMap(n + 1, x[n], 0);
			this.getChildren().add(this.measureMap[n]);
		}
	}

	public void createRegion() {
		this.region = new Rectangle(0, 0, MUSIC_MAP_WIDTH, MUSIC_MAP_HEIGHT);
		this.region.setStyle(
			"-fx-fill: null;"
			+ "-fx-stroke: #000000;"
			+ "-fx-stroke-type: outside;"
			+ "-fx-arc-width: 5;"
			+ "-fx-arc-height: 5;"
		);
		this.getChildren().add(this.region);
	}
}
