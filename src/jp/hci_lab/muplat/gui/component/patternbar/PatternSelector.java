package gui.component.patternbar;
import engine.PredictionPattern;
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
public class PatternSelector extends ListViewBase<PredictionPattern> {
	private ObservableList<PredictionPattern> patternList;
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
		setCellFactory(new Callback<ListView<PredictionPattern>, ListCell<PredictionPattern>>() {
			@Override
			public ListCell<PredictionPattern> call(ListView<PredictionPattern> arg0) {
				return new CustomCell();
			}
		});
	}

	public void setupEventListener() {
		getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends PredictionPattern> ov, PredictionPattern oldVal, PredictionPattern newVal)->{
				if(newVal != null) {
					owner.arrange(newVal);
				}
			}
		);
	}

	public void addList(PredictionPattern predictionPattern) {
		patternList.add(predictionPattern);
	}

	/*
	 * ListView<ArrangePattern>表示用セルのクラス
	 */
	private static class CustomCell extends ListCell<PredictionPattern> {
		@Override
		protected void updateItem(PredictionPattern me, boolean empty) {
			super.updateItem(me, empty);
			if(!empty) {
				setText(me.getName());
			}
		}
	}
}