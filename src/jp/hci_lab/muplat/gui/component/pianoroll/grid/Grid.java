package gui.component.pianoroll.grid;
import gui.component.pianoroll.Pianoroll;
import system.AppConstants;

/**
 * ノートを配置するグリッド
 * グリッドの情報とGUIのクラスを持つ
 * @author Shun Yamashita
 */
public class Grid {
	private GridModel model;
	private GridView view;
	private Pianoroll owner;

	public Grid(int pitch, int resolution, AppConstants.AvailabilityType availabilityType, AppConstants.KeyType keyType, int x, int y, int width, int height, Pianoroll owner) {
		this.owner = owner;
		model = new GridModel(pitch, resolution);
		view = new GridView(availabilityType, keyType, x, y, width, height, this);
	}

	public void putNote(int x, int y, int width, int height, boolean isPronounceable) {
		int pitch = model.getPitch();
		owner.putNote(pitch, x, y, width, height, isPronounceable);
	}

	public int getCurrentTrack() {
		return owner.getCurrentTrack();
	}

	public void setResolution(int resolution) {
		model.setResolution(resolution);
	}

	public GridModel getModel() {
		return model;
	}

	public GridView getView() {
		return view;
	}
}
