package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.util.Pair;
import model.geohash.GeoHashHelper;
import model.geohash.Location;

public class JSON {

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

	public static JSONObject getFromFile(String path)
	{
		try
		{
			InputStream input = JSON.class.getResourceAsStream(path);
			Reader reader = new InputStreamReader(input);
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

	public static String buildGeoHashRequest(String geohash, String specieName)//String s = GeoHash plus tard
	{
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.obis.org/v3/occurrence/grid/3?");
		if(!specieName.equals(""))
		{
			specieName = specieName.replace(" ", "%20");
			sb.append("scientificname=" + specieName+"&");
		}
		sb.append("geometry="+geohash);

		return sb.toString();
	}

	// Return object Specie instead (for Model)?
	public static String buildSpecieRequest(String specieName)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.obis.org/v3/occurrence");
		sb.append("?scientificname=" + specieName);
		return sb.toString();
	}

	public static String buildSpecieRequest(String specieName, Date startDate, Date endDate)
	{
		return null;
	}

	public static HashMap<String, Long> fetchResultSpecieOccurences(JSONObject rawResult)
	{
		HashMap<String, Long> nbOccurences = new HashMap<>(); //String = geohash d'une pos


		JSONArray resultat = rawResult.getJSONArray("features");
		resultat.forEach(item -> {

			JSONObject pos = (JSONObject) item;
			JSONObject coord = pos.getJSONObject("geometry");
			JSONArray temp = coord.getJSONArray("coordinates");
			temp.forEach(carre -> {
				//
				Pair<BigDecimal, BigDecimal> a, c, milieu; //On veut calculer le centre du carré géo, qui vaut le milieu du segment AC
				JSONArray temp_coord = (JSONArray) carre;
				a = new Pair<>(new BigDecimal(temp_coord.getJSONArray(0).get(0).toString()), new BigDecimal(temp_coord.getJSONArray(0).get(1).toString()));
				c = new Pair<>(new BigDecimal(temp_coord.getJSONArray(2).get(0).toString()), new BigDecimal(temp_coord.getJSONArray(2).get(1).toString()));
				milieu = new Pair<>((a.getKey().add(c.getKey())).divide(new BigDecimal("2.0")), (a.getValue().add(c.getValue())).divide(new BigDecimal("2.0")));
				Long nb = new Long(pos.getJSONObject("properties").getInt("n"));
				nbOccurences.put(GeoHashHelper.getGeohash(new Location("", milieu.getKey().doubleValue(), milieu.getValue().doubleValue()), 5), nb);
			});
		});
		return nbOccurences;
	}
	public static void main(String[] args)
	{
		HashMap<String, Long> nbOccurences = fetchResultSpecieOccurences(getFromFile("/res/Selachii.json"));
		/*for(Entry<String, Integer> i : nbOccurences.entrySet())
		{
			System.out.println(i);
		}*/
		System.out.println(buildGeoHashRequest("spd", "Manta birostris"));

	}
}