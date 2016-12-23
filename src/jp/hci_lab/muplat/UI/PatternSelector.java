import static constants.PatternAreaConstants.*;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * パターンを指定するリストビューのクラス
 * @author Shun Yamashita
 */
public class PatternSelector extends ListView<ArrangePattern> {
	private ObservableList<ArrangePattern> patterns;
	private PatternArea parent;

	public PatternSelector(int x, int y, PatternArea parent) {
		super();
		this.parent = parent;
		setupSize(PATTERN_SELECTOR_WIDTH, PATTERN_SELECTOR_HEIGHT);
		setupPoint(x, y);
		setupList();
		setupEventListener();
	}

	public void setupSize(int w, int h) {
		setPrefWidth(w);
		setPrefHeight(h);
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupList() {
		patterns = FXCollections.observableArrayList();
		setItems(patterns);
		setCellFactory(new Callback<ListView<ArrangePattern>, ListCell<ArrangePattern>>() {
			@Override
			public ListCell<ArrangePattern> call(ListView<ArrangePattern> arg0) {
				return new CustomCell();
			}
		});
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends ArrangePattern> ov, ArrangePattern oldVal, ArrangePattern newVal)->{
				parent.arrange(newVal);
			}
		);
	}

	public void addList(ArrangePattern arrangePattern) {
		patterns.add(arrangePattern);
	}

	/*
	 * ListView<ArrangePattern>表示用セルのクラス
	 */
	private static class CustomCell extends ListCell<ArrangePattern> {
		@Override
		protected void updateItem(ArrangePattern me, boolean empty) {
			super.updateItem(me, empty);
			if(!empty) {
				setText(me.getPatternName());
			}
		}
	}
}