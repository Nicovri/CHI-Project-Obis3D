package test;

import static org.junit.Assert.*;
import org.junit.Test;

import utils.Requests;


public class SimpleTest {


	@Test testBuildingRequest()
	{
			String res = "https://api.obis.org/v3/occurrence/grid/3?scientificname=Delphinidae";
			assertEquals(res, Requests.buildSpecieRequestWithGrid("Delphinidae", 3));

			String res = "https://api.obis.org/v3/occurrence?scientificname=Manta%20birostris&geometry=spd";
			assertEquals(res, Requests.buildGeoHashRequest("spd", "Manta birostris"));

			String res = "https://api.obis.org/v3/taxon/complete/verbose/agab";
			assertEquals(res, Requests.buildRequestAutoIndent("agab"));

	}

	@Test testFetching()
	{
		String[] names = {""};
		String

	}


	//TODO: Add more test

}