package view;

import java.util.Map;

import javafx.util.Pair;

/**
 * Interface de mise à jour nom d'espèce de la vue concernée.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ViewSpecieInterface {
	/**
	 * Met à jour la vue en accord avec le modèle.
	 * 
	 * @param specieName : le nom de l'espèce à inclure dans la vue
	 * @param occurrences : le nombre d'occurrences par géohash à inclure dans la vue
	 * @param maxMinOcc : les valeurs max et min d'occurrences à un géohash
	 * @param is3D : le rendu est-il 3D ou 2D
	 */
	public void updateSpecie(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc, boolean is3D);
}
