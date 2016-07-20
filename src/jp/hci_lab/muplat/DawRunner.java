package jp.hci_lab.muplat;


public class DawRunner {
	public static void main(String[] args) {
		try {

			System.out.println("Hello DAW!");
			
			DAW daw = new DAW();
	
			daw.score.SetTrack(0);
			daw.score.AddNote(480,  60,  480);
			daw.score.AddNote(960,  62,  480);
			daw.score.AddNote(1440,  64,  480);

			daw.controller.Start();
	
			// -------------------------------------------
			System.out.println("hit return key to play note");
			System.in.read();			
			daw.track[0].NoteOn(72, 127);
			System.out.println("hit return key to stop note");
			System.in.read();		
			daw.track[0].NoteOff(72);
			
			daw.controller.Stop();	
			System.out.println("Bye!");

		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
