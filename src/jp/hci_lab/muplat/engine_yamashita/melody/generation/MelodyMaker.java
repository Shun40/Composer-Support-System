package engine_yamashita.melody.generation;

import java.util.ArrayList;

import engine_yamashita.melody.reference.PitchPattern;
import engine_yamashita.melody.reference.PitchPatternData;
import engine_yamashita.melody.reference.RhythmPattern;
import engine_yamashita.melody.reference.RhythmPatternData;

public class MelodyMaker {
	public MelodyMaker() {
	}

	public ArrayList<MelodyLabel> makeMelody(RhythmPattern rhythmPatternWord, PitchPattern pitchPatternWord, RhythmPattern rhythmPatternContext, PitchPattern pitchPatternContext, ArrayList<String> chordProgression) {
		ArrayList<MelodyLabel> melodyLabels = new ArrayList<MelodyLabel>();
		ArrayList<RhythmPatternData> rhythms = rhythmPatternWord.getRhythms();
		ArrayList<PitchPatternData> pitches = pitchPatternWord.getPitches();
		int v = pitchPatternContext.getPitches().get(pitchPatternContext.getPitches().size() - 1).getVariation();
		int d = pitchPatternContext.getPitches().get(pitchPatternContext.getPitches().size() - 1).getDifference();
		int p = pitchPatternContext.getPitches().get(pitchPatternContext.getPitches().size() - 1).getPlace();
		melodyLabels.add(new MelodyLabel(-1, chordProgression.get(0), 0, 0, new PitchPatternData(v, d, p)));
		float index = pitches.size() / rhythms.size();
		for(int l = 0; l < rhythms.size(); l++) {
			int pitch = -1;
			String chord = "N.C.";
			int position = rhythms.get(l).getPosition();
			int duration = rhythms.get(l).getDuration();
			if(position / (960 * 2) == 0) chord = chordProgression.get(0); // 960でOKか要確認
			if(position / (960 * 2) == 1) chord = chordProgression.get(1); // 960でOKか要確認
			int variation = pitches.get((int)(l*index)).getVariation();
			int difference = pitches.get((int)(l*index)).getDifference();
			int place = pitches.get((int)(l*index)).getPlace();
			System.out.println(pitch + ", " + chord + ", " + position + ", " + duration + ", " + variation + ", " + difference + ", " + place);
			melodyLabels.add(new MelodyLabel(pitch, chord, position, duration, new PitchPatternData(variation, difference, place)));
		}

		DynamicProgramming dynamicProgramming = new DynamicProgramming();
		dynamicProgramming.makeMelodyByDP(melodyLabels);
		System.out.println("Melody created by DP :");
		for(int i = 0; i < melodyLabels.size(); i++) {
			System.out.print(melodyLabels.get(i).getPitch() + ", ");
		}
		System.out.println();

		return melodyLabels;
	}

