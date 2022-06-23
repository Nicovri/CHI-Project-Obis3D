package model.animal;

/**
 * Classe représentant une espèce.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
public class Specie {
	private String scientificName;
	private String order;
	private String superClass;
	
	public Specie(String scientificName, String order, String superClass)
	{
		this.scientificName = scientificName;
		this.order = order;
		this.superClass = superClass;
	}
	
	public String getScientificName() 
	{
		return scientificName;
	}
	
	public String getOrder() 
	{
		return order;
	}
	
	public String getSuperClass() 
	{
		return superClass;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this==o) return true;
		if(o == null) return false;

		if(o.getClass()!=this.getClass()) return false;
		Specie s = (Specie) o;
		if(this.getScientificName().equals(s.getScientificName())) return true;
		return false;
	}
}
