package engine_yamashita.melody.generation;

import static gui.constants.UniversalConstants.*;

import java.util.ArrayList;

import engine_yamashita.melody.reference.MelodyPattern;
import gui.Note;

public class MelodyMaker {
	public MelodyMaker() {
	}

	public ArrayList<MelodyLabel> makeMelody(MelodyPattern context, MelodyPattern word, ArrayList<String> chordProgression, Note justBeforeNote) {
		ArrayList<MelodyLabel> melodyLabels = new ArrayList<MelodyLabel>();

		// 直前音符の情報
		int pitch = justBeforeNote.getPitch();
		String chord = chordProgression.get(0);
		int variation = context.get(context.size() - 1).getVariation();
		int difference = context.get(context.size() - 1).getDifference();
		int position = justBeforeNote.getPosition();
		int duration = justBeforeNote.getDuration();
		melodyLabels.add(new MelodyLabel(pitch, chord, variation, difference, position, duration));

		for(int i = 0; i < word.size(); i++) {
			pitch = -1;
			variation = word.get(i).getVariation();
			difference = word.get(i).getDifference();
			position = word.get(i).getPosition();
			duration = word.get(i).getDuration();
			if(position / (PPQ * 2) == 0) chord = chordProgression.get(0);
			if(position / (PPQ * 2) == 1) chord = chordProgression.get(1);
			System.out.println(pitch + ", " + chord + ", " + variation + ", " + difference + ", " + position + ", " + duration);
			melodyLabels.add(new MelodyLabel(pitch, chord, variation, difference, position, duration));
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
}
