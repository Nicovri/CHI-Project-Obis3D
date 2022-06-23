package view;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import controller.Controller;
import event.SpecieNameChangedEvent;
import event.SpecieNameListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import utils.ColorLegend8;

/**
 * Vue responsable de l'affichage de la légende et du nombre minimal et maximal pour chaque couleur.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public class LegendView implements Initializable, ViewSpecieInterface, SpecieNameListener {
	@SuppressWarnings("unused")
	private Controller controller;
	
	@FXML
	private Pane pane1;
	@FXML
	private Pane pane2;
	@FXML
	private Pane pane3;
	@FXML
	private Pane pane4;
	@FXML
	private Pane pane5;
	@FXML
	private Pane pane6;
	@FXML
	private Pane pane7;
	@FXML
	private Pane pane8;
	
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private Label label3;
	@FXML
	private Label label4;
	@FXML
	private Label label5;
	@FXML
	private Label label6;
	@FXML
	private Label label7;
	@FXML
	private Label label8;
	
	
	public LegendView(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pane1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		pane2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		pane3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		pane4.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		pane5.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		pane6.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		pane7.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		pane8.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		Color color1 = ColorLegend8.C1.getColor();
		Color color2 = ColorLegend8.C2.getColor();
		Color color3 = ColorLegend8.C3.getColor();
		Color color4 = ColorLegend8.C4.getColor();
		Color color5 = ColorLegend8.C5.getColor();
		Color color6 = ColorLegend8.C6.getColor();
		Color color7 = ColorLegend8.C7.getColor();
		Color color8 = ColorLegend8.C8.getColor();
		
		pane1.setBackground(new Background(new BackgroundFill(color1, new CornerRadii(0), new Insets(0))));
		pane2.setBackground(new Background(new BackgroundFill(color2, new CornerRadii(0), new Insets(0))));
		pane3.setBackground(new Background(new BackgroundFill(color3, new CornerRadii(0), new Insets(0))));
		pane4.setBackground(new Background(new BackgroundFill(color4, new CornerRadii(0), new Insets(0))));
		pane5.setBackground(new Background(new BackgroundFill(color5, new CornerRadii(0), new Insets(0))));
		pane6.setBackground(new Background(new BackgroundFill(color6, new CornerRadii(0), new Insets(0))));
		pane7.setBackground(new Background(new BackgroundFill(color7, new CornerRadii(0), new Insets(0))));
		pane8.setBackground(new Background(new BackgroundFill(color8, new CornerRadii(0), new Insets(0))));
	}

	@Override
	public void updateSpecie(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc, boolean is3D) {
		if(maxMinOcc.getKey() > 0) {
			// Selon le nombre d'occurrences max et min, on change le nombre minimal et maximal pour chaque couleur
			Double scale = (maxMinOcc.getKey().longValue() - maxMinOcc.getValue().longValue()) / 8.0;
			label1.setText("From " + maxMinOcc.getValue().longValue() + " to " + Math.round(scale*1));
			label2.setText("From " + Math.round(scale*1) + " to " + Math.round(scale*2));
			label3.setText("From " + Math.round(scale*2) + " to " + Math.round(scale*3));
			label4.setText("From " + Math.round(scale*3) + " to " + Math.round(scale*4));
			label5.setText("From " + Math.round(scale*4) + " to " + Math.round(scale*5));
			label6.setText("From " + Math.round(scale*5) + " to " + Math.round(scale*6));
			label7.setText("From " + Math.round(scale*6) + " to " + Math.round(scale*7));
			label8.setText("From " + Math.round(scale*7) + " to " + maxMinOcc.getKey());
		}
	}

	@Override
	public void specieNameChanged(SpecieNameChangedEvent event) {
		this.updateSpecie(event.getSpecieName(), event.getGeoHashAndNumberOfOccurrences(), event.getMaxMinOccurrences(), event.getIs3D());
	}

}
