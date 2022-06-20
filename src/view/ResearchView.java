package view;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.ResourceBundle;

import controller.Controller;
import event.SpecieNameChangedEvent;
import event.SpecieNameListener;
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
		});
		mode3DButton.setOnMouseClicked(event -> {
			mode2DButton.setFont(fontRegular);
			mode3DButton.setFont(fontBold);
		});
		
//		searchBar.focusedProperty().addListener(event -> {
//			if(searchBar.isFocused()) {				
//				List<String> list = controller.getListSuggestions("Selachii");
//				for(String s : list) {				
//					suggestions.getItems().add(s);
//				}
//				suggestions.setVisible(true);
//			} else {
//				for(String s : suggestions.getItems()) {					
//					suggestions.getItems().remove(s);
//				}
//				suggestions.setVisible(false);
//			}
//		});
		
		searchBar.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ENTER) {
				controller.notifySpecieNameChanged(searchBar.getText());
			}
		});
		
		searchButton.setOnMouseClicked(event -> {
			controller.notifySpecieNameChanged(searchBar.getText());
		});
	}

	@Override
	public void update(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc) {
		if(maxMinOcc.getKey() > 0) {
			this.correctSpecieName = specieName;
			this.searchBar.setText(correctSpecieName);
		} else {
			Alert error = new Alert(AlertType.ERROR, "The specie's scientific name is not in the database...", ButtonType.OK);
			error.show();
			this.searchBar.setText(correctSpecieName);
		}
	}

	@Override
	public void specieNameChanged(SpecieNameChangedEvent event) {
		this.update(event.getSpecieName(), event.getGeoHashAndNumberOfOccurrences(), event.getMaxOccurrences());
	}
}
