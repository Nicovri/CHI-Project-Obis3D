package controller;

import java.util.List;

import event.GeoHashListener;
import event.SpecieNameListener;
import model.animal.Specie;

public interface ControllerInterface {
	
	public String getSpecieName();
	
	public void setSpecieName(String specieName);
	
	public String getGeoHash();
	
	public void setGeoHash(String geoHash);
	
	public void addSpecieNameListener(SpecieNameListener specieNameListener);
	
	public void removeSpecieNameListener(SpecieNameListener specieNameListener);
	
	public void addGeoHashListener(GeoHashListener geoHashListener);
	
	public void removeGeoHashListener(GeoHashListener geoHashListener);
	
	public List<String> getListSuggestions(String text);
	
	public void notifySpecieNameChanged(String specieName);
	
	public void notifySpecieNameAndDateChanged(String specieName, String startdate, String enddate);
	
	public void notifyGeoHashChanged(String geoHash);
}
