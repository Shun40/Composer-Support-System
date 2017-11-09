package gui.component.base;

import javafx.scene.control.ListView;

public class ListViewBase<T> extends ListView<T> {
	public ListViewBase() {
		super();
	}

	public void setPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setSize(int w, int h) {
		setPrefWidth(w);
		setPrefHeight(h);
	}
}
