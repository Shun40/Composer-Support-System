package engine.melody;

import java.util.ArrayList;

import gui.component.pianoroll.note.NoteModel;
import midi.MidiUtil;

/**
 * メロディのクラス
 * @author Shun Yamashita
 */
public class Melody extends ArrayList<NoteModel> {
	public Melody() {
		super();
	}

	public int getMinPitch() {
		int minPitch = 128;
		for(NoteModel note : this) {
			if(minPitch >= note.getPitch()) {
				minPitch = note.getPitch();
			}
		}
		return minPitch;
	}

	public int getMaxPitch() {
		int maxPitch = -1;
		for(NoteModel note : this) {
			if(maxPitch <= note.getPitch()) {
				maxPitch = note.getPitch();
			}
		}
		return maxPitch;
	}

	/**
	 * 指定した小節に含まれるメロディを返す
	 * @param measure 小節
	 * @return 指定小節に含まれるメロディ
	 */
	public Melody getIncludedInMeasure(int measure) {
		Melody melody = new Melody();
		for(int i = 0; i < size(); i++) {
			if(MidiUtil.getMeasure(get(i).getPosition()) == measure) {
				melody.add(get(i));
			}
		}
		// 一応発音位置の早い順にソートしておく
		if(!melody.isEmpty()) {
			melody.sort((a, b) -> a.getPosition() - b.getPosition());
		}
		return melody;
	}

	/**
	 * 発音位置が最後のノートを返す
	 * @return 発音位置が最後のノート
	 */
	public NoteModel getLastPosition() {
		NoteModel note = null;
		// 一応発音位置の早い順にソートしておく
		if(!isEmpty()) {
			sort((a, b) -> a.getPosition() - b.getPosition());
			note = get(size() - 1);
		}
		return note;
	}

	/**
	 * 指定した小節に含まれるメロディから相対メロディを抽出する
	 * @param measure 小節
	 * @return 指定小節に含まれるメロディから抽出した相対メロディ
	 */
	public RelativeMelody extractRelativeMelodyIncludedInMeasure(int measure) {
		// 対象小節のメロディを抽出
		Melody melody = getIncludedInMeasure(measure);
		// 直前ノート（対象小節の1小節前に含まれる最後のノート）を抽出
		NoteModel justBeforeNote = getIncludedInMeasure(measure - 1).getLastPosition();
		// 音高情報を抽出
		int[] differences = new int[melody.size()];
		for(int i = 0; i < melody.size(); i++) {
			int previousPitch = 0;
			int currentPitch = melody.get(i).getPitch();
			if(i <= 0) {
				if(justBeforeNote == null) {
					previousPitch = melody.get(i).getPitch();
				} else {
					previousPitch = justBeforeNote.getPitch();
				}
			} else {
				previousPitch = melody.get(i-1).getPitch();
			}
			differences[i] = currentPitch - previousPitch;
		}
		// リズム情報を抽出
		int[] positions = new int[melody.size()];
		int[] durations = new int[melody.size()];
		for(int i = 0; i < melody.size(); i++) {
			positions[i] = melody.get(i).getPosition() % MidiUtil.getDurationOf1Measure();
			durations[i] = melody.get(i).getDuration();
		}
		// 相対メロディ生成
		RelativeMelody relativeMelody = new RelativeMelody("");
		for(int i = 0; i < melody.size(); i++) {
			//relativeMelody.add(new RelativeNote(variations[i], differences[i], positions[i], durations[i]));
			relativeMelody.add(new RelativeNote(differences[i], positions[i], durations[i]));
		}
		return relativeMelody;
	}
}
