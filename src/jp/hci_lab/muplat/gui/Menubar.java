package gui;
import static gui.constants.MenubarConstants.*;

import gui.constants.UniversalConstants.Algorithm;
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
		Menu menu1 = new Menu("ファイル");
		MenuItem item1_1 = new MenuItem("MUPファイルを開く");
		MenuItem item1_2 = new MenuItem("MUPファイルに保存");
		MenuItem item1_3 = new MenuItem("MIDIファイルに保存");
		MenuItem item1_4 = new MenuItem("終了");
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
		menu1.getItems().addAll(item1_1, item1_2, item1_3, new SeparatorMenuItem(), item1_4);
		getMenus().addAll(menu1);

		Menu menu2 = new Menu("辞書");
		MenuItem item2_1 = new MenuItem("単語辞書ファイルを開く");
		MenuItem item2_2 = new MenuItem("単語辞書を表示");
		MenuItem item2_3 = new MenuItem("例文辞書ファイルを開く");
		MenuItem item2_4 = new MenuItem("例文辞書を表示");
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
		menu2.getItems().addAll(item2_1, item2_2, new SeparatorMenuItem(), item2_3, item2_4);
		getMenus().addAll(menu2);

		Menu menu3 = new Menu("アルゴリズム");
		MenuItem item3_1 = new MenuItem("予測変換");
		MenuItem item3_2 = new MenuItem("メロディ構造");
		MenuItem item3_3 = new MenuItem("予測変換 + メロディ構造");
		item3_1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.setAlgorithm(Algorithm.PC);
			}
		});
		item3_2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.setAlgorithm(Algorithm.MS);
			}
		});
		item3_3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.setAlgorithm(Algorithm.PC_AND_MS);
			}
		});
		menu3.getItems().addAll(item3_1, item3_2, item3_3);
		getMenus().addAll(menu3);
	}
}
