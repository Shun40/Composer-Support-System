package engine.melody.dictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import engine.melody.RelativeMelody;
import engine.melody.RelativeNote;
import file.FileUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
	private Map<String, RelativeMelody> map;

	public PhraseDictionary() {
		super();
		map = new HashMap<String, RelativeMelody>();
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
			RelativeMelody context = map.get(contextId);
			RelativeMelody word = map.get(wordId);
			String name = getNewPhraseEntryName(context, word);
			int frequency = 2;
			add(new PhraseDictionaryEntry(index, name, context, word, frequency));
		}
	}

	public void incPatternFrequency(int index) {
		get(index).incFrequency();
	}

	private String getNewPhraseEntryName(RelativeMelody context, RelativeMelody word) {
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
				map.put(data, new RelativeMelody(data));
				break;
			case "data":
				String patternName = data.split(",")[0];
				int variation = Integer.parseInt(data.split(",")[1]);
				int difference = Integer.parseInt(data.split(",")[2]);
				int position = Integer.parseInt(data.split(",")[3]);
				int duration = Integer.parseInt(data.split(",")[4]);
				map.get(patternName).add(new RelativeNote(variation, difference, position, duration));
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
		Dialog<Object> dialog = new Dialog<Object>();
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		dialog.setTitle("例文辞書内容");
		dialog.setHeaderText("例文辞書に登録されているパターンの一覧です.");
		dialog.getDialogPane().setPrefWidth(800);
		dialog.setResizable(true);

		// テーブル作成
		TableView<PhraseEntry> table = new TableView<PhraseEntry>();
		TableColumn<PhraseEntry, String> index = new TableColumn<PhraseEntry, String>("インデックス");
		index.setCellValueFactory(new PropertyValueFactory<PhraseEntry, String>("index"));
		TableColumn<PhraseEntry, String> name = new TableColumn<PhraseEntry, String>("パターン名");
		name.setCellValueFactory(new PropertyValueFactory<PhraseEntry, String>("name"));
		TableColumn<PhraseEntry, String> context = new TableColumn<PhraseEntry, String>("コンテクスト");
		context.setCellValueFactory(new PropertyValueFactory<PhraseEntry, String>("context"));
		TableColumn<PhraseEntry, String> word = new TableColumn<PhraseEntry, String>("ワード");
		word.setCellValueFactory(new PropertyValueFactory<PhraseEntry, String>("word"));
		TableColumn<PhraseEntry, String> frequency = new TableColumn<PhraseEntry, String>("選択回数");
		frequency.setCellValueFactory(new PropertyValueFactory<PhraseEntry, String>("frequency"));
		table.getColumns().add(index);
		table.getColumns().add(name);
		table.getColumns().add(context);
		table.getColumns().add(word);
		table.getColumns().add(frequency);

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

	public class PhraseEntry {
		private final StringProperty index;
		private final StringProperty name;
		private final StringProperty context;
		private final StringProperty word;
		private final StringProperty frequency;
		public PhraseEntry(String index, String name, String context, String word, String frequency) {
			this.index = new SimpleStringProperty(index);
			this.name = new SimpleStringProperty(name);
			this.context = new SimpleStringProperty(context);
			this.word = new SimpleStringProperty(word);
			this.frequency = new SimpleStringProperty(frequency);
		}
		public StringProperty indexProperty() {
			return index;
		}
		public StringProperty nameProperty() {
			return name;
		}
		public StringProperty contextProperty() {
			return context;
		}
		public StringProperty wordProperty() {
			return word;
		}
		public StringProperty frequencyProperty() {
			return frequency;
		}
	}
}
