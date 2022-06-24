package event;

/**
 * Listener reagissant a un changement de geohash.<br/>
 * Utilise lorsqu'on a besoin de la valeur d'un geohash precis.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface GeoHashListener {
	/**
	 * Reagit a un changement de la valeur du geohash dans le modele.
	 * 
	 * @param event : l'evenement correspondant a ce changement
	 */
	public void geoHashChanged(GeoHashChangedEvent event);
}
