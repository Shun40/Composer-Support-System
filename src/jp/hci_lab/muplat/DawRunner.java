package jp.hci_lab.muplat;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
 
public class DawRunner extends Application {
	DAW daw;
	
    public static void main(String[] args) {
    	launch(args);
    }
 
    @Override
    public void start(Stage stage) throws Exception {
		daw = new DAW();
		System.out.println("Lounch DAW");

		daw.score.SetTrack(0);
		daw.score.AddNote(480 * 1,  60,  480);
		daw.score.AddNote(480 * 2,  62,  480);
		daw.score.AddNote(480 * 3,  64,  480);
		daw.score.AddNote(480 * 4,  65,  480);
		daw.score.AddNote(480 * 5,  67,  480);
		daw.score.AddNote(480 * 6,  69,  480);
		daw.score.AddNote(480 * 7,  71,  480);
		daw.score.AddNote(480 * 8,  72,  480);
		daw.score.SetTrack(1);
		daw.score.AddNote(480 * 1,  64,  480);
		daw.score.AddNote(480 * 2,  65,  480);
		daw.score.AddNote(480 * 3,  67,  480);
		daw.score.AddNote(480 * 4,  69,  480);
		daw.score.AddNote(480 * 5,  71,  480);
		daw.score.AddNote(480 * 6,  72,  480);
		daw.score.AddNote(480 * 7,  74,  480);
		daw.score.AddNote(480 * 8,  76,  480);
		
		// グリッドの作成
		Group pane = new Group();

		// メニューの作成
		MenuBar menu = new MenuBar();
		menu.setPrefWidth(2000);
		menu.setStyle("-fx-background-color: darkgrey");
		// file menu
		Menu fileMenu = new Menu("_File");
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction((ActionEvent t) -> {
			ExitProc();
		    System.exit(0);
		});
		fileMenu.getItems().addAll(exit);
		// edit menu
		Menu editMenu = new Menu("_Edit");
		// set submenu
		menu.getMenus().addAll(fileMenu, editMenu);
		pane.getChildren().add(menu);		
		
		// BPMボックスの作成
		// label
    	Label labelBPM = new Label("BPM");
    	labelBPM.setLayoutX(160);
    	labelBPM.setLayoutY(60);
        pane.getChildren().add(labelBPM);
        // text field
		TextField textBPM = new TextField ();
		textBPM.setPrefWidth(50);
		textBPM.setLayoutX(200);
		textBPM.setLayoutY(60);
		textBPM.setText("120");
        pane.getChildren().add(textBPM);
		
		// STOPボタン
        Button stopbtn = new Button("◾︎︎");
        stopbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				daw.controller.Stop();
            }
        });
        stopbtn.setLayoutX(30);
        stopbtn.setLayoutY(60);
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
        startbtn.setLayoutY(60);
        pane.getChildren().add(startbtn);
        
        // 音色選択ドロップダウン
		ObservableList<String> names = FXCollections.observableArrayList(daw.track[0].GetInstrumentList());
		ComboBox<String> instSelector = new ComboBox<String>(names);
		instSelector.setLayoutX(400);
		instSelector.setLayoutY(60);
		instSelector.getSelectionModel().selectedItemProperty().addListener((r, o, val) -> {
	        if (val == null) {
	        } else {
	        	int item = names.indexOf(val);
	        	daw.SetInstrument(item);
	        }
	    });
		instSelector.setValue(names.get(0));
		pane.getChildren().add(instSelector);
        
        // 鍵盤
        MakeKeyboard(400, 120, pane);

        // ミキサー
        for (int i = 0; i < 16; i++) {
        	MakeTrackControl(i, pane);
        }
        
        
        // 終了時コールバック
        stage.setOnCloseRequest((WindowEvent event) -> {
        	ExitProc();
        });

        // シーンの作成
        Scene scene = new Scene(pane, 800, 600, Color.GRAY);
        stage.setScene(scene);
        
        // 画面起動
        stage.show(); 
    }
    
    void ExitProc() {
		daw.CloseProject();
		System.out.println("Bye!");    	
    }

    public void MakeTrackControl(int n, Group g) {
    	int x = 30 + n * 70;
    	int y = 300;
    	
    	ToggleButton solo = new ToggleButton("S");
		solo.setStyle("-fx-background-color: lightgrey");         		
        solo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if (solo.isSelected()) {
            		solo.setStyle("-fx-background-color: red");
            	} else {
            		solo.setStyle("-fx-background-color: lightgrey");         		
            	}
				daw.mixer.SetSolo(n, solo.isSelected());
            }
        });
    	solo.setMaxSize(16, 16);
        solo.setLayoutX(x);
        solo.setLayoutY(y);
        g.getChildren().add(solo);

    	ToggleButton mute = new ToggleButton("M");
		mute.setStyle("-fx-background-color: lightgrey");         		
        mute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if (mute.isSelected()) {
            		mute.setStyle("-fx-background-color: red");
            	} else {
            		mute.setStyle("-fx-background-color: lightgrey");         		
            	}
				daw.mixer.SetMute(n, mute.isSelected());
            }
        });
    	mute.setMaxSize(16, 16);
    	mute.setLayoutX(x + 30);
    	mute.setLayoutY(y);
        g.getChildren().add(mute);
    	
    	Slider s = new Slider();
        s.setOrientation(Orientation.VERTICAL);
        s.setPrefHeight(150);
        s.setShowTickMarks(false);
        s.setShowTickLabels(false);
        s.setValue(80);
		s.setLayoutX(x + 20);
		s.setLayoutY(y + 30);
        g.getChildren().add(s);
    }
    
    void MakeKeyboard(int x, int y, Group g) {    	
    	MakeWhiteKey(x + 40 * 0, y, 60, g);
    	MakeWhiteKey(x + 40 * 1, y, 62, g);
    	MakeWhiteKey(x + 40 * 2, y, 64, g);
    	MakeWhiteKey(x + 40 * 3, y, 65, g);
    	MakeWhiteKey(x + 40 * 4, y, 67, g);
    	MakeWhiteKey(x + 40 * 5, y, 69, g);
    	MakeWhiteKey(x + 40 * 6, y, 71, g);
    	MakeWhiteKey(x + 40 * 7, y, 72, g);

    	MakeBlackKey(x + 20 + 40 * 0, y, 61, g);
    	MakeBlackKey(x + 20 + 40 * 1, y, 63, g);
    	MakeBlackKey(x + 20 + 40 * 3, y, 66, g);
    	MakeBlackKey(x + 20 + 40 * 4, y, 68, g);
    	MakeBlackKey(x + 20 + 40 * 5, y, 70, g);    	
    }
    
    void MakeWhiteKey(int x, int y, int note, Group g) {
    	Rectangle rect = new Rectangle(x, y, 32, 100);
        rect.setFill(Color.WHITE);
        g.getChildren().add(rect);
        
        rect.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
				daw.NoteOn(note, 127);
                rect.setFill(Color.LIGHTGRAY);
                event.consume();
            }
        });     

        rect.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
				daw.NoteOff(note);
                rect.setFill(Color.WHITE);
                event.consume();
            }
        });
    }
    void MakeBlackKey(int x, int y, int note, Group g) {
    	Rectangle rect = new Rectangle(x, y, 32, 70);
        rect.setFill(Color.BLACK);
        g.getChildren().add(rect);    	
        
        rect.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
				daw.NoteOn(note, 127);
                rect.setFill(Color.DIMGRAY);
                event.consume();
            }
        });     

        rect.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
				daw.NoteOff(note);
                rect.setFill(Color.BLACK);
                event.consume();
            }
        });

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