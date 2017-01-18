package engine_yamashita.melody.generation;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * �������������f�B��R�[�h��MIDI���Đ�����N���X
 * @author Shun Yamashita
 */
public class Player {
	private Sequencer sequencer;
	private Sequence  sequence;

	public Player() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 480);
			for(int track = 0; track < 16; track++) { // 16�̃g���b�N����
				sequence.createTrack();
			}
			setBpm(120); // �f�t�H���g��BPM
			sequencer.setSequence(sequence);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * BPM���w�肷��
	 * @param bpm : 1���Ԃɑł��̉�
	 */
	public void setBpm(int bpm) {
		try {
			MetaMessage bpmChange = new MetaMessage();
			int l = 60 * 1000000 / bpm;
			bpmChange.setMessage(0x51, new byte[]{ (byte)(l / 65536), (byte)(l % 65536 / 256), (byte)(l % 256) }, 3);
			MidiEvent bpmChangeEvent = new MidiEvent(bpmChange, 0);
			for(Track track : sequence.getTracks()) {
				track.add(bpmChangeEvent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �w�肵���g���b�N�ɉ�����ǉ�����
	 * @param track      : ������炷�g���b�N�ԍ�
	 * @param program    : �y��ԍ�
	 * @param note       : �����ԍ�(MIDI�ԍ�)
	 * @param position   : ������炷�ʒu
	 * @param duration   : ������炷����
	 */
	public void addNote(int track, int program, int note, int position, int duration) {
		try {
			// �y��ύX�C�x���g�𐶐�
			ShortMessage programChange = new ShortMessage();
			programChange.setMessage(ShortMessage.PROGRAM_CHANGE, track - 1, program - 1, 0);
			MidiEvent programChangeEvent = new MidiEvent(programChange, position);

			// �m�[�g�I���C�x���g�𐶐�
			ShortMessage noteOn = new ShortMessage();
			noteOn.setMessage(ShortMessage.NOTE_ON, track - 1, note, 100);
			MidiEvent noteOnEvent = new MidiEvent(noteOn, position);

			// �m�[�g�I�t�C�x���g�𐶐�
			ShortMessage noteOff = new ShortMessage();
			noteOff.setMessage(ShortMessage.NOTE_OFF, track - 1, note, 0);
			MidiEvent noteOffEvent = new MidiEvent(noteOff, position + duration);

			// �V�[�P���X�ɃC�x���g�Q��ǉ�
			sequence.getTracks()[track - 1].add(programChangeEvent);
			sequence.getTracks()[track - 1].add(noteOnEvent);
			sequence.getTracks()[track - 1].add(noteOffEvent);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����f�B��ǉ�����
	 * @param midi     : �����f�B��MIDI�ԍ�
	 * @param program  : �y��ԍ�
	 * @param position : �R�[�h��炷�ʒu
	 * @param duration : �R�[�h��炷����
	 */
	public void addMelody(byte midi, int program, int position, int duration) {
		addNote(1, program, midi, position, duration);
	}

	/**
	 * �����f�B��ǉ�����(�����^�C�~���O�����߂┏, �������Ԓ���n�������Ŏw�肷��ꍇ�Ɏg��)
	 * @param midi       : �R�[�h�\������MIDI�ԍ��z��
	 * @param program    : �y��ԍ�
	 * @param measure    : �R�[�h��炷����
	 * @param beat       : �R�[�h��炷��
	 * @param separation : �R�[�h��炷����(separation = 4�Ƃ����4���������̒����ɂȂ�)
	 */
	public void addMelody(byte midi, int program, int measure, int beat, int separation) {
		int position = (480 * 4) * (measure - 1) + 480 * (beat - 1);
		int duration = (480 * 4) / separation;
		addMelody(midi, program, position, duration);
	}

	/**
	 * �R�[�h��ǉ�����
	 * @param midi     : �R�[�h�\������MIDI�ԍ��z��
	 * @param program  : �y��ԍ�
	 * @param position : �R�[�h��炷�ʒu
	 * @param duration : �R�[�h��炷����
	 */
	public void addChord(byte[] midi, int program, int position, int duration) {
		for(int n = 0; n < midi.length; n++) {
			addNote(2, program, midi[n], position, duration);
		}
	}

	/**
	 * �R�[�h��ǉ�����(�����^�C�~���O�����߂┏, �������Ԓ���n�������Ŏw�肷��ꍇ�Ɏg��)
	 * @param midi       : �R�[�h�\������MIDI�ԍ��z��
	 * @param program    : �y��ԍ�
	 * @param measure    : �R�[�h��炷����
	 * @param beat       : �R�[�h��炷��
	 * @param separation : �R�[�h��炷����(separation = 4�Ƃ����4���������̒����ɂȂ�)
	 */
	public void addChord(byte[] midi, int program, int measure, int beat, int separation) {
		int position = (480 * 4) * (measure - 1) + 480 * (beat - 1);
		int duration = (480 * 4) * (1 / separation);
		addChord(midi, program, position, duration);
	}

	public void play() {
		try {
			sequencer.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		if(sequencer.isRunning()) {
			sequencer.stop();
		}
		sequencer.setTickPosition(0);
	}

	public void close() {
		sequencer.close();
	}
}
