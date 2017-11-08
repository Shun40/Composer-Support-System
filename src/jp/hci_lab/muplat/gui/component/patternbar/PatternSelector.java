package gui.component.patternbar;
import engine.melody.CandidateMelody;
import gui.component.base.ListViewBase;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * パターンを指定するリストビューのクラス
 * @author Shun Yamashita
 */
public class PatternSelector extends ListViewBase<CandidateMelody> {
	private ObservableList<CandidateMelody> patternList;
	private PatternBar owner;

	public PatternSelector(int x, int y, PatternBar owner) {
		super();
		this.owner = owner;
		setSize(300, 400);
		setPoint(x, y);
		setupList();
		setupEventListener();
	}

	public void setupList() {
		patternList = FXCollections.observableArrayList();
		setItems(patternList);
		setCellFactory(new Callback<ListView<CandidateMelody>, ListCell<CandidateMelody>>() {
			@Override
			public ListCell<CandidateMelody> call(ListView<CandidateMelody> arg0) {
				return new CustomCell();
			}
		});
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends CandidateMelody> ov, CandidateMelody oldVal, CandidateMelody newVal)->{
				if(newVal != null) {
					owner.arrange(newVal);
				}
			}
		);
	}

	public void addList(CandidateMelody candidateMelody) {
		patternList.add(candidateMelody);
	}

	/*
	 * ListView<ArrangePattern>表示用セルのクラス
	 */
	private static class CustomCell extends ListCell<CandidateMelody> {
		@Override
		protected void updateItem(CandidateMelody me, boolean empty) {
			super.updateItem(me, empty);
			if(!empty) {
				setText(me.getName());
			}
		}
	}
}