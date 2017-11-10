package engine.melody.dictionary;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

abstract public class Dictionary extends ArrayList<DictionaryEntry> {
	public Dictionary() {
		super();
	}

	/** 単語辞書用仮想メソッド */
	abstract public void incPatternFrequency(String wordId);
	/** 例文辞書用仮想メソッド */
	abstract public void incPatternFrequency(String contextId, String wordId);
	abstract public void readDictionary();
	abstract public void readDictionary(String filename);
	abstract protected void loadDictionary(List<String> lines);
	abstract public void showDictionary();

	/** 辞書内容表示に使う内部クラス */
	public class Entry {
		private final StringProperty index;
		private final StringProperty name;
		private final StringProperty context;
		private final StringProperty word;
		private final StringProperty frequency;
		public Entry(String index, String name, String context, String word, String frequency) {
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
