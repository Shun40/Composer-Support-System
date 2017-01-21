package gui;
import static gui.constants.PatternAreaConstants.*;

import engine_yamashita.PredictionPattern;
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
public class PatternSelector extends ListView<PredictionPattern> {
	private ObservableList<PredictionPattern> patterns;
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
		setCellFactory(new Callback<ListView<PredictionPattern>, ListCell<PredictionPattern>>() {
			@Override
			public ListCell<PredictionPattern> call(ListView<PredictionPattern> arg0) {
				return new CustomCell();
			}
		});
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends PredictionPattern> ov, PredictionPattern oldVal, PredictionPattern newVal)->{
				if(newVal != null) parent.arrange(newVal);
			}
		);
	}

	public void addList(PredictionPattern predictionPattern) {
		patterns.add(predictionPattern);
	}

	/*
	 * ListView<ArrangePattern>表示用セルのクラス
	 */
	private static class CustomCell extends ListCell<PredictionPattern> {
		@Override
		protected void updateItem(PredictionPattern me, boolean empty) {
			super.updateItem(me, empty);
			if(!empty) {
				setText(me.getName());
			}
		}
	}
}