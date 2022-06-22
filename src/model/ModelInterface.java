package model;

import java.util.List;

import event.GeoHashListener;
import event.SpecieNameListener;
import javafx.collections.ObservableList;
import model.animal.Specie;

public interface ModelInterface {
	
	public String getSpecieName();
	
	public String getStartDate();
	
	public String getEndDate();
	
	public void setSpecieName(String specieName);
	
	public void setStartDate(String startDate);
	
	public void setEndDate(String enddate);
	
	public void setIs3D(boolean is3D);
	
	public String getGeoHash();
	
	public void setGeoHash(String geoHash);
	
	public void addSpecieNameListener(SpecieNameListener specieNameListener);
	
	public void removeSpecieNameListener(SpecieNameListener specieNameListener);
	
	public void addGeoHashListener(GeoHashListener geoHashListener);
	
	public void removeGeoHashListener(GeoHashListener geoHashListener);
	
	public ObservableList<String> getListSuggestions(String startName);
	
	public boolean updateOccurrences(boolean isInit);
	
	public boolean updateReports();
	
	public void fireSpecieNameChanged(boolean isInit);
	
	public void fireGeoHashChanged();
}
