package controller;

import java.util.List;

import event.GeoHashListener;
import event.SpecieNameListener;
import model.Model;
import model.animal.Specie;

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
	public void setSpecieName(String specieName) {
		this.model.setSpecieName(specieName);
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
	public void notifySpecieNameChanged(String specieName) {
		this.model.setSpecieName(specieName);
	}

	@Override
	public void notifyGeoHashChanged(String geoHash) {
		this.model.setGeoHash(geoHash);
	}

}
