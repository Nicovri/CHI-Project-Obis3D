package view;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import controller.Controller;
import event.SpecieNameChangedEvent;
import event.SpecieNameListener;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

public class ResearchView implements Initializable, ViewSpecieInterface, SpecieNameListener {
	private Controller controller;
	
	private String correctSpecieName;
	
	private Font fontBold;
	private Font fontRegular;

	@FXML
	private Button searchButton;
	
	@FXML
	private TextField searchBar;
	
	@FXML
	private ListView<String> suggestions;
	
	@FXML
	private DatePicker startDate;
	
	@FXML
	private DatePicker endDate;
	
	@FXML
	private Button mode2DButton;
	
	@FXML
	private Button mode3DButton;
	
	public ResearchView(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse("01-01-1027", formatter);
		startDate.setValue(localDate);
		endDate.setValue(LocalDate.now());
		
		startDate.setOnAction(event -> {
			controller.notifySpecieNameAndDateChanged(searchBar.getText(), startDate.getValue().toString(), endDate.getValue().toString());
		});
		
		suggestions.setVisible(false);
		
		fontRegular = mode2DButton.getFont();
		fontBold = Font.font(fontRegular.getName(), FontWeight.BOLD, FontPosture.REGULAR, fontRegular.getSize());
		mode2DButton.setFont(fontBold);
		
		mode2DButton.setOnMouseClicked(event -> {
			mode2DButton.setFont(fontBold);
			mode3DButton.setFont(fontRegular);
			controller.notifySpecieNameChanged(searchBar.getText(), false);
		});
		mode3DButton.setOnMouseClicked(event -> {
			mode2DButton.setFont(fontRegular);
			mode3DButton.setFont(fontBold);
			controller.notifySpecieNameChanged(searchBar.getText(), true);
		});
		
		searchBar.textProperty().addListener(event -> {
			suggestions.getItems().clear();
			suggestions.getItems().addAll(controller.getListSuggestions(searchBar.getText()));
		});
		
		searchBar.focusedProperty().addListener(event -> {
			if(!(searchBar.isFocused() || suggestions.isFocused())) {
				suggestions.setVisible(false);
			} else {
				suggestions.setVisible(true);
			}
		});
		
		searchBar.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ENTER) {
				controller.notifySpecieNameChanged(searchBar.getText());
			}
		});
		
		searchButton.setOnMouseClicked(event -> {
			controller.notifySpecieNameChanged(searchBar.getText());
		});
		
		suggestions.setOnMouseClicked(event -> {
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
				// Des fois, il n'y a pas d'occurrences, alors que l'espèce est proposée dans la liste
				controller.notifySpecieNameChanged(suggestions.getSelectionModel().getSelectedItem());
			}
		});
	}

	@Override
	public void updateSpecie(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc, boolean is3D) {
		if(maxMinOcc.getKey() > 0) {
			this.correctSpecieName = specieName;
			this.searchBar.setText(correctSpecieName);
			this.suggestions.getItems().clear();
			suggestions.setVisible(false);
		} else {
			Alert error = new Alert(AlertType.ERROR, "The specie's scientific name is not in the database...", ButtonType.OK);
			error.show();
			this.searchBar.setText(correctSpecieName);
//			this.searchBar.selectEnd();
		}
	}

	@Override
	public void specieNameChanged(SpecieNameChangedEvent event) {
		this.updateSpecie(event.getSpecieName(), event.getGeoHashAndNumberOfOccurrences(), event.getMaxMinOccurrences(), event.getIs3D());
	}
}
