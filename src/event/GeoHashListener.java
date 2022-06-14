package event;

public interface GeoHashListener {
	/**
	 * R�agit � un changement de la valeur du GeoHash dans le mod�le.
	 * @param event : l'�v�nement correcpondant � ce changement
	 */
	public void geoHashChanged(GeoHashChangedEvent event);
}
