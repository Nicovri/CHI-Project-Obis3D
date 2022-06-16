package view;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ResearchView implements Initializable {

	@FXML
	private TextField searchBar;
	
	@FXML
	private ListView suggestions;
	
	@FXML
	private DatePicker startDate;
	
	@FXML
	private DatePicker endDate;
	
	@FXML
	private Button mode2DButton;
	
	@FXML
	private Button mode3DButton;
	
	public ResearchView() {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse("01-01-1027", formatter);
		startDate.setValue(localDate);
		endDate.setValue(LocalDate.now());
	}
}
