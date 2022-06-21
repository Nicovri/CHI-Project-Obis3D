package view;

import java.util.List;

import javafx.collections.ObservableList;
import model.animal.Individual;
import model.animal.Specie;

public interface ViewGeoHashInterface {
	public void updateGeoHash(String geoHash, ObservableList<Individual> individuals, ObservableList<Specie> species);
}
