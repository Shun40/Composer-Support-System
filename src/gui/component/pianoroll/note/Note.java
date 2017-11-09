package gui.component.pianoroll.note;

import gui.component.pianoroll.Pianoroll;

/**
 * ノート
 * ノートの情報とGUIのクラスを持つ
 * @author Shun Yamashita
 */
public class Note {
	private NoteModel model;
	private NoteView view;
	private Pianoroll owner;

	public Note(int track, int program, int pitch, int position, int duration, int velocity, int x, int y, int width, int height, boolean isPronounceable, Pianoroll owner) {
		this.owner = owner;
		model = new NoteModel(track, program, pitch, position, duration, velocity);
		view = new NoteView(x, y, width, height, isPronounceable, this);
	}

	public void updateNote(int pitch, int position, int duration) {
		model.setPitch(pitch);
		model.setPosition(position);
		model.setDuration(duration);
	}

	public void addNoteToPianoroll() {
		owner.addNoteToPianoroll(this);
	}

	public void addNoteToSequencer() {
		owner.addNoteToSequencer(this);
	}

	public void removeNoteFromPianoroll() {
		owner.removeNoteFromPianoroll(this);
	}

	public void removeNoteFromSequencer() {
		owner.removeNoteFromSequencer(this);
	}

	public int getResolution() {
		return owner.getResolution();
	}

	public NoteModel getModel() {
		return model;
	}

	public NoteView getView() {
		return view;
	}
}
