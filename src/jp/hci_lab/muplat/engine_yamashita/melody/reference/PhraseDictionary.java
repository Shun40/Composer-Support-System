package engine_yamashita.melody.reference;

import java.util.ArrayList;
import java.util.HashMap;

import engine_yamashita.melody.MelodyAnalyzer;

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
			String name = parent.getNewPhraseEntryName(context, word);
			int frequency = 2;
			add(new PhraseDictionaryEntry(index, name, context, word, frequency));
		}
	}

	public void incPatternFrequency(int index) {
		get(index).incFrequency();
	}

	public void readDictionary(ArrayList<String> lines) {
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
}
