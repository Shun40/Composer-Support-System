package jp.hci_lab.muplat;

import java.io.File;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
 
public class DawRunner extends Application {
	DAW daw;

	Label lblNumEvent[];
	
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
		MenuItem open = new MenuItem("Open");
		open.setOnAction((ActionEvent t) -> {
			// ファイル入出力
			FileChooser fc = new FileChooser();
            File inFile = fc.showOpenDialog(stage);
            if (inFile != null) {
                daw.OpenProject(inFile.getPath().toString());
                UpdateNumEvent();
            }
        });
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction((ActionEvent t) -> {
			ExitProc();
		    System.exit(0);
		});
		fileMenu.getItems().addAll(open, exit);
		// edit menu
		Menu editMenu = new Menu("_Edit");
		// set submenu
		menu.getMenus().addAll(fileMenu, editMenu);
		pane.getChildren().add(menu);		
			
		// BPMボックスの作成
		// label
    	Label labelBPM = new Label("BPM");
    	labelBPM.setLayoutX(200);
    	labelBPM.setLayoutY(65);
        pane.getChildren().add(labelBPM);
        // text field
		TextField textBPM = new TextField ();
		textBPM.setPrefWidth(50);
		textBPM.setLayoutX(235);
		textBPM.setLayoutY(60);
		textBPM.setText("120");
        pane.getChildren().add(textBPM);
		
		// STOPボタン
        Button stopbtn = new Button("STOP");
        stopbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				daw.controller.Stop();
            }
        });
		stopbtn.setPrefWidth(70);
        stopbtn.setLayoutX(30);
        stopbtn.setLayoutY(60);
        pane.getChildren().add(stopbtn);

        // STARTボタン
        Button startbtn = new Button("START︎");
        startbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				daw.controller.Restart();
            }
        });
		startbtn.setPrefWidth(70);
        startbtn.setLayoutX(110);
        startbtn.setLayoutY(60);
        pane.getChildren().add(startbtn);

        // Chord Playボタン
        TextArea araChord = new TextArea("C Am Dm7 G7 C Am Dm7 G7");
        araChord.setPrefWidth(200);
        araChord.setPrefHeight(100);
        araChord.setLayoutX(30);
        araChord.setLayoutY(155);
        pane.getChildren().add(araChord);

        Button chordplaybtn = new Button("Chord Play");
        chordplaybtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	daw.chordplayer.PlayChordProgression(araChord.getText());
            }
        });
        chordplaybtn.setPrefWidth(100);
        chordplaybtn.setLayoutX(30);
        chordplaybtn.setLayoutY(120);
        pane.getChildren().add(chordplaybtn);
        
        
        
        
        
        
        // 音色選択ドロップダウン
		ObservableList<String> names = FXCollections.observableArrayList(daw.GetInstrumentList());
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
    	lblNumEvent = new Label[16];
        for (int i = 0; i < 16; i++) {
        	MakeTrackControl(i, pane);
    		// label
        	lblNumEvent[i] = new Label("0");
        	lblNumEvent[i].setLayoutX(50 + i * 70);
        	lblNumEvent[i].setLayoutY(380);
            pane.getChildren().add(lblNumEvent[i]);    	
        }        
        UpdateNumEvent();
        
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
    	int y = 400;

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

    void UpdateNumEvent() {
    	for (int i = 0; i < 16; i++) {
    		int n = daw.track[i].GetEventCount();
    		lblNumEvent[i].setText(String.valueOf(n));
    	}
    }

}
