package utils;

import java.util.List;
import java.util.Map;

/**
* Classe specifiquement faite pour le fetching des intervalles
*
* @version 1.0.0
*
* @auhor Nicolas Vrignaud
* @auhor Ruben Delamarche
*
*/

public class RequestIntervalsThread implements Runnable
{
		private int indice; //Indice d'acces a la liste des intervalles
		private List<Map<String, Long>> intervals;
		private String start;
		private String end;
		private String specieName;

		/**
		* Constructeur. Il faut la liste des intervals, l'indice d'ajout d'une map dans cette liste, date de début et de fin et un nom d'espece
		*/
		public RequestIntervalsThread(List<Map<String, Long>> _intervals, int _indice, String _start, String _end, String _specieName)
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
			//On ajoute au bonne indice la map du fetching entre les deux dates que contient le thread actuel
			intervals.set(indice, Requests.fetchResultSpecieOccurences(Requests.getFromRequest(
					Requests.buildSpecieRequestWithGrid(specieName, start, end, 3))));
		}

}
