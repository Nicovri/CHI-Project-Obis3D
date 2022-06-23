package model.animal;

/**
 * Classe repr�sentant un signalement d'individu d'une esp�ce donn�e.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public class Report {
	private String id;
	private String recordedBy;
	private Specie specie;

	public Report(String id, String recordedBy, Specie specie)
	{
		this.id = id;
		this.recordedBy = recordedBy;
		this.specie = specie;
	}

	public String getId()
	{
		return id;
	}

	public String getRecordedBy()
	{
		return recordedBy;
	}

	public Specie getSpecie()
	{
		return specie;
	}
}
