package file;

import java.util.List;

import gui.component.pianoroll.note.NoteModel;

public class MupFileData {
	private int bpm;
	private List<NoteModel> noteModels;

	public MupFileData(int bpm, List<NoteModel> noteModels) {
		this.bpm = bpm;
		this.noteModels = noteModels;
	}

	public int getBpm() {
		return bpm;
	}

	public List<NoteModel> getNoteModels() {
		return noteModels;
	}
}
