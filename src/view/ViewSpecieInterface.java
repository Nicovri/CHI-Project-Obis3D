package view;

import java.util.Map;

public interface ViewSpecieInterface {
	public void update(String specieName, Map<String, Long> occurrences, Long maxOcc);
}
