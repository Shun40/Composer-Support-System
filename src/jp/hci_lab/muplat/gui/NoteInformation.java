package gui;
import static gui.constants.UniversalConstants.*;

/**
 * ノートが持つ音楽的情報のクラス
 * @author Shun Yamashita
 */
public class NoteInformation {
	private int track;    // Track Number (1 ~ 16)
	private int program;  // General MIDI Program Number (1 ~ 128)
	private int note;     // Note Number (0 ~ 127)
	private int position; // NoteOn Time Position
	private int duration; // Duration of Note
	private int velocity; // Velocity of Note
	private NoteBlock parent;

	public NoteInformation(int track, int program, int note, int position, int duration, int velocity, NoteBlock parent) {
		this.track    = track;
		this.program  = program;
		this.note     = note;
		this.position = position;
		this.duration = duration;
		this.velocity = velocity;
		this.parent   = parent;
	}

	public void updateNoteInfo() {
		int x = (int)(parent.getX() - 0.5);
		int y = (int)(parent.getY() - 0.5);
		int oldNote = note;
		int newNote = (int)((MAX_OCTAVE + 2) * 12 - (y / parent.getHeight()) - 1);
		position = 240 * (x / 10); // 240 is number of tick of 1/16 musical note
		duration = (int)((parent.getWidth() / BEAT_WIDTH) * 960); // 960 is number of tick of 1 measure
		// 音高が変化したら再発音
		if(oldNote != newNote) {
			// 無発音
			parent.toneOff(track);
			// 発音
			parent.toneOn(track, program, note, velocity);
		}
		note = newNote;
	}

	public int getTrack() { return track; }
	public int getProgram() { return program; }
	public int getNote() { return note; }
	public int getPosition() { return position; }
	public int getDuration() { return duration; }
	public int getVelocity() { return velocity; }
}
