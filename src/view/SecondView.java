package view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.Controller;
import event.GeoHashChangedEvent;
import event.GeoHashListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import model.animal.Individual;
import model.animal.Specie;

public class SecondView implements Initializable, ViewGeoHashInterface, GeoHashListener {
	private Controller controller;

	@FXML
	private Label geoHashLabel1;
	
	@FXML
	private Label geoHashLabel2;
	
	@FXML
	private ListView<String> listViewSpecies;
	
	@FXML
	private Accordion accordionIndividuals;
	
	@FXML
	private Button useSelectedSpecieButton;
	
	public SecondView(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.geoHashLabel1.setText("For the following GeoHash: " + controller.getGeoHash());
		this.geoHashLabel2.setText("For the following GeoHash: " + controller.getGeoHash());
		
//		this.listViewSpecies.getSelectionModel().selectedItemProperty().addListener(null);
		
		ObservableList<String> specieItems = FXCollections.observableArrayList();
		specieItems.add("test1");
		specieItems.add("test2");
		
		this.listViewSpecies.getItems().addAll(specieItems);
		
		ObservableList<TitledPane> individualItems = FXCollections.observableArrayList();
		individualItems.add(new TitledPane("title", new Label("scientificName\nOrder\nClass\nRecordedBy")));
		this.accordionIndividuals.getPanes().addAll(individualItems);
	}

	@Override
	public void updateGeoHash(String geoHash, ObservableList<Individual> individuals, ObservableList<Specie> species) {
		this.geoHashLabel1.setText("For the following GeoHash: " + geoHash);
		this.geoHashLabel2.setText("For the following GeoHash: " + geoHash);
	}

	@Override
	public void geoHashChanged(GeoHashChangedEvent event) {
		this.updateGeoHash(event.getGeoHash(), event.getListIndividuals(), event.getListSpecie());
	}
}
