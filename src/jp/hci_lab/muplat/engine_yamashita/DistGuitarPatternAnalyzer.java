package engine_yamashita;

import java.util.ArrayList;

public class DistGuitarPatternAnalyzer {
	public DistGuitarPatternAnalyzer() {
	}

	public ArrayList<DistGuitarPattern> getDistGuitarPatterns(ArrangeInformation arrangeInformation, ArrayList<DrumPattern> drumPatterns, ArrayList<Double> accentScores) {
		ArrayList<DistGuitarPattern> patterns = new ArrayList<DistGuitarPattern>();
		ArrayList<String> chordProgression = arrangeInformation.getChordProgression();

		for(int n = 0; n < drumPatterns.size(); n++) {
			DistGuitarPattern pattern = new DistGuitarPattern();
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
				note -= 12; // 2オクターブ下げる
				// とりあえずパワーコード
				if(note >= 0) {
					pattern.addNote(note, DistGuitarPattern.calcPosition((m * 2) + 1, 1), DistGuitarPattern.calcDuration(8), 100);
					pattern.addNote(note + 7, DistGuitarPattern.calcPosition((m * 2) + 1, 1), DistGuitarPattern.calcDuration(8), 100);
				}
				if(note >= 0) {
					pattern.addNote(note, DistGuitarPattern.calcPosition((m * 2) + 1, 3), DistGuitarPattern.calcDuration(8), 100);
					pattern.addNote(note + 7, DistGuitarPattern.calcPosition((m * 2) + 1, 3), DistGuitarPattern.calcDuration(8), 100);
				}
				if(note >= 0) {
					pattern.addNote(note, DistGuitarPattern.calcPosition((m * 2) + 2, 1), DistGuitarPattern.calcDuration(8), 100);
					pattern.addNote(note + 7, DistGuitarPattern.calcPosition((m * 2) + 2, 1), DistGuitarPattern.calcDuration(8), 100);
				}
				if(note >= 0) {
					pattern.addNote(note, DistGuitarPattern.calcPosition((m * 2) + 2, 3), DistGuitarPattern.calcDuration(8), 100);
					pattern.addNote(note + 7, DistGuitarPattern.calcPosition((m * 2) + 2, 3), DistGuitarPattern.calcDuration(8), 100);
				}
			}
			patterns.add(pattern);
		}
		return patterns;
	}
}
