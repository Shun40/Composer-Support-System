package gui.component.pianoroll.note;
import gui.GuiConstants;
import gui.component.base.RectangleBase;
import javafx.event.EventHandler;
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
	private PressedPosition pressedPosition;
	private Note owner;
	private enum PressedPosition {
		LEFT,
		CENTER,
		RIGHT
	}

	public NoteView(int x, int y, int width, int height, boolean isPronounceable, Note owner) {
		super();
		this.isPronounceable = isPronounceable;
		this.owner = owner;
		setPoint(x + 0.5, y + 0.5);
		setSize(width, height);
		setupColor();
		setupEventListener();
		// 発音
		if(isPronounceable) {
			MidiUtil.toneOn(owner.getModel().getTrack(), owner.getModel().getProgram(), owner.getModel().getPitch(), owner.getModel().getVelocity());
		}
	}

	public void setupColor() {
		int track = owner.getCurrentTrack();
		Stop[] stops = new Stop[] { new Stop(0.0, GuiConstants.Note.DARK_COLORS[track - 1]), new Stop(1.0, GuiConstants.Note.LIGHT_COLORS[track - 1]) };
		LinearGradient grad = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
		setFill(grad);
		setStyle(GuiConstants.Note.STYLE);
	}

	public void setupEventListener() {
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

	public void press(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) { // 左クリック
			int pressX = (int)(e.getX() - getX());
			if(0 <= pressX && pressX < getWidth() / 3) {
				pressedPosition = PressedPosition.LEFT;
			}
			if(getWidth() / 3 <= pressX && pressX < (getWidth() / 3) * 2) {
				pressedPosition = PressedPosition.CENTER;
			}
			if((getWidth() / 3) * 2 <= pressX && pressX < getWidth()) {
				pressedPosition = PressedPosition.RIGHT;
			}
			// 発音
			if(isPronounceable) {
				MidiUtil.toneOn(owner.getModel().getTrack(), owner.getModel().getProgram(), owner.getModel().getPitch(), owner.getModel().getVelocity());
			}
		} else { // 右クリック
			owner.removeNoteFromPianoroll();
			owner.removeNoteFromSequencer();
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
			int pitch = (int)((AppConstants.Settings.MAX_OCTAVE + 2) * 12 - ((e.getY() - 0.5) / getHeight()) - 1) + 1;
			if(AppConstants.Settings.AVAILABLE_PITCHES.contains(pitch)) {
				// 垂直方向のドラッグ
				verticalDrug(e);
				// 水平方向のドラッグ
				horizontalDrug(e);
				// ドラッグ操作後のノート情報の更新
				updateModel();
				// ノート追加
				owner.addNoteToPianoroll();
				owner.addNoteToSequencer();
			}
		}
	}

	/**
	 * 垂直方向のドラッグ操作によるノートの移動を行う
	 * @param e マウスイベントオブジェクト
	 */
	public void verticalDrug(MouseEvent e) {
		for(int n = 0; n < 12 * AppConstants.Settings.OCTAVES; n++) {
			float min = GuiConstants.Note.MIN_Y + 12 * n;
			float max = min + 12;
			if(min <= e.getY() && e.getY() < max) {
				if(GuiConstants.Note.MIN_Y <= e.getY() && e.getY() < GuiConstants.Note.MAX_Y) {
					setY(min);
				}
			}
		}
	}

	/**
	 * 水平方向のドラッグ操作によるノートの伸縮を行う
	 * @param e マウスイベントオブジェクト
	 */
	public void horizontalDrug(MouseEvent e) {
		int resolution = owner.getResolution();
		int moveX = (int)(e.getX() - getX());
		int resolutionWidth = GuiConstants.Pianoroll.MEASURE_WIDTH / resolution;
		// 右方向へのドラッグ
		if(pressedPosition == PressedPosition.RIGHT && 0 <= Math.abs(moveX)) {
			for(int n = 0; n < resolution; n++) {
				int min = resolutionWidth * n;
				int max = min + resolutionWidth;
				if(min <= moveX && moveX < max) {
					if(e.getX() < GuiConstants.Note.MAX_X) {
						setWidth(resolutionWidth * (n + 1));
					}
				}
			}
		}
		// 左方向へのドラッグ
		else if(pressedPosition == PressedPosition.LEFT && 0 <= Math.abs(moveX)) {
			/*
			for(int n = 0; n < resolution; n++) {
				int min = -resolutionWidth * n;
				int max = min + resolutionWidth;
				if(min <= moveX && moveX < max) {
					if(GuiConstants.Note.MIN_X <= e.getX()) {
						setWidth(resolutionWidth * (n + 1));
					}
				}
			}
			*/
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

	public void updateView(int currentTrack) {
		int track = owner.getModel().getTrack();
		if(track == currentTrack) {
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

	public void setIsPronounceable(boolean isPronounceable) {
		this.isPronounceable = isPronounceable;
	}
}
