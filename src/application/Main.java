package application;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.FirstView;
import javafx.scene.Scene;

/**
 * Classe principale de l'application.<br/>
 * Initialise la fenetre principale et l'affiche.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public class Main extends Application {
	private Controller controller;
	private Model model;
	
	private FirstView firstView;
	
	/**
	 * Initialisation de la {@code Stage}
	 */
	@Override
	public void start(Stage primaryStage) {
		model = new Model();
		controller = new Controller(model);
		
		firstView = new FirstView(controller);
		
		model.initModel("Selachii");
		
		try {
			Scene scene = new Scene(firstView);
			primaryStage.setTitle("Obis3D");
			primaryStage.setScene(scene);
			
			final double START_WIDTH = 1075; //firstView.getWidth();
			final double START_HEIGHT = 690; //firstView.getHeight();
			primaryStage.setMinWidth(START_WIDTH);
			primaryStage.setMinHeight(START_HEIGHT);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
