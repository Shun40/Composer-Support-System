package gui.component.base;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

/**
 * チョイスボックスの基底クラス
 * @author Shun Yamashita
 */
public class ChoiceBoxBase<T>extends ChoiceBox<T> {
	public ChoiceBoxBase() {
		super();
	}

	public void setup(ObservableList<T> items, T value) {
		setItems(items);
		setValue(value);
	}

	public void setPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}
}
