import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * メロディを解析するクラス
 * @author Shun Yamashita
 */
public class MelodyAnalyzer {
	public MelodyAnalyzer() {

	}

	public void analyze(ArrayList<NoteInformation> noteInformations) {
		// 元の配列に影響を与えないようディープコピー
		ArrayList<NoteInformation> _noteInformations = new ArrayList<NoteInformation>(noteInformations);

		if(_noteInformations.size() > 0) {
			System.out.println("Number   :" + getNumber(_noteInformations));
			System.out.println("Highest  :" + getHighest(_noteInformations).getNote());
			System.out.println("Lowest   :" + getLowest(_noteInformations).getNote());
			System.out.println("Longest  :" + getLongest(_noteInformations).getDuration());
			System.out.println("Shortest :" + getShortest(_noteInformations).getDuration());
		}
	}

	/*
	 * ノートの個数を返す
	 */
	public int getNumber(ArrayList<NoteInformation> noteInformations) {
		return noteInformations.size();
	}

	/*
	 * 最も音高が高いノートの情報を返す
	 */
	public NoteInformation getHighest(ArrayList<NoteInformation> noteInformations) {
		// ノート番号で降順ソート
		Collections.sort(noteInformations, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e2.getNote() - e1.getNote();
			}
		});
		return noteInformations.get(0);
	}

	/*
	 * 最も音高が低いノートの情報を返す
	 */
	public NoteInformation getLowest(ArrayList<NoteInformation> noteInformations) {
		// ノート番号で昇順ソート
		Collections.sort(noteInformations, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e1.getNote() - e2.getNote();
			}
		});
		return noteInformations.get(0);
	}

	/*
	 * 最も長いノートの情報を返す
	 */
	public NoteInformation getLongest(ArrayList<NoteInformation> noteInformations) {
		// デュレーションで降順ソート
		Collections.sort(noteInformations, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e2.getDuration() - e1.getDuration();
			}
		});
		return noteInformations.get(0);
	}

	/*
	 * 最も短いノートの情報を返す
	 */
	public NoteInformation getShortest(ArrayList<NoteInformation> noteInformations) {
		// デュレーションで降順ソート
		Collections.sort(noteInformations, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e1.getDuration() - e2.getDuration();
			}
		});
		return noteInformations.get(0);
	}
}
