package engine_yamashita.melody.generation;

public class PitchPatternWeight {
	private double[] appearanceWeightsLow;
	private double[] appearanceWeightsMid;
	private double[] appearanceWeightsHigh;
	private int minPitch;
	private int maxPitch;

	public PitchPatternWeight() {
		appearanceWeightsLow = new double[29];
		appearanceWeightsMid = new double[29];
		appearanceWeightsHigh = new double[29];
		minPitch = 55;
		maxPitch = 83;
		makeAppearanceWeightsLow();
		makeAppearanceWeightsMid();
		makeAppearanceWeightsHigh();
	}

	public void makeAppearanceWeightsLow() {
		for(int x = minPitch; x <= maxPitch; x++) {
			if(55 <= x && x <= 64) appearanceWeightsLow[x - minPitch] = 1.0;
			if(65 <= x && x <= 74) appearanceWeightsLow[x - minPitch] = 0.25;
			if(75 <= x && x <= 83) appearanceWeightsLow[x - minPitch] = 0.1;
		}
	}

	public void makeAppearanceWeightsMid() {
		for(int x = minPitch; x <= maxPitch; x++) {
			if(55 <= x && x <= 64) appearanceWeightsMid[x - minPitch] = 0.25;
			if(65 <= x && x <= 74) appearanceWeightsMid[x - minPitch] = 1.0;
			if(75 <= x && x <= 83) appearanceWeightsMid[x - minPitch] = 0.25;
		}
	}

	public void makeAppearanceWeightsHigh() {
		for(int x = minPitch; x <= maxPitch; x++) {
			if(55 <= x && x <= 64) appearanceWeightsHigh[x - minPitch] = 0.1;
			if(65 <= x && x <= 74) appearanceWeightsHigh[x - minPitch] = 0.25;
			if(75 <= x && x <= 83) appearanceWeightsHigh[x - minPitch] = 1.0;
		}
	}

	public double getAppearanceWeight(int place, int x) {
		double weight = 0.0;
		if(55 <= place && place <= 64) weight = appearanceWeightsLow[x];
		if(65 <= place && place <= 74) weight = appearanceWeightsMid[x];
		if(75 <= place && place <= 83) weight = appearanceWeightsHigh[x];
		return weight;
	}
}
