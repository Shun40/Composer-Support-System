package engine;

import java.util.ArrayList;
import java.util.List;

import gui.component.pianoroll.note.NoteModel;

public class Accompaniment {
	private List<NoteModel> pianoPart;
	private List<NoteModel> bassPart;
	private List<NoteModel> drumPart;

	public Accompaniment() {
		pianoPart = new ArrayList<NoteModel>();
		bassPart = new ArrayList<NoteModel>();
		drumPart = new ArrayList<NoteModel>();
	}

	public List<NoteModel> getPianoPart() {
		return pianoPart;
	}

	public void setPianoPart(List<NoteModel> pianoPart) {
		this.pianoPart = pianoPart;
	}

	public List<NoteModel> getBassPart() {
		return bassPart;
	}

	public void setBassPart(List<NoteModel> bassPart) {
		this.bassPart = bassPart;
	}

	public List<NoteModel> getDrumPart() {
		return drumPart;
	}

	public void setDrumPart(List<NoteModel> drumPart) {
		this.drumPart = drumPart;
	}
}
