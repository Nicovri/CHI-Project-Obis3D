package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import controller.Controller;
import event.SpecieNameChangedEvent;
import event.SpecieNameListener;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

/**
 * Vue responsable de l'affichage de l'interface de lecture, pause et arrêt
 * du défilement des occurrences sur la Terre par pas de 5 ans.
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
	private String startdate;
	private String enddate;
	private double scaleProg;
	
	private List<Map<String, Long>> intervals;
	
	private AnimationTimer timer;
	
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
	
	public PlayView(Controller controller) {
		this.controller = controller;
		this.timeline = new ArrayList<>();
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
		
		// Cliquer sur le bouton play le transforme en bouton next (pour l'instant, tant que le timer n'est pas implémenté)
		playButton.setOnMouseClicked(event -> {
			playButton.setFont(fontBold);
			playButton.setText("Next");
			pauseButton.setFont(fontRegular);
			stopButton.setFont(fontRegular);
			
			if(!timeline.isEmpty()) {
				controller.notifySpecieNameChanged(controller.getSpecieName(), intervals.get(0));
				timeline.remove(0);
				intervals.remove(0);
				System.out.println(scaleProg);
				System.out.println(timelineProgressBar.getProgress());
				timelineProgressBar.setProgress(timelineProgressBar.getProgress() + scaleProg);
				System.out.println(timelineProgressBar.getProgress());
//				timer.start();
			} else {
				System.out.println("empty");
				
//				timer.stop();
				
				timeline.clear();
				intervals.clear();
				controller.notifySpecieNameAndDateChanged(controller.getSpecieName(), startdate, enddate);
			}
			
		});
		// Cliquer sur le bouton pause arête le timer 
		pauseButton.setOnMouseClicked(event -> {
			playButton.setFont(fontRegular);
			playButton.setText("Play");
			pauseButton.setFont(fontBold);
			stopButton.setFont(fontRegular);
			
//			timer.stop();
		});
		// Cliquer sur le bouton stop arête le timer, vide les données restantes et recharge les données initiales pour l'espèce et les dates
		stopButton.setOnMouseClicked(event -> {
			playButton.setFont(fontRegular);
			playButton.setText("Play");
			pauseButton.setFont(fontRegular);
			stopButton.setFont(fontBold);
			
//			timer.stop();
			
			timeline.clear();
			intervals.clear();
			controller.notifySpecieNameAndDateChanged(controller.getSpecieName(), startdate, enddate);
		});
	}

	@Override
	public void updateSpecie(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc, boolean is3D) {
		if(timeline.isEmpty()) {
			startdate = this.controller.getStartDate();
			enddate = this.controller.getEndDate();
			timelineProgressBar.setProgress(0);
			playButton.setText("Play");
			
			// On crée une timeline d'intervalles de 5 ans
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
			
			// Et on récupère les données correspondantes
			this.intervals = new ArrayList<>();
			this.intervals = controller.getOccurrencesPerInterval();
			
			// On initialise le timer selon les données reçues de timeline
			this.timer = new AnimationTimer() {
				@Override
				public void handle(long currentNanoTime) {
					if(timeline.isEmpty()) {
						this.stop();
					}
					if(currentNanoTime % 1000000000 == 0) {
						controller.notifySpecieNameChanged(controller.getSpecieName(), intervals.get(0));
						timeline.remove(0);
						intervals.remove(0);
						timelineProgressBar.setProgress(timelineProgressBar.getProgress() + scaleProg);
					}
//					timelineProgressBar.setProgress(currentNanoTime / 1000000000);
//					System.out.println(timelineProgressBar.getProgress());
				}
			};
		}
		
	}
	
	@Override
	public void specieNameChanged(SpecieNameChangedEvent event) {
		this.updateSpecie(event.getSpecieName(), event.getGeoHashAndNumberOfOccurrences(), event.getMaxMinOccurrences(), event.getIs3D());
	}
}
