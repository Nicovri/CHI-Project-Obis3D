package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.util.Pair;
import model.animal.Report;
import model.animal.Specie;
import model.geohash.GeoHashHelper;
import model.geohash.Location;

/**
 * Classe de fonctions statiques qui s'occupe de construire et d'exploiter les requetes necessaires a l'application.
 * 
 * @version 1.0.0
 * 
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */

public class Requests {

	 
	 /**
	 * Permet de lire un fichier et de le retourner au complet en String
	 */
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

	 /**
	 * Recupere le JSONObject venant d'un fichier
	 */

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
	
	 /**
	 * Recupere le JSONObject venant d'un url
	 */

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
		}
		catch(Exception e)
		{
			return null;
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

	/**
	* Cree la requete pour un geohash et un specieName(qui peut etre vide)
	*/

	public static String buildGeoHashRequest(String geohash, String specieName)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.obis.org/v3/occurrence?");
		if(!specieName.equals(""))
		{
			specieName = specieName.replace(" ", "%20");
			sb.append("scientificname=" + specieName+"&");
		}
		sb.append("geometry="+geohash);

		return sb.toString();
	}

	/**
	* Cree la requete pour un specieName uniquement
	*/
	public static String buildSpecieRequest(String specieName)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.obis.org/v3/occurrence");
		specieName = specieName.replace(" ", "%20");
		sb.append("?scientificname=" + specieName);
		return sb.toString();
	}

	/**
	* Cree la requete pour un specieName, on doit specifier ici le grid
	*/
	
	public static String buildSpecieRequestWithGrid(String specieName, int grid)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.obis.org/v3/occurrence/grid/"+grid);
		specieName = specieName.replace(" ", "%20");
		sb.append("?scientificname=" + specieName);
		return sb.toString();
	}

	/**
	* Cree la requete pour un specieName, une date de debut et de fin et un grid
	*/
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
	
	/**
	* Cree la requete pour un debut de recherche d'une espece
	*/
	public static String buildRequestAutoIndent(String startName)
	{
		StringBuilder sb = new StringBuilder();
		startName = startName.replace(" ", "%20");
		sb.append("https://api.obis.org/v3/taxon/complete/verbose/"+startName);
		return sb.toString();
	}

	/**
	* Retourne une liste des noms d'espece (scientificName)
	*/
	public static List<String> fetchAutoIndent(List<JSONObject> rawResult)
	{
		if(rawResult == null) {
			return null;
		}
		
		List<String> listName = new ArrayList<String>();
		for(JSONObject jo : rawResult)
		{
			listName.add(jo.getString("scientificName"));
		}
		return listName;
	}
	/**
	* Retourne une map qui lie un geohash et un nombre d'occurences
	*/
	public static HashMap<String, Long> fetchResultSpecieOccurences(List<JSONObject> rawResult)
	{
		if(rawResult == null) {
			return null;
		}
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
	/**
	* Retourne une liste de maps (qui lient geohash et un nombre d'occurences) par intervalle de temps de 5 ans
	* entre une date de debut et une de fin
	*/
	public static List<Map<String, Long>> fetchTimeIntervals(String specieName, String _startDate, String _endDate)
	{
		List<Map<String, Long>> intervals = new ArrayList<Map<String, Long>>();
		LocalDate startDate = LocalDate.parse(_startDate);
		LocalDate endDate = LocalDate.parse(_endDate);
		while(startDate.plusYears(5).isBefore(endDate))
		{
			intervals.add(fetchResultSpecieOccurences(getFromRequest(
					buildSpecieRequestWithGrid(specieName, startDate.toString(), startDate.plusYears(5).toString(), 3))));
			startDate = startDate.plusYears(5);
		}
		intervals.add(fetchResultSpecieOccurences(getFromRequest(
				buildSpecieRequestWithGrid(specieName, startDate.toString(), endDate.toString(), 3))));

		return intervals;
	}
	
	/**
	* (plus rapide que fetchTimeIntervals) Retourne une liste de maps (qui lient geohash et un nombre d'occurences) par intervalle de temps de 5 ans
	* entre une date de debut et une de fin, en utilisant des threads
	*/
	public static List<Map<String, Long>> fetchTimeIntervalsThread(String specieName, String _startDate, String _endDate)
	{

		LocalDate startDate = LocalDate.parse(_startDate);
		LocalDate endDate = LocalDate.parse(_endDate);
		int nbIntervals = (endDate.minusYears(startDate.getYear()).getYear() + 5 -1)/5;
//		int nbIntervals = (endDate.minusYears(startDate.getYear()).getYear()/5)+1;
		List<Map<String, Long>> intervals = new ArrayList<Map<String, Long>>();
		for(int i = 0; i < nbIntervals; i++) {
			intervals.add(null);
		}
		System.out.println(intervals.size());
		int cpt = 0;
		Thread listThread[] = new Thread[nbIntervals];
		while(startDate.plusYears(5).isBefore(endDate))
		{
			listThread[cpt] = new Thread(new RequestIntervalsThread(intervals, cpt, startDate.toString(), startDate.plusYears(5).toString(), specieName));
			listThread[cpt].start();
			startDate = startDate.plusYears(5);
			cpt++;
		}
		listThread[cpt] = new Thread(new RequestIntervalsThread(intervals, cpt, startDate.toString(), endDate.toString(), specieName));
		listThread[cpt].start();
		for(Thread t : listThread)
		{
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return intervals;
	}

	/**
	* Retourne une liste de signalements (lier a la fonction buildGeoHashRequest)
	*/
	public static List<Report> fetchResultGeoHash(List<JSONObject> rawResult)
	{
		if(rawResult == null) {
			return null;
		}
		
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
	
	//main de test
	public static void main(String[] args)
	{
		long start = System.currentTimeMillis();
		List<Map<String, Long>> list = fetchTimeIntervalsThread("Delphinidae", "1030-01-01", "2022-01-01");
		for(Map<String, Long> m : list) {
			System.out.println(m);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
