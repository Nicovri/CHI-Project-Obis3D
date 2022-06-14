package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import view.FirstView;
import javafx.scene.Scene;


public class Main extends Application {
	private FirstView firstView;
	
	@Override
	public void start(Stage primaryStage) {
		firstView = new FirstView();
		
		try {
			primaryStage.setTitle("Obis3D");
			primaryStage.setScene(new Scene(firstView, 700, 500));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
