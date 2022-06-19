package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

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
			Reader reader = new FileReader(path);
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
		String json ="";
		HttpClient client = HttpClient.newBuilder()
				.version(Version.HTTP_1_1)
				.followRedirects(Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(20))
				.build();
		HttpRequest request = HttpRequest.newBuilder()
		        .uri(URI.create(requestUrl))
		        .timeout(Duration.ofMinutes(2))
		        .header("Content-Type", "application/json")
		        .GET()
		        .build();
		try
		{
			json = client.sendAsync(request, BodyHandlers.ofString())
					.thenApply(HttpResponse::body).get(10, TimeUnit.SECONDS);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return new JSONObject(json);
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
	
	public static String buildSpecieRequestWithGrid(String specieName, String grid)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.obis.org/v3/occurrence/grid/"+grid);
		sb.append("?scientificname=" + specieName);
		return sb.toString();
	}

	//https://api.obis.org/v3/occurrence?scientificname=Delphinidae&startdate=2000-01-01&enddate=2010-12-31
	public static String buildSpecieRequestWithGrid(String specieName, String startDate, String endDate, String grid)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.obis.org/v3/occurrence/grid/"+grid);
		sb.append("?scientificname=" + specieName);
		sb.append("&startdate="+startDate);
		sb.append("&enddate="+endDate);		
		return sb.toString();
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

				Pair<BigDecimal, BigDecimal> a, c, milieu; //On veut calculer le centre du carré géo, qui vaut le milieu du segment AC
				JSONArray temp_coord = (JSONArray) carre;
				a = new Pair<>(new BigDecimal(temp_coord.getJSONArray(0).get(0).toString()), new BigDecimal(temp_coord.getJSONArray(0).get(1).toString()));
				c = new Pair<>(new BigDecimal(temp_coord.getJSONArray(2).get(0).toString()), new BigDecimal(temp_coord.getJSONArray(2).get(1).toString()));
				milieu = new Pair<>((a.getKey().add(c.getKey())).divide(new BigDecimal("2.0")), (a.getValue().add(c.getValue())).divide(new BigDecimal("2.0")));
				Long nb = pos.getJSONObject("properties").getLong("n");
				// Precision a modifier en fonction du nombre entre(occurrence/grid/n? precision de n) (geohash spd precision 3, geohash spdef precision 5)
				// Ou mettre 3 tout le temps (pour correspondre � grid/3 de la fonction buildGeoHashRequest)
				// On en a besoin pour connaitre la taille du rectangle a dessiner sur la Terre
				nbOccurences.put(GeoHashHelper.getGeohash(new Location("", milieu.getKey().doubleValue(), milieu.getValue().doubleValue()), 5), nb);
			});
		});
		return nbOccurences;
	}
	
	public static void main(String[] args)
	{
		/*HashMap<String, Long> nbOccurences = fetchResultSpecieOccurences(getFromRequest(buildSpecieRequestWithGrid("Delphinidae", "3")));
		for(Entry<String, Long> i : nbOccurences.entrySet())
		{
			System.out.println(i);
		}*/
		
		/*for(String s : nbOccurences.keySet()) {
			System.out.println(s);
		}*/
		//System.out.println(buildGeoHashRequest("spd", "Manta birostris"));

	}
}
