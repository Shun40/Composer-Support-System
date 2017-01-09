package engine_yamashita;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import gui.NoteInformation;

/**
 * メロディを解析するクラス
 * @author Shun Yamashita
 */
public class MelodyAnalyzer {
	public MelodyAnalyzer() {

	}

	public ArrayList<Double> getAccentScores(ArrayList<NoteInformation> melody) {
		ArrayList<Double> accentScores = new ArrayList<Double>();
		// 元の配列に影響を与えないようディープコピー
		ArrayList<NoteInformation> _melody = new ArrayList<NoteInformation>(melody);

		if(_melody.size() <= 0) {
			return null;
		} else {
			ArrayList<Boolean> silhouetteAccents = getSilhouetteAccents(_melody);
			ArrayList<Boolean> longAccents = getLongAccents(_melody);
			ArrayList<Boolean> isolationAccents = getIsolationAccents(_melody);

			/*
			System.out.println("Silhouette Accents");
			for(int n = 0; n < _melody.size(); n++) {
				System.out.println(silhouetteAccents.get(n));
			}
			System.out.println("Long Accents");
			for(int n = 0; n < _melody.size(); n++) {
				System.out.println(longAccents.get(n));
			}
			System.out.println("Isolation Accents");
			for(int n = 0; n < _melody.size(); n++) {
				System.out.println(isolationAccents.get(n));
			}
			*/

			System.out.println("Accents");
			for(int n = 0; n < _melody.size(); n++) {
				double accentScore = 0.0;
				if(silhouetteAccents.get(n)) accentScore += 0.25;
				if(longAccents.get(n)) accentScore += 0.5;
				if(isolationAccents.get(n)) accentScore += 0.25;
				accentScore /= 1.0;
				accentScores.add(accentScore);
				System.out.println("Note[" + (n + 1) + "] : " + accentScore);
			}
			return accentScores;
		}
	}

	/*
	 * ノートの個数を返す
	 */
	public int getNumber(ArrayList<NoteInformation> melody) {
		return melody.size();
	}

	/*
	 * 最も音高が高いノートの情報を返す
	 */
	public NoteInformation getHighest(ArrayList<NoteInformation> melody) {
		// 元の配列に影響を与えないようディープコピー
		ArrayList<NoteInformation> _melody = new ArrayList<NoteInformation>(melody);
		// ノート番号で降順ソート
		Collections.sort(_melody, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e2.getNote() - e1.getNote();
			}
		});
		return _melody.get(0);
	}

	/*
	 * 最も音高が低いノートの情報を返す
	 */
	public NoteInformation getLowest(ArrayList<NoteInformation> melody) {
		// 元の配列に影響を与えないようディープコピー
			ArrayList<NoteInformation> _melody = new ArrayList<NoteInformation>(melody);
		// ノート番号で昇順ソート
		Collections.sort(_melody, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e1.getNote() - e2.getNote();
			}
		});
		return _melody.get(0);
	}

	/*
	 * 最も長いノートの情報を返す
	 */
	public NoteInformation getLongest(ArrayList<NoteInformation> melody) {
		// 元の配列に影響を与えないようディープコピー
			ArrayList<NoteInformation> _melody = new ArrayList<NoteInformation>(melody);
		// デュレーションで降順ソート
		Collections.sort(_melody, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e2.getDuration() - e1.getDuration();
			}
		});
		return _melody.get(0);
	}

	/*
	 * 最も短いノートの情報を返す
	 */
	public NoteInformation getShortest(ArrayList<NoteInformation> melody) {
		// 元の配列に影響を与えないようディープコピー
		ArrayList<NoteInformation> _melody = new ArrayList<NoteInformation>(melody);
		// デュレーションで降順ソート
		Collections.sort(_melody, new Comparator<NoteInformation>() {
			public int compare(NoteInformation e1, NoteInformation e2) {
				return e1.getDuration() - e2.getDuration();
			}
		});
		return _melody.get(0);
	}

	/*
	 * 輪郭アクセントと見なせるノート群を返す
	 */
	public ArrayList<Boolean> getSilhouetteAccents(ArrayList<NoteInformation> melody) {
		ArrayList<Boolean> accents = new ArrayList<Boolean>();
		if(melody.size() == 1) {
			accents.add(false);
			return accents;
		}
		for(int n = 0; n < melody.size(); n++) {
			if(n == 0) {
				if(melody.get(n).getNote() > melody.get(n+1).getNote()) {
					accents.add(true);
				} else {
					accents.add(false);
				}
			} else if(n == melody.size() - 1) {
				if(melody.get(n-1).getNote() < melody.get(n).getNote()) {
					accents.add(true);
				} else {
					accents.add(false);
				}
			} else {
				if(melody.get(n-1).getNote() < melody.get(n).getNote() && melody.get(n).getNote() > melody.get(n+1).getNote()) {
					accents.add(true);
				} else {
					accents.add(false);
				}
			}
		}
		return accents;
	}

	/*
	 * 長音アクセントと見なせるノート群を返す
	 */
	public ArrayList<Boolean> getLongAccents(ArrayList<NoteInformation> melody) {
		ArrayList<Boolean> accents = new ArrayList<Boolean>();
		int shortestDuration = getShortest(melody).getDuration();
		for(int n = 0; n < melody.size(); n++) {
			if(melody.get(n).getDuration() > shortestDuration) {
				accents.add(true);
			} else {
				accents.add(false);
			}
		}
		return accents;
	}

	/*
	 * 隔離アクセントと見なせるノートを調べる
	 */
	public ArrayList<Boolean> getIsolationAccents(ArrayList<NoteInformation> melody) {
		ArrayList<Boolean> accents = new ArrayList<Boolean>();
		for(int n = 0; n < melody.size(); n++) {
			if(n == melody.size() - 1) {
				if((melody.get(n).getPosition() + melody.get(n).getDuration()) % (960 * 4) < 960 * 4) {
					accents.add(true);
				} else {
					accents.add(false);
				}
			} else {
				if(melody.get(n).getPosition() + melody.get(n).getDuration() < melody.get(n+1).getPosition()) {
					accents.add(true);
				} else {
					accents.add(false);
				}
			}
		}
		return accents;
	}
}
