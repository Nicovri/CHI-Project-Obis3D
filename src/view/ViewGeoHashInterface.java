package view;

import javafx.collections.ObservableList;
import model.animal.Report;
import model.animal.Specie;

/**
 * Interface de mise � jour g�ohash de la vue concern�e.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ViewGeoHashInterface {
	/**
	 * Met � jour la vue en accord avec le mod�le.
	 * 
	 * @param geoHash : la valeur du g�ohash � inclure dans la vue
	 * @param reports : la liste des signalements � inclure dans la vue
	 * @param species : la liste des esp�ces � inclure dans la vue
	 */
	public void updateGeoHash(String geoHash, ObservableList<Report> reports, ObservableList<Specie> species);
}
