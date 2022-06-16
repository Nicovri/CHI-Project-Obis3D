package event;

import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventType;
import model.animal.Individual;
import model.animal.Specie;

public class GeoHashChangedEvent extends Event {
	public static String TYPE = "VALUE_CHANGED";
	private String geoHash;
	private List<Individual> individuals = new ArrayList<>();
	public final static EventType<GeoHashChangedEvent> VALUE_CHANGED = new EventType<>(TYPE);
	
	public GeoHashChangedEvent(Object source, String geoHash, List<Individual> individuals) {
		super(VALUE_CHANGED);
		this.geoHash = geoHash;
		this.individuals.addAll(individuals);
	}
	
	public String getGeoHash() { return this.geoHash; }
	
	public List<Individual> getListIndividuals() { return this.individuals; }
	
	public List<Specie> getListSpecie() {
		// TODO
		return null;
	}
}
