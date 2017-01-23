package gui;
import static gui.constants.MenubarConstants.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * メニューバーのクラス
 * @author Shun Yamashita
 */
public class Menubar extends MenuBar {
	private MainScene parent;

	public Menubar(MainScene parent) {
		super();
		this.parent = parent;
		setupSize(MENUBAR_WIDTH, MENUBAR_HEIGHT);
		setupFileMenu();
	}

	public void setupSize(int w, int h) {
		setPrefWidth(w);
		setPrefHeight(h);
	}

	public void setupFileMenu() {
		Menu menu1 = new Menu("File");
		MenuItem item1_1 = new MenuItem("Read MUP File");
		MenuItem item1_2 = new MenuItem("Save MUP File");
		MenuItem item1_3 = new MenuItem("Save MIDI FILE");
		MenuItem item1_4 = new MenuItem("Close");
		MenuItem separator = new SeparatorMenuItem();
		item1_1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.read();
			}
		});
		item1_2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.save();
			}
		});
		item1_3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.saveMidiFile();
			}
		});
		item1_4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});
		menu1.getItems().addAll(item1_1, item1_2, item1_3, separator, item1_4);
		getMenus().addAll(menu1);

		Menu menu2 = new Menu("Dictionary");
		MenuItem item2_1 = new MenuItem("Read Word Dictionary File");
		MenuItem item2_2 = new MenuItem("Show Word Dictionary");
		MenuItem item2_3 = new MenuItem("Read Phrase Dictionary File");
		MenuItem item2_4 = new MenuItem("Show Phrase Dictionary");
		item2_1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.readWordDictionaryFile();
			}
		});
		item2_2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.showWordDictionary();
			}
		});
		item2_3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.readPhraseDictionaryFile();
			}
		});
		item2_4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.showPhraseDictionary();
			}
		});
		menu2.getItems().addAll(item2_1, item2_2, item2_3, item2_4);
		getMenus().addAll(menu2);
	}
}
