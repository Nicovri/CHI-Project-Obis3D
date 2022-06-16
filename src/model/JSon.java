import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import model.geohash.*;

public class JSon {

	private static String readAll(Reader rd) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		while((cp=rd.read())!=-1)
		{
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public static JSONObject getFromFile(String fichier)
	{
		try
		{
			Reader reader = new FileReader(fichier);
			BufferedReader rd = new BufferedReader(reader);
			String jsonText = readAll(rd);
			JSONObject jsonRoot = new JSONObject(jsonText);
			return jsonRoot;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static JSONObject getFromRequest(String requestUrl)
	{
		return null;
	}
	
	public static String buildGeoHashRequest(String s)//String s = GeoHash plus tard
	{
		return null;
	}
	
	public static String buildSpecieRequest(String specieName)
	{
		return null;
	}
	
	public static String buildSpecieRequest(String specieName, Date startDate, Date endDate)
	{
		return null;
	}
	
	public static JSONObject fetchResultSpecieOccurences(JSONObject rawResult)
	{
		HashMap<Location, Integer> nbOccurences = new HashMap<Location, Integer>();
		
		
		JSONArray resultat = rawResult.getJSONArray("features");
		resultat.forEach(item -> {
			JSONObject j = (JSONObject) item;
			System.out.println(j.getJSONObject("geometry"));
		});
		JSONObject article = resultat.getJSONObject(0);
		return article;
	}
	public static void main(String[] args) 
	{
		JSONObject res = fetchResultSpecieOccurences(getFromFile("Delphinidae.json"));
		//System.out.println(res);
		
	}
}
