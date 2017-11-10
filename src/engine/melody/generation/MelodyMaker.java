package engine.melody.generation;

import java.util.ArrayList;
import java.util.List;

import engine.melody.ChordProgression;
import engine.melody.RelativeMelody;
import gui.component.pianoroll.note.NoteModel;
import midi.MidiUtil;
import system.AppConstants;

public class MelodyMaker {
	public static List<CandidateLabel> makeMelody(RelativeMelody context, RelativeMelody word, NoteModel justBeforeNote, ChordProgression chordProgression, String justBeforeChord, List<AppConstants.Algorithm> algorithms) {
		List<CandidateLabel> labels = new ArrayList<CandidateLabel>();

		// 直前音符の情報
		if(justBeforeNote == null) { // 対象小節の直前に音符が存在しない場合
			int difference = 0;
			int position = 0;
			int duration = 0;
			int pitch = -1;
			String chord = justBeforeChord;
			labels.add(new CandidateLabel(difference, position, duration, pitch, chord));
		} else {
			int difference = context.get(context.size() - 1).getDifference();
			int position = justBeforeNote.getPosition();
			int duration = justBeforeNote.getDuration();
			int pitch = justBeforeNote.getPitch();
			String chord = justBeforeChord;
			labels.add(new CandidateLabel(difference, position, duration, pitch, chord));
		}

		for(int i = 0; i < word.size(); i++) {
			int difference = word.get(i).getDifference();
			int position = word.get(i).getPosition();
			int duration = word.get(i).getDuration();
			int pitch = -1;
			String chord = chordProgression.get(position / MidiUtil.getDurationOf2Beats());;
			labels.add(new CandidateLabel(difference, position, duration, pitch, chord));
		}

		DynamicProgramming dynamicProgramming = new DynamicProgramming();
		dynamicProgramming.makeMelody(labels, algorithms);

		return labels;
	}
}
