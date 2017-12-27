package gui.component.pianoroll.note;
import gui.GuiConstants;
import gui.component.base.RectangleBase;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import midi.MidiConstants;
import midi.MidiUtil;
import system.AppConstants;

/**
 * ノートのGUIのクラス
 * @author Shun Yamashita
 */
public class NoteView extends RectangleBase {
	private boolean isPronounceable; // ノートが置かれた際に発音するかどうかのフラグ
	private OperationMode operationMode;
	private Note owner;
	private enum OperationMode {
		LEFT_RESIZE,
		RIGHT_RESIZE,
		MOVE
	}
	private int pressRelativeX; // 左クリックされた位置の相対x座標
	private int prevRelativeX; // 直前の相対x座標
	private int prevMove; // 直前の移動量

	public NoteView(int x, int y, int width, int height, boolean isPronounceable, Note owner) {
		super();
		this.isPronounceable = isPronounceable;
		this.owner = owner;
		setPoint(x + 0.5, y + 0.5);
		setSize(width, height);
		setupColor();
		updateView();
		setupEventListener();
		// 発音
		if(isPronounceable) {
			MidiUtil.toneOn(owner.getModel().getTrack(), owner.getModel().getProgram(), owner.getModel().getPitch(), owner.getModel().getVelocity());
		}
	}

	public void setupColor() {
		int track = owner.getModel().getTrack();
		Stop[] stops = new Stop[] { new Stop(0.0, GuiConstants.Note.DARK_COLORS[track - 1]), new Stop(1.0, GuiConstants.Note.LIGHT_COLORS[track - 1]) };
		LinearGradient grad = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
		setFill(grad);
		setStyle(GuiConstants.Note.STYLE);
	}

