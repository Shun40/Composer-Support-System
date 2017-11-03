package engine_yamashita.melody.generation;

import java.util.ArrayList;

import MIDI.MIDIConstants;
import engine_yamashita.melody.reference.MelodyPattern;
import gui.Note;
import gui.constants.UniversalConstants.Algorithm;

public class MelodyMaker {
	public MelodyMaker() {
	}

	public ArrayList<MelodyLabel> makeMelody(MelodyPattern context, MelodyPattern word, Note justBeforeNote, ArrayList<String> chordProgression, String justBeforeChord, ArrayList<Algorithm> algorithms) {
		ArrayList<MelodyLabel> melodyLabels = new ArrayList<MelodyLabel>();

		// 直前音符の情報
		if(justBeforeNote == null) { // 対象小節の直前に音符が存在しない場合
			int pitch = -1;
			String chord = justBeforeChord;
			int variation = 0;
			int difference = 0;
			int position = 0;
			int duration = 0;
			melodyLabels.add(new MelodyLabel(pitch, chord, variation, difference, position, duration));
		} else {
			int pitch = justBeforeNote.getPitch();
			String chord = justBeforeChord;
			int variation = context.get(context.size() - 1).getVariation();
			int difference = context.get(context.size() - 1).getDifference();
			int position = justBeforeNote.getPosition();
			int duration = justBeforeNote.getDuration();
			melodyLabels.add(new MelodyLabel(pitch, chord, variation, difference, position, duration));
		}

		for(int i = 0; i < word.size(); i++) {
			int pitch = -1;
			int variation = word.get(i).getVariation();
			int difference = word.get(i).getDifference();
			int position = word.get(i).getPosition();
			int duration = word.get(i).getDuration();
			String chord = "N.C.";
			if(position / (MIDIConstants.PPQ * 2) == 0) chord = chordProgression.get(0);
			if(position / (MIDIConstants.PPQ * 2) == 1) chord = chordProgression.get(1);
			melodyLabels.add(new MelodyLabel(pitch, chord, variation, difference, position, duration));
		}

		DynamicProgramming dynamicProgramming = new DynamicProgramming();
		dynamicProgramming.makeMelodyByDP(melodyLabels, algorithms);

		return melodyLabels;
	}
}
