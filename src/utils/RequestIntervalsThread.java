package utils;

import java.util.List;
import java.util.Map;

//Classe specifiquement faite pour le fetching des intervalles
//Peut-etre a mettre directement dans le fetching et non dans une classe a part
public class RequestIntervalsThread implements Runnable
{
		private int indice; //Indice d'acces a la liste des intervalles
		private List<Map<String, Long>> intervals;
		private String start;
		private String end;
		private String specieName;

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
			System.out.println(indice);
			intervals.set(indice, Requests.fetchResultSpecieOccurences(Requests.getFromRequest(
					Requests.buildSpecieRequestWithGrid(specieName, start, end, 3))));
		}

	}