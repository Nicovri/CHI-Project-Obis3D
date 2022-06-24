package model;

import java.util.List;
import java.util.Map;

import event.GeoHashListener;
import event.SpecieNameListener;
import javafx.collections.ObservableList;

/**
 * Interface contenant les fonctions utilisees dans le modele principal.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ModelInterface {
	
	/**
	 * Permet d'obtenir la valeur actuelle du nom de l'espece.
	 * 
	 * @return la valeur actuelle du nom de l'espece
	 */
	public String getSpecieName();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de debut.
	 * 
	 * @return la valeur actuelle de la date de debut
	 */
	public String getStartDate();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de fin.
	 * 
	 * @return la valeur actuelle de la date de fin.
	 */
	public String getEndDate();
	
	/**
	 * Permet de modifier la valeur du nom de l'espece.
	 * 
	 * @param specieName : le nouveau nom de l'espece
	 */
	public void setSpecieName(String specieName);
	
	/**
	 * Permet de modifier la valeur du nom de l'espece et de specifier a l'avance les occurrences.
	 * 
	 * @param specieName : la nouveau nom de l'espece
	 * @param occurrences : la Map geohash -> nombre d'occurrences a utiliser
	 */
	public void setSpecieName(String specieName, Map<String, Long> occurrences);
	
	/**
	 * Permet de modifier la valeur de la date de debut.
	 * 
	 * @param startDate : la nouvelle date de debut
	 */
	public void setStartDate(String startDate);
	
	/**
	 * Permet de modifier la valeur de la date de fin.
	 * 
	 * @param enddate : la nouvelle date de fin
	 */
	public void setEndDate(String enddate);
	
	/**
	 * Permet de modifier la valeur du parametre de rendu 3D ou 2D.
	 * 
	 * @param is3D : indique si le rendu sera 3D ou 2D (pour passer l'information aux autres listeners)
	 */
	public void setIs3D(boolean is3D);
	
	/**
	 * Permet d'obtenir la valeur actuelle du geohash.
	 * 
	 * @return la valeur actuelle du geohash
	 */
	public String getGeoHash();
	
	/**
	 * Permet de modifier la valeur du geohash.
	 * 
	 * @param geoHash : le nouveau geohash
	 */
	public void setGeoHash(String geoHash);
	
	/**
	 * Ajoute un element implementant l'interface {@code SpecieNameListener} a la liste du modele.
	 * 
	 * @param specieNameListener : nouveau listener a ajouter
	 */
	public void addSpecieNameListener(SpecieNameListener specieNameListener);
	
	/**
	 * Supprime un element implementant l'interface {@code SpecieNameListener} a la liste du modele.
	 * 
	 * @param specieNameListener : listener a supprimer
	 */
	public void removeSpecieNameListener(SpecieNameListener specieNameListener);
	
	/**
	 * Ajoute un element implementant l'interface {@code GeoHashListener} a la liste du modele.
	 * 
	 * @param geoHashListener : nouveau listener a ajouter
	 */
	public void addGeoHashListener(GeoHashListener geoHashListener);
	
	/**
	 * Supprime un element implementant l'interface {@code GeoHashListener} a la liste du modele.
	 * 
	 * @param geoHashListener : listener a supprimer
	 */
	public void removeGeoHashListener(GeoHashListener geoHashListener);
	
	/**
	 * Permet d'obtenir les suggestions des 20 premiers noms d'espece.
	 * 
	 * @param text : le debut de nom d'espece entre
	 * 
	 * @return la liste des suggestions de 20 noms d'espece.
	 */
	public ObservableList<String> getListSuggestions(String startName);
	
	/**
	 * Permet d'obtenir la liste des occurrences par intervalle de 5 ans.
	 * 
	 * @return la liste des Map geohash -> nombre d'occurrences par intervalle de 5 ans
	 */
	public List<Map<String, Long>> getOccurrencesPerInterval();
	
	/**
	 * Mise a jour de la valeur des occurrences par geohash pour le nom d'espece actuellement sauvegarde dans le modele.
	 * 
	 * @param isInit : initialisation du modele ou pas
	 * @return la Map geohash -> nombre d'occurrences a passer aux listeners
	 */
	public boolean updateOccurrences(boolean isInit);
	
	/**
	 * Mise a jour de la liste des signalements pour le geohash actuellement sauvegarde dans le modele.
	 * 
	 * @return la liste des signalements pour un geohash donne.
	 */
	public boolean updateReports();
	
	/**
	 * Signale que la valeur actuelle du nom d'espece a change.
	 * 
	 * @param isInit : initialisation du modele ou pas
	 */
	public void fireSpecieNameChanged(boolean isInit);
	
	/**
	 * Signale que la valeur actuelle du nom d'espece a change.
	 * 
	 * @param occurrences : les occurrences a propager avec l'evenement
	 */
	public void fireSpecieNameChanged(Map<String, Long> occurrences);
	
	/**
	 * Signale que la valeur actuelle du geohash a change.
	 */
	public void fireGeoHashChanged();
}
