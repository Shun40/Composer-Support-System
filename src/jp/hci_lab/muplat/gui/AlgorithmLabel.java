package gui;
import gui.constants.UniversalConstants.Algorithm;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 現在選択中のアルゴリズムを表示するラベルのクラス
 * @author Shun Yamashita
 */
public class AlgorithmLabel extends Label {
	public AlgorithmLabel(Algorithm algorithm, int x, int y) {
		super("Algorithm: " + algorithm.toString());
		setFont(Font.font("Arial", 14));
		setTextFill(Color.web("#000000"));
		setupPoint(x, y);
	}

	public void setupPoint(int x, int y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	public void setAlgorithm(Algorithm algorithm) {
		setText("Algorithm: " + algorithm.toString());
	}
}
