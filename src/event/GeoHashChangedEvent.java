package event;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import model.animal.Report;
import model.animal.Specie;

/**
 * Ev�nement propag� lorsque la valeur du g�ohash est modifi�e.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
@SuppressWarnings("serial")
public class GeoHashChangedEvent extends Event {
	public static String TYPE = "VALUE_GEOHASH_CHANGED";
	private String geoHash;
	private ObservableList<Report> reports = FXCollections.observableArrayList();
	public final static EventType<GeoHashChangedEvent> VALUE_GEOHASH_CHANGED = new EventType<>(TYPE);
	
	public GeoHashChangedEvent(Object source, String geoHash, List<Report> reports) {
		super(VALUE_GEOHASH_CHANGED);
		this.geoHash = geoHash;
		if(reports != null) {			
			this.reports.addAll(reports);
		}
	}
	
	public String getGeoHash() { return this.geoHash; }
	
	public ObservableList<Report> getListReports() { return this.reports; }
	
	public ObservableList<Specie> getListSpecie() {
		ObservableList<Specie> species = FXCollections.observableArrayList();
		for(Report r : reports)
		{
			if(!species.contains(r.getSpecie())) species.add(r.getSpecie());
		}
		return species;
	}
}
