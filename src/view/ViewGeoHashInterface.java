package view;

import javafx.collections.ObservableList;
import model.animal.Report;
import model.animal.Specie;

/**
 * Interface de mise à jour géohash de la vue concernée.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ViewGeoHashInterface {
	/**
	 * Met à jour la vue en accord avec le modèle.
	 * 
	 * @param geoHash : la valeur du géohash à inclure dans la vue
	 * @param reports : la liste des signalements à inclure dans la vue
	 * @param species : la liste des espèces à inclure dans la vue
	 */
	public void updateGeoHash(String geoHash, ObservableList<Report> reports, ObservableList<Specie> species);
}
