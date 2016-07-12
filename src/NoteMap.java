import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class NoteMap extends Rectangle {
	public NoteMap(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.setStyle(
			"-fx-fill: #FFFFFF;"
			+ "-fx-stroke: #444444;"
			+ "-fx-stroke-type: outside;"
			+ "-fx-arc-width: 0;"
			+ "-fx-arc-height: 0;"
		);
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println(e.getSceneX() + " " + e.getSceneY());
			}
		});
	}
}
