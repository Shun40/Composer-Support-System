package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class DecisionButton extends Button {
	private PatternArea parent;

	public DecisionButton(int x, int y, PatternArea parent) {
		super("Decision");
		this.parent = parent;
		setupPoint(x, y);
		setupEventListener();
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setupEventListener() {
		setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				parent.decision();
			}
		});
	}
}
