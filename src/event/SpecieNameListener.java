package event;

/**
 * Listener r�agissant � un changement de nom d'esp�ce.<br/>
 * Utilis� lorsqu'on a besoin de la valeur d'un nom d'esp�ce pr�cis.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface SpecieNameListener {

	/**
	 * R�agit � un changement de la valeur du nom de l'esp�ce dans le mod�le.
	 * @param event : l'�v�nement correspondant � ce changement
	 */
	public void specieNameChanged(SpecieNameChangedEvent event);
}
