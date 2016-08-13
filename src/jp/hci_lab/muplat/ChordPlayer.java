package jp.hci_lab.muplat;

import java.util.HashMap;
import java.util.StringTokenizer;

public class ChordPlayer {
	boolean baseEnable = true;
	int bassCenter = 36;
	boolean chordAround = true;
	int chordCenter = 66;
	
	HashMap<String, Integer> noteId = new HashMap<String, Integer>();
	HashMap<String, int[]> chordnotes = new HashMap<String, int[]>();
	DAW daw_;
	
	ChordPlayer(DAW daw) {
		noteId.put("C",  0);
		noteId.put("C#", 1);
		noteId.put("Db", 1);
		noteId.put("D",  2);
		noteId.put("D#", 3);
		noteId.put("Eb", 3);
		noteId.put("E",  4);
		noteId.put("F",  5);
		noteId.put("F#", 6);
		noteId.put("Gb", 6);
		noteId.put("G",  7);
		noteId.put("G#", 8);
		noteId.put("Ab", 8);
		noteId.put("A",  9);
		noteId.put("A#", 10);
		noteId.put("Bb", 10);
		noteId.put("B",  11);		
		
		daw_ = daw;

		DefineChord("C", new int[] {60, 64, 67});
		ShiftAll("");

		DefineChord("Cm", new int[] {60, 63, 67});
		ShiftAll("m");

		DefineChord("C6", new int[] {60, 64, 67, 69});
		ShiftAll("6");

		DefineChord("C7", new int[] {60, 64, 67, 70});
		ShiftAll("7");

		DefineChord("CM7", new int[] {60, 64, 67, 71});
		ShiftAll("M7");

		DefineChord("Cm7", new int[] {60, 63, 67, 70});
		ShiftAll("m7");

		DefineChord("Csus4", new int[] {60, 65, 67});
		ShiftAll("sus4");

		DefineChord("Cadd9", new int[] {60, 64, 67, 74});
		ShiftAll("add9");
		
		DefineChord("Bmb5", new int[] {71, 74, 77});
		ShiftAll("B", "mb5");
		
		DefineChord("Bm7b5", new int[]{71, 74, 77, 81});
		ShiftAll("B", "m7b5");

	}
	
	public void PlayChordProgression(String s) {
     	class PlayThread extends Thread {
    	    public void run() {
    	        try {
    	        	StringTokenizer st = new StringTokenizer(s);
	    	   	     while (st.hasMoreTokens()) {
	    	   	    	 daw_.chordplayer.PlayChord(st.nextToken());
	    		     }	
    	        }
    	        catch(Exception ex) {
    	            ex.printStackTrace();
    	        }
    	    }
    	}
    	PlayThread p = new PlayThread();
    	p.start();
    }
	
	public void SetChordPattern(String s) {
		
	}

	public void SetChord(String s) {
		
	}

	public void BassOn(boolean b) {
		baseEnable = b;
	}
	
	int aroundNote(int center, int noteNo) {
		int noteId = noteNo % 12;
		int offset = noteId - (center % 12);
		if (offset < -6) {
			offset += 12;
		}
		return center + offset;
	}
	
	public void PlayChord(String s) {
		try {
			int root = 0;
			int notes[];

			StringTokenizer chordName = new StringTokenizer(s, "/");
			if (chordName.countTokens() == 2) {
				String part = chordName.nextToken();
				notes = chordnotes.get(part).clone();
				part = chordName.nextToken();
				root = noteId.get(part);
			} else {
				notes = chordnotes.get(s).clone();				
				root = notes[0];
			}
			root = aroundNote(bassCenter, root);

			if (chordAround) {
				for (int i = 0; i < notes.length; i++) {
					notes[i] = aroundNote(chordCenter, notes[i]);
				}
			}

			if (notes != null) {
				if (baseEnable) {
					daw_.NoteOn(root, 100);
				}
				
				for (int i = 0; i < notes.length; i++) {
					daw_.NoteOn(notes[i], 100);
				}
			}
			Thread.sleep(1500);
			if (baseEnable) {
				daw_.NoteOff(root);
			}
			if (notes != null) {
				for (int i = 0; i < notes.length; i++) {
					daw_.NoteOff(notes[i]);
				}
			}
			Thread.sleep(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void DefineChord(String name, int notes[]) {
		chordnotes.put(name, notes.clone());
	}

	void ShiftAll(String type) {
		ShiftAll("C", type);
	}

	void ShiftAll(String base, String type) {
		int offset = noteId.get(base);
		String chord = base + type;

		DefineChord("C"  + type, Shift(chord,  0 - offset));
		DefineChord("C#" + type, Shift(chord,  1 - offset));
		DefineChord("Db" + type, Shift(chord,  1 - offset));
		DefineChord("D"  + type, Shift(chord,  2 - offset));
		DefineChord("D#" + type, Shift(chord,  3 - offset));
		DefineChord("Eb" + type, Shift(chord,  3 - offset));
		DefineChord("E"  + type, Shift(chord,  4 - offset));
		DefineChord("F"  + type, Shift(chord,  5 - offset));
		DefineChord("F#" + type, Shift(chord,  6 - offset));
		DefineChord("Gb" + type, Shift(chord,  6 - offset));
		DefineChord("G"  + type, Shift(chord,  7 - offset));
		DefineChord("G#" + type, Shift(chord,  8 - offset));
		DefineChord("Ab" + type, Shift(chord,  8 - offset));
		DefineChord("A"  + type, Shift(chord,  9 - offset));
		DefineChord("A#" + type, Shift(chord, 10 - offset));
		DefineChord("Bb" + type, Shift(chord, 10 - offset));
		DefineChord("B"  + type, Shift(chord, 11 - offset));		
	}
	
	int[] Shift(String base, int offset) {
		int notes[] = chordnotes.get(base).clone();
		for (int i = 0; i < notes.length; i++) {
			notes[i] += offset;
		}
		return notes;
	}

}
