import static constants.UniversalConstants.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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

	public void addNote(Note note) {
		uiController.addNote(note);
	}

	public void removeNote(Note note) {
		uiController.removeNote(note);
	}

	public void removeAllNote() {
		uiController.removeAllNote();
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
				pianoroll.removeAllNote();
				String note = br.readLine();
				while(note != null) {
					int channel    = Integer.parseInt(note.split(":", -1)[0]);
					int noteNumber = Integer.parseInt(note.split(":", -1)[2]);
					int position   = Integer.parseInt(note.split(":", -1)[3]);
					int duration   = Integer.parseInt(note.split(":", -1)[4]);
					pianoroll.setCurrentChannel(channel);
					pianoroll.putNote(noteNumber, position, duration);

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
		ArrayList<Note> notes = pianoroll.getNotes();

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
				for(Note note : notes) {
					pw.println(
						note.getNoteInformation().getChannel() + ":" +
						note.getNoteInformation().getProgNumber() + ":" +
						note.getNoteInformation().getNoteNumber() + ":" +
						note.getNoteInformation().getPosition() + ":"+
						note.getNoteInformation().getDuration()
					);
				}
				pw.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}
