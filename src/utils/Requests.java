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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.util.Pair;
import model.animal.Report;
import model.animal.Specie;
import model.geohash.GeoHashHelper;
import model.geohash.Location;

public class Requests {

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

	//On retourne une liste car la recherche verbose retourne directement un array
	//solution : enlever l'array et retourner la liste d'objets a l'interieur
	public static List<JSONObject> getFromFile(String path)
	{
		try
		{
			InputStream input = Requests.class.getResourceAsStream(path);
			Reader reader = new InputStreamReader(input);
			BufferedReader rd = new BufferedReader(reader);
			String jsonText = readAll(rd);
			ArrayList<JSONObject> listJson = new ArrayList<JSONObject>();
			if(jsonText.charAt(0)=='{')
			{
				JSONObject jsonRoot = new JSONObject(jsonText);
				listJson.add(jsonRoot);
				return listJson;
			}
			else
			{
				JSONArray jsonArray = new JSONArray(jsonText);
				jsonArray.forEach(item ->
				{
					JSONObject objet = (JSONObject) item;
					listJson.add(objet);
				});
				return listJson;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static List<JSONObject> getFromRequest(String requestUrl)
	{
		String jsonText ="";
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
			jsonText = client.sendAsync(request, BodyHandlers.ofString())
					.thenApply(HttpResponse::body).get(10, TimeUnit.SECONDS);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		ArrayList<JSONObject> listJson = new ArrayList<JSONObject>();
		if(jsonText.charAt(0)=='{')
		{
			JSONObject jsonRoot = new JSONObject(jsonText);
			listJson.add(jsonRoot);
			return listJson;
		}
		else
		{
			JSONArray jsonArray = new JSONArray(jsonText);
			jsonArray.forEach(item ->
			{
				JSONObject objet = (JSONObject) item;
				listJson.add(objet);
			});
			return listJson;
		}
	}

	public static String buildGeoHashRequest(String geohash, String specieName)//String s = GeoHash plus tard
	{
		StringBuilder sb = new StringBuilder();
		// pas besoin de /grid/3 ici, sinon on n'a pas les infos des signalements
		sb.append("https://api.obis.org/v3/occurrence?");
		// pas besoin de specieName? (on regarde les espèces en fonction de l'espèce déjà entrée ou juste en général?)
		if(!specieName.equals(""))
		{
			specieName = specieName.replace(" ", "%20");
			sb.append("scientificname=" + specieName+"&");
		}
		sb.append("geometry="+geohash);

		return sb.toString();
	}

	public static String buildSpecieRequest(String specieName)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.obis.org/v3/occurrence");
		specieName = specieName.replace(" ", "%20");
		sb.append("?scientificname=" + specieName);
		return sb.toString();
	}

	public static String buildSpecieRequestWithGrid(String specieName, int grid)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.obis.org/v3/occurrence/grid/"+grid);
		specieName = specieName.replace(" ", "%20");
		sb.append("?scientificname=" + specieName);
		return sb.toString();
	}

	public static String buildSpecieRequestWithGrid(String specieName, String startDate, String endDate, int grid)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.obis.org/v3/occurrence/grid/"+grid);
		specieName = specieName.replace(" ", "%20");
		sb.append("?scientificname=" + specieName);
		sb.append("&startdate="+startDate);
		sb.append("&enddate="+endDate);
		return sb.toString();
	}

	public static String buildRequestAutoIndent(String startName)
	{
		StringBuilder sb = new StringBuilder();
		startName = startName.replace(" ", "%20");
		sb.append("https://api.obis.org/v3/taxon/complete/verbose/"+startName);
		return sb.toString();
	}

	public static List<String> fetchAutoIndent(List<JSONObject> rawResult)
	{
		List<String> listName = new ArrayList<String>();
		for(JSONObject jo : rawResult)
		{
			listName.add(jo.getString("scientificName"));
		}
		return listName;
	}

	public static HashMap<String, Long> fetchResultSpecieOccurences(List<JSONObject> rawResult)
	{
		HashMap<String, Long> nbOccurences = new HashMap<>(); //String = geohash d'une pos
		
		//Normalement un seul jo (un seul tour de boucle) mais sait-on jamais
		for(JSONObject jo : rawResult)
		{
			
			try
			{
				
				JSONArray resultat = jo.getJSONArray("features");
				resultat.forEach(item -> {
					JSONObject pos = (JSONObject) item;
					JSONObject coord = pos.getJSONObject("geometry");
					JSONArray temp = coord.getJSONArray("coordinates");
					temp.forEach(carre -> {
						
						Pair<BigDecimal, BigDecimal> a, c, milieu; //On veut calculer le centre du carrÃ© gÃ©o, qui vaut le milieu du segment AC
						JSONArray temp_coord = (JSONArray) carre;
						a = new Pair<>(new BigDecimal(temp_coord.getJSONArray(0).get(1).toString()), new BigDecimal(temp_coord.getJSONArray(0).get(0).toString()));
						c = new Pair<>(new BigDecimal(temp_coord.getJSONArray(2).get(1).toString()), new BigDecimal(temp_coord.getJSONArray(2).get(0).toString()));
						milieu = new Pair<>((a.getKey().add(c.getKey())).divide(new BigDecimal("2.0")), (a.getValue().add(c.getValue())).divide(new BigDecimal("2.0")));
						Long nb = pos.getJSONObject("properties").getLong("n");
						// Precision a modifier en fonction du nombre entre(occurrence/grid/n? precision de n) (geohash spd precision 3, geohash spdef precision 5)
						// Ou mettre 3 tout le temps (pour correspondre a grid/3 de la fonction buildGeoHashRequest)
						// On en a besoin pour connaitre la taille du rectangle a dessiner sur la Terre
						nbOccurences.put(GeoHashHelper.getGeohash(new Location("", milieu.getKey().doubleValue(), milieu.getValue().doubleValue()), 3), nb);
					});
				});
				
			} catch(JSONException e) {
				//
			}
		}
		return nbOccurences;
	}
	
	public static List<Report> fetchResultGeoHash(List<JSONObject> rawResult)
	{
		List<Report> individuals = new ArrayList<Report>(); //String = geohash d'une pos
		
		//Normalement un seul jo (un seul tour de boucle) mais sait-on jamais
		for(JSONObject jo : rawResult)
		{
			JSONArray resultat = jo.getJSONArray("results");
			resultat.forEach(item -> {
				JSONObject obj = (JSONObject) item;
				try
				{
					individuals.add(new Report(obj.getString("id"), "", //identifiedBy n'existe pas dans la requete apparemment
							new Specie(obj.getString("scientificName"), obj.getString("order"), ""))); //superclass non plus
				}
				catch(Exception e)
				{
					individuals.add(new Report(obj.getString("id"), "", //identifiedBy n'existe pas dans la requete 
							new Specie(obj.getString("scientificName"), "", ""))); //superclass non plus
				}
				
			});
		}
		return individuals;
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
