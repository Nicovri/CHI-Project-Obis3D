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
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.geohash.GeoHashHelper;
import model.geohash.Location;
import utils.CameraManager;
import utils.ColorLegend8;

/**
 * Vue responsable de l'affichage de la planete Terre et des elements.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
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
	
	/**
	 * Initialisation du modele 3D de la Terre
	 * et du gestionnaire d'evenements directement associe
	 * (gestion du clic a la surface de la Terre)
	 */
	private void initializeEarthModel() {
		// Creation et affichage du modele 3D
        ObjModelImporter objImporter = new ObjModelImporter();
        // URL a remplacer selon comment on importe le code (/earth.obj)
        URL modelUrl = this.getClass().getResource("/res/earth.obj");
        objImporter.read(modelUrl);
        MeshView[] meshViews = objImporter.getImport();
        Group earth = new Group(meshViews);
        root3D.getChildren().add(earth);

        // Mise en place de la camera et de son gestionnaire
        Camera camera = new PerspectiveCamera(true);
        new CameraManager(camera, this, root3D);

        // Ajout de lumiere directionnelle
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-180);
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);

        // Ajout de lumiere ambiente
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);
        
        // Ajout de la subscene associee a la Terre
        SubScene subscene = new SubScene(root3D, 600, 600, true, SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
        subscene.setFill(Color.GREY);
		this.getChildren().add(subscene);
		
		// Rendre les cubes visibles en les eloignant legerement de la surface de la Terre
		occs.setScaleX(1.001);
		occs.setScaleY(1.001);
		occs.setScaleZ(1.001);
		root3D.getChildren().add(occs);
		
		// Lorsqu'on clique sur la Terre avec la touche CTRL appuyee
        subscene.addEventHandler(MouseEvent.ANY, event -> {
        	if(event.getEventType() == MouseEvent.MOUSE_PRESSED && event.isControlDown()) {
        		// On recupere les coordonnees du clic
        		PickResult pickResult = event.getPickResult();
        		Point3D spaceCoord = pickResult.getIntersectedPoint();
        		
        		// On les transforme en une localisation sur le globe
        		Point2D cursor = spaceCoordToGeoCoord(spaceCoord);
        		Location loc = new Location("selectedGeoHash", cursor.getX(), cursor.getY());
        		
        		// Si le geohash est bien valide
        		if(!GeoHashHelper.getGeohash(loc, 3).equals("000")) {
        			pointer = new Group();
        			root3D.getChildren().add(pointer);
        			
        			// On ajoute une sphere a cet endroit
        			Sphere sphere = new Sphere(0.01);
        			sphere.setTranslateX(spaceCoord.getX());
        			sphere.setTranslateY(spaceCoord.getY());
        			sphere.setTranslateZ(spaceCoord.getZ());
        			final PhongMaterial cursorMaterial = new PhongMaterial();
        			cursorMaterial.setDiffuseColor(Color.RED);
        			cursorMaterial.setSpecularColor(Color.RED);
        			sphere.setMaterial(cursorMaterial);
        			pointer.getChildren().add(sphere);
        			
        			// On charge la vue secondaire et on l'affiche dans une fenetre modale
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
        			
        			// Apres la fermeture de la fenetre, on enleve la sphere de l'endroit ou on a clique
        			pointer.getChildren().clear();
        		}
        	}
        });
	}
	
	/**
	 * Convertit une latitude et longitude en coordonnees 3D.
	 * 
	 * @param lat : lattitude
	 * @param lon : longitude
	 * @return les coordonnees 3D correspondantes
	 */
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
    
    /**
     * Ajoute un quadrilatere dans le groupe parent entre en parametres.
     * 
     * @param parent : le groupe parent auquel est ajoute le quadrilatere
     * @param topRight : le point superieur droit
     * @param bottomRight : le point inferieur droit
     * @param bottomLeft : le point inferieur gauche
     * @param topLeft : le point superieur gauche
     * @param material : le materiau a utiliser sur le quadrilatere
     */
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
    
    /**
     * Ajoute un item d'histogramme 3D dans le groupe parent entre en parametres.
     * 
     * @param parent : le groupe parent auquel est ajoute le quadrilatere
     * @param middle : le point milieu
     * @param occ : le nombre d'occurrence (pour faire varier la profondeur)
     * @param material : le materiau a utiliser sur le quadrilatere
     */
    private void addHistogramItem(Group parent, Point3D middle, double occ, PhongMaterial material) {
    	Point3D origin = new Point3D(0, 0, 0);
    	
    	double x = middle.getX();
    	double y = middle.getY();
    	double z = middle.getZ();
    	
    	double h = Math.sqrt(Math.sqrt(occ));
    	x += x*h/10;
    	y += y*h/10;
    	z += z*h/10;
    	Point3D m = new Point3D(x, y, z);
    	
    	Point3D yAxis = new Point3D(0,1,0);
    	Point3D seg = m.subtract(origin);
    	double height = seg.magnitude();
    	Point3D midPoint = m.midpoint(origin);
    	Translate moveToMidPoint = new Translate(midPoint.getX(), midPoint.getY(), midPoint.getZ());
    	Point3D axisOfRotation = seg.crossProduct(yAxis);
    	double angle = Math.acos(seg.normalize().dotProduct(yAxis));
    	Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);
    	
    	Cylinder line = new Cylinder(0.01, height);
    	line.getTransforms().addAll(moveToMidPoint, rotateAroundCenter);
    	
    	line.setMaterial(material);
    	parent.getChildren().add(line);
    }
    
    /**
     * Convertit des coordonnees 3D en une latitude et une longitude.
     * 
     * @param p : les coordonnees 3D a convertir
     * @return la paire lattitude/longitude correspondante
     */
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
		if(maxMinOcc.getKey() >= 0) {
			occs.getChildren().clear();
			
			// Selon le nombre d'occurrences, on change la couleur du quadrilatere affiche
			Long[] scales = new Long[8];
			for(int i = 0; i < 8; i++) {
				scales[i] = Math.round(maxMinOcc.getKey() / (Math.pow(8-i, 4)));
			}
			for(String geoHash : occurrences.keySet()) {
				int i = 1;
				for(int j = 0; j < 8; j++) {
					if(occurrences.get(geoHash).longValue() > scales[j]) {
						i = j;
					}
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
				
				Point3D m = geoCoordTo3dCoord(la, lo);
				
				if(!is3D) {
					this.addQuadrilateral(occs, tr, br, bl, tl, material);
				} else {
					this.addHistogramItem(occs, m, occurrences.get(geoHash), material);
				}
			}
		}
	}

	@Override
	public void specieNameChanged(SpecieNameChangedEvent event) {
		this.updateSpecie(event.getSpecieName(), event.getGeoHashAndNumberOfOccurrences(), event.getMaxMinOccurrences(), event.getIs3D());
	}
}
