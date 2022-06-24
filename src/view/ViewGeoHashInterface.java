package view;

import javafx.collections.ObservableList;
import model.animal.Report;
import model.animal.Specie;

/**
 * Interface de mise a jour geohash de la vue concernee.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ViewGeoHashInterface {
	/**
	 * Met a jour la vue en accord avec le modele.
	 * 
	 * @param geoHash : la valeur du geohash a inclure dans la vue
	 * @param reports : la liste des signalements a inclure dans la vue
	 * @param species : la liste des especes a inclure dans la vue
	 */
	public void updateGeoHash(String geoHash, ObservableList<Report> reports, ObservableList<Specie> species);
}
