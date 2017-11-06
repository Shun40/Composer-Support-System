package engine.melody.generation;

public class RangeAppearanceProbability {
	private double[] probabilities;
	private int minPitch;
	private int maxPitch;

	public RangeAppearanceProbability() {
		probabilities = new double[29];
		minPitch = 55;
		maxPitch = 83;
		for(int x = 55; x <= 83; x++) {
			double probability = 0.0;
			if(55 <= x && x <= 58) probability = 0.50;
			if(59 <= x && x <= 62) probability = 0.60;
			if(x == 63) probability = 0.70;
			if(x == 64) probability = 0.74;
			if(x == 65) probability = 0.78;
			if(x == 66) probability = 0.82;
			if(x == 67) probability = 0.86;
			if(68 <= x && x <= 70) probability = 0.9;
			if(x == 71) probability = 0.86;
			if(x == 72) probability = 0.82;
			if(x == 73) probability = 0.78;
			if(x == 74) probability = 0.74;
			if(x == 75) probability = 0.70;
			if(76 <= x && x <= 79) probability = 0.60;
			if(80 <= x && x <= 83) probability = 0.50;
			probabilities[x - minPitch] = probability;
		}
	}

	public double getProbability(int x) {
		return probabilities[x];
	}
	public int getMinPitch() { return minPitch; }
	public int getMaxPitch() { return maxPitch; }
}
