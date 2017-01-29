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
import java.util.Optional;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import engine_yamashita.Accompaniment;
import engine_yamashita.PredictionInformation;
import engine_yamashita.PredictionPattern;
import engine_yamashita.melody.reference.MelodyPattern;
import engine_yamashita.melody.reference.PhraseDictionary;
import engine_yamashita.melody.reference.WordDictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
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
		this.uiController = new UIController(this);
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

	public void play(int startMeasure) {
		uiController.play(startMeasure);
	}

	public void stop() {
		uiController.stop();
	}

	public void close() {
		uiController.close();
	}

	public ArrayList<PredictionPattern> prediction(PredictionInformation predictionInformation) {
		return uiController.prediction(predictionInformation);
	}

	public void incWordDictionaryFrequency(String wordId) {
		uiController.incWordDictionaryFrequency(wordId);
	}

	public void incPhraseDictionaryFrequency(String contextId, String wordId) {
		uiController.incPhraseDictionaryFrequency(contextId, wordId);
	}

	public Accompaniment makeAccompaniment(String chord) {
		return uiController.makeAccompaniment(chord);
	}

	// 本当はエンジン側で読み込み処理をやるべきだが, とりあえずGUI側で簡易な読み込み処理を実装
	public void read() {
		final FileChooser fc = new FileChooser();
		fc.setTitle("MUPファイルを読み込む");
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
		fc.setTitle("MUPファイルに保存");
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
		fc.setTitle("MIDIファイルに保存");
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

	public void readWordDictionaryFile() {
		final FileChooser fc = new FileChooser();
		fc.setTitle("単語辞書ファイルを開く");
		fc.getExtensionFilters().addAll(
			new ExtensionFilter("Dictionary Files", "*.dic"),
			new ExtensionFilter("All Files", "+.+")
		);
		ArrayList<String> lines = new ArrayList<String>();
		File readFile = fc.showOpenDialog(null);
		if(readFile != null) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(readFile));
				String line = br.readLine();
				while(line != null) {
					lines.add(line);
					line = br.readLine();
				}
				br.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		if(!lines.isEmpty()) uiController.readWordDictionary(lines);
	}

	public void readPhraseDictionaryFile() {
		final FileChooser fc = new FileChooser();
		fc.setTitle("例文辞書ファイルを開く");
		fc.getExtensionFilters().addAll(
			new ExtensionFilter("Dictionary Files", "*.dic"),
			new ExtensionFilter("All Files", "+.+")
		);
		ArrayList<String> lines = new ArrayList<String>();
		File readFile = fc.showOpenDialog(null);
		if(readFile != null) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(readFile));
				String line = br.readLine();
				while(line != null) {
					lines.add(line);
					line = br.readLine();
				}
				br.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		if(!lines.isEmpty()) uiController.readPhraseDictionary(lines);
	}

	public void showWordDictionary() {
		WordDictionary wordDictionary = uiController.getWordDictionary();
		Dialog dialog = new Dialog<>();
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		dialog.setTitle("単語辞書内容");
		dialog.setHeaderText("単語辞書に登録されているパターンの一覧です.");
		dialog.getDialogPane().setPrefWidth(800);
		dialog.setResizable(true);

		// テーブル作成
		TableView<WordEntry> table = new TableView<>();
		TableColumn<WordEntry, String> index = new TableColumn<WordEntry, String>("インデックス");
		index.setCellValueFactory(new PropertyValueFactory<>("index"));
		TableColumn<WordEntry, String> name = new TableColumn<WordEntry, String>("エントリ名");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<WordEntry, String> word = new TableColumn<WordEntry, String>("旋律概形");
		word.setCellValueFactory(new PropertyValueFactory<>("word"));
		TableColumn<WordEntry, String> frequency = new TableColumn<WordEntry, String>("選択頻度");
		frequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
		table.getColumns().setAll(index, name, word, frequency);

		ObservableList<WordEntry> records = FXCollections.observableArrayList();
		for(int i = 0; i < wordDictionary.size(); i++) {
			String _index = Integer.toString(wordDictionary.get(i).getIndex());
			String _name = wordDictionary.get(i).getName();
			String _word = wordDictionary.get(i).getWord().getId();
			String _frequency = Integer.toString(wordDictionary.get(i).getFrequency() - 1);
			records.add(new WordEntry(_index, _name, _word, _frequency));
		}
		table.setItems(records);
		dialog.getDialogPane().setContent(table);
		dialog.showAndWait();
	}

	public void showPhraseDictionary() {
		PhraseDictionary phraseDictionary = uiController.getPhraseDictionary();
		Dialog dialog = new Dialog<>();
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		dialog.setTitle("例文辞書内容");
		dialog.setHeaderText("例文辞書に登録されているパターンの一覧です.");
		dialog.getDialogPane().setPrefWidth(800);
		dialog.setResizable(true);

		// テーブル作成
		TableView<PhraseEntry> table = new TableView<>();
		TableColumn<PhraseEntry, String> index = new TableColumn<PhraseEntry, String>("インデックス");
		index.setCellValueFactory(new PropertyValueFactory<>("index"));
		TableColumn<PhraseEntry, String> name = new TableColumn<PhraseEntry, String>("エントリ名");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<PhraseEntry, String> context = new TableColumn<PhraseEntry, String>("先行旋律概形");
		context.setCellValueFactory(new PropertyValueFactory<>("context"));
		TableColumn<PhraseEntry, String> word = new TableColumn<PhraseEntry, String>("旋律概形");
		word.setCellValueFactory(new PropertyValueFactory<>("word"));
		TableColumn<PhraseEntry, String> frequency = new TableColumn<PhraseEntry, String>("選択頻度");
		frequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
		table.getColumns().setAll(index, name, context, word, frequency);

		ObservableList<PhraseEntry> records = FXCollections.observableArrayList();
		for(int i = 0; i < phraseDictionary.size(); i++) {
			String _index = Integer.toString(phraseDictionary.get(i).getIndex());
			String _name = phraseDictionary.get(i).getName();
			String _context = phraseDictionary.get(i).getContext().getId();
			String _word = phraseDictionary.get(i).getWord().getId();
			String _frequency = Integer.toString(phraseDictionary.get(i).getFrequency() - 1);
			records.add(new PhraseEntry(_index, _name, _context, _word, _frequency));
		}
		table.setItems(records);
		dialog.getDialogPane().setContent(table);
		dialog.showAndWait();
	}

	public String getNewPhraseEntryName(MelodyPattern context, MelodyPattern word) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("新しい例文辞書エントリの登録");
		dialog.setHeaderText("新しい例文辞書エントリの名前を入力して下さい.");
		dialog.setContentText("先行旋律概形: " + context.getId() + "\n" + "旋律概形: " + word.getId());
		Optional<String> name = dialog.showAndWait();
		if(name.isPresent()){
			return name.get();
		} else {
			return context.getId() + "-" + word.getId();
		}
	}

	public class WordEntry {
		private final String index;
		private final String name;
		private final String word;
		private final String frequency;
		public WordEntry(String index, String name, String word, String frequency) {
			this.index = index;
			this.name = name;
			this.word = word;
			this.frequency = frequency;
		}
		public String getIndex() { return index; }
		public String getName() { return name; }
		public String getWord() { return word; }
		public String getFrequency() { return frequency; }
	}

	public class PhraseEntry {
		private final String index;
		private final String name;
		private final String context;
		private final String word;
		private final String frequency;
		public PhraseEntry(String index, String name, String context, String word, String frequency) {
			this.index = index;
			this.name = name;
			this.context = context;
			this.word = word;
			this.frequency = frequency;
		}
		public String getIndex() { return index; }
		public String getName() { return name; }
		public String getContext() { return context; }
		public String getWord() { return word; }
		public String getFrequency() { return frequency; }
	}
}
