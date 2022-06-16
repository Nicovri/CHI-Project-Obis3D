package model;

import java.util.ArrayList;
import java.util.List;

import event.GeoHashListener;
import event.SpecieNameListener;
import model.animal.Specie;

public class Model implements ModelInterface {
	private List<SpecieNameListener> specieNameListeners;
	private List<GeoHashListener> geoHashListeners;
	
	public Model() {
		specieNameListeners = new ArrayList<>();
		geoHashListeners = new ArrayList<>();
	}
	
	@Override
	public Specie getSpecie() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSpecie(String specieName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getGeoHash() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGeoHash(String geoHash) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSpecieNameListener(SpecieNameListener specieNameListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSpecieNameListener(SpecieNameListener specieNameListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGeoHashListener(GeoHashListener geoHashListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeGeoHashListener(GeoHashListener geoHashListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireSpecieNameChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireGeoHashChanged() {
		// TODO Auto-generated method stub
		
	}

}
