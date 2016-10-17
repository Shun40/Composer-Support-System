import static constants.MenubarConstants.*;

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
		Menu menu = new Menu("File");
		MenuItem item1 = new MenuItem("Read");
		MenuItem item2 = new MenuItem("Save");
		MenuItem item3 = new MenuItem("Close");
		MenuItem separator = new SeparatorMenuItem();

		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.read();
			}
		});
		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.save();
			}
		});
		item3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});

		menu.getItems().addAll(item1, item2, separator, item3);
		getMenus().addAll(menu);
	}
}
