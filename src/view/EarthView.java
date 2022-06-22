package view;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import controller.Controller;
import event.SpecieNameChangedEvent;
import event.SpecieNameListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;
import model.geohash.GeoHashHelper;
import model.geohash.Location;
import utils.CameraManager;
import utils.ColorLegend8;

public class EarthView extends Pane implements ViewSpecieInterface, SpecieNameListener {
    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;
    private static final float TEXTURE_OFFSET = 1.01f;
    
    private static final int SIZE_RECT = 1;
    
	private Controller controller;
	
	private SecondView secondView;
	
	private Group root3D;
	private Group occs;
	private Group pointer;
	
	public EarthView(Controller controller) {
		this.controller = controller;
		
		root3D = new Group();
		
		occs = new Group();
		
		pointer = new Group();
		
		this.initializeEarthModel();
	}
	
	private void initializeEarthModel() {
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
		
		occs.setScaleX(1.001);
		occs.setScaleY(1.001);
		occs.setScaleZ(1.001);
		root3D.getChildren().add(occs);
		
        subscene.addEventHandler(MouseEvent.ANY, event -> {
        	if(event.getEventType() == MouseEvent.MOUSE_PRESSED && event.isControlDown()) {
        		PickResult pickResult = event.getPickResult();
        		Point3D spaceCoord = pickResult.getIntersectedPoint();
        		
        		Point2D cursor = spaceCoordToGeoCoord(spaceCoord);
        		Location loc = new Location("selectedGeoHash", cursor.getX(), cursor.getY());
        		        		
        		if(!GeoHashHelper.getGeohash(loc, 3).equals("000")) {
        			pointer = new Group();
        			root3D.getChildren().add(pointer);
        			
        			Sphere sphere = new Sphere(0.01);
        			sphere.setTranslateX(spaceCoord.getX());
        			sphere.setTranslateY(spaceCoord.getY());
        			sphere.setTranslateZ(spaceCoord.getZ());
        			final PhongMaterial cursorMaterial = new PhongMaterial();
        			cursorMaterial.setDiffuseColor(Color.RED);
        			cursorMaterial.setSpecularColor(Color.RED);
        			sphere.setMaterial(cursorMaterial);
        			pointer.getChildren().add(sphere);
        			
        			secondView = new SecondView(this.controller);
        			
        			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SecondView.fxml"));
        			fxmlLoader.setController(secondView);
        			
        			this.controller.addGeoHashListener(secondView);
        			
        			Parent root = null;
        			try {
        				root = fxmlLoader.load();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			
        			this.controller.notifyGeoHashChanged(GeoHashHelper.getGeohash(loc, 3));
        			
        			Stage stage = new Stage();
        			stage.initModality(Modality.APPLICATION_MODAL); // Modality.NONE
//        			stage.setAlwaysOnTop(this.getScene().getWindow().isShowing());
        			stage.setOpacity(1);
        			stage.setTitle("Lists");
        			stage.setScene(new Scene(root));        			
        			stage.showAndWait();
        			
        			pointer.getChildren().clear();
        		}
        	}
        });
	}
	
    private Point3D geoCoordTo3dCoord(float lat, float lon) {
        float lat_cor = lat + TEXTURE_LAT_OFFSET;
        float lon_cor = lon + TEXTURE_LON_OFFSET;
        return new Point3D(
                -java.lang.Math.sin(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor)),
                -java.lang.Math.sin(java.lang.Math.toRadians(lat_cor)),
                java.lang.Math.cos(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor)));
    }
    
    private void addQuadrilateral(Group parent, Point3D topRight, Point3D bottomRight, Point3D bottomLeft, Point3D topLeft, PhongMaterial material) {
    	final TriangleMesh triangleMesh = new TriangleMesh();
    	
    	final float[] points = {
    		(float)topRight.getX(), (float)topRight.getY(), (float)topRight.getZ(),
    		(float)topLeft.getX(), (float)topLeft.getY(), (float)topLeft.getZ(),
    		(float)bottomLeft.getX(), (float)bottomLeft.getY(), (float)bottomLeft.getZ(),
    		(float)bottomRight.getX(), (float)bottomRight.getY(), (float)bottomRight.getZ()
    	};
    	
    	final float[] texCoords = {
    			1, 1,
    			1, 0,
    			0, 1,
    			0, 0
    	};
    	
    	final int[] faces = {
    		0, 1, 1, 0, 2, 2,
    		0, 1, 2, 2, 3, 3
    	};
    	
    	triangleMesh.getPoints().setAll(points);
    	triangleMesh.getTexCoords().setAll(texCoords);
    	triangleMesh.getFaces().setAll(faces);
    	
    	final MeshView meshView = new MeshView(triangleMesh);
    	meshView.setMaterial(material);
    	parent.getChildren().addAll(meshView);
    }
    
    private Point2D spaceCoordToGeoCoord(Point3D p) {
    	float lat = (float)(Math.asin(-p.getY() / TEXTURE_OFFSET) * (180 / Math.PI) - TEXTURE_LAT_OFFSET);
    	float lon;
    	
    	if(p.getZ() < 0) {
    		lon = 180 - (float)(Math.asin(-p.getX() / (TEXTURE_OFFSET * Math.cos((Math.PI / 180) * (lat + TEXTURE_LAT_OFFSET)))) * 180 / Math.PI + TEXTURE_LON_OFFSET);
    	} else {
    		lon = (float)(Math.asin(-p.getX() / (TEXTURE_OFFSET * Math.cos((Math.PI / 180) * (lat + TEXTURE_LAT_OFFSET)))) * 180 / Math.PI + TEXTURE_LON_OFFSET);
    	}
    	return new Point2D(lat, lon);
    }

	@Override
	public void updateSpecie(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc, boolean is3D) {
		if(maxMinOcc.getKey() > 0) {
			occs.getChildren().clear();
			
			Double scale = (maxMinOcc.getKey().longValue() - maxMinOcc.getValue().longValue()) / 8.0;
			for(String geoHash : occurrences.keySet()) {
				int i = 1;
				while(occurrences.get(geoHash).longValue() > Math.round(scale*i)) {
					i++;
				}
				Color color;
				switch(i) {
				case 1:
					color = ColorLegend8.C1.getColor();
					break;
				case 2:
					color = ColorLegend8.C2.getColor();
					break;
				case 3:
					color = ColorLegend8.C3.getColor();
					break;
				case 4:
					color = ColorLegend8.C4.getColor();
					break;
				case 5:
					color = ColorLegend8.C5.getColor();
					break;
				case 6:
					color = ColorLegend8.C6.getColor();
					break;
				case 7:
					color = ColorLegend8.C7.getColor();
					break;
				case 8:
					color = ColorLegend8.C8.getColor();
					break;
				default:
					color = ColorLegend8.C8.getColor();
					break;
				}
				
				final PhongMaterial material = new PhongMaterial();
				material.setDiffuseColor(color);
				material.setSpecularColor(color);
				
				Location location = GeoHashHelper.getLocation(geoHash);
				float la = ((Double)location.lat()).floatValue();
				float lo = ((Double)location.lng()).floatValue();
				
				Point3D tr = geoCoordTo3dCoord(la, lo + SIZE_RECT);
				Point3D br = geoCoordTo3dCoord(la, lo);
				Point3D bl = geoCoordTo3dCoord(la + SIZE_RECT, lo);
				Point3D tl = geoCoordTo3dCoord(la + SIZE_RECT, lo + SIZE_RECT);
				
				if(!is3D) {
					this.addQuadrilateral(occs, tr, br, bl, tl, material);
				} else {
//					this.addHistogram(occs, ...)
				}
			}
		}
	}

	@Override
	public void specieNameChanged(SpecieNameChangedEvent event) {
		this.updateSpecie(event.getSpecieName(), event.getGeoHashAndNumberOfOccurrences(), event.getMaxMinOccurrences(), event.getIs3D());
	}
}
