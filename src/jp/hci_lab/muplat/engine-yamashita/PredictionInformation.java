import java.util.ArrayList;

public class PredictionInformation {
	private ArrayList<NoteInformation> originalMelody;
	private DrumPatternParameter drumPatternParameter;

	public PredictionInformation(ArrayList<NoteInformation> originalMelody, DrumPatternParameter drumPatternParameter) {
		this.originalMelody = originalMelody;
		this.drumPatternParameter = drumPatternParameter;
	}

	public ArrayList<NoteInformation> getOriginalMelody() { return originalMelody; }
	public DrumPatternParameter getDrumPatternParameter() { return drumPatternParameter; }
}
