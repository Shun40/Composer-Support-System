package jp.hci_lab.muplat;

import java.util.List;

public class DawRunner {
	public static void main(String[] args) {
		try {

			System.out.println("Hello DAW!");

			DAW daw = new DAW();

			// MIDIデバイス名の一覧出力
			System.out.println("MIDI Devices:");
			List<String>devices = daw.config.GetMidiDeviceNameList();
			for (String s: devices) {
				System.out.println("  [" + s + "]");
			}
			
			daw.SetBPM(250);
			
			daw.score.SetTrack(0);
			daw.score.AddNote(480,  60,  480);
			daw.score.AddNote(960,  62,  480);
			daw.score.AddNote(1440,  64,  480);

			boolean loop = true;
			while (loop) {
				System.out.println("[p:Play  m:Midi t:Stop  o:NoteOn  f:NoteOff  q:quit]");
				System.out.print("> ");
				int c = System.in.read();		

				switch (c) {
				case 'p':
					daw.controller.Start();
					break;
				case 'm':
					daw.PlayMidiFile("spain.mid");
					break;
				case 't':
					daw.controller.Stop();
					break;
				case ' ':
					daw.controller.Pause();
					break;
				case 'o':
					daw.track[0].NoteOn(72, 127);
					break;
				case 'f':
					daw.track[0].NoteOff(72);
					break;
				case 'q':
					daw.Close();
					System.out.println("Bye!");
					loop = false;
					break;
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
