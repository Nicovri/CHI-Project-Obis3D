package controller;

import java.util.List;
import java.util.Map;

import event.GeoHashListener;
import event.SpecieNameListener;
import model.Model;

/**
 * Controlleur principal de l'application.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 * 
 * @see controller.ControllerInterface
 *
 */
public class Controller implements ControllerInterface {
	private Model model;
	
	public Controller(Model model) {
		this.model = model;
	}
	
	@Override
	public String getSpecieName() {
		return this.model.getSpecieName();
	}
	
	@Override
	public String getStartDate() {
		return this.model.getStartDate();
	}
	
	@Override
	public String getEndDate() {
		return this.model.getEndDate();
	}

	@Override
	public void setSpecieName(String specieName) {
		this.model.setSpecieName(specieName);
	}
	
	public void setIs3D(boolean is3D) {
		this.model.setIs3D(is3D);
	}

	@Override
	public String getGeoHash() {
		return this.model.getGeoHash();
	}

	@Override
	public void setGeoHash(String geoHash) {
		this.model.setGeoHash(geoHash);
	}

	@Override
	public void addSpecieNameListener(SpecieNameListener specieNameListener) {
		this.model.addSpecieNameListener(specieNameListener);
	}

	@Override
	public void removeSpecieNameListener(SpecieNameListener specieNameListener) {
		this.model.removeSpecieNameListener(specieNameListener);
	}

	@Override
	public void addGeoHashListener(GeoHashListener geoHashListener) {
		this.model.addGeoHashListener(geoHashListener);
	}

	@Override
	public void removeGeoHashListener(GeoHashListener geoHashListener) {
		this.model.removeGeoHashListener(geoHashListener);
	}
	
	@Override
	public List<String> getListSuggestions(String text) {
		return this.model.getListSuggestions(text);
	}
	
	@Override
	public List<Map<String, Long>> getOccurrencesPerInterval() {
		return this.model.getOccurrencesPerInterval();
	}

	@Override
	public void notifySpecieNameChanged(String specieName) {
		this.model.setSpecieName(specieName);
	}
	
	@Override
	public void notifySpecieNameChanged(String specieName, Map<String, Long> occurrences) {
		this.model.setSpecieName(specieName, occurrences);
	}
	
	@Override
	public void notifySpecieNameChanged(boolean is3D) {
		this.model.setIs3D(is3D);
	}
	
	@Override
	public void notifySpecieNameAndDateChanged(String specieName, String startdate, String enddate) {
		this.model.setStartDate(startdate);
		this.model.setEndDate(enddate);
		this.model.setSpecieName(specieName);
	}

	@Override
	public void notifyGeoHashChanged(String geoHash) {
		this.model.setGeoHash(geoHash);
	}

}
