package event;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import model.animal.Individual;
import model.animal.Specie;

public class GeoHashChangedEvent extends Event {
	public static String TYPE = "VALUE_CHANGED";
	private String geoHash;
	private ObservableList<Individual> individuals = FXCollections.observableArrayList();
	public final static EventType<GeoHashChangedEvent> VALUE_CHANGED = new EventType<>(TYPE);
	
	public GeoHashChangedEvent(Object source, String geoHash, List<Individual> individuals) {
		super(VALUE_CHANGED);
		this.geoHash = geoHash;
		this.individuals.addAll(individuals);
	}
	
	public String getGeoHash() { return this.geoHash; }
	
	public ObservableList<Individual> getListIndividuals() { return this.individuals; }
	
	public ObservableList<Specie> getListSpecie() {
		// TODO
		return null;
	}
}
