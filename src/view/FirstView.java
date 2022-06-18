package view;

import java.io.IOException;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FirstView extends HBox {
	private Controller controller;
	
	private EarthView earthView;
	private PlayView playView;
	
	private ResearchView researchView;
	private LegendView legendView;
	
	public FirstView(Controller controller) {
		this.controller = controller;
		
		earthView = new EarthView(controller);
		
		playView = new PlayView();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PlayView.fxml"));
		loader.setController(playView);
		Parent play = null;
		try {
			play = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		VBox leftPane = new VBox();
		leftPane.getChildren().addAll(earthView, play);
		
		researchView = new ResearchView(controller);
		loader = new FXMLLoader(getClass().getResource("/fxml/ResearchView.fxml"));
		loader.setController(researchView);
		Parent research = null;
		try {
			research = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		legendView = new LegendView();
		loader = new FXMLLoader(getClass().getResource("/fxml/LegendView.fxml"));
		loader.setController(legendView);
		Parent legend = null;
		try {
			legend = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Separator s = new Separator();
		s.setOrientation(Orientation.HORIZONTAL);
		s.setPadding(new Insets(30, 0, 30, 0));
		VBox rightPane = new VBox();
		rightPane.getChildren().addAll(research, s, legend);
				
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(leftPane, rightPane);
	}
}
