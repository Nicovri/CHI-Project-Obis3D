package model;

import java.util.List;
import java.util.Map;

import event.GeoHashListener;
import event.SpecieNameListener;
import javafx.collections.ObservableList;

/**
 * Interface contenant les fonctions utilisées dans le modèle principal.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ModelInterface {
	
	/**
	 * Permet d'obtenir la valeur actuelle du nom de l'espèce.
	 * 
	 * @return la valeur actuelle du nom de l'espèce
	 */
	public String getSpecieName();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de début.
	 * 
	 * @return la valeur actuelle de la date de début
	 */
	public String getStartDate();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de fin.
	 * 
	 * @return la valeur actuelle de la date de fin.
	 */
	public String getEndDate();
	
	/**
	 * Permet de modifier la valeur du nom de l'espèce.
	 * 
	 * @param specieName : le nouveau nom de l'espèce
	 */
	public void setSpecieName(String specieName);
	
	/**
	 * Permet de modifier la valeur du nom de l'espèce et de spécifier à l'avance les occurrences.
	 * 
	 * @param specieName : la nouveau nom de l'espèce
	 * @param occurrences : la Map géohash -> nombre d'occurrences à utiliser
	 */
	public void setSpecieName(String specieName, Map<String, Long> occurrences);
	
	/**
	 * Permet de modifier la valeur de la date de début.
	 * 
	 * @param startDate : la nouvelle date de début
	 */
	public void setStartDate(String startDate);
	
	/**
	 * Permet de modifier la valeur de la date de fin.
	 * 
	 * @param enddate : la nouvelle date de fin
	 */
	public void setEndDate(String enddate);
	
	/**
	 * Permet de modifier la valeur du paramètre de rendu 3D ou 2D.
	 * 
	 * @param is3D : indique si le rendu sera 3D ou 2D (pour passer l'information aux autres listeners)
	 */
	public void setIs3D(boolean is3D);
	
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
	public ObservableList<String> getListSuggestions(String startName);
	
	/**
	 * Permet d'obtenir la liste des occurrences par intervalle de 5 ans depuis le modèle.
	 * 
	 * @return la liste des Map géohash -> nombre d'occurrences par intervalle de 5 ans
	 */
	public List<Map<String, Long>> getOccurrencesPerInterval();
	
	/**
	 * Mise à jour de la valeur des occurrences par géohash pour le nom d'espèce actuellement sauvegardé dans le modèle.
	 * 
	 * @param isInit : initialisation du modèle ou pas
	 * @return la Map géohash -> nombre d'occurrences à passer aux listeners
	 */
	public boolean updateOccurrences(boolean isInit);
	
	/**
	 * Mise à jour de la liste des signalements pour le géohash actuellement sauvegardé dans le modèle.
	 * 
	 * @return la liste des signalements pour un géohash donné.
	 */
	public boolean updateReports();
	
	/**
	 * Signale que la valeur actuelle du nom d'espèce a changé.
	 * 
	 * @param isInit : initialisation du modèle ou pas
	 */
	public void fireSpecieNameChanged(boolean isInit);
	
	/**
	 * Signale que la valeur actuelle du nom d'espèce a changé.
	 * 
	 * @param occurrences : les occurrences à propager avec l'événement
	 */
	public void fireSpecieNameChanged(Map<String, Long> occurrences);
	
	/**
	 * Signale que la valeur actuelle du géohash a changé.
	 */
	public void fireGeoHashChanged();
}
