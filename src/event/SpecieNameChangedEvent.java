package event;

import java.util.HashMap;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.util.Pair;

/**
 * Evénement propagé lorsque la valeur du nom d'espèce est modifiée.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
@SuppressWarnings("serial")
public class SpecieNameChangedEvent extends Event {
	public static String TYPE = "VALUE_SPECIE_CHANGED";
	private String specieName;
	private boolean is3D;
	private Map<String, Long> data = new HashMap<>();
	public final static EventType<SpecieNameChangedEvent> VALUE_SPECIE_CHANGED = new EventType<>(TYPE);
	
	public SpecieNameChangedEvent(Object source, String specieName, Map<String, Long> data, boolean is3D) {
		super(VALUE_SPECIE_CHANGED);
		this.specieName = specieName;
		this.data.putAll(data);
		this.is3D = is3D;
	}
	
	public String getSpecieName() { return this.specieName; }
	
	public boolean getIs3D() { return this.is3D; }
	
	public Map<String, Long> getGeoHashAndNumberOfOccurrences() { return this.data; }
	
	public Pair<Long, Long> getMaxMinOccurrences() {
		long max = 0;
		long min = 0;
		if(data.isEmpty()) {
			return new Pair<Long, Long>(max, min);
		}
		// Iterables.get(collection, 0)
		min = ((Long)data.values().toArray()[0]).longValue();
		for(Long l : data.values()) {
			if(l.longValue() > max) {
				max = l.longValue();
			}
			if(l.longValue() < min) {
				min = l.longValue();
			}
		}
		return new Pair<Long, Long>(Long.valueOf(max), Long.valueOf(min));
	}
}
