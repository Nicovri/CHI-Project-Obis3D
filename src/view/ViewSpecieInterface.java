package view;

import java.util.Map;

import javafx.util.Pair;

/**
 * Interface de mise a jour nom d'espece de la vue concernee.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ViewSpecieInterface {
	/**
	 * Met a jour la vue en accord avec le modele.
	 * 
	 * @param specieName : le nom de l'espece a inclure dans la vue
	 * @param occurrences : le nombre d'occurrences par geohash a inclure dans la vue
	 * @param maxMinOcc : les valeurs max et min d'occurrences a un geohash
	 * @param is3D : le rendu est-il 3D ou 2D
	 */
	public void updateSpecie(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc, boolean is3D);
}
