package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayView implements Initializable {
	
	@FXML
	private Button playButton;
	
	@FXML
	private Button pauseButton;
	
	@FXML
	private Button stopButton;
	
	@FXML
	private ImageView playIcon;
	
	@FXML
	private ImageView pauseIcon;
	
	@FXML
	private ImageView stopIcon;
	
	public PlayView() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		playIcon.setImage(new Image(getClass().getResource("/res/baseline_play_arrow_black_24dp.png").toExternalForm()));
		pauseIcon.setImage(new Image(getClass().getResource("/res/baseline_pause_black_24dp.png").toExternalForm()));
		stopIcon.setImage(new Image(getClass().getResource("/res/baseline_stop_black_24dp.png").toExternalForm()));
	}
}
