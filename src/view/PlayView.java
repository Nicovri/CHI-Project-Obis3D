package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import controller.Controller;
import event.SpecieNameChangedEvent;
import event.SpecieNameListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * Vue responsable de l'affichage de l'interface de lecture, pause et arret
 * du defilement des occurrences sur la Terre par pas de 5 ans.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public class PlayView implements Initializable, ViewSpecieInterface, SpecieNameListener {
	private Font fontBold;
	private Font fontRegular;
	
	private Controller controller;
	
	private List<Pair<String, String>> timeline;
	private String currentSpecieName;
	private String startdate;
	private String enddate;
	private double scaleProg;
	
	private List<Map<String, Long>> intervals;
	
	private Timeline timer;
	
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
	
	@FXML
	private ProgressBar timelineProgressBar;
	
	@FXML
	private Label startDateLabel;
	
	@FXML
	private Label middleDateLabel;
	
	@FXML
	private Label endDateLabel;
	
	public PlayView(Controller controller) {
		this.controller = controller;
		this.timeline = new ArrayList<>();
		this.intervals = new ArrayList<>();
		this.currentSpecieName = " ";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.timelineProgressBar.setVisible(true);
		
		playIcon.setImage(new Image(getClass().getResource("/res/baseline_play_arrow_black_24dp.png").toExternalForm()));
		pauseIcon.setImage(new Image(getClass().getResource("/res/baseline_pause_black_24dp.png").toExternalForm()));
		stopIcon.setImage(new Image(getClass().getResource("/res/baseline_stop_black_24dp.png").toExternalForm()));
		
		fontRegular = stopButton.getFont();
		fontBold = Font.font(fontRegular.getName(), FontWeight.BOLD, FontPosture.REGULAR, fontRegular.getSize());
		stopButton.setFont(fontBold);
		
		// Cliquer sur le bouton play le transforme en bouton next (pour l'instant, tant que le timer n'est pas implemente)
		playButton.setOnMouseClicked(event -> {
			playButton.setFont(fontBold);
			pauseButton.setFont(fontRegular);
			stopButton.setFont(fontRegular);
			
			timer.play();
		});
		// Cliquer sur le bouton pause arete le timer 
		pauseButton.setOnMouseClicked(event -> {
			playButton.setFont(fontRegular);
			pauseButton.setFont(fontBold);
			stopButton.setFont(fontRegular);
			
			timer.pause();
		});
		// Cliquer sur le bouton stop arete le timer, vide les donnees restantes et recharge les donnees initiales pour l'espece et les dates
		stopButton.setOnMouseClicked(event -> {
			playButton.setFont(fontRegular);
			pauseButton.setFont(fontRegular);
			stopButton.setFont(fontBold);
			
			timer.stop();
			
			timeline.clear();
			intervals.clear();
			timelineProgressBar.setProgress(0);
			controller.notifySpecieNameAndDateChanged(controller.getSpecieName(), startdate, enddate);
		});
	}

	@Override
	public void updateSpecie(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc, boolean is3D) {
		if(!currentSpecieName.equals(specieName) || !startdate.equals(controller.getStartDate()) || !enddate.equals(controller.getEndDate()) || timeline.isEmpty()) {
			currentSpecieName = specieName;
			startdate = this.controller.getStartDate();
			enddate = this.controller.getEndDate();
			timelineProgressBar.setProgress(0);
			
			this.startDateLabel.setText(startdate);
			int middleYear = (Integer.parseInt(enddate.substring(0, 4)) - Integer.parseInt(startdate.substring(0, 4)))/2;
			middleYear += Integer.parseInt(startdate.substring(0, 4));
			this.middleDateLabel.setText(middleYear + startdate.substring(4));
			this.endDateLabel.setText(enddate);
			
			// On cree une timeline d'intervales de 5 ans
			int startYear = Integer.parseInt(startdate.substring(0, 4));
			String restStart = startdate.substring(4); 
			int endYear = Integer.parseInt(enddate.substring(0, 4));
			
			this.timeline = new ArrayList<>();
			
			while(startYear+5 < endYear) {
				this.timeline.add(new Pair<>(Integer.toString(startYear) + restStart, Integer.toString(startYear+5) + restStart));
				startYear += 5;
			}
			this.timeline.add(new Pair<>(Integer.toString(startYear) + restStart, enddate));
			
			this.scaleProg = 1.0 / timeline.size();
			System.out.println(scaleProg);
			System.out.println(timeline.size());
			
			// Et on recupere les donnees correspondantes
			this.intervals = new ArrayList<>();
			this.intervals = controller.getOccurrencesPerInterval();
			
			// On initialise le timer selon les donnees reçues de timeline
			this.timer = new Timeline(new KeyFrame(Duration.millis(50), event -> {
				if(timeline.isEmpty()) {
					timer.stop();
					timeline.clear();
					intervals.clear();
					timelineProgressBar.setProgress(0);
					controller.notifySpecieNameAndDateChanged(controller.getSpecieName(), startdate, enddate);
				}
				timer.pause();
				controller.notifySpecieNameChanged(controller.getSpecieName(), intervals.get(0));
				timeline.remove(0);
				intervals.remove(0);
				timer.play();
				scaleProg += scaleProg / 10000;
				timelineProgressBar.setProgress(timelineProgressBar.getProgress() + scaleProg);
			}));
			timer.setCycleCount(Timeline.INDEFINITE);
			timer.stop();
		}
		
	}
	
	@Override
	public void specieNameChanged(SpecieNameChangedEvent event) {
		this.updateSpecie(event.getSpecieName(), event.getGeoHashAndNumberOfOccurrences(), event.getMaxMinOccurrences(), event.getIs3D());
	}
}
