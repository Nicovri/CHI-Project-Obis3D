package model;

import java.util.List;
import java.util.Map;

import event.GeoHashListener;
import event.SpecieNameListener;
import javafx.collections.ObservableList;

/**
 * Interface contenant les fonctions utilis�es dans le mod�le principal.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ModelInterface {
	
	/**
	 * Permet d'obtenir la valeur actuelle du nom de l'esp�ce.
	 * 
	 * @return la valeur actuelle du nom de l'esp�ce
	 */
	public String getSpecieName();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de d�but.
	 * 
	 * @return la valeur actuelle de la date de d�but
	 */
	public String getStartDate();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de fin.
	 * 
	 * @return la valeur actuelle de la date de fin.
	 */
	public String getEndDate();
	
	/**
	 * Permet de modifier la valeur du nom de l'esp�ce.
	 * 
	 * @param specieName : le nouveau nom de l'esp�ce
	 */
	public void setSpecieName(String specieName);
	
	/**
	 * Permet de modifier la valeur du nom de l'esp�ce et de sp�cifier � l'avance les occurrences.
	 * 
	 * @param specieName : la nouveau nom de l'esp�ce
	 * @param occurrences : la Map g�ohash -> nombre d'occurrences � utiliser
	 */
	public void setSpecieName(String specieName, Map<String, Long> occurrences);
	
	/**
	 * Permet de modifier la valeur de la date de d�but.
	 * 
	 * @param startDate : la nouvelle date de d�but
	 */
	public void setStartDate(String startDate);
	
	/**
	 * Permet de modifier la valeur de la date de fin.
	 * 
	 * @param enddate : la nouvelle date de fin
	 */
	public void setEndDate(String enddate);
	
	/**
	 * Permet de modifier la valeur du param�tre de rendu 3D ou 2D.
	 * 
	 * @param is3D : indique si le rendu sera 3D ou 2D (pour passer l'information aux autres listeners)
	 */
	public void setIs3D(boolean is3D);
	
	/**
	 * Permet d'obtenir la valeur actuelle du g�ohash dans le mod�le.
	 * 
	 * @return la valeur actuelle du g�ohash
	 */
	public String getGeoHash();
	
	/**
	 * Permet de modifier la valeur du g�ohash dans le mod�le.
	 * 
	 * @param geoHash : le nouveau g�ohash
	 */
	public void setGeoHash(String geoHash);
	
	/**
	 * Ajoute un �l�ment impl�mentant l'interface {@code SpecieNameListener} � la liste dans le mod�le.
	 * 
	 * @param specieNameListener : nouveau listener � ajouter
	 */
	public void addSpecieNameListener(SpecieNameListener specieNameListener);
	
	/**
	 * Supprime un �l�ment impl�mentant l'interface {@code SpecieNameListener} � la liste dans le mod�le.
	 * 
	 * @param specieNameListener : listener � supprimer
	 */
	public void removeSpecieNameListener(SpecieNameListener specieNameListener);
	
	/**
	 * Ajoute un �l�ment impl�mentant l'interface {@code GeoHashListener} � la liste dans le mod�le.
	 * 
	 * @param geoHashListener : nouveau listener � ajouter
	 */
	public void addGeoHashListener(GeoHashListener geoHashListener);
	
	/**
	 * Supprime un �l�ment impl�mentant l'interface {@code GeoHashListener} � la liste dans le mod�le.
	 * 
	 * @param geoHashListener : listener � supprimer
	 */
	public void removeGeoHashListener(GeoHashListener geoHashListener);
	
	/**
	 * Permet d'obtenir les suggestions des 20 premiers noms d'esp�ce depuis le mod�le.
	 * 
	 * @param text : le d�but de nom d'esp�ce entr�
	 * 
	 * @return la liste des suggestions de 20 noms d'esp�ce.
	 */
	public ObservableList<String> getListSuggestions(String startName);
	
	/**
	 * Permet d'obtenir la liste des occurrences par intervalle de 5 ans depuis le mod�le.
	 * 
	 * @return la liste des Map g�ohash -> nombre d'occurrences par intervalle de 5 ans
	 */
	public List<Map<String, Long>> getOccurrencesPerInterval();
	
	/**
	 * Mise � jour de la valeur des occurrences par g�ohash pour le nom d'esp�ce actuellement sauvegard� dans le mod�le.
	 * 
	 * @param isInit : initialisation du mod�le ou pas
	 * @return la Map g�ohash -> nombre d'occurrences � passer aux listeners
	 */
	public boolean updateOccurrences(boolean isInit);
	
	/**
	 * Mise � jour de la liste des signalements pour le g�ohash actuellement sauvegard� dans le mod�le.
	 * 
	 * @return la liste des signalements pour un g�ohash donn�.
	 */
	public boolean updateReports();
	
	/**
	 * Signale que la valeur actuelle du nom d'esp�ce a chang�.
	 * 
	 * @param isInit : initialisation du mod�le ou pas
	 */
	public void fireSpecieNameChanged(boolean isInit);
	
	/**
	 * Signale que la valeur actuelle du nom d'esp�ce a chang�.
	 * 
	 * @param occurrences : les occurrences � propager avec l'�v�nement
	 */
	public void fireSpecieNameChanged(Map<String, Long> occurrences);
	
	/**
	 * Signale que la valeur actuelle du g�ohash a chang�.
	 */
	public void fireGeoHashChanged();
}
