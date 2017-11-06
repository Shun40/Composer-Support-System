package gui.component.pianoroll;
import java.util.ArrayList;
import java.util.List;

import gui.GuiConstants;
import gui.GuiManager;
import gui.component.base.GroupBase;
import gui.component.pianoroll.note.Note;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;
import midi.MidiConstants;
import midi.MidiUtil;
import system.AppConstants;

/**
 * ピアノロール
 * @author Shun Yamashita
 */

public class Pianoroll extends GroupBase {
	private List<Note> notes;
	private Sheet sheet;
	private Line playLine;
	private Timeline playTimeline;
	private GuiManager owner;

	public Pianoroll(GuiManager owner) {
		super();
		this.notes = new ArrayList<Note>();
		this.owner = owner;
		setPoint(GuiConstants.Pianoroll.X, GuiConstants.Pianoroll.Y);
		setupClip();
		setupSheet();
		setupPlayLine();
	}

	/**
	 * 実際に表示する領域のサイズを設定する
	 */
	private void setupClip() {
		double width = GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.SHOW_MEASURES + 0.5;
		double height = GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.SHOW_OCTAVES + 0.5;
		setClip(new Rectangle(width, height));
	}

	private void setupSheet() {
		sheet = new Sheet(this);
		getChildren().add(sheet);
	}

	public void setupPlayLine() {
		int startY = 0;
		int endY = startY + GuiConstants.Pianoroll.MEASURE_HEIGHT * AppConstants.Settings.OCTAVES;
		playLine = new Line(0 + 0.5, startY, 0 + 0.5, endY);
		playLine.setStrokeLineCap(StrokeLineCap.BUTT);
		playLine.setStroke(Color.web("#FF0000"));
		playLine.toFront();
		getChildren().add(playLine);
	}

	public void updateResolution(int resolution) {
		sheet.updateResolution(resolution);
	}

	public void translateX(int move) {
		for(Note note : notes) {
			note.getView().setTranslateX(move);
		}
		sheet.setTranslateX(move);
		playLine.setTranslateX(move);
	}

	public void translateY(int move) {
		for(Note note : notes) {
			note.getView().setTranslateY(move);
		}
		sheet.setTranslateY(move);
		playLine.setTranslateY(move);
	}

	public void addNoteToPianoroll(Note note) {
		if(!notes.contains(note)) {
			notes.add(note);
			getChildren().add(note.getView());
		}
	}

	public void addNoteToSequencer(Note note) {
		owner.addNoteToSequencer(note);
	}

	public void removeNoteFromPianoroll(Note note) {
		if(notes.contains(note)) {
			notes.remove(note);
			getChildren().remove(note.getView());
		}
	}

	public void removeNoteFromSequencer(Note note) {
		owner.removeNoteFromSequencer(note);
	}

	public void clearNoteFromPianoroll() {
		for(Note note : notes) {
			getChildren().remove(note.getView());
		}
		notes.clear();
	}

	public void clearNoteFromSequencer() {
		owner.clearNoteFromSequencer();
	}

	public void putNote(int pitch, int x, int y, int width, int height, boolean isPronounceable) {
		// ノート生成
		int track = getCurrentTrack();
		int program = MidiConstants.PROGRAM_NUMBERS[track - 1];
		int position = (x / (GuiConstants.Pianoroll.BEAT_WIDTH / 4)) * (MidiConstants.PPQ / 4);
		int duration = (width / (GuiConstants.Pianoroll.BEAT_WIDTH / 4)) * (MidiConstants.PPQ / 4);
		int velocity = 100;
		Note note = new Note(track, program, pitch, position, duration, velocity, x, y, width, height, isPronounceable, this);
		// ノート平行移動
		double hIncPerUnit = -(GuiConstants.Pianoroll.MEASURE_WIDTH * (AppConstants.Settings.MEASURES - AppConstants.Settings.SHOW_MEASURES)) / 100.0;
		double vIncPerUnit = -(GuiConstants.Pianoroll.MEASURE_HEIGHT * (AppConstants.Settings.OCTAVES - AppConstants.Settings.SHOW_OCTAVES)) / 100.0;
		double hScrollBarVal = owner.getHScrollBarValue();
		double vScrollBarVal = owner.getVScrollBarValue();
		int hMove = (int)(hIncPerUnit * hScrollBarVal);
		int vMove = (int)(vIncPerUnit * vScrollBarVal);
		note.getView().setTranslateX(hMove);
		note.getView().setTranslateY(vMove);
		// ノートの発音フラグをリセット
		note.getView().setIsPronounceable(true);
		// ノートをGUIとシーケンサへ追加
		addNoteToPianoroll(note);
		addNoteToSequencer(note);
		System.out.println(pitch + ", " + position + ", " + ", " + duration + ", " + x + ", " + y);
	}

