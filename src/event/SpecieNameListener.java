package event;

/**
 * Listener réagissant à un changement de nom d'espèce.<br/>
 * Utilisé lorsqu'on a besoin de la valeur d'un nom d'espèce précis.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface SpecieNameListener {

	/**
	 * Réagit à un changement de la valeur du nom de l'espèce dans le modèle.
	 * @param event : l'événement correspondant à ce changement
	 */
	public void specieNameChanged(SpecieNameChangedEvent event);
}
