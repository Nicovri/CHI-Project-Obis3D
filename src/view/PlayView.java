package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.SVGPath;

public class PlayView implements Initializable {
	
	@FXML
	private Button playButton;
	
	@FXML
	private Button pauseButton;
	
	@FXML
	private Button stopButton;
	
	public PlayView() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
