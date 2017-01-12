package engine_yamashita;

import java.util.ArrayList;

public class BassPatternAnalyzer {
	public BassPatternAnalyzer() {
	}

	public ArrayList<BassPattern> getBassPatterns(ArrangeInformation arrangeInformation, ArrayList<DrumPattern> drumPatterns, ArrayList<Double> accentScores) {
		ArrayList<BassPattern> patterns = new ArrayList<BassPattern>();
		ArrayList<String> chordProgression = arrangeInformation.getCurrentChordProgression();

		for(int n = 0; n < drumPatterns.size(); n++) {
			BassPattern pattern = new BassPattern();
			DrumPattern drumPattern = drumPatterns.get(n);
			for(int m = 0; m < chordProgression.size(); m++) {
				String chord = chordProgression.get(m);
				int note = 0;
				switch(chord) {
				case "C": note = 60; break;
				case "Dm": note = 62; break;
				case "Em": note = 64; break;
				case "F": note = 65; break;
				case "G": note = 67; break;
				case "Am": note = 69; break;
				case "Bdim": note = 71; break;
				default: note = 0; break;
				}
				note -= 24; // 2オクターブ下げる
				if(note >= 0) pattern.addNote(note, BassPattern.calcPosition((m * 2) + 1, 1), BassPattern.calcDuration(8), 100);
				if(note >= 0) pattern.addNote(note, BassPattern.calcPosition((m * 2) + 1, 3), BassPattern.calcDuration(8), 100);
				if(note >= 0) pattern.addNote(note, BassPattern.calcPosition((m * 2) + 2, 1), BassPattern.calcDuration(8), 100);
				if(note >= 0) pattern.addNote(note, BassPattern.calcPosition((m * 2) + 2, 3), BassPattern.calcDuration(8), 100);
			}
			patterns.add(pattern);
		}
		return patterns;
	}
}
