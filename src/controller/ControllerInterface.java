package controller;

import java.util.List;
import java.util.Map;

import event.GeoHashListener;
import event.SpecieNameListener;

/**
 * Interface contenant les fonctions utilisees dans le controlleur principal.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ControllerInterface {
	
	/**
	 * Permet d'obtenir la valeur actuelle du nom de l'espece dans le modele.
	 * 
	 * @return la valeur actuelle du nom de l'espece
	 */
	public String getSpecieName();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de debut dans le modele.
	 * 
	 * @return la valeur actuelle de la date de debut
	 */
	public String getStartDate();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de fin dans le modele.
	 * 
	 * @return la valeur actuelle de la date de fin.
	 */
	public String getEndDate();
	
	/**
	 * Permet de modifier la valeur du nom de l'espèce dans le modele.
	 * 
	 * @param specieName : le nouveau nom de l'espece
	 */
	public void setSpecieName(String specieName);
	
	/**
	 * Permet d'obtenir la valeur actuelle du geohash dans le modele.
	 * 
	 * @return la valeur actuelle du geohash
	 */
	public String getGeoHash();
	
	/**
	 * Permet de modifier la valeur du geohash dans le modele.
	 * 
	 * @param geoHash : le nouveau geohash
	 */
	public void setGeoHash(String geoHash);
	
	/**
	 * Ajoute un element implementant l'interface {@code SpecieNameListener} a la liste dans le modele.
	 * 
	 * @param specieNameListener : nouveau listener a ajouter
	 */
	public void addSpecieNameListener(SpecieNameListener specieNameListener);
	
	/**
	 * Supprime un element implementant l'interface {@code SpecieNameListener} a la liste dans le modele.
	 * 
	 * @param specieNameListener : listener a supprimer
	 */
	public void removeSpecieNameListener(SpecieNameListener specieNameListener);
	
	/**
	 * Ajoute un element implementant l'interface {@code GeoHashListener} a la liste dans le modele.
	 * 
	 * @param geoHashListener : nouveau listener a ajouter
	 */
	public void addGeoHashListener(GeoHashListener geoHashListener);
	
	/**
	 * Supprime un element implementant l'interface {@code GeoHashListener} a la liste dans le modele.
	 * 
	 * @param geoHashListener : listener a supprimer
	 */
	public void removeGeoHashListener(GeoHashListener geoHashListener);
	
	/**
	 * Permet d'obtenir les suggestions des 20 premiers noms d'espece depuis le modele.
	 * 
	 * @param text : le debut de nom d'espece entre
	 * 
	 * @return la liste des suggestions de 20 noms d'espece.
	 */
	public List<String> getListSuggestions(String text);
	
	/**
	 * Permet d'obtenir la liste des occurrences par intervalle de 5 ans depuis le modele.
	 * 
	 * @return la liste des Map geohash -> nombre d'occurrences par intervalle de 5 ans
	 */
	public List<Map<String, Long>> getOccurrencesPerInterval();
	
	/**
	 * Previent le modele que la valeur actuelle du nom d'espece a change.
	 * 
	 * @param specieName : le nom de l'espece a changer
	 */
	public void notifySpecieNameChanged(String specieName);
	
	/**
	 * Previent le modele que la valeur actuelle du nom d'espece a change, et specifie a l'avance les occurrences.
	 * 
	 * @param specieName : le nom de l'espece a changer
	 * @param occurrences : la Map geohash -> nombre d'occurrences a utiliser
	 */
	public void notifySpecieNameChanged(String specieName, Map<String, Long> occurrences);
	
	/**
	 * Previent le modele que la valeur actuelle du rendu 3D ou 2D a change.
	 * 
	 * @param is3D : indique au modele si le rendu sera 3D ou 2D (pour qu'il passe l'information aux autres listeners)
	 */
	public void notifySpecieNameChanged(boolean is3D);
	
	/**
	 * Previent le modele que la valeur actuelle du nom d'espece a change, et specifie les dates de debut et de fin.
	 * 
	 * @param specieName : le nom de l'espece a changer.
	 * @param startdate : la date de debut de la recherche.
	 * @param enddate : la date de fin de la recherche.
	 */
	public void notifySpecieNameAndDateChanged(String specieName, String startdate, String enddate);
	
	/**
	 * Previent le modele que la valeur actuelle du geohash a change.
	 * 
	 * @param geoHash : la valeur du geohash a changer
	 */
	public void notifyGeoHashChanged(String geoHash);
}
