package engine.melody.dictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class WordDictionary extends ArrayList<WordDictionaryEntry> {
	public WordDictionary() {
		super();
	}

	public void incPatternFrequency(String wordId) {
		for(int i = 0; i < size(); i++) {
			if(wordId.equals(get(i).getWord().getId())) {
				get(get(i).getIndex()).incFrequency();
				break;
			}
		}
	}

	public void readDictionary() {
		final FileChooser fc = new FileChooser();
		fc.setTitle("単語辞書ファイルを読み込む");
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
		Map<String, RelativeMelody> map = new HashMap<String, RelativeMelody>();
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
				String wordName = data.split(",")[1];
				int frequency = Integer.parseInt(data.split(",")[2]);
				add(new WordDictionaryEntry(index, recordName, map.get(wordName), frequency));
				index++;
				break;
			}
		}
	}

	public void showDictionary() {
		Dialog<Object> dialog = new Dialog<Object>();
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		dialog.setTitle("単語辞書内容");
		dialog.setHeaderText("単語辞書に登録されているパターンの一覧です.");
		dialog.getDialogPane().setPrefWidth(800);
		dialog.setResizable(true);

		// テーブル作成
		TableView<WordEntry> table = new TableView<WordEntry>();
		TableColumn<WordEntry, String> index = new TableColumn<WordEntry, String>("インデックス");
		index.setCellValueFactory(new PropertyValueFactory<WordEntry, String>("index"));
		TableColumn<WordEntry, String> name = new TableColumn<WordEntry, String>("パターン名");
		name.setCellValueFactory(new PropertyValueFactory<WordEntry, String>("name"));
		TableColumn<WordEntry, String> word = new TableColumn<WordEntry, String>("ワード");
		word.setCellValueFactory(new PropertyValueFactory<WordEntry, String>("word"));
		TableColumn<WordEntry, String> frequency = new TableColumn<WordEntry, String>("選択回数");
		frequency.setCellValueFactory(new PropertyValueFactory<WordEntry, String>("frequency"));
		table.getColumns().add(index);
		table.getColumns().add(name);
		table.getColumns().add(word);
		table.getColumns().add(frequency);

		ObservableList<WordEntry> records = FXCollections.observableArrayList();
		for(int i = 0; i < this.size(); i++) {
			String _index = Integer.toString(this.get(i).getIndex());
			String _name = this.get(i).getName();
			String _word = this.get(i).getWord().getId();
			String _frequency = Integer.toString(this.get(i).getFrequency() - 1);
			records.add(new WordEntry(_index, _name, _word, _frequency));
		}
		table.setItems(records);
		dialog.getDialogPane().setContent(table);
		dialog.showAndWait();
	}

	public class WordEntry {
		private final StringProperty index;
		private final StringProperty name;
		private final StringProperty word;
		private final StringProperty frequency;
		public WordEntry(String index, String name, String word, String frequency) {
			this.index = new SimpleStringProperty(index);
			this.name = new SimpleStringProperty(name);
			this.word = new SimpleStringProperty(word);
			this.frequency = new SimpleStringProperty(frequency);
		}
		public StringProperty indexProperty() {
			return index;
		}
		public StringProperty nameProperty() {
			return name;
		}
		public StringProperty wordProperty() {
			return word;
		}
		public StringProperty frequencyProperty() {
			return frequency;
		}
	}
}
