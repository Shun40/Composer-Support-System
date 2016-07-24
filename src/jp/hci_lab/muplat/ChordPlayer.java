package jp.hci_lab.muplat;

import java.util.HashMap;
import java.util.StringTokenizer;

public class ChordPlayer {
	HashMap<String, int[]> chordnotes = new HashMap<String, int[]>();
	DAW daw_;
	
	ChordPlayer(DAW daw) {
		daw_ = daw;

		DefineChord("C", new int[] {60, 64, 67});
		DefineChord("D", Shift("C", 2));
		DefineChord("E", Shift("C", 4));
		DefineChord("F", Shift("C", 5));
		DefineChord("G", Shift("C", 7));
		DefineChord("A", Shift("C", 9));
		DefineChord("B", Shift("C", 11));

		DefineChord("Cm", new int[] {60, 63, 67});
		DefineChord("Dm", Shift("Cm", 2));
		DefineChord("Em", Shift("Cm", 4));
		DefineChord("Fm", Shift("Cm", 5));
		DefineChord("Gm", Shift("Cm", 7));
		DefineChord("Am", Shift("Cm", 9));
		DefineChord("Bm", Shift("Cm", 11));

		DefineChord("C7", new int[] {60, 64, 67, 70});
		DefineChord("D7", Shift("C7", 2));
		DefineChord("E7", Shift("C7", 4));
		DefineChord("F7", Shift("C7", 5));
		DefineChord("G7", Shift("C7", 7));
		DefineChord("A7", Shift("C7", 9));
		DefineChord("B7", Shift("C7", 11));

		DefineChord("CM7", new int[] {60, 64, 67, 71});
		DefineChord("DM7", Shift("CM7", 2));
		DefineChord("EM7", Shift("CM7", 4));
		DefineChord("FM7", Shift("CM7", 5));
		DefineChord("GM7", Shift("CM7", 7));
		DefineChord("AM7", Shift("CM7", 9));
		DefineChord("BM7", Shift("CM7", 11));

		DefineChord("Cm7", new int[] {60, 63, 67, 70});
		DefineChord("Dm7", Shift("Cm7", 2));
		DefineChord("Em7", Shift("Cm7", 4));
		DefineChord("Fm7", Shift("Cm7", 5));
		DefineChord("Gm7", Shift("Cm7", 7));
		DefineChord("Am7", Shift("Cm7", 9));
		DefineChord("Bm7", Shift("Cm7", 11));

		DefineChord("Bmb5", new int[] {71, 74, 77});
		DefineChord("Bm7b5", new int[]{71, 74, 77, 81});

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

	public void PlayChord(String s) {
		try {
			int notes[] = chordnotes.get(s);
			if (notes != null) {
				for (int i = 0; i < notes.length; i++) {
					daw_.NoteOn(notes[i], 100);
				}
			}
			Thread.sleep(1500);
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

	int[] Shift(String base, int offset) {
		int notes[] = chordnotes.get(base).clone();
		for (int i = 0; i < notes.length; i++) {
			notes[i] += offset;
		}
		return notes;
	}

}
