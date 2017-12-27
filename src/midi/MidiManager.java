package midi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * シーケンサやノートイベントリストを持つクラス
 * @author Shun Yamashita
 */
public class MidiManager {
	private Sequencer sequencer;
	private Sequence sequence;
	private Map<Integer, List<MidiEvent>> events;

	public MidiManager() {
		setupSequencer();
		events = new HashMap<Integer, List<MidiEvent>>();
	}

	/**
	 * シーケンサをセットアップする
	 */
	private void setupSequencer() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, MidiConstants.PPQ);
			for(int t = 0; t < 16; t++) {
				sequence.createTrack();
			}
			sequencer.setSequence(sequence);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * シーケンサのBPMを設定する
	 * @param bpm 設定するBPM値
	 */
	public void setBpmToSequencer(int bpm) {
		MidiEvent bpmChangeEvent = MidiUtil.createBpmChangeEvent(bpm);
		for(Track track : sequence.getTracks()) {
			track.add(bpmChangeEvent);
		}
	}

	/**
	 * シーケンサへノートイベントを追加する
	 * @param id ノートのID
	 * @param track ノートのトラック番号
	 * @param program ノートのプログラム番号
	 * @param pitch ノートのノート番号
	 * @param position ノートの発音位置
	 * @param duration ノートの発音時間長
	 * @param velocity ノートのべロシティ
	 */
	public void addNoteToSequencer(int id, int track, int program, int pitch, int position, int duration, int velocity) {
		// 既に登録済みのイベントが編集されて渡された場合
		if(events.containsKey(id)) {
			removeNoteFromSequencer(id, track); // 登録済みのイベントを一回消してから追加し直す
		}
		try {
			MidiEvent programChangeEvent = MidiUtil.createProgramChangeEvent(track, program, position);
			MidiEvent noteOnEvent = MidiUtil.createNoteOnEvent(track, pitch, position, velocity);
			MidiEvent noteOffEvent = MidiUtil.createNoteOffEvent(track, pitch, position, duration);
			sequence.getTracks()[track - 1].add(programChangeEvent);
			sequence.getTracks()[track - 1].add(noteOnEvent);
			sequence.getTracks()[track - 1].add(noteOffEvent);
			events.put(id, new ArrayList<MidiEvent>(Arrays.asList(programChangeEvent, noteOnEvent, noteOffEvent)));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * シーケンサからノートイベントを削除する
	 * @param id ノートのID
	 * @param track ノートのトラック番号
	 */
	public void removeNoteFromSequencer(int id, int track) {
		sequence.getTracks()[track - 1].remove(events.get(id).get(0)); // プログラムチェンジイベント削除
		sequence.getTracks()[track - 1].remove(events.get(id).get(1)); // ノートオンイベント削除
		sequence.getTracks()[track - 1].remove(events.get(id).get(2)); // ノートオフイベント削除
		events.remove(id);
	}

	/**
	 * シーケンサから全てのノートイベントを削除する
	 */
	public void clearNoteFromSequencer() {
		for(Track track : sequence.getTracks()) {
			List<MidiEvent> allEvents = new ArrayList<MidiEvent>();
			for(int n = 0; n < track.size(); n++) {
				allEvents.add(track.get(n));
			}
			for(MidiEvent event : allEvents) {
				track.remove(event);
			}
		}
		events.clear();
	}

	/**
	 * シーケンスを再生する
	 */
	public void play(int playPosition) {
		sequencer.setTickPosition((long)(sequence.getResolution() * 4) * (playPosition - 1));
		try {
			sequencer.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * シーケンスを停止する
	 */
	public void stop() {
		if(sequencer.isRunning()) {
			sequencer.stop();
		}
		sequencer.setTickPosition(0);
	}

	/**
	 * シーケンサを閉じる
	 */
	public void close() {
		sequencer.close();
	}

	/**
	 * シーケンスをMIDI形式のファイルとして書き込む
	 */
	public void writeMidiFile() {
		final FileChooser fc = new FileChooser();
		fc.setTitle("MIDIファイルに保存");
		fc.setInitialFileName((new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())) + ".mid");
		fc.getExtensionFilters().addAll(
			new ExtensionFilter("MIDI Files", "*.mid"),
			new ExtensionFilter("All Files", "+.+")
		);
		File saveFile = fc.showSaveDialog(null);
		if(saveFile != null) {
			try {
				MidiSystem.write(sequence, 1, saveFile); // タイプはマルチトラックフォーマットの1を指定
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