	public void setupEventListener() {
		setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				NoteView.this.move(e);
			}
		});
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				NoteView.this.press(e);
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				NoteView.this.release(e);
			}
		});
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				NoteView.this.drag(e);
			}
		});
	}

	public void move(MouseEvent e) {
		int x = (int)(e.getX() - getX());
		if(0 <= x && x < 3) {
			operationMode = OperationMode.LEFT_RESIZE;
		} else if(getWidth() - 3 <= x && x <= getWidth()) {
			operationMode = OperationMode.RIGHT_RESIZE;
		} else {
			operationMode = OperationMode.MOVE;
		}
		updateCursor();
	}

	public void press(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // 左クリック
			pressRelativeX = (int)(e.getX() - getX() + 0.5f);
			// 発音
			if(isPronounceable) {
				MidiUtil.toneOn(owner.getModel().getTrack(), owner.getModel().getProgram(), owner.getModel().getPitch(), owner.getModel().getVelocity());
			}
		} else { // 右クリック
			if(owner.getModel().getTrack() == AppConstants.MelodySettings.MELODY_TRACK) {
				owner.removeNoteFromPianoroll();
				owner.removeNoteFromSequencer();
			}
		}
	}

	public void release(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // 左クリック
			// 無発音
			MidiUtil.toneOff(owner.getModel().getTrack());
		} else {
		}
	}

	public void drag(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // 左クリック
			// 水平方向のドラッグ
			horizontalDrug(e);
			int pitch = (int)((AppConstants.Settings.MAX_OCTAVE + 2) * 12 - ((e.getY() - 0.5) / getHeight()) - 1) + 1;
			if(AppConstants.Settings.AVAILABLE_PITCHES.contains(pitch)) {
				// 垂直方向のドラッグ
				verticalDrug(e);
			}
			// ドラッグ操作後のノート情報の更新
			updateModel();
			// ノート追加
			owner.addNoteToPianoroll();
			owner.addNoteToSequencer();
		}
	}

	/**
	 * 水平方向のドラッグ操作によるノートの伸縮と移動を行う
	 * @param e マウスイベントオブジェクト
	 */
	public void horizontalDrug(MouseEvent e) {
		int resolution = owner.getResolution();
		int resolutionWidth = GuiConstants.Pianoroll.MEASURE_WIDTH / resolution;

		int relativeX; // ノートにおける相対的なx座標値
		switch(operationMode) {
		case RIGHT_RESIZE:
			if(GuiConstants.Note.MAX_X < e.getX()) {
				break;
			}
			relativeX = (int)(e.getX() - getX() + 0.5f); // ノートの左端座標からの相対的なx座標値
			if(relativeX < 0) {
				break;
			}
			if(prevRelativeX < relativeX) { // 右方向へのドラッグ (ノートが伸びる)
				int oldWidth = (int)getWidth();
				int newWidth = resolutionWidth * (Math.abs(relativeX) / resolutionWidth);
				if(newWidth > oldWidth) {
					setWidth(newWidth);
					if(!owner.canExtendAndMove(this)) {
						setWidth(oldWidth);
					}
				}
			}
			if(prevRelativeX > relativeX) { // 左方向へのドラッグ (ノートが縮む)
				int oldWidth = (int)getWidth();
				int newWidth = resolutionWidth * ((Math.abs(relativeX) / resolutionWidth) + 1);
				if(newWidth < oldWidth) {
					setWidth(newWidth);
				}
			}
			prevRelativeX = relativeX;
			break;
		case LEFT_RESIZE:
			if(e.getX() < GuiConstants.Note.MIN_X) {
				break;
			}
			relativeX = (int)(e.getX() - (getX() + getWidth()) - 0.5f); // ノートの右端座標からの相対的なx座標値
			if(relativeX > 0) {
				break;
			}
			if(prevRelativeX < relativeX) { // 右方向へのドラッグ (ノートが縮む)
				int newWidth = resolutionWidth * ((Math.abs(relativeX) / resolutionWidth) + 1);
				int oldWidth = (int)getWidth();
				if(newWidth < oldWidth) {
					float newX = (float)(getX() + getWidth() - newWidth);
					setX(newX);
					setWidth(newWidth);
				}
			}
			if(prevRelativeX > relativeX) { // 左方向へのドラッグ (ノートが伸びる)
				int newWidth = resolutionWidth * (Math.abs(relativeX) / resolutionWidth);
				int oldWidth = (int)getWidth();
				if(newWidth > oldWidth) {
					float newX = (float)(getX() + getWidth() - newWidth);
					float oldX = (float)getX();
					setX(newX);
					setWidth(newWidth);
					if(!owner.canExtendAndMove(this)) {
						setX(oldX);
						setWidth(oldWidth);
					}
				}
			}
			prevRelativeX = relativeX;
			break;
		case MOVE:
			if(e.getX() < GuiConstants.Note.MIN_X || GuiConstants.Note.MAX_X < e.getX()) {
				break;
			}
			relativeX = (int)(e.getX() - getX() + 0.5f - pressRelativeX); // クリックした箇所からの相対的なx座標値
			int move = resolutionWidth * (relativeX / resolutionWidth);
			if(prevMove != move) {
				float newX = (float)(getX() + move);
				float oldX = (float)getX();
				if(newX < GuiConstants.Note.MIN_X || GuiConstants.Note.MAX_X < newX + getWidth()) {
					break;
				}
				setX(newX);
				if(!owner.canExtendAndMove(this)) {
					setX(oldX);
				}
			}
			prevMove = move;
			break;
		}
	}

	/**
	 * 垂直方向のドラッグ操作によるノートの移動を行う
	 * @param e マウスイベントオブジェクト
	 */
	public void verticalDrug(MouseEvent e) {
		if(operationMode == OperationMode.MOVE) {
			for(int n = 0; n < 12 * AppConstants.Settings.OCTAVES; n++) {
				float min = GuiConstants.Note.MIN_Y + 12 * n;
				float max = min + 12;
				if(min <= e.getY() && e.getY() < max) {
					if(GuiConstants.Note.MIN_Y <= e.getY() && e.getY() <= GuiConstants.Note.MAX_Y) {
						setY(min);
					}
				}
			}
		}
	}

	/**
	 * ノートが移動・伸縮された時にノート情報を更新する
	 */
	public void updateModel() {
		int x = (int)(getX() - 0.5);
		int y = (int)(getY() - 0.5);
		int currentPitch = owner.getModel().getPitch();
		int newPitch = (int)((AppConstants.Settings.MAX_OCTAVE + 2) * 12 - (y / getHeight()) - 1);
		int position = (MidiConstants.PPQ / 4) * (x / 10);
		int duration = (int)((getWidth() / GuiConstants.Pianoroll.BEAT_WIDTH) * MidiConstants.PPQ);
		owner.updateNote(newPitch, position, duration);
		// 音高が変化したら再発音
		if(newPitch != currentPitch) {
			// 無発音
			MidiUtil.toneOff(owner.getModel().getTrack());
			// 発音
			if(isPronounceable) {
				MidiUtil.toneOn(owner.getModel().getTrack(), owner.getModel().getProgram(), newPitch, owner.getModel().getVelocity());
			}
		}
	}

	public void updateView() {
		int track = owner.getModel().getTrack();
		if(track == AppConstants.MelodySettings.MELODY_TRACK) {
			// ノートを不透明にして前面に描画する
			toFront();
			setOpacity(1.0);
			setDisable(false);
		} else {
			// ノートを半透明にして描画する
			setOpacity(0.5);
			setDisable(true);
		}
	}

	private void updateCursor() {
		switch(operationMode) {
		case LEFT_RESIZE:
			setCursor(Cursor.W_RESIZE);
			break;
		case MOVE:
			setCursor(Cursor.MOVE);
			break;
		case RIGHT_RESIZE:
			setCursor(Cursor.E_RESIZE);
			break;
		}
	}

	public void setIsPronounceable(boolean isPronounceable) {
		this.isPronounceable = isPronounceable;
	}
}
