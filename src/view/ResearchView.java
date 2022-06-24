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
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;
import utils.Requests;

/**
 * Vue responsable de l'affichage des champs de recherche et de changement de mode.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate;
		if(controller.getStartDate() != null) {			
			localDate = LocalDate.parse(controller.getStartDate(), formatter);
		} else {
			localDate = LocalDate.parse("1027-01-01", formatter);
		}
		this.startDate.setValue(localDate);
		endDate.setValue(LocalDate.now());
		suggestions.setVisible(false);
		
		// Si on modifie les dates, on en informe le modele grace au controlleur
		startDate.setOnAction(event -> {
			controller.notifySpecieNameAndDateChanged(searchBar.getText(), startDate.getValue().toString(), endDate.getValue().toString());
		});
		endDate.setOnAction(event -> {
			controller.notifySpecieNameAndDateChanged(searchBar.getText(), startDate.getValue().toString(), endDate.getValue().toString());
		});
		
		// Chaque bouton informe le modele du mode de rendu visuel (3D ou 2D) lorsqu'il est clique
		// Le texte devient ecrit en gras
		fontRegular = mode2DButton.getFont();
		fontBold = Font.font(fontRegular.getName(), FontWeight.BOLD, FontPosture.REGULAR, fontRegular.getSize());
		mode2DButton.setFont(fontBold);
		
		mode2DButton.setOnMouseClicked(event -> {
			mode2DButton.setFont(fontBold);
			mode3DButton.setFont(fontRegular);
			controller.notifySpecieNameChanged(false);
		});
		mode3DButton.setOnMouseClicked(event -> {
			mode2DButton.setFont(fontRegular);
			mode3DButton.setFont(fontBold);
			controller.notifySpecieNameChanged(true);
		});
		
		// Si la barre de recherche ou la liste des suggestions est en focus
		// On affiche la liste des suggestions, sinon, on la cache
		searchBar.focusedProperty().addListener(event -> {
			if(!(searchBar.isFocused() || suggestions.isFocused())) {
				suggestions.setVisible(false);
			} else {
				suggestions.setVisible(true);
			}
		});
		
		// Lorsque le texte de la barre de recherche change, on affiche les suggestions dans la ListView
		// N'effectue la requete qu'au dessus de 2 caracteres (pour moins de requetes)
		searchBar.textProperty().addListener(event -> {
			suggestions.getItems().clear();
			// erreur String null si pas Internet
			if(searchBar.getText().length() > 2) {
				suggestions.getItems().addAll(controller.getListSuggestions(searchBar.getText()));
			}
		});
		
		// Si on appuie sur entree, qu'on clique sur le bouton de recherche,
		// ou qu'on selectionne un element dans la liste des suggestions,
		// on effectue la recherche pour cette espece 
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
				controller.notifySpecieNameChanged(suggestions.getSelectionModel().getSelectedItem());
			}
		});
		
		suggestions.setOnKeyPressed(event -> {
			if(event.getCode().equals(KeyCode.ENTER)) {
				controller.notifySpecieNameChanged(suggestions.getSelectionModel().getSelectedItem());
			}
		});
	}

	@Override
	public void updateSpecie(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc, boolean is3D) {
		// Si la requete est nulle, on affiche une fenetre d'erreur, le nom d'espece n'existe pas
		// (pas trouve mieux comme solution, sinon, on depend du fait que le nombre d'occurrences est positif)
		// (alors que s'il est nul, on doit juste ne rien afficher, pour ne pas voir apparaitre la fenetre d'erreur lors du mode lecture)
		if(Requests.getFromRequest(Requests.buildSpecieRequestWithGrid(specieName, this.startDate.getValue().toString(), this.endDate.getValue().toString(), 3)) != null) {
			this.correctSpecieName = specieName;
			this.searchBar.setText(correctSpecieName);
			this.suggestions.getItems().clear();
			suggestions.setVisible(false);
		} else {
			Alert error = new Alert(AlertType.ERROR, "The specie's scientific name is not in the database...", ButtonType.OK);
			error.show();
			this.searchBar.setText(correctSpecieName);
		}
	}

	@Override
	public void specieNameChanged(SpecieNameChangedEvent event) {
		this.updateSpecie(event.getSpecieName(), event.getGeoHashAndNumberOfOccurrences(), event.getMaxMinOccurrences(), event.getIs3D());
	}
}
