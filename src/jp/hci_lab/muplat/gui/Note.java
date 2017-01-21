package gui;
import static gui.constants.UniversalConstants.*;

/**
 * ノートが持つ音楽的情報のクラス
 * @author Shun Yamashita
 */
public class Note {
	private int track;    // Track Number (1 ~ 16)
	private int program;  // General MIDI Program Number (1 ~ 128)
	private int pitch;    // Note Number (0 ~ 127)
	private int position; // NoteOn Time Position
	private int duration; // Duration of Note
	private int velocity; // Velocity of Note
	private NoteBlock parent;

	public Note(int track, int program, int pitch, int position, int duration, int velocity, NoteBlock parent) {
		this.track    = track;
		this.program  = program;
		this.pitch    = pitch;
		this.position = position;
		this.duration = duration;
		this.velocity = velocity;
		this.parent   = parent;
	}

	public void updateNoteInfo() {
		int x = (int)(parent.getX() - 0.5);
		int y = (int)(parent.getY() - 0.5);
		int oldPitch = pitch;
		int newPitch = (int)((MAX_OCTAVE + 2) * 12 - (y / parent.getHeight()) - 1);
		position = (PPQ / 4) * (x / 10);
		duration = (int)((parent.getWidth() / BEAT_WIDTH) * PPQ);
		// 音高が変化したら再発音
		if(oldPitch != newPitch) {
			// 無発音
			parent.toneOff(track);
			// 発音
			parent.toneOn(track, program, newPitch, velocity);
		}
		pitch = newPitch;
	}

	public int getTrack() { return track; }
	public int getProgram() { return program; }
	public int getPitch() { return pitch; }
	public int getPosition() { return position; }
	public int getDuration() { return duration; }
	public int getVelocity() { return velocity; }
}
