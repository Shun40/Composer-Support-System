import static constants.PatternAreaConstants.*;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * パターンを指定するリストビューのクラス
 * @author Shun Yamashita
 */
public class PatternSelector extends ListView<String> {
	private ObservableList<String> patterns;
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
		patterns.addAll("P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9", "P10");
		setItems(patterns);
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends String> ov, String oldVal, String newVal)->{
				System.out.println(newVal);
			}
		);
	}
}