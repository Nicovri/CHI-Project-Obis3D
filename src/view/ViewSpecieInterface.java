package view;

import java.util.Map;

import javafx.util.Pair;

public interface ViewSpecieInterface {
	public void update(String specieName, Map<String, Long> occurrences, Pair<Long, Long> maxMinOcc);
}