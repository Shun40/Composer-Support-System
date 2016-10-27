import static constants.UniversalConstants.*;

/**
 * ノートが持つ音楽的情報のクラス
 * @author Shun Yamashita
 */
public class NoteInformation {
	private int trackNumber; // Track Number (1 ~ 16)
	private int progNumber;  // General MIDI Program Number (1 ~ 128)
	private int noteNumber;
	private int position;    // NoteOn Position
	private int duration;
	private int velocity;
	private Note parent;

	public NoteInformation(int trackNumber, int progNumber, int noteNumber, int position, int duration, int velocity, Note parent) {
		this.trackNumber = trackNumber;
		this.progNumber  = progNumber;
		this.noteNumber  = noteNumber;
		this.position    = position;
		this.duration    = duration;
		this.velocity    = velocity;
		this.parent      = parent;
	}

	public void updateNoteInfo() {
		int x = (int)(parent.getX() - 0.5);
		int y = (int)(parent.getY() - 0.5);
		int oldNoteNumber = noteNumber;
		int newNoteNumber = (int)((MAX_OCTAVE + 2) * 12 - (y / parent.getHeight()) - 1);
		position = 240 * (x / 10); // 240 is number of tick of 1/16 musical note
		duration = (int)((parent.getWidth() / BEAT_WIDTH) * 960); // 960 is number of tick of 1 measure
		// 音高が変化したら再発音
		if(oldNoteNumber != newNoteNumber) {
			// 無発音
			parent.toneOff(trackNumber);
			// 発音
			parent.toneOn(trackNumber, progNumber, noteNumber, velocity);
		}
		noteNumber = newNoteNumber;
	}

	public int getTrackNumber() { return  trackNumber; }
	public int getProgNumber() { return  progNumber; }
	public int getNoteNumber() { return noteNumber; }
	public int getPosition() { return position; }
	public int getDuration() { return duration; }
	public int getVelocity() { return velocity; }
}
