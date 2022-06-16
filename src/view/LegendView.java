package view;

import java.net.URL;
import java.util.ResourceBundle;

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

public class LegendView implements Initializable {

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
	
	
	public LegendView() {
		
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
		
		Color color1 = Color.web("#250A0A");
		Color color2 = Color.web("#3E008E");
		Color color3 = Color.web("#0A2CBE");
		Color color4 = Color.web("#01C2C7");
		Color color5 = Color.web("#64DA09");
		Color color6 = Color.web("#DBDE00");
		Color color7 = Color.web("#F2AA06");
		Color color8 = Color.web("#B10026");
		
		pane1.setBackground(new Background(new BackgroundFill(color1, new CornerRadii(0), new Insets(0))));
		pane2.setBackground(new Background(new BackgroundFill(color2, new CornerRadii(0), new Insets(0))));
		pane3.setBackground(new Background(new BackgroundFill(color3, new CornerRadii(0), new Insets(0))));
		pane4.setBackground(new Background(new BackgroundFill(color4, new CornerRadii(0), new Insets(0))));
		pane5.setBackground(new Background(new BackgroundFill(color5, new CornerRadii(0), new Insets(0))));
		pane6.setBackground(new Background(new BackgroundFill(color6, new CornerRadii(0), new Insets(0))));
		pane7.setBackground(new Background(new BackgroundFill(color7, new CornerRadii(0), new Insets(0))));
		pane8.setBackground(new Background(new BackgroundFill(color8, new CornerRadii(0), new Insets(0))));
	}

}
