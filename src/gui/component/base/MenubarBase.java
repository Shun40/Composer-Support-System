package gui.component.base;

import javafx.scene.control.MenuBar;

/**
 * メニューバーの基底クラス
 * @author Shun Yamashita
 */
public class MenubarBase extends MenuBar {
	public MenubarBase() {
		super();
	}

	public void setSize(int width, int height) {
		setPrefWidth(width);
		setPrefHeight(height);
	}
}
