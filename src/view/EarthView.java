package view;

import java.net.URL;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MeshView;

public class EarthView extends Pane {
	private Group root3D;
	
	public EarthView() {
		root3D = new Group();
		
        ObjModelImporter objImporter = new ObjModelImporter();
        URL modelUrl = this.getClass().getResource("/res/earth.obj");
        objImporter.read(modelUrl);
        MeshView[] meshViews = objImporter.getImport();
        Group earth = new Group(meshViews);
        root3D.getChildren().add(earth);
        
        this.getChildren().add(root3D);
	}
}
