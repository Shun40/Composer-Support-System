import static constants.NoteGridConstants.*;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * ノートが配置される格子のクラス
 * @author Shun Yamashita
 */
public class NoteGrid extends Group {
	private boolean isWhite;
	private int resolution;
	private Rectangle frame;
	private Pianoroll parent;

	public NoteGrid(boolean isWhite, int resolution, int x, int y, int width, int height, Pianoroll parent) {
		super();
		this.isWhite = isWhite;
		this.resolution = resolution;
		this.parent = parent;
		setupFrame(x, y, width, height);
		setupEventListener();
	}

	public void setupFrame(int x, int y, int width, int height) {
		frame = new Rectangle(x + 0.5, y + 0.5, width, height);
		frame.setStyle(DEFAULT_STYLE);
		if(isWhite) {
			frame.setStyle("-fx-fill: " + WHITE_GRID_COLOR + ";" + DEFAULT_STYLE);
		} else {
			frame.setStyle("-fx-fill: " + BLACK_GRID_COLOR + ";" + DEFAULT_STYLE);
		}
		getChildren().add(frame);
	}

	public void setupEventListener() {
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				NoteGrid.this.press(e);
			}
		});
	}

	public void press(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // Left click
			this.putNote(e);
		} else {
		}
	}

	public void putNote(MouseEvent e) {
		int putX = 0;
		int putY = (int)frame.getY();
		int width = 0;
		int height = 12;
		int clickX = (int)(e.getX() - frame.getX());
		if(resolution == 4) {
			width = 40;
			if(0 <= clickX && clickX < 40) putX = (int)frame.getX();
		}
		if(resolution == 8) {
			width = 20;
			if(0 <= clickX && clickX < 20) putX = (int)frame.getX();
			else if(20 <= clickX && clickX < 40) putX = (int)frame.getX() + 20;
		}
		if(resolution == 16) {
			width = 10;
			if(0 <= clickX && clickX < 10) putX = (int)frame.getX();
			else if(10 <= clickX && clickX < 20) putX = (int)frame.getX() + 10;
			else if(20 <= clickX && clickX < 30) putX = (int)frame.getX() + 20;
			else if(30 <= clickX && clickX < 40) putX = (int)frame.getX() + 30;
		}
		parent.putNote(putX, putY, width, height);
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}
}
