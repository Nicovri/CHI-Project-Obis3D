package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import event.GeoHashListener;
import event.SpecieNameChangedEvent;
import event.SpecieNameListener;
import model.animal.Individual;
import model.animal.Specie;
import utils.JSON;

public class Model implements ModelInterface {
	private String currentSpecieName;
	private Map<String, Long> occurrences;
	
	private String currentGeoHash;
	private List<Individual> individuals;
	
	private List<SpecieNameListener> specieNameListeners;
	private List<GeoHashListener> geoHashListeners;
	
	public Model() {
		occurrences = new HashMap<>();
		individuals = new ArrayList<>();
		
		specieNameListeners = new ArrayList<>();
		geoHashListeners = new ArrayList<>();
	}
	
	public void initModel(String specieName) {
		this.currentSpecieName = specieName;
		this.fireSpecieNameChanged(true);
	}
	
	@Override
	public String getSpecieName() {
		return this.currentSpecieName;
	}

	@Override
	public void setSpecieName(String specieName) {
		this.currentSpecieName = specieName;
		this.fireSpecieNameChanged(false);
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
	public List<String> getListSuggestions(String text) {
		List<String> suggestions = new ArrayList<>();
		suggestions.add("test1");
		suggestions.add("test2");
		//getFromrequest("https://api.obis.org/v3/taxon/complete/verbose/" + text)
		return suggestions;
	}
	
	@Override
	public boolean updateOccurrences(boolean isInit) {
		if(isInit) {
			this.occurrences = JSON.fetchResultSpecieOccurences(JSON.getFromFile("/res/" + currentSpecieName + ".json"));
			return true;
		} else {			
			this.occurrences = JSON.fetchResultSpecieOccurences(JSON.getFromRequest("https://api.obis.org/v3/occurrence/grid/3?scientificname=" + currentSpecieName));
			return true;
		}
		// If something went wrong return false?
	}
	
	@Override
	public boolean updateIndividuals() {
		// TODO
		return false;
	}
	
	@Override
	public void fireSpecieNameChanged(boolean isInit) {
		if(this.updateOccurrences(isInit)) {			
			for(SpecieNameListener s : specieNameListeners) {
				s.specieNameChanged(new SpecieNameChangedEvent(this, currentSpecieName, occurrences));
			}
		}/* else {
			this.setSpecie(this.currentSpecie.getScientificName());
		}*/
	}

	@Override
	public void fireGeoHashChanged() {
		if(this.updateIndividuals()) {
			for(GeoHashListener g : geoHashListeners) {
				// TODO
			}
		}
	}

}
