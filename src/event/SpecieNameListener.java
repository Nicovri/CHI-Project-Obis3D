package event;

/**
 * Listener reagissant a un changement de nom d'espece.<br/>
 * Utilise lorsqu'on a besoin de la valeur d'un nom d'espece precis.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface SpecieNameListener {

	/**
	 * Reagit a un changement de la valeur du nom de l'espece dans le modele.
	 * @param event : l'evenement correspondant a ce changement
	 */
	public void specieNameChanged(SpecieNameChangedEvent event);
}
