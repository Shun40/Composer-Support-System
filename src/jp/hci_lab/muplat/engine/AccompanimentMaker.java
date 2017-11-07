package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gui.component.pianoroll.note.NoteModel;
import midi.MidiConstants;
import midi.MidiUtil;
import system.AppConstants;

public class AccompanimentMaker {
	public static List<NoteModel> makeChordPart(String chord, int measure, int beat) {
		List<NoteModel> chordPart = new ArrayList<NoteModel>();
		List<Integer> chordPitches = new ArrayList<Integer>();
		int track = AppConstants.AccompanimentSettings.CHORD_TRACK;
		int program = AppConstants.AccompanimentSettings.CHORD_PROGRAM;
		int position = MidiUtil.getPosition(measure, beat, 0);
		int duration = MidiUtil.getDurationOf2Beats();

		switch(chord) {
		case "C":
			chordPitches.addAll(Arrays.asList(60, 64, 67));
			break;
		case "Dm":
			chordPitches.addAll(Arrays.asList(62, 65, 69));
			break;
		case "Em":
			chordPitches.addAll(Arrays.asList(64, 67, 71));
			break;
		case "F":
			chordPitches.addAll(Arrays.asList(65, 69, 72));
			break;
		case "G":
			chordPitches.addAll(Arrays.asList(67, 71, 74));
			break;
		case "Am":
			chordPitches.addAll(Arrays.asList(69, 72, 76));
			break;
		case "Bmb5":
			chordPitches.addAll(Arrays.asList(71, 74, 77));
			break;
		}
		for(int n = 0; n < chordPitches.size(); n++) {
			chordPart.add(new NoteModel(track, program, chordPitches.get(n), position, duration, 100));
		}
		return chordPart;
	}

	public static List<NoteModel> makeBassPart(String chord, int measure, int beat) {
		List<NoteModel> bassPart = new ArrayList<NoteModel>();
		List<Integer> rootPitch = new ArrayList<Integer>();
		int track = AppConstants.AccompanimentSettings.BASS_TRACK;
		int program = AppConstants.AccompanimentSettings.BASS_PROGRAM;
		int position = MidiUtil.getPosition(measure, beat, 0);
		int duration = MidiConstants.PPQ / 2; // 8分音符

		switch(chord) {
		case "C":
			rootPitch.addAll(Arrays.asList(48));
			break;
		case "Dm":
			rootPitch.addAll(Arrays.asList(50));
			break;
		case "Em":
			rootPitch.addAll(Arrays.asList(52));
			break;
		case "F":
			rootPitch.addAll(Arrays.asList(53));
			break;
		case "G":
			rootPitch.addAll(Arrays.asList(55));
			break;
		case "Am":
			rootPitch.addAll(Arrays.asList(57));
			break;
		case "Bmb5":
			rootPitch.addAll(Arrays.asList(59));
			break;
		}
		for(int n = 0; n < rootPitch.size(); n++) {
			bassPart.add(new NoteModel(track, program, rootPitch.get(n), position + (duration * 0), duration, 100));
			bassPart.add(new NoteModel(track, program, rootPitch.get(n), position + (duration * 1), duration, 100));
			bassPart.add(new NoteModel(track, program, rootPitch.get(n), position + (duration * 2), duration, 100));
			bassPart.add(new NoteModel(track, program, rootPitch.get(n), position + (duration * 3), duration, 100));
		}
		return bassPart;
	}

	public static List<NoteModel> makeDrumPart(int measure, int beat) {
		List<NoteModel> drumPart = new ArrayList<NoteModel>();
		int track = AppConstants.AccompanimentSettings.DRUM_TRACK;
		int program = AppConstants.AccompanimentSettings.DRUM_PROGRAM;
		int position = MidiUtil.getPosition(measure, beat, 0);
		int duration = MidiConstants.PPQ / 4; // 16分音符

		// キック
		drumPart.add(new NoteModel(track, program, 36, position + (duration * 0), duration, 100));
		// スネア
		drumPart.add(new NoteModel(track, program, 38, position + (duration * 4), duration, 100));
		// ハイハット
		drumPart.add(new NoteModel(track, program, 42, position + (duration * 0), duration, 100));
		drumPart.add(new NoteModel(track, program, 42, position + (duration * 2), duration, 100));
		drumPart.add(new NoteModel(track, program, 42, position + (duration * 4), duration, 100));
		drumPart.add(new NoteModel(track, program, 42, position + (duration * 6), duration, 100));
		return drumPart;
	}
}
