package gui.component.keyboard.key;

import system.AppConstants;

/**
 * 鍵盤
 * 鍵盤の情報とGUIのクラスを持つ
 * @author Shun Yamashita
 */
public class Key {
	private KeyModel model;
	private KeyView view;

	public Key(int pitch, AppConstants.AvailabilityType availabilityType, AppConstants.KeyType keyType, int x, int y, int width, int height) {
		model = new KeyModel(pitch);
		view = new KeyView(availabilityType, keyType, x, y, width, height, this);
	}

	public KeyModel getModel() {
		return model;
	}

	public KeyView getView() {
		return view;
	}
}
