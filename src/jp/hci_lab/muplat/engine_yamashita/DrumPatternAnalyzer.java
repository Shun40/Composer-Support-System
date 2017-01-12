package engine_yamashita;

import java.util.ArrayList;

import gui.NoteInformation;

public class DrumPatternAnalyzer {
	public DrumPatternAnalyzer() {
	}

	public ArrayList<DrumPattern> getBaseDrumPatterns(ArrangeInformation arrangeInformation) {
		ArrangeParameter arrangeParameter = arrangeInformation.getArrangeParameter();
		KickAndSnarePatternPreset kickAndSnarePatternPreset = new KickAndSnarePatternPreset();
		HihatPatternPreset hihatPatternPreset = new HihatPatternPreset();
		ArrayList<DrumPattern> patternsForClimax = new ArrayList<DrumPattern>(kickAndSnarePatternPreset);
		ArrayList<DrumPattern> patternsForSpeed = new ArrayList<DrumPattern>(hihatPatternPreset);
		ArrayList<DrumPattern> patterns = new ArrayList<DrumPattern>();
		ArrayList<Tuple> pairs = new ArrayList<Tuple>();

		// 盛り上がり度が最も近い値を持つ順にパターンをソート
		for(int n = 0; n < patternsForClimax.size(); n++) {
			for(int m = n; m < patternsForClimax.size(); m++) {
				double climaxDiffN = Math.abs(patternsForClimax.get(n).getClimax() - arrangeParameter.getClimax());
				double climaxDiffM = Math.abs(patternsForClimax.get(m).getClimax() - arrangeParameter.getClimax());
				patternsForClimax.get(n).setDiffQueryClimax(climaxDiffN);
				patternsForClimax.get(m).setDiffQueryClimax(climaxDiffM);
				if(climaxDiffN > climaxDiffM) {
					DrumPattern temp = patternsForClimax.get(n);
					patternsForClimax.set(n, patternsForClimax.get(m));
					patternsForClimax.set(m, temp);
				}
			}
		}

		// 疾走感が最も近い値を持つ順にパターンをソート
		for(int n = 0; n < patternsForSpeed.size(); n++) {
			for(int m = n; m < patternsForSpeed.size(); m++) {
				double speedDiffN = Math.abs(patternsForSpeed.get(n).getSpeed() - arrangeParameter.getSpeed());
				double speedDiffM = Math.abs(patternsForSpeed.get(m).getSpeed() - arrangeParameter.getSpeed());
				patternsForSpeed.get(n).setDiffQuerySpeed(speedDiffN);
				patternsForSpeed.get(m).setDiffQuerySpeed(speedDiffM);
				if(speedDiffN > speedDiffM) {
					DrumPattern temp = patternsForSpeed.get(n);
					patternsForSpeed.set(n, patternsForSpeed.get(m));
					patternsForSpeed.set(m, temp);
				}
			}
		}

		for(int n = 0; n < patternsForClimax.size(); n++) {
			for(int m = 0; m < patternsForSpeed.size(); m++) {
				pairs.add(new Tuple(patternsForClimax.get(n), patternsForSpeed.get(m)));
			}
		}
		for(int n = 0; n < pairs.size(); n++) {
			for(int m = n; m < pairs.size(); m++) {
				double diffN = pairs.get(n).getP1().getDiffQueryClimax() + pairs.get(n).getP2().getDiffQuerySpeed();
				double diffM = pairs.get(m).getP1().getDiffQueryClimax() + pairs.get(m).getP2().getDiffQuerySpeed();
				if(diffN > diffM) {
					Tuple temp = pairs.get(n);
					pairs.set(n, pairs.get(m));
					pairs.set(m, temp);
				}
			}
		}
		for(Tuple pair : pairs) {
			DrumPattern _pattern = new DrumPattern();
			_pattern.setClimax(pair.getP1().getClimax());
			_pattern.setSpeed(pair.getP2().getSpeed());
			_pattern.setDiffQueryClimax(pair.getP1().getDiffQueryClimax());
			_pattern.setDiffQuerySpeed(pair.getP2().getDiffQuerySpeed());
			_pattern.setKicks(pair.getP1().getKicks());
			_pattern.setSnares(pair.getP1().getSnares());
			_pattern.setHihats(pair.getP2().getHihats());
			_pattern.setRcymbals(pair.getP2().getRcymbals());
			patterns.add(_pattern);
		}

		return patterns;
	}

	public ArrayList<DrumPattern> getRhythmicalDrumPatterns(ArrangeInformation arrangeInformation, ArrayList<DrumPattern> baseDrumPatterns, ArrayList<Double> accentScores) {
		ArrayList<DrumPattern> patterns = new ArrayList<DrumPattern>(baseDrumPatterns);
		ArrayList<NoteInformation> melody = arrangeInformation.getCurrentMelody();
		ArrangeParameter arrangeParameter = arrangeInformation.getArrangeParameter();
		double rhythm = arrangeParameter.getRhythm();

		if(accentScores == null) return baseDrumPatterns; // メロディがない場合はベースのドラムパターンをそのまま返す

		for(int n = 0; n < patterns.size(); n++) {
			DrumPattern basePattern = patterns.get(n);
			for(int m = 0; m < accentScores.size(); m++) {
				double accentScore = accentScores.get(m) * rhythm;
				int position = melody.get(m).getPosition() % (960 * 4);
				// メロディのアクセント位置にキックを置く
				if(0.25 <= accentScore) {
					basePattern.removeKick(position);
					basePattern.addKick(position, 100);
				}
				// メロディのアクセント位置にシンバルを置く
				if(0.5 <= accentScore) {
					// ハイハットとライドシンバルはクラッシュシンバルと同時には鳴らさない
					basePattern.removeCloseHihat(position);
					basePattern.removeOpenHihat(position);
					basePattern.removeRideCymbal(position);
					basePattern.addCrashCymbal2(position, 100);
				}
			}
		}
		return patterns;
	}

	public class Tuple {
		public DrumPattern p1;
		public DrumPattern p2;

		public Tuple(DrumPattern p1, DrumPattern p2) {
			this.p1 = p1;
			this.p2 = p2;
		}

		public DrumPattern getP1() { return p1; }
		public DrumPattern getP2() { return p2; }
	}
}
