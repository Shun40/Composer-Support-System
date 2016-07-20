package jp.hci_lab.muplat;


public class DawRunner {
	public static void main(String[] args) {
		System.out.println("Hello DAW!");
		
		DAW daw = new DAW();

		daw.score.SetTrack(1);
		daw.score.AddNote(480,  60,  480);
		daw.score.AddNote(960,  62,  480);
		daw.score.AddNote(1440,  64,  480);
		
		daw.controller.Start();

		System.out.println("Bye!");
		
	}

}