	// ファイルからノートを読み込んで置く際に呼ばれる
	public void putNote(int pitch, int position, int duration) {
		int x = (GuiConstants.Pianoroll.BEAT_WIDTH / 4) * (position / (MidiConstants.PPQ / 4));
		int y = GuiConstants.Grid.HEIGHT * (((AppConstants.Settings.MAX_OCTAVE + 2) * 12 - 1) - pitch);
		int width = (GuiConstants.Pianoroll.BEAT_WIDTH / 4) * (duration / (MidiConstants.PPQ / 4));
		int height = GuiConstants.Grid.HEIGHT;
		putNote(pitch, x, y, width, height, false);
	}

	public void removeNoteInMeasure(int targetMeasure, int targetTrack) {
		List<Note> removeNotes = new ArrayList<Note>();
		for(Note note : notes) {
			int measure = MidiUtil.getMeasure(note.getModel().getPosition());
			int track = note.getModel().getTrack();
			if(measure == targetMeasure && track == targetTrack) {
				removeNotes.add(note);
			}
		}
		for(Note removeNote : removeNotes) {
			removeNoteFromSequencer(removeNote);
			removeNoteFromPianoroll(removeNote);
		}
	}

	public void removeNoteIn2Beat(int targetMeasure, int targetBeat1, int targetBeat2, int targetTrack) {
		List<Note> removeNotes = new ArrayList<Note>();
		for(Note note : notes) {
			int measure = MidiUtil.getMeasure(note.getModel().getPosition());
			int track = note.getModel().getTrack();
			if(measure == targetMeasure && track == targetTrack) {
				int beat = MidiUtil.getBeat(note.getModel().getPosition());
				if(beat == targetBeat1){
					removeNotes.add(note);
				}
				if(beat == targetBeat2) {
					removeNotes.add(note);
				}
			}
		}
		for(Note removeNote : removeNotes) {
			removeNoteFromSequencer(removeNote);
			removeNoteFromPianoroll(removeNote);
		}
	}

	public void changeCurrentTrack(int currentTrack) {
		// トラック番号に応じてノートの見た目を変更する
		for(Note note : notes) {
			note.getView().updateView(currentTrack);
		}
	}

	public void setupBeforePlay() {
		for(Note note : notes) {
			note.getView().setDisable(true);
		}
		sheet.setDisable(true);
		playLine.setLayoutX(0);
	}

	public void setupAfterPlay() {
		for(Note note : notes) {
			note.getView().setDisable(false);
		}
		sheet.setDisable(false);
		playLine.setLayoutX(0);
	}

	public void playAnimation(int bpm) {
		float secPerBeat = 60.f / bpm;
		float secPerMeasure = secPerBeat * 4;
		float playTimeSec = secPerMeasure * AppConstants.Settings.MEASURES;
		playTimeline = new Timeline();
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.stop();
			}
		};
		KeyValue kvStart = new KeyValue(playLine.layoutXProperty(), 0);
		KeyFrame kfStart = new KeyFrame(Duration.ZERO, kvStart);
		KeyValue kvEnd = new KeyValue(playLine.layoutXProperty(), GuiConstants.Pianoroll.MEASURE_WIDTH * AppConstants.Settings.MEASURES);
		KeyFrame kfEnd = new KeyFrame(Duration.millis(playTimeSec * 1000), onFinished, kvEnd);
		KeyValue[] kvMeasureChange = new KeyValue[AppConstants.Settings.MEASURES / AppConstants.Settings.SHOW_MEASURES - 1];
		KeyFrame[] kfMeasureChange = new KeyFrame[AppConstants.Settings.MEASURES / AppConstants.Settings.SHOW_MEASURES - 1];
		for(int n = 0; n < kfMeasureChange.length; n++) {
			kvMeasureChange[n] = new KeyValue(owner.getHScrollBarValueProperty(), (100.0 / (AppConstants.Settings.MEASURES - AppConstants.Settings.SHOW_MEASURES)) * 4 * (n + 1), Interpolator.DISCRETE);
			kfMeasureChange[n] = new KeyFrame(Duration.millis(secPerMeasure * 4 * (n + 1) * 1000), kvMeasureChange[n]);
			playTimeline.getKeyFrames().add(kfMeasureChange[n]);
		}
		playTimeline.getKeyFrames().add(kfStart);
		playTimeline.getKeyFrames().add(kfEnd);
		playTimeline.setCycleCount(1);
		playTimeline.setAutoReverse( true );
		playTimeline.play();
	}

	public void stop() {
		playTimeline.stop();
	}

	public int getCurrentTrack() {
		return owner.getCurrentTrack();
	}

	public int getResolution() {
		return owner.getResolution();
	}

	public List<Note> getNotes() {
		return notes;
	}
}
