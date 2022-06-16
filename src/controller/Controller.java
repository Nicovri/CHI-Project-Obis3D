package controller;

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
	public void notifySpecieNameChanged(String specieName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyGeoHashChanged(String geoHash) {
		// TODO Auto-generated method stub
		
	}

}
