package jp.hci_lab.muplat;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
 
public class DawRunner extends Application {
    public static void main(String[] args) {
    	launch(args);
    }
 
    @Override
    public void start(Stage stage) throws Exception {
		DAW daw = new DAW();
		System.out.println("Lounch DAW");

		daw.score.SetTrack(0);
		daw.score.AddNote(480,  60,  480);
		daw.score.AddNote(960,  62,  480);
		daw.score.AddNote(1440,  64,  480);
		
		// グリッドの作成
		Group pane = new Group();
		
		// ラベルの作成
//    	Label label = new Label("This is JavaFX!");
//        grid.add(label,  1,  1);

		// STOPボタン
        Button stopbtn = new Button("◾︎︎");
        stopbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				daw.controller.Stop();
            }
        });
        stopbtn.setLayoutX(30);
        stopbtn.setLayoutY(30);
        pane.getChildren().add(stopbtn);

        // STARTボタン
        Button startbtn = new Button("▶︎");
        startbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				daw.controller.Restart();
            }
        });
        startbtn.setLayoutX(60);
        startbtn.setLayoutY(30);
        pane.getChildren().add(startbtn);
        
        // 音色選択ドロップダウン
		ObservableList<String> names = FXCollections.observableArrayList(daw.track[0].GetInstrumentList());
		ComboBox<String> instSelector = new ComboBox<String>(names);
		instSelector.setLayoutX(120);
		instSelector.setLayoutY(30);
        pane.getChildren().add(instSelector);
        
        stage.setOnCloseRequest((WindowEvent event) -> {
			daw.CloseProject();
			System.out.println("Bye!");
        });

        // シーンの作成
        Scene scene = new Scene(pane, 640, 480);
        stage.setScene(scene);
        
        // 画面起動
        stage.show(); 
    }
 
}

/*
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
				System.out.println("[p:Play  m:FileLoad  s:FileSave  t:Stop  ' ':Pause  o:NoteOn  f:NoteOff  q:quit]");
				System.out.print("> ");
				int c = System.in.read();		

				switch (c) {
				case '0':
					daw.track[0].SetInstrument(0);
					break;
				case '1':
					daw.track[0].SetInstrument(1);
					break;
				case '2':
					daw.track[0].SetInstrument(2);
					break;
				case '3':
					daw.track[0].SetInstrument(3);
					break;
				case '4':
					daw.track[0].SetInstrument(4);
					break;
				case 'p':
					daw.controller.Restart();
					break;
				case 'm':
					daw.OpenProject("spain.mid");
					break;
				case 's':
					daw.SaveProject("save.mid");
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
					daw.CloseProject();
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
*/