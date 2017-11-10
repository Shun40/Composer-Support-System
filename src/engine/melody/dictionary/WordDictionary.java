package engine.melody.dictionary;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.melody.RelativeMelody;
import engine.melody.RelativeNote;
import file.FileUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * 単語辞書のクラス
 * @author Shun Yamashita
 */
public class WordDictionary extends Dictionary {
	public WordDictionary() {
		super();
	}

	@Override
	public void incPatternFrequency(String wordId) {
		for(int i = 0; i < size(); i++) {
			if(wordId.equals(get(i).getWord().getId())) {
				get(get(i).getIndex()).incFrequency();
				break;
			}
		}
	}

	@Override
	public void incPatternFrequency(String contextId, String wordId) {
		// 単語辞書では何も実装しなくてよい
	}

	@Override
	public void readDictionary() {
		final FileChooser fc = new FileChooser();
		fc.setTitle("単語辞書ファイルを読み込む");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Dictionary Files", "*.dic"), new ExtensionFilter("All Files", "+.+"));
		File file = fc.showOpenDialog(null);
		List<String> lines = FileUtil.readFile(file);
		loadDictionary(lines);
	}

	@Override
	public void readDictionary(String filename) {
		File file = new File(filename);
		List<String> lines = FileUtil.readFile(file);
		loadDictionary(lines);
	}

	@Override
	protected void loadDictionary(List<String> lines) {
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
				int difference = Integer.parseInt(data.split(",")[1]) * Integer.parseInt(data.split(",")[2]);
				int position = Integer.parseInt(data.split(",")[3]);
				int duration = Integer.parseInt(data.split(",")[4]);
				map.get(patternName).add(new RelativeNote(difference, position, duration));
				break;
			case "record":
				String recordName = data.split(",")[0];
				String wordName = data.split(",")[1];
				int frequency = Integer.parseInt(data.split(",")[2]);
				add(new DictionaryEntry(index, recordName, null, map.get(wordName), frequency));
				index++;
				break;
			}
		}
	}

	@Override
	public void showDictionary() {
		Dialog<Object> dialog = new Dialog<Object>();
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		dialog.setTitle("単語辞書内容");
		dialog.setHeaderText("単語辞書に登録されているパターンの一覧です.");
		dialog.getDialogPane().setPrefWidth(800);
		dialog.setResizable(true);

		// テーブル作成
		TableView<Entry> table = new TableView<Entry>();
		TableColumn<Entry, String> index = new TableColumn<Entry, String>("インデックス");
		index.setCellValueFactory(new PropertyValueFactory<Entry, String>("index"));
		TableColumn<Entry, String> name = new TableColumn<Entry, String>("パターン名");
		name.setCellValueFactory(new PropertyValueFactory<Entry, String>("name"));
		TableColumn<Entry, String> word = new TableColumn<Entry, String>("ワード");
		word.setCellValueFactory(new PropertyValueFactory<Entry, String>("word"));
		TableColumn<Entry, String> frequency = new TableColumn<Entry, String>("選択回数");
		frequency.setCellValueFactory(new PropertyValueFactory<Entry, String>("frequency"));
		table.getColumns().add(index);
		table.getColumns().add(name);
		table.getColumns().add(word);
		table.getColumns().add(frequency);

		ObservableList<Entry> records = FXCollections.observableArrayList();
		for(int i = 0; i < this.size(); i++) {
			String _index = Integer.toString(this.get(i).getIndex());
			String _name = this.get(i).getName();
			String _word = this.get(i).getWord().getId();
			String _frequency = Integer.toString(this.get(i).getFrequency() - 1);
			records.add(new Entry(_index, _name, null, _word, _frequency));
		}
		table.setItems(records);
		dialog.getDialogPane().setContent(table);
		dialog.showAndWait();
	}
}
