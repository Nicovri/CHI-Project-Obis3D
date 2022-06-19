package event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventType;

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
	
	public Long getMaxOccurrences() {
		long max = 0;
		for(Long l : data.values()) {
			max += l.longValue();
		}
		return Long.valueOf(max);
	}
}
