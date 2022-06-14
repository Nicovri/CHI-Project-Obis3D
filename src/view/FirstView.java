package view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class FirstView extends HBox {

	private EarthView earthView;
	private PlayView playView;
	
	public FirstView() {
		earthView = new EarthView();
		
		playView = new PlayView();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayView.fxml"));
		loader.setController(playView);
		
		Parent play = null;
		try {
			play = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		VBox leftPane = new VBox();
		leftPane.getChildren().addAll(earthView, play);
		
		VBox rightPane = new VBox();
		
//		Color color = Color.BLACK;
//		Background b = new Background(new BackgroundFill(color, new CornerRadii(0), new Insets(0)));
//		earthView.setBackground(b);
//		earthView.setMinHeight(400);
//		earthView.setMinWidth(400);
		
		this.getChildren().addAll(leftPane, rightPane);
	}
}
