package controller;

import event.GeoHashListener;
import event.SpecieNameListener;
import model.animal.Specie;

public interface ControllerInterface {
	
	public Specie getSpecie();
	
	public void setSpecie(String specieName);
	
	public String getGeoHash();
	
	public void setGeoHash(String geoHash);
	
	public void addSpecieNameListener(SpecieNameListener specieNameListener);
	
	public void removeSpecieNameListener(SpecieNameListener specieNameListener);
	
	public void addGeoHashListener(GeoHashListener geoHashListener);
	
	public void removeGeoHashListener(GeoHashListener geoHashListener);
	
	public void notifySpecieNameChanged(String specieName);
	
	public void notifyGeoHashChanged(String geoHash);
}