	public static void main(String[] args) {
		Player player = new Player();
		player.setBpm(171);

		// メルトサビ
		/*
		player.addChord(new byte[] { 65, 69, 72 }, 1, 1, 1, 1);
		player.addChord(new byte[] { 67, 71, 74 }, 1, 2, 1, 1);
		player.addChord(new byte[] { 60, 64, 67 }, 1, 3, 1, 1);
		player.addChord(new byte[] { 67, 71, 74 }, 1, 4, 1, 1);

		ArrayList<MelodyLabel> melodyLabels = new ArrayList<MelodyLabel>();
		melodyLabels.add(new MelodyLabel(-1, "FM7", 0, 0, 0, 1, new PitchPattern("up", "small", "mid"))); // dummy
		melodyLabels.add(new MelodyLabel(-1, "FM7", 1, 1, 1, 4, new PitchPattern("up", "small", "high")));
		melodyLabels.add(new MelodyLabel(-1, "FM7", 1, 2, 3, 8, new PitchPattern("down", "small", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "FM7", 1, 3, 1, 4, new PitchPattern("keep", "", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "FM7", 1, 4, 1, 4, new PitchPattern("down", "large", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "G7", 2, 1, 1, 4, new PitchPattern("up", "large", "high")));
		melodyLabels.add(new MelodyLabel(-1, "G7", 2, 2, 1, 4, new PitchPattern("down", "small", "high")));
		melodyLabels.add(new MelodyLabel(-1, "G7", 2, 3, 1, 4, new PitchPattern("down", "small", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "G7", 2, 4, 1, 4, new PitchPattern("down", "small", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "C", 3, 1, 1, 4, new PitchPattern("up", "small", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "C", 3, 2, 1, 8, new PitchPattern("up", "small", "high")));
		melodyLabels.add(new MelodyLabel(-1, "C", 3, 2, 3, 2, new PitchPattern("keep", "", "high")));
		melodyLabels.add(new MelodyLabel(-1, "G", 4, 2, 1, 4, new PitchPattern("down", "large", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "G", 4, 3, 1, 4, new PitchPattern("up", "large", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "G", 4, 4, 1, 4, new PitchPattern("down", "small", "mid")));
		*/

		// 天体観測Aメロ
		/*
		player.addChord(new byte[] { 60, 64, 67 }, 1, 1, 1, 1);
		player.addChord(new byte[] { 60, 64, 67 }, 1, 2, 1, 1);
		player.addChord(new byte[] { 69, 72, 76 }, 1, 3, 1, 1);
		player.addChord(new byte[] { 69, 72, 76 }, 1, 4, 1, 1);

		ArrayList<MelodyLabel> melodyLabels = new ArrayList<MelodyLabel>();
		melodyLabels.add(new MelodyLabel(-1, "Cadd9", 0, 0, 0, 1, new PitchPattern("keep", "", "low"))); // dummy
		melodyLabels.add(new MelodyLabel(-1, "Cadd9", 1, 1, 1, 4, new PitchPattern("keep", "", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Cadd9", 1, 2, 3, 8, new PitchPattern("up", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Cadd9", 1, 3, 1, 4, new PitchPattern("keep", "", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Cadd9", 1, 4, 3, 8, new PitchPattern("down", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Cadd9", 2, 1, 1, 8, new PitchPattern("up", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Cadd9", 2, 1, 3, 8, new PitchPattern("up", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Cadd9", 2, 2, 1, 4, new PitchPattern("up", "small", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "Cadd9", 2, 3, 1, 2, new PitchPattern("down", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Am7", 3, 1, 1, 4, new PitchPattern("down", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Am7", 3, 2, 1, 4, new PitchPattern("up", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Am7", 3, 3, 1, 8, new PitchPattern("up", "small", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "Am7", 3, 3, 3, 8, new PitchPattern("down", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Am7", 3, 4, 1, 8, new PitchPattern("down", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Am7", 3, 4, 3, 8, new PitchPattern("down", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Am7", 4, 1, 1, 4, new PitchPattern("up", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Am7", 4, 2, 1, 4, new PitchPattern("up", "large", "mid")));
		melodyLabels.add(new MelodyLabel(-1, "Am7", 4, 3, 1, 4, new PitchPattern("down", "small", "low")));
		melodyLabels.add(new MelodyLabel(-1, "Am7", 4, 4, 3, 8, new PitchPattern("keep", "", "low")));

		DynamicProgramming dynamicProgramming = new DynamicProgramming();
		dynamicProgramming.makeMelodyByDP(melodyLabels);
		System.out.println("Melody created by DP :");
		for(int i = 0; i < melodyLabels.size(); i++) {
			System.out.print(melodyLabels.get(i).getPitch() + ", ");
		}
		System.out.println();

		for(int i = 1; i < melodyLabels.size(); i++) {
			byte pitch = (byte)melodyLabels.get(i).getPitch();
			int position = melodyLabels.get(i).getPosition();
			int duration = melodyLabels.get(i).getDuration();
			player.addMelody(pitch, 82, position, duration);
		}

		player.play();
		*/
	}
}
