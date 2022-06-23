package event;

/**
 * Listener réagissant à un changement de géohash.<br/>
 * Utilisé lorsqu'on a besoin de la valeur d'un géohash précis.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface GeoHashListener {
	/**
	 * Réagit à un changement de la valeur du GeoHash dans le modèle.
	 * 
	 * @param event : l'événement correspondant à ce changement
	 */
	public void geoHashChanged(GeoHashChangedEvent event);
}
