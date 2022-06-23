package event;

/**
 * Listener r�agissant � un changement de g�ohash.<br/>
 * Utilis� lorsqu'on a besoin de la valeur d'un g�ohash pr�cis.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface GeoHashListener {
	/**
	 * R�agit � un changement de la valeur du GeoHash dans le mod�le.
	 * 
	 * @param event : l'�v�nement correspondant � ce changement
	 */
	public void geoHashChanged(GeoHashChangedEvent event);
}
