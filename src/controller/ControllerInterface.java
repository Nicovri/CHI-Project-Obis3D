package controller;

import java.util.List;
import java.util.Map;

import event.GeoHashListener;
import event.SpecieNameListener;

/**
 * Interface contenant les fonctions utilisées dans le controlleur principal.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ControllerInterface {
	
	/**
	 * Permet d'obtenir la valeur actuelle du nom de l'espèce dans le modèle.
	 * 
	 * @return la valeur actuelle du nom de l'espèce
	 */
	public String getSpecieName();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de début dans le modèle.
	 * 
	 * @return la valeur actuelle de la date de début
	 */
	public String getStartDate();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de fin dans le modèle.
	 * 
	 * @return la valeur actuelle de la date de fin.
	 */
	public String getEndDate();
	
	/**
	 * Permet de modifier la valeur du nom de l'espèce dans le modèle.
	 * 
	 * @param specieName : le nouveau nom de l'espèce
	 */
	public void setSpecieName(String specieName);
	
	/**
	 * Permet d'obtenir la valeur actuelle du géohash dans le modèle.
	 * 
	 * @return la valeur actuelle du géohash
	 */
	public String getGeoHash();
	
	/**
	 * Permet de modifier la valeur du géohash dans le modèle.
	 * 
	 * @param geoHash : le nouveau géohash
	 */
	public void setGeoHash(String geoHash);
	
	/**
	 * Ajoute un élément implémentant l'interface {@code SpecieNameListener} à la liste dans le modèle.
	 * 
	 * @param specieNameListener : nouveau listener à ajouter
	 */
	public void addSpecieNameListener(SpecieNameListener specieNameListener);
	
	/**
	 * Supprime un élément implémentant l'interface {@code SpecieNameListener} à la liste dans le modèle.
	 * 
	 * @param specieNameListener : listener à supprimer
	 */
	public void removeSpecieNameListener(SpecieNameListener specieNameListener);
	
	/**
	 * Ajoute un élément implémentant l'interface {@code GeoHashListener} à la liste dans le modèle.
	 * 
	 * @param geoHashListener : nouveau listener à ajouter
	 */
	public void addGeoHashListener(GeoHashListener geoHashListener);
	
	/**
	 * Supprime un élément implémentant l'interface {@code GeoHashListener} à la liste dans le modèle.
	 * 
	 * @param geoHashListener : listener à supprimer
	 */
	public void removeGeoHashListener(GeoHashListener geoHashListener);
	
	/**
	 * Permet d'obtenir les suggestions des 20 premiers noms d'espèce depuis le modèle.
	 * 
	 * @param text : le début de nom d'espèce entré
	 * 
	 * @return la liste des suggestions de 20 noms d'espèce.
	 */
	public List<String> getListSuggestions(String text);
	
	/**
	 * Permet d'obtenir la liste des occurrences par intervalle de 5 ans depuis le modèle.
	 * 
	 * @return la liste des Map géohash -> nombre d'occurrences par intervalle de 5 ans
	 */
	public List<Map<String, Long>> getOccurrencesPerInterval();
	
	/**
	 * Prévient le modèle que la valeur actuelle du nom d'espèce a changé.
	 * 
	 * @param specieName : le nom de l'espèce à changer
	 */
	public void notifySpecieNameChanged(String specieName);
	
	/**
	 * Prévient le modèle que la valeur actuelle du nom d'espèce a changé, et spécifie à l'avance les occurrences.
	 * 
	 * @param specieName : le nom de l'espèce à changer
	 * @param occurrences : la Map géohash -> nombre d'occurrences à utiliser
	 */
	public void notifySpecieNameChanged(String specieName, Map<String, Long> occurrences);
	
	/**
	 * Prévient le modèle que la valeur actuelle du nom d'espèce a changé.
	 * 
	 * @param specieName : le nom de l'espèce à changer.
	 * @param is3D : indique au modèle si le rendu sera 3D ou 2D (pour qu'il passe l'information aux autres listeners)
	 */
	public void notifySpecieNameChanged(String specieName, boolean is3D);
	
	/**
	 * Prévient le modèle que la valeur actuelle du nom d'espèce a changé, et spécifie les dates de début et de fin.
	 * 
	 * @param specieName : le nom de l'espèce à changer.
	 * @param startdate : la date de début de la recherche.
	 * @param enddate : la date de fin de la recherche.
	 */
	public void notifySpecieNameAndDateChanged(String specieName, String startdate, String enddate);
	
	/**
	 * Prévient le modèle que la valeur actuelle du nom d'espèce a changé, et spécifie les dates de début et de fin.
	 * 
	 * @param specieName : le nom de l'espèce à changer
	 * @param startdate : la date de début de la recherche
	 * @param enddate : la date de fin de la recherche
	 * @param is3D : indique au modèle si le rendu sera 3D ou 2D (pour qu'il passe l'information aux autres listeners)
	 */
	public void notifySpecieNameAndDateChanged(String specieName, String startdate, String enddate, boolean is3D);
	
	/**
	 * Prévient le modèle que la valeur actuelle du géohash a changé.
	 * 
	 * @param geoHash : la valeur du géohash à changer
	 */
	public void notifyGeoHashChanged(String geoHash);
}
