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
 * Classe de fonctions statiques qui s'occupe de construire et d'exploiter les requï¿½tes nï¿½cessaires ï¿½ l'application.
 *
 * @version 1.0.0
 *
 * @author Nicolas Vrignaud
 * @author Ruben Delamarche
 *
 */
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

	public static String buildGeoHashRequest(String geohash, String specieName)//String s = GeoHash plus tard
	{
		StringBuilder sb = new StringBuilder();
		// pas besoin de /grid/3 ici, sinon on n'a pas les infos des signalements
		sb.append("https://api.obis.org/v3/occurrence?");
		// pas besoin de specieName? (on regarde les espï¿½ces en fonction de l'espï¿½ce dï¿½jï¿½ entrï¿½e ou juste en gï¿½nï¿½ral?)
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

	public static List<Map<String, Long>> fetchTimeIntervals(String specieName, String _startDate, String _endDate)
	{

		LocalDate startDate = LocalDate.parse(_startDate);
		LocalDate endDate = LocalDate.parse(_endDate);
		int nbIntervals = (endDate.minusYears(startDate.getYear()).getYear()/5)+1;
		List<Map<String, Long>> intervals = new ArrayList<Map<String, Long>>(nbIntervals);
		int cpt = 0;
		Thread listThread[] = new Thread[nbIntervals];
		while(startDate.plusYears(5).isBefore(endDate))
		{
			if(cpt+1!=nbIntervals)
			{
				listThread[cpt] = new Thread(new requestIntervalsThread(intervals, cpt, startDate.toString(), endDate.toString(), specieName));
				listThread[cpt].start();
			}
			else
			{
				listThread[cpt] = new Thread(new requestIntervalsThread(intervals, cpt, startDate.toString(), startDate.plusYears(5).toString(), specieName));
				listThread[cpt].start();
			}
			startDate = startDate.plusYears(5);
		}
		for(Thread t : listThread)
		{
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return intervals;
	}

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
							new Specie(obj.getString("scientificName"), obj.getString("order"), obj.getString("class")))); //superclass non plus
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
		fetchTimeIntervals("", "2000-01-01", "2022-01-01");
	}

}

//Classe specifiquement faite pour le fetching des intervalles
//Peut-etre a mettre directement dans le fetching et non dans une classe a part
class requestIntervalsThread implements Runnable
{
	private int indice; //Indice d'acces a la liste des intervalles
	private List<Map<String, Long>> intervals;
	private String start;
	private String end;
	private String specieName;
	//private static Object lock = new Object();

	public requestIntervalsThread(List<Map<String, Long>> _intervals, int _indice, String _start, String _end, String _specieName)
	{
		indice = _indice;
		intervals = _intervals;
		start = _start;
		end = _end;
		specieName = _specieName;
	}

	@Override
	public void run()
	{
		intervals.add(indice, Requests.fetchResultSpecieOccurences(Requests.getFromRequest(
				Requests.buildSpecieRequestWithGrid(specieName, start, end, 3))));
		/*synchronized (lock) {
			arrive[cpt] = Integer.parseInt(num);
			cpt++;
		}*/
	}

}
