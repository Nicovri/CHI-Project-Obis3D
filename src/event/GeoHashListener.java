package event;

public interface GeoHashListener {
	/**
	 * Réagit à un changement de la valeur du GeoHash dans le modèle.
	 * @param event : l'événement correcpondant à ce changement
	 */
	public void geoHashChanged(GeoHashChangedEvent event);
}
