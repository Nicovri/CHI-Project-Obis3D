package event;

public interface SpecieNameListener {

	/**
	 * R�agit � un changement de la valeur du nom de l'esp�ce dans le mod�le.
	 * @param event : l'�v�nement correcpondant � ce changement
	 */
	public void specieNameChanged(SpecieNameChangedEvent event);
}
