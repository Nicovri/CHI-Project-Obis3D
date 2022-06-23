package view;

import java.util.Map;

import javafx.util.Pair;

/**
 * Interface de mise � jour nom d'esp�ce de la vue concern�e.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ViewSpecieInterface {
	/**
	 * Met � jour la vue en accord avec le mod�le.
	 * 
	 * @param specieName : le nom de l'esp�ce � inclure dans la vue
	 * @param occurrences : le nombre d'occurrences par g�ohash � inclure dans la vue
	 * @param maxMinOcc : les valeurs max et min d'occurrences � un g�ohash
	 * @param is3D : le rendu est-il 3D ou 2D
	 */
	public void updateSpecie(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc, boolean is3D);
}
