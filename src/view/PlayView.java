package view;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import controller.Controller;
import event.SpecieNameChangedEvent;
import event.SpecieNameListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

public class PlayView implements Initializable, ViewSpecieInterface, SpecieNameListener {
	private Font fontBold;
	private Font fontRegular;
	
	private Controller controller;
	
	private List<Pair<String, String>> timeline;
	
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
	
	public PlayView(Controller controller) {
		this.controller = controller;
		this.timeline = new ArrayList<>();
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

	@Override
	public void updateSpecie(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc, boolean is3D) {
		String startdate = this.controller.getStartDate();
		String enddate = this.controller.getEndDate();
		
		int startYear = Integer.parseInt(startdate.substring(0, 4));
		String restStart = startdate.substring(4); 
		int endYear = Integer.parseInt(enddate.substring(0, 4));
		
		this.timeline = new ArrayList<>();
		
		while(startYear < endYear) {
			this.timeline.add(new Pair<>(Integer.toString(startYear) + restStart, Integer.toString(startYear+5) + restStart));
			startYear += 5;
		}
		this.timeline.add(new Pair<>(Integer.toString(startYear) + restStart, enddate));
	}
	
	@Override
	public void specieNameChanged(SpecieNameChangedEvent event) {
		this.updateSpecie(event.getSpecieName(), event.getGeoHashAndNumberOfOccurrences(), event.getMaxMinOccurrences(), event.getIs3D());
	}
}
