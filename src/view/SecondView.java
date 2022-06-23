package view;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Controller;
import event.GeoHashChangedEvent;
import event.GeoHashListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import model.animal.Report;
import model.animal.Specie;

/**
 * Vue secondaire responsable de l'affichage de la liste des espèces et des signalements.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public class SecondView implements Initializable, ViewGeoHashInterface, GeoHashListener {
	private Controller controller;

	@FXML
	private Label geoHashLabel1;
	
	@FXML
	private Label geoHashLabel2;
	
	@FXML
	private ListView<String> listViewSpecies;
	
	@FXML
	private Accordion accordionReports;
	
	@FXML
	private Button useSelectedSpecieButton;
	
	public SecondView(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Si on clique 2 fois sur le nom d'une espèce ou sur le bouton de sélection
		// On effectue la recherche pour l'espèce selectionnée
		this.listViewSpecies.setOnMouseClicked(event -> {
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				controller.notifySpecieNameChanged(listViewSpecies.getSelectionModel().getSelectedItem());
			}
		});
		
		this.useSelectedSpecieButton.setOnMouseClicked(event -> {
			controller.notifySpecieNameChanged(listViewSpecies.getSelectionModel().getSelectedItem());
		});
		
	}

	@Override
	public void updateGeoHash(String geoHash, ObservableList<Report> reports, ObservableList<Specie> species) {
		// On ajoute le géohash pour lequel on a demandé les infos
		this.geoHashLabel1.setText("For the following GeoHash: " + geoHash);
		this.geoHashLabel2.setText("For the following GeoHash: " + geoHash);
		
		// On ajoute la liste des espèces à ce géohash
		ObservableList<String> specieItems = FXCollections.observableArrayList();
		for(Specie s : species) {
			specieItems.add(s.getScientificName());
		}
		
		this.listViewSpecies.getItems().clear();
		
		this.listViewSpecies.getItems().addAll(specieItems);
		
		// On ajoute la liste des signalements et les infos supplémentaires dans l'accordéon
		ObservableList<TitledPane> reportItems = FXCollections.observableArrayList();
		for(Report r : reports) {			
			reportItems.add(
					new TitledPane(r.getId(),
					new Label("scientificName: " + r.getSpecie().getScientificName() + "\n"
							+ "order: " + r.getSpecie().getOrder() + "\n"
							+ "class: " + r.getSpecie().getSuperClass() + "\n"
							+ "recordedBy: " + r.getRecordedBy())));
		}
		this.accordionReports.getPanes().addAll(reportItems);
	}

	@Override
	public void geoHashChanged(GeoHashChangedEvent event) {
		this.updateGeoHash(event.getGeoHash(), event.getListReports(), event.getListSpecie());
	}
}
