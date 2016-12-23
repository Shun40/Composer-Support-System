public class DrumPatternMaker {
	public DrumPatternMaker() {

	}

	public void makeKickAndSnare(DrumPattern drumPattern, DrumPatternParameter parameter) {
		double climax = parameter.getClimax();
		if(0.0 <= climax && climax < 0.2) {
			drumPattern.addKick(calcPosition(1, 1), 100);
			drumPattern.addKick(calcPosition(2, 1), 100);
			drumPattern.addKick(calcPosition(3, 1), 100);
			drumPattern.addKick(calcPosition(4, 1), 100);
		}
		if(0.2 <= climax && climax < 0.4) {
			drumPattern.addKick(calcPosition(1, 1), 100);
			drumPattern.addKick(calcPosition(2, 1), 100);
			drumPattern.addKick(calcPosition(3, 1), 100);
			drumPattern.addSnare(calcPosition(4, 1), 100);
		}
		if(0.4 <= climax && climax < 0.6) {
			drumPattern.addKick(calcPosition(1, 1), 100);
			drumPattern.addKick(calcPosition(3, 1), 100);
			drumPattern.addKick(calcPosition(3, 3), 100);
			drumPattern.addSnare(calcPosition(2, 1), 100);
			drumPattern.addSnare(calcPosition(4, 1), 100);
		}
		if(0.6 <= climax && climax < 0.8) {
			drumPattern.addKick(calcPosition(1, 1), 100);
			drumPattern.addKick(calcPosition(3, 1), 100);
			drumPattern.addKick(calcPosition(3, 3), 100);
			drumPattern.addSnare(calcPosition(2, 1), 100);
			drumPattern.addSnare(calcPosition(4, 1), 100);
			drumPattern.addSnare(calcPosition(4, 4), 100);
		}
		if(0.8 <= climax && climax < 1.0) {
			drumPattern.addKick(calcPosition(1, 3), 100);
			drumPattern.addKick(calcPosition(2, 3), 100);
			drumPattern.addKick(calcPosition(3, 3), 100);
			drumPattern.addKick(calcPosition(4, 3), 100);
			drumPattern.addSnare(calcPosition(1, 1), 100);
			drumPattern.addSnare(calcPosition(2, 1), 100);
			drumPattern.addSnare(calcPosition(3, 1), 100);
			drumPattern.addSnare(calcPosition(4, 1), 100);
		}
	}

	public void makeHihat(DrumPattern drumPattern, DrumPatternParameter parameter) {
		double speed = parameter.getSpeed();
		if(0.0 <= speed && speed < 0.2) {
		}
		if(0.2 <= speed && speed < 0.4) {
			drumPattern.addCloseHihat(calcPosition(1, 1), 100);
			drumPattern.addCloseHihat(calcPosition(2, 1), 100);
			drumPattern.addCloseHihat(calcPosition(3, 1), 100);
			drumPattern.addCloseHihat(calcPosition(4, 1), 100);
		}
		if(0.4 <= speed && speed < 0.6) {
			drumPattern.addCloseHihat(calcPosition(1, 1), 100);
			drumPattern.addCloseHihat(calcPosition(1, 3), 100);
			drumPattern.addCloseHihat(calcPosition(2, 1), 100);
			drumPattern.addCloseHihat(calcPosition(2, 3), 100);
			drumPattern.addCloseHihat(calcPosition(3, 1), 100);
			drumPattern.addCloseHihat(calcPosition(3, 3), 100);
			drumPattern.addCloseHihat(calcPosition(4, 1), 100);
			drumPattern.addCloseHihat(calcPosition(4, 3), 100);
		}
		if(0.6 <= speed && speed < 0.8) {
			drumPattern.addOpenHihat(calcPosition(1, 1), 100);
			drumPattern.addOpenHihat(calcPosition(1, 3), 100);
			drumPattern.addOpenHihat(calcPosition(2, 1), 100);
			drumPattern.addOpenHihat(calcPosition(2, 3), 100);
			drumPattern.addOpenHihat(calcPosition(3, 1), 100);
			drumPattern.addOpenHihat(calcPosition(3, 3), 100);
			drumPattern.addOpenHihat(calcPosition(4, 1), 100);
			drumPattern.addOpenHihat(calcPosition(4, 3), 100);
		}
		if(0.8 <= speed && speed < 1.0) {
			drumPattern.addCloseHihat(calcPosition(1, 1), 100);
			drumPattern.addOpenHihat(calcPosition(1, 3), 100);
			drumPattern.addCloseHihat(calcPosition(2, 1), 100);
			drumPattern.addOpenHihat(calcPosition(2, 3), 100);
			drumPattern.addCloseHihat(calcPosition(3, 1), 100);
			drumPattern.addOpenHihat(calcPosition(3, 3), 100);
			drumPattern.addCloseHihat(calcPosition(4, 1), 100);
			drumPattern.addOpenHihat(calcPosition(4, 3), 100);
		}
	}

	public int calcPosition(int beat, int position) {
		return 960 * (beat - 1) + (960 / 4) * (position - 1);
	}
}
