package view;

import java.net.URL;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import utils.CameraManager;

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

        Camera camera = new PerspectiveCamera(true);
        new CameraManager(camera, this, root3D);

        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-180);
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);

        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);
        
        SubScene subscene = new SubScene(root3D, 600, 600, true, SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
        subscene.setFill(Color.GREY);
		this.getChildren().add(subscene);
	}
}
