package gui.component;

import gui.GuiConstants;
import gui.GuiManager;
import gui.component.base.MenubarBase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import system.AppConstants;

/**
 * メニューバー
 * @author Shun Yamashita
 */
public class Menubar extends MenubarBase {
	private GuiManager owner;

	public Menubar(GuiManager owner) {
		super();
		this.owner = owner;
		setSize(GuiConstants.Menubar.WIDTH, GuiConstants.Menubar.HEIGHT);
		setupFileMenu();
		setupDictionaryMenu();
		setupChordProgressionMenu();
		setupDemoMenu();
	}

	public void setupFileMenu() {
		Menu menu = new Menu("ファイル");
		MenuItem item1 = new MenuItem("MUPファイルを開く");
		MenuItem item2 = new MenuItem("MUPファイルに保存");
		MenuItem item3 = new MenuItem("MIDIファイルに保存");
		MenuItem item4 = new MenuItem("終了");
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.readMupFile();
			}
		});
		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.writeMupFile();
			}
		});
		item3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.writeMidiFile();
			}
		});
		item4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});
		menu.getItems().addAll(item1, item2, item3, new SeparatorMenuItem(), item4);
		getMenus().addAll(menu);
	}

	public void setupDictionaryMenu() {
		Menu menu = new Menu("辞書");
		MenuItem item1 = new MenuItem("単語辞書ファイルを開く");
		MenuItem item2 = new MenuItem("単語辞書を表示");
		MenuItem item3 = new MenuItem("例文辞書ファイルを開く");
		MenuItem item4 = new MenuItem("例文辞書を表示");
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.readWordDictionary();
			}
		});
		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.showWordDictionary();
			}
		});
		item3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.readPhraseDictionary();
			}
		});
		item4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.showPhraseDictionary();
			}
		});
		menu.getItems().addAll(item1, item2, new SeparatorMenuItem(), item3, item4);
		getMenus().addAll(menu);
	}

	public void setupChordProgressionMenu() {
		Menu menu = new Menu("コード進行");
		MenuItem[] items = new MenuItem[AppConstants.ChordProgression.values().length];
		for(int n = 0; n < items.length; n++) {
			items[n] = new MenuItem(AppConstants.ChordProgression.values()[n].toString());
			items[n].setUserData(n);
			items[n].setMnemonicParsing(false);
			MenuItem self = items[n];
			items[n].setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					owner.setChordProgression((int)self.getUserData());
				}
			});
			menu.getItems().add(items[n]);
		}
		getMenus().addAll(menu);
	}

	public void setupDemoMenu() {
		Menu menu = new Menu("デモ");
		MenuItem item = new MenuItem("セットアップ");
		item.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				owner.setupDemo();
			}
		});
		menu.getItems().addAll(item);
		getMenus().addAll(menu);
	}
}
