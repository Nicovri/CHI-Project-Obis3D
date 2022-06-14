module Obis3D {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires jimObjModelImporterJFX;
	
	opens application to javafx.graphics, javafx.fxml;
	opens view to javafx.fxml;
}
