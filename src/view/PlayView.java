package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class PlayView implements Initializable {
	private Font fontBold;
	private Font fontRegular;
	
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
		
		fontRegular = stopButton.getFont();
		fontBold = Font.font(fontRegular.getName(), FontWeight.BOLD, FontPosture.REGULAR, fontRegular.getSize());
		stopButton.setFont(fontBold);
		
		playButton.setOnMouseClicked(event -> {
			playButton.setFont(fontBold);
			pauseButton.setFont(fontRegular);
			stopButton.setFont(fontRegular);
		});
		pauseButton.setOnMouseClicked(event -> {
			playButton.setFont(fontRegular);
			pauseButton.setFont(fontBold);
			stopButton.setFont(fontRegular);
		});
		stopButton.setOnMouseClicked(event -> {
			playButton.setFont(fontRegular);
			pauseButton.setFont(fontRegular);
			stopButton.setFont(fontBold);
		});
	}
}
