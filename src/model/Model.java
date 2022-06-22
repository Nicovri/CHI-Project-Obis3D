package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import event.GeoHashChangedEvent;
import event.GeoHashListener;
import event.SpecieNameChangedEvent;
import event.SpecieNameListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.animal.Report;
import model.animal.Specie;
import utils.Requests;

public class Model implements ModelInterface {
	private String currentSpecieName;
	private String startdate;
	private String enddate;
	private boolean is3D;
	private Map<String, Long> occurrences;
	
	private String currentGeoHash;
	private List<Report> reports;
	
	private List<SpecieNameListener> specieNameListeners;
	private List<GeoHashListener> geoHashListeners;
	
	public Model() {
		occurrences = new HashMap<>();
		reports = new ArrayList<>();
		
		specieNameListeners = new ArrayList<>();
		geoHashListeners = new ArrayList<>();
	}
	
	public void initModel(String specieName) {
		this.currentSpecieName = specieName;
		this.startdate = "1027-01-01";
		this.enddate = LocalDate.now().toString();
		this.is3D = false;
		this.fireSpecieNameChanged(true);
	}
	
	public void initModelWithDates(String specieName, String startdate, String enddate) {
		this.currentSpecieName = specieName;
		this.startdate = startdate;
		this.enddate = enddate;
		this.is3D = false;
		this.fireSpecieNameChanged(true);
	}
	
	@Override
	public String getSpecieName() {
		return this.currentSpecieName;
	}
	
	@Override
	public String getStartDate() {
		return this.startdate;
	}
	
	@Override
	public String getEndDate() {
		return this.enddate;
	}

	@Override
	public void setSpecieName(String specieName) {
		this.currentSpecieName = specieName;
		this.fireSpecieNameChanged(false);
	}
	
	@Override
	public void setStartDate(String startdate) {
		this.startdate = startdate;
	}
	
	@Override
	public void setEndDate(String enddate) {
		this.enddate = enddate;
	}
	
	@Override
	public void setIs3D(boolean is3D) {
		this.is3D = is3D;
	}

	@Override
	public String getGeoHash() {
		return this.currentGeoHash;
	}

	@Override
	public void setGeoHash(String geoHash) {
		this.currentGeoHash = geoHash;
		this.fireGeoHashChanged();
	}

	@Override
	public void addSpecieNameListener(SpecieNameListener specieNameListener) {
		this.specieNameListeners.add(specieNameListener);
	}

	@Override
	public void removeSpecieNameListener(SpecieNameListener specieNameListener) {
		this.specieNameListeners.remove(specieNameListener);
	}

	@Override
	public void addGeoHashListener(GeoHashListener geoHashListener) {
		this.geoHashListeners.add(geoHashListener);
	}

	@Override
	public void removeGeoHashListener(GeoHashListener geoHashListener) {
		this.geoHashListeners.remove(geoHashListener);
	}
	
	@Override
	public ObservableList<String> getListSuggestions(String startName) {
		ObservableList<String> suggestions = FXCollections.observableArrayList();
		suggestions.addAll(Requests.fetchAutoIndent(Requests.getFromRequest(Requests.buildRequestAutoIndent(startName))));
		return suggestions;
	}
	
	@Override
	public boolean updateOccurrences(boolean isInit) {
		if(isInit) {
			this.occurrences = Requests.fetchResultSpecieOccurences(Requests.getFromFile("/res/" + currentSpecieName + ".json"));
			return true;
		} else {
			this.occurrences = Requests.fetchResultSpecieOccurences(Requests.getFromRequest(Requests.buildSpecieRequestWithGrid(currentSpecieName, startdate, enddate, 3)));
			return true;
		}
		// If something went wrong return false?
	}
	
	@Override
	public boolean updateReports() {
		this.reports = Requests.fetchResultGeoHash(Requests.getFromRequest(Requests.buildGeoHashRequest(currentGeoHash, "")));
		return true;
	}
	
	@Override
	public void fireSpecieNameChanged(boolean isInit) {
		if(this.updateOccurrences(isInit)) {			
			for(SpecieNameListener s : specieNameListeners) {
				s.specieNameChanged(new SpecieNameChangedEvent(this, currentSpecieName, occurrences, is3D));
			}
		}/* else {
			this.setSpecie(this.currentSpecie.getScientificName());
		}*/
	}

	@Override
	public void fireGeoHashChanged() {
		if(this.updateReports()) {
			for(GeoHashListener g : geoHashListeners) {
				g.geoHashChanged(new GeoHashChangedEvent(this, currentGeoHash, reports));
			}
		}
	}

}
