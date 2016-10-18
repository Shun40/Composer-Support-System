import static constants.UniversalConstants.*;

/**
 * ノートが持つ音楽的情報のクラス
 * @author Shun Yamashita
 */
public class NoteInformation {
	private int measure;
	private int beat;
	private int place;
	private int duration;
	private int interval;
	private int octave;
	private int position; // NoteOn Position
	private int velocity;
	private int noteNumber;
	private int progNumber; // General MIDI Program Number
	private Note parent;

	public NoteInformation(int measure, int beat, int place, int duration, int interval, int octave, int position, int velocity, int noteNumber, int progNumber, Note parent) {
		this.measure = measure;
		this.beat = beat;
		this.place = place;
		this.duration = duration;
		this.interval = interval;
		this.octave = octave;
		this.position = position;
		this.velocity = velocity;
		this.noteNumber = noteNumber;
		this.progNumber = progNumber;
		this.parent = parent;
	}

	public void updateNoteInfo() {
		int x = (int)(parent.getX() - 0.5);
		int y = (int)(parent.getY() - 0.5);
		int oldInterval = interval;
		int newInterval = 12 - (y % MEASURE_HEIGHT / 12 + 1);
		measure = (x / MEASURE_WIDTH + 1);
		beat = (x % MEASURE_WIDTH / BEAT_WIDTH + 1);
		place = (int)((x % BEAT_WIDTH * 0.1) * 240); // 240 is number of tick of 1/16 musical note
		duration = (int)((parent.getWidth() / BEAT_WIDTH) * 960); // 960 is number of tick of 1 measure
		interval = newInterval;
		octave = MAX_OCTAVE - (y / MEASURE_HEIGHT);
		position = (960 * 4) * (measure - 1) + 960 * (beat - 1) + place;
		noteNumber =  60 + (12 * (octave - 4)) + interval;
		// 音高が変化したら再発音
		if(oldInterval != newInterval) {
			// 無発音
			parent.toneOff();
			// 発音
			parent.toneOn(noteNumber, progNumber, velocity);
		}
	}

	public int getMeasure() { return measure; }
	public int getBeat() { return beat; }
	public int getPlace() { return place; }
	public int getDuration() { return duration; }
	public int getInterval() { return interval; }
	public int getOctave() { return octave; }
	public int getPosition() { return position; }
	public int getVelocity() { return velocity; }
	public int getNoteNumber() { return noteNumber; }
	public int getProgNumber() { return  progNumber; }
}
