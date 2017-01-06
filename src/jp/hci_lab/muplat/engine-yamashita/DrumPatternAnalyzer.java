import java.util.ArrayList;

public class DrumPatternAnalyzer {
	public DrumPatternAnalyzer() {
	}

	public ArrayList<DrumPattern> getDrumPattern(DrumPatternParameter queryParameter) {
		KickAndSnarePatternPreset kickAndSnarePatternPreset = new KickAndSnarePatternPreset();
		HihatPatternPreset hihatPatternPreset = new HihatPatternPreset();
		ArrayList<DrumPattern> patternsForClimax = new ArrayList<DrumPattern>(kickAndSnarePatternPreset);
		ArrayList<DrumPattern> patternsForSpeed = new ArrayList<DrumPattern>(hihatPatternPreset);
		ArrayList<DrumPattern> patterns = new ArrayList<DrumPattern>();
		ArrayList<Tuple> pairs = new ArrayList<Tuple>();

		// 盛り上がり度が最も近い値を持つ順にパターンをソート
		for(int n = 0; n < patternsForClimax.size(); n++) {
			for(int m = n; m < patternsForClimax.size(); m++) {
				double climaxDiffN = Math.abs(patternsForClimax.get(n).getClimax() - queryParameter.getClimax());
				double climaxDiffM = Math.abs(patternsForClimax.get(m).getClimax() - queryParameter.getClimax());
				patternsForClimax.get(n).setDiffQueryClimax(climaxDiffN);
				patternsForClimax.get(m).setDiffQueryClimax(climaxDiffM);
				if(climaxDiffN > climaxDiffM) {
					DrumPattern temp = patternsForClimax.get(n);
					patternsForClimax.set(n, patternsForClimax.get(m));
					patternsForClimax.set(m, temp);
				}
			}
		}

		System.out.println("Query climax : " + queryParameter.getClimax());
		for(DrumPattern pattern : patternsForClimax) {
			System.out.println(pattern.getName() + " : " + pattern.getClimax());
		}

		// 疾走感が最も近い値を持つ順にパターンをソート
		for(int n = 0; n < patternsForSpeed.size(); n++) {
			for(int m = n; m < patternsForSpeed.size(); m++) {
				double speedDiffN = Math.abs(patternsForSpeed.get(n).getSpeed() - queryParameter.getSpeed());
				double speedDiffM = Math.abs(patternsForSpeed.get(m).getSpeed() - queryParameter.getSpeed());
				patternsForSpeed.get(n).setDiffQuerySpeed(speedDiffN);
				patternsForSpeed.get(m).setDiffQuerySpeed(speedDiffM);
				if(speedDiffN > speedDiffM) {
					DrumPattern temp = patternsForSpeed.get(n);
					patternsForSpeed.set(n, patternsForSpeed.get(m));
					patternsForSpeed.set(m, temp);
				}
			}
		}

		System.out.println("Query speed : " + queryParameter.getSpeed());
		for(DrumPattern pattern : patternsForSpeed) {
			System.out.println(pattern.getName() + " : " + pattern.getSpeed());
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
			_pattern.addAll(pair.getP1());
			_pattern.addAll(pair.getP2());
			_pattern.setClimax(pair.getP1().getClimax());
			_pattern.setSpeed(pair.getP2().getSpeed());
			_pattern.setDiffQueryClimax(pair.getP1().getDiffQueryClimax());
			_pattern.setDiffQuerySpeed(pair.getP2().getDiffQuerySpeed());
			patterns.add(_pattern);
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
