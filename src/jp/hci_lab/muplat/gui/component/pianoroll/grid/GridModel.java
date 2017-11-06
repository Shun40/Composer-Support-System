package gui.component.pianoroll.grid;

public class GridModel {
	private final int pitch;
	private int resolution;

	public GridModel(int pitch, int resolution) {
		this.pitch = pitch;
		this.resolution = resolution;
	}

	public int getPitch() {
		return pitch;
	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}
}
