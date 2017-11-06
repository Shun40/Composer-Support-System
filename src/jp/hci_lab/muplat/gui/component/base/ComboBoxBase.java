package gui.component.base;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * コンボボックスの基底クラス
 * @author Shun Yamashita
 */
public class ComboBoxBase<T>extends ComboBox<T> {
	public ComboBoxBase() {
		super();
	}

	public void setup(ObservableList<T> items, T value) {
		setItems(items);
		setValue(value);
		setEditable(false);
	}

	public void setPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}
}
