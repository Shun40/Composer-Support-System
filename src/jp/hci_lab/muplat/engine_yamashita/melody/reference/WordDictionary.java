package engine_yamashita.melody.reference;

import java.util.ArrayList;
import java.util.HashMap;

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

	public void readDictionary(ArrayList<String> lines) {
		clear();
		int index = 0;
		HashMap<String, MelodyPattern> map = new HashMap<String, MelodyPattern>();
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
				String wordName = data.split(",")[1];
				int frequency = Integer.parseInt(data.split(",")[2]);
				add(new WordDictionaryEntry(index, recordName, map.get(wordName), frequency));
				index++;
				break;
			}
		}
	}
}
