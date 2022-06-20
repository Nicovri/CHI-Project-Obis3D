package event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.util.Pair;

public class SpecieNameChangedEvent extends Event {
	public static String TYPE = "VALUE_CHANGED";
	private String specieName;
	private Map<String, Long> data = new HashMap<>();
	public final static EventType<SpecieNameChangedEvent> VALUE_CHANGED = new EventType<>(TYPE);
	
	public SpecieNameChangedEvent(Object source, String specieName, Map<String, Long> data) {
		super(VALUE_CHANGED);
		this.specieName = specieName;
		this.data.putAll(data);
	}
	
	public String getSpecieName() { return this.specieName; }
	
	public Map<String, Long> getGeoHashAndNumberOfOccurrences() { return this.data; }
	
	public Pair<Long, Long> getMaxOccurrences() {
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
