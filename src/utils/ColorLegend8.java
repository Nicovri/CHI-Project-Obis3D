package utils;

import javafx.scene.paint.Color;

public enum ColorLegend8 {
	C1(Color.web("#4B1919")),
	C2(Color.web("#3E008E")),
	C3(Color.web("#0A2CBE")),
	C4(Color.web("#01C2C7")),
	C5(Color.web("#64DA09")),
	C6(Color.web("#DBDE00")),
	C7(Color.web("#F2AA06")),
	C8(Color.web("#B10026"));
	
	private Color color;
	
	private ColorLegend8(Color color) {
		this.color = color;
	}
	
	public Color getColor() { return this.color; }
}
