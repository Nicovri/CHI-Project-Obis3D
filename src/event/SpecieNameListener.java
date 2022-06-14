package event;

public interface SpecieNameListener {

	/**
	 * Réagit à un changement de la valeur du nom de l'espèce dans le modèle.
	 * @param event : l'événement correcpondant à ce changement
	 */
	public void specieNameChanged(SpecieNameChangedEvent event);
}
