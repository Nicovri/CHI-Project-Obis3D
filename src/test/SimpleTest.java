package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class SimpleTest {

	ResourceManager resourceManager;
	

	@Before
	public void setUp() throws Exception {
		
		//Exemple
        resourceManager = new ResourceManager();
		
		assertNotEquals(resourceManager, null);
		
        try
        {
            resourceManager.readLocationfile(this.getClass().getResource("Selachi.json").toURI().getPath());        	
        }
        catch (Exception e) {
        	e.printStackTrace();
        }

	}
	
	
	
	@Test
	public void YearNumberTest() {		

		//Vérifier que le nombre d'enregistrements est 4513
		assertEquals(4513, resourceManager.sampleNumber);
	    
	}

	//TODO: Add more test
		
}



