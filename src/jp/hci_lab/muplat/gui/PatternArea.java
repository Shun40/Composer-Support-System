package gui;
import java.util.ArrayList;

import engine_yamashita.ArrangePattern;
import javafx.scene.Group;

/**
 * パターン予測変換の編集領域のクラス
 * @author Shun Yamashita
 */
public class PatternArea extends Group {
	private PatternSelector patternSelector;
	private PredictionButton predictionButton;
	private Pianoroll parent;

	public PatternArea(int x, int y, Pianoroll parent) {
		super();
		this.parent = parent;
		setupPoint(x, y);
		setupPatternSelector();
		setupPredictionButton();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupPatternSelector() {
		patternSelector = new PatternSelector(0, 32, this);
		getChildren().add(patternSelector);
	}

	public void setupPredictionButton() {
		predictionButton = new PredictionButton(0, 140, this);
		getChildren().add(predictionButton);
	}

	public void prediction() {
		patternSelector.getItems().clear(); // 一度リストの中身を空にする
		ArrayList<ArrangePattern> patterns = parent.prediction();
		for(ArrangePattern pattern : patterns) {
			patternSelector.addList(pattern);
		}
	}

	public void arrange(ArrangePattern pattern) {
		int targetMeasure = parent.getArrangeTargetMeasure();
		parent.removeNoteInMeasure(targetMeasure, 1); // メロディトラックのノートを消去
		//parent.removeNoteInMeasure(targetMeasure, 6); // ディストーションギタートラックのノートを消去
		//parent.removeNoteInMeasure(targetMeasure, 9); // ベーストラックのノートを消去
		//parent.removeNoteInMeasure(targetMeasure, 10); // ドラムストラックのノートを消去
		ArrayList<NoteInformation> melodyNotes = pattern.getMelodyPattern();
		//ArrayList<NoteInformation> distGuitarPatternNotes = pattern.getDistGuitarPattern().getDistGuitarPatternNotes();
		//ArrayList<NoteInformation> bassPatternNotes = pattern.getBassPattern().getBassPatternNotes();
		//ArrayList<NoteInformation> drumPatternNotes = pattern.getDrumPattern().getDrumPatternNotes();
		for(NoteInformation noteInformation : melodyNotes) {
			int track    = noteInformation.getTrack();
			int note     = noteInformation.getNote();
			int position = noteInformation.getPosition() * 2 + (960 * 4) * (targetMeasure - 1);
			int duration = noteInformation.getDuration() * 2;
			parent.setCurrentTrack(track);
			parent.putNote(note, position, duration);
		}
		/*
		for(NoteInformation noteInformation : distGuitarPatternNotes) {
			int track    = noteInformation.getTrack();
			int note     = noteInformation.getNote();
			int position = noteInformation.getPosition() + (960 * 4) * (targetMeasure - 1);
			int duration = noteInformation.getDuration();
			parent.setCurrentTrack(track);
			parent.putNote(note, position, duration);
		}
		for(NoteInformation noteInformation : bassPatternNotes) {
			int track    = noteInformation.getTrack();
			int note     = noteInformation.getNote();
			int position = noteInformation.getPosition() + (960 * 4) * (targetMeasure - 1);
			int duration = noteInformation.getDuration();
			parent.setCurrentTrack(track);
			parent.putNote(note, position, duration);
		}
		for(NoteInformation noteInformation : drumPatternNotes) {
			int track    = noteInformation.getTrack();
			int note     = noteInformation.getNote();
			int position = noteInformation.getPosition() + (960 * 4) * (targetMeasure - 1);
			int duration = noteInformation.getDuration();
			parent.setCurrentTrack(track);
			parent.putNote(note, position, duration);
		}
		*/
		parent.setCurrentTrack(1);
	}
}
