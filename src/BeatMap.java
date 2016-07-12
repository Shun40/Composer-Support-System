import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class BeatMap extends Group {
	public static final int BEAT_MAP_WIDTH = 40;
	public static final int BEAT_MAP_HEIGHT = 256;

	private int number;
	private NoteMap[] noteMap;
	private Rectangle region;

	public BeatMap(int number, int x, int y) {
		super();
		this.number = number;
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.createNoteMap();
		this.createRegion();
	}

	public void createNoteMap() {
		int[] y = {0, 32, 56, 72, 88, 104, 120, 136, 160, 184, 200, 216, 232};
		this.noteMap = new NoteMap[13];
		for(int n = 0; n < 13; n++) {
			if(n == 0) this.noteMap[n] = new NoteMap(0, y[n], 40, 32);
			else if(n == 1 || n == 7 || n == 8 || n == 12) this.noteMap[n] = new NoteMap(0, y[n], 40, 24);
			else this.noteMap[n] = new NoteMap(0, y[n], 40, 16);
			this.getChildren().add(this.noteMap[n]);
		}
	}

	public void createRegion() {
		this.region = new Rectangle(0, 0, BEAT_MAP_WIDTH, BEAT_MAP_HEIGHT);
		this.region.setStyle(
			"-fx-fill: null;"
			+ "-fx-stroke: #AAAAAA;"
			+ "-fx-stroke-type: outside;"
			+ "-fx-arc-width: 0;"
			+ "-fx-arc-height: 0;"
		);
		this.getChildren().add(this.region);
	}

	public int getNumber() { return this.number; }
}
