package controller;

import java.util.List;
import java.util.Map;

import event.GeoHashListener;
import event.SpecieNameListener;

/**
 * Interface contenant les fonctions utilis�es dans le controlleur principal.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public interface ControllerInterface {
	
	/**
	 * Permet d'obtenir la valeur actuelle du nom de l'esp�ce dans le mod�le.
	 * 
	 * @return la valeur actuelle du nom de l'esp�ce
	 */
	public String getSpecieName();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de d�but dans le mod�le.
	 * 
	 * @return la valeur actuelle de la date de d�but
	 */
	public String getStartDate();
	
	/**
	 * Permet d'obtenir la valeur actuelle de la date de fin dans le mod�le.
	 * 
	 * @return la valeur actuelle de la date de fin.
	 */
	public String getEndDate();
	
	/**
	 * Permet de modifier la valeur du nom de l'esp�ce dans le mod�le.
	 * 
	 * @param specieName : le nouveau nom de l'esp�ce
	 */
	public void setSpecieName(String specieName);
	
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
	public List<String> getListSuggestions(String text);
	
	/**
	 * Permet d'obtenir la liste des occurrences par intervalle de 5 ans depuis le mod�le.
	 * 
	 * @return la liste des Map g�ohash -> nombre d'occurrences par intervalle de 5 ans
	 */
	public List<Map<String, Long>> getOccurrencesPerInterval();
	
	/**
	 * Pr�vient le mod�le que la valeur actuelle du nom d'esp�ce a chang�.
	 * 
	 * @param specieName : le nom de l'esp�ce � changer
	 */
	public void notifySpecieNameChanged(String specieName);
	
	/**
	 * Pr�vient le mod�le que la valeur actuelle du nom d'esp�ce a chang�, et sp�cifie � l'avance les occurrences.
	 * 
	 * @param specieName : le nom de l'esp�ce � changer
	 * @param occurrences : la Map g�ohash -> nombre d'occurrences � utiliser
	 */
	public void notifySpecieNameChanged(String specieName, Map<String, Long> occurrences);
	
	/**
	 * Pr�vient le mod�le que la valeur actuelle du nom d'esp�ce a chang�.
	 * 
	 * @param specieName : le nom de l'esp�ce � changer.
	 * @param is3D : indique au mod�le si le rendu sera 3D ou 2D (pour qu'il passe l'information aux autres listeners)
	 */
	public void notifySpecieNameChanged(String specieName, boolean is3D);
	
	/**
	 * Pr�vient le mod�le que la valeur actuelle du nom d'esp�ce a chang�, et sp�cifie les dates de d�but et de fin.
	 * 
	 * @param specieName : le nom de l'esp�ce � changer.
	 * @param startdate : la date de d�but de la recherche.
	 * @param enddate : la date de fin de la recherche.
	 */
	public void notifySpecieNameAndDateChanged(String specieName, String startdate, String enddate);
	
	/**
	 * Pr�vient le mod�le que la valeur actuelle du nom d'esp�ce a chang�, et sp�cifie les dates de d�but et de fin.
	 * 
	 * @param specieName : le nom de l'esp�ce � changer
	 * @param startdate : la date de d�but de la recherche
	 * @param enddate : la date de fin de la recherche
	 * @param is3D : indique au mod�le si le rendu sera 3D ou 2D (pour qu'il passe l'information aux autres listeners)
	 */
	public void notifySpecieNameAndDateChanged(String specieName, String startdate, String enddate, boolean is3D);
	
	/**
	 * Pr�vient le mod�le que la valeur actuelle du g�ohash a chang�.
	 * 
	 * @param geoHash : la valeur du g�ohash � changer
	 */
	public void notifyGeoHashChanged(String geoHash);
}
