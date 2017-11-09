package gui.component.base;

import javafx.scene.Group;

/**
 * グループの基底クラス
 * @author Shun Yamashita
 */
public class GroupBase extends Group {
	public GroupBase() {
		super();
	}

	public void setPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}
}
