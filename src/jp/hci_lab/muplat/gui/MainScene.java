package gui;
import static gui.constants.UniversalConstants.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import engine_yamashita.Accompaniment;
import engine_yamashita.ArrangeInformation;
import engine_yamashita.ArrangePattern;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import system.UIController;

/**
 * コンポーネントを配置するシーンのクラス
 * @author Shun Yamashita
 */
public class MainScene extends Scene {
	private UIController uiController;
	private Pianoroll pianoroll;

	public MainScene(Group root, int width, int height) {
		super(root, width, height);
		this.uiController = new UIController();
		setupMenuBar();
		setupPianoroll();
	}

	public void setupMenuBar() {
		MenuBar menubar = new Menubar(this);
		((Group)getRoot()).getChildren().add(menubar);
	}

	public void setupPianoroll() {
		pianoroll = new Pianoroll(DEFAULT_MEASURE, DEFAULT_OCTAVE, DEFAULT_BPM, this);
		((Group)getRoot()).getChildren().add(pianoroll);
	}

	public void setBpm(int bpm) {
		uiController.setBpm(bpm);
	}

	public void addNoteToEngine(Note note) {
		uiController.addNoteToEngine(note);
	}

	public void removeNoteFromEngine(Note note) {
		uiController.removeNoteFromEngine(note);
	}

	public void clearNoteFromEngine() {
		uiController.clearNoteFromEngine();
	}

	public void setTrackMute(int track, boolean mute) {
		uiController.setTrackMute(track, mute);
	}

	public void setTrackSolo(int track, boolean solo) {
		uiController.setTrackSolo(track, solo);
	}

	public void play() {
		uiController.play();
	}

	public void stop() {
		uiController.stop();
	}

	public void close() {
		uiController.close();
	}

	public ArrayList<ArrangePattern> prediction(ArrangeInformation arrangeInformation) {
		return uiController.prediction(arrangeInformation);
	}

	public Accompaniment makeAccompaniment(String chord) {
		return uiController.makeAccompaniment(chord);
	}

	// 本当はエンジン側で読み込み処理をやるべきだが, とりあえずGUI側で簡易な読み込み処理を実装
	public void read() {
		final FileChooser fc = new FileChooser();
		fc.setTitle("Read");
		fc.getExtensionFilters().addAll(
			new ExtensionFilter("Muplat Files", "*.mup"),
			new ExtensionFilter("All Files", "+.+")
		);
		File readFile = fc.showOpenDialog(null);
		if(readFile != null) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(readFile));
				pianoroll.setBpm(Integer.parseInt(br.readLine()));
				pianoroll.clearNoteFromUi();
				pianoroll.clearNoteFromEngine();
				String note = br.readLine();
				while(note != null) {
					int track    = Integer.parseInt(note.split(":", -1)[0]);
					int pitch    = Integer.parseInt(note.split(":", -1)[1]);
					int position = Integer.parseInt(note.split(":", -1)[2]);
					int duration = Integer.parseInt(note.split(":", -1)[3]);
					pianoroll.setCurrentTrack(track);
					pianoroll.putNote(pitch, position, duration);

					note = br.readLine();
				}
				br.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}
	}

	// 本当はエンジン側で保存処理をやるべきだが, とりあえずGUI側で簡易な保存処理を実装
	public void save() {
		int bpm = pianoroll.getBpm();
		ArrayList<NoteBlock> noteBlocks = pianoroll.getNoteBlocks();

		final FileChooser fc = new FileChooser();
		fc.setTitle("Save");
		fc.setInitialFileName((new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())) + ".mup");
		fc.getExtensionFilters().addAll(
			new ExtensionFilter("Muplat Files", "*.mup"),
			new ExtensionFilter("All Files", "+.+")
		);
		File saveFile = fc.showSaveDialog(null);
		if(saveFile != null) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(saveFile)));
				pw.println(bpm);
				for(NoteBlock noteBlock : noteBlocks) {
					pw.println(
						noteBlock.getNote().getTrack() + ":" +
						noteBlock.getNote().getPitch() + ":" +
						noteBlock.getNote().getPosition() + ":"+
						noteBlock.getNote().getDuration()
					);
				}
				pw.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}
	}

	// 本当はエンジン側で保存処理をやるべきだが, とりあえずGUI側で簡易な保存処理を実装
	public void saveMidiFile() {
		Sequence sequence = uiController.getSequence();

		final FileChooser fc = new FileChooser();
		fc.setTitle("Save");
		fc.setInitialFileName((new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())) + ".mid");
		fc.getExtensionFilters().addAll(
			new ExtensionFilter("MIDI Files", "*.mid"),
			new ExtensionFilter("All Files", "+.+")
		);
		File saveFile = fc.showSaveDialog(null);
		if(saveFile != null) {
			try {
				MidiSystem.write(sequence, 1, saveFile); // タイプはマルチトラックフォーマットの1を指定
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
