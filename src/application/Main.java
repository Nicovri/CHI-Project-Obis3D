package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import view.FirstView;
import javafx.scene.Scene;


public class Main extends Application {
	private FirstView firstView;
	
	@Override
	public void start(Stage primaryStage) {
		firstView = new FirstView();
		
		try {
			Scene scene = new Scene(firstView);
			primaryStage.setTitle("Obis3D");
			primaryStage.setScene(scene);
			
			final double START_WIDTH = 1075; //firstView.getWidth();
			final double START_HEIGHT = 690; //firstView.getHeight();
			primaryStage.setMinWidth(START_WIDTH);
			primaryStage.setMinHeight(START_HEIGHT);
			
			// Plutôt changer la taille des sous-vues à la place
			// Get largeur des vues de firstView, de Window, faire la différence et ajouter un spacing
			
//			primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
//			    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//			    	if(newSceneWidth.doubleValue() < START_WIDTH) {
//			    		primaryStage.setWidth(START_WIDTH);
//			    	}
//			        System.out.println("Width: " + newSceneWidth);
//			}});
//			primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
//			    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//			    	if(newSceneHeight.doubleValue() < START_HEIGHT) {			    		
//			    		primaryStage.setHeight(START_HEIGHT);
//			    	}
//			        System.out.println("Height: " + newSceneHeight);
//			}});
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
