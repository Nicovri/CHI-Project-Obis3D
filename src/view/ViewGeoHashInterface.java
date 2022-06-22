package view;

import java.util.List;

import javafx.collections.ObservableList;
import model.animal.Report;
import model.animal.Specie;

public interface ViewGeoHashInterface {
	public void updateGeoHash(String geoHash, ObservableList<Report> reports, ObservableList<Specie> species);
}
