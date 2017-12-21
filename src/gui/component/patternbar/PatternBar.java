package gui.component.patternbar;

import java.util.List;

import engine.melody.CandidateMelody;
import gui.GuiConstants;
import gui.GuiManager;
import gui.component.base.ButtonBase;
import gui.component.base.GroupBase;
import gui.component.base.TextFieldBase;
import gui.component.pianoroll.note.NoteModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import system.AppConstants;

/**
 * パターン予測変換の編集領域のクラス
 * @author Shun Yamashita
 */
public class PatternBar extends GroupBase {
	/** 小節毎の候補リストを保持する */
	private PatternSelector[] patternSelectors;
	/** 小節毎の最後に決定された候補のIDを保持する */
	private String[] lastDecidedId;
	/** 小節毎の最後に決定された候補を表示する */
	private TextFieldBase[] lastDecidedName;
	private GuiManager owner;

	public PatternBar(GuiManager owner) {
		super();
		this.owner = owner;
		setPoint(GuiConstants.PatternBar.X, GuiConstants.PatternBar.Y);
		setupPredictionButton();
		setupDecisionButton();
		setupPatternSelector();
		setupLastDecidedId();
		setupLastDecidedName();
	}

	public void setupPredictionButton() {
		ButtonBase predictionButton = new ButtonBase();
		predictionButton.setText("予測");
		predictionButton.setPoint(0, 0);
		predictionButton.setSize(145, 16);
		predictionButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				prediction();
			}
		});
		getChildren().add(predictionButton);
	}

	public void setupDecisionButton() {
		ButtonBase decisionButton = new ButtonBase();
		decisionButton.setText("決定");
		decisionButton.setPoint(155, 0);
		decisionButton.setSize(145, 16);
		decisionButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				decision();
			}
		});
		getChildren().add(decisionButton);
	}

	public void setupPatternSelector() {
		patternSelectors = new PatternSelector[AppConstants.Settings.MEASURES];
		for(int n = 0; n < patternSelectors.length; n++) {
			patternSelectors[n] = new PatternSelector(0, 30, this);
		}
		getChildren().add(patternSelectors[0]);
	}

	public void setupLastDecidedId() {
		lastDecidedId = new String[AppConstants.Settings.MEASURES];
		for(int n = 0; n < lastDecidedId.length; n++) {
			lastDecidedId[n] = null;
		}
	}

	public void setupLastDecidedName() {
		lastDecidedName = new TextFieldBase[AppConstants.Settings.MEASURES];
		for(int n = 0; n < lastDecidedName.length; n++) {
			lastDecidedName[n] = new TextFieldBase();
			lastDecidedName[n].setPoint(0, 438);
			lastDecidedName[n].setSize(300, 24);
			lastDecidedName[n].setDisable(true);
			lastDecidedName[n].setPromptText("最後に決定したパターン");
			getChildren().add(lastDecidedName[n]);
		}
	}

	public void prediction() {
		int targetMeasure = owner.getPredictionTargetMeasure();
		patternSelectors[targetMeasure - 1].getItems().clear(); // 一度リストの中身を空にする
		List<CandidateMelody> melodies = owner.prediction();
		for(CandidateMelody melody : melodies) {
			patternSelectors[targetMeasure - 1].addList(melody);
		}
	}

	public void decision() {
		int targetMeasure = owner.getPredictionTargetMeasure();
		CandidateMelody melody = patternSelectors[targetMeasure - 1].getSelectionModel().getSelectedItem();
		if(melody == null) return;

		String wordId = melody.getId();
		if(targetMeasure == 1) { // 単語辞書の頻度更新
			owner.incWordDictionaryFrequency(wordId);
		} else {
			String contextId = lastDecidedId[targetMeasure - 1 - 1];
			if(contextId == null) { // 単語辞書の頻度更新
				owner.incWordDictionaryFrequency(wordId);
			} else { // 例文辞書の頻度更新
				owner.incPhraseDictionaryFrequency(contextId, wordId);
			}
		}
		lastDecidedId[targetMeasure - 1] = wordId;
		lastDecidedName[targetMeasure - 1].setText(melody.getName());
	}

	public void arrange(CandidateMelody melody) {
		int targetMeasure = owner.getPredictionTargetMeasure();
		owner.removeNoteInMeasure(targetMeasure, AppConstants.MelodySettings.MELODY_TRACK); // メロディトラックのノートを消去
		for(NoteModel noteModel : melody) {
			owner.putNote(noteModel);
		}
	}

	public void setPredictionPatternList(int targetMeasure) {
		for(int n = 0; n < patternSelectors.length; n++) {
			getChildren().remove(patternSelectors[n]);
		}
		getChildren().add(patternSelectors[targetMeasure - 1]);
		for(int n = 0; n < lastDecidedName.length; n++) {
			getChildren().remove(lastDecidedName[n]);
		}
		getChildren().add(lastDecidedName[targetMeasure - 1]);
	}

	public int getTargetMeasure() {
		return owner.getPredictionTargetMeasure();
	}
}
