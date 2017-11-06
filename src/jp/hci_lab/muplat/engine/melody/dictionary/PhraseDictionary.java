package engine.melody.dictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import engine.melody.MelodyAnalyzer;
import engine.melody.reference.MelodyPattern;
import file.FileUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class PhraseDictionary extends ArrayList<PhraseDictionaryEntry> {
	private HashMap<String, MelodyPattern> map;
	private MelodyAnalyzer parent;

	public PhraseDictionary(MelodyAnalyzer parent) {
		super();
		map = new HashMap<String, MelodyPattern>();
		this.parent = parent;
	}

	public void incPatternFrequency(String contextId, String wordId) {
		boolean isExist = false;
		for(int i = 0; i < size(); i++) {
			if(contextId.equals(get(i).getContext().getId()) && wordId.equals(get(i).getWord().getId())) {
				get(get(i).getIndex()).incFrequency();
				isExist = true;
				break;
			}
		}
		if(!isExist) {
			int index = size();
			MelodyPattern context = map.get(contextId);
			MelodyPattern word = map.get(wordId);
			String name = getNewPhraseEntryName(context, word);
			int frequency = 2;
			add(new PhraseDictionaryEntry(index, name, context, word, frequency));
		}
	}

	public void incPatternFrequency(int index) {
		get(index).incFrequency();
	}

	private String getNewPhraseEntryName(MelodyPattern context, MelodyPattern word) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("新しい例文辞書エントリの登録");
		dialog.setHeaderText("新しい例文辞書エントリの名前を入力して下さい.");
		dialog.setContentText("コンテクスト: " + context.getId() + "\n" + "ワード: " + word.getId());
		Optional<String> name = dialog.showAndWait();
		if(name.isPresent()){
			return name.get();
		} else {
			return context.getId() + "-" + word.getId();
		}
	}

	public void readDictionary() {
		final FileChooser fc = new FileChooser();
		fc.setTitle("例文辞書ファイルを読み込む");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Dictionary Files", "*.dic"), new ExtensionFilter("All Files", "+.+"));
		File file = fc.showOpenDialog(null);
		List<String> lines = FileUtil.readFile(file);
		loadDictionary(lines);
	}

	public void readDictionary(String filename) {
		File file = new File(filename);
		List<String> lines = FileUtil.readFile(file);
		loadDictionary(lines);
	}

	private void loadDictionary(List<String> lines) {
		clear();
		int index = 0;
		for(int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String type = line.split(":")[0];
			String data = line.split(":")[1];
			switch(type) {
			case "pattern":
				map.put(data, new MelodyPattern(data));
				break;
			case "data":
				String patternName = data.split(",")[0];
				int variation = Integer.parseInt(data.split(",")[1]);
				int difference = Integer.parseInt(data.split(",")[2]);
				int position = Integer.parseInt(data.split(",")[3]);
				int duration = Integer.parseInt(data.split(",")[4]);
				map.get(patternName).add(variation, difference, position, duration);
				break;
			case "record":
				String recordName = data.split(",")[0];
				String transition = data.split(",")[1];
				String contextName = transition.split("-")[0];
				String wordName = transition.split("-")[1];
				int frequency = Integer.parseInt(data.split(",")[2]);
				add(new PhraseDictionaryEntry(index, recordName, map.get(contextName), map.get(wordName), frequency));
				index++;
				break;
			}
		}
	}

	public void showDictionary() {
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
		TableColumn<PhraseEntry, String> name = new TableColumn<PhraseEntry, String>("パターン名");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<PhraseEntry, String> context = new TableColumn<PhraseEntry, String>("コンテクスト");
		context.setCellValueFactory(new PropertyValueFactory<>("context"));
		TableColumn<PhraseEntry, String> word = new TableColumn<PhraseEntry, String>("ワード");
		word.setCellValueFactory(new PropertyValueFactory<>("word"));
		TableColumn<PhraseEntry, String> frequency = new TableColumn<PhraseEntry, String>("選択回数");
		frequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
		table.getColumns().setAll(index, name, context, word, frequency);

		ObservableList<PhraseEntry> records = FXCollections.observableArrayList();
		for(int i = 0; i < this.size(); i++) {
			String _index = Integer.toString(this.get(i).getIndex());
			String _name = this.get(i).getName();
			String _context = this.get(i).getContext().getId();
			String _word = this.get(i).getWord().getId();
			String _frequency = Integer.toString(this.get(i).getFrequency() - 1);
			records.add(new PhraseEntry(_index, _name, _context, _word, _frequency));
		}
		table.setItems(records);
		dialog.getDialogPane().setContent(table);
		dialog.showAndWait();
	}

	private class PhraseEntry {
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
	}
}
