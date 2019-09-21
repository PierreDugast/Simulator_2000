package transmetteursTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;



import transmetteurs.EmetteurNrz;
import information.*;

public class EmetteurNrzTest {
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	@Before
	public void setUp()
	{
		
	}

	@After
	public void tearDown()
	{
		//TODO:
	}
	
	@Test
	public void emettreTest()
	{
		int nbEchantillon = 100;
		Float amplitudeMax = (float) 5.0;
		Float amplitudeMin = (float) -5;
		Boolean [] bool = {true, false, true};
		Information information = new Information(bool);
		EmetteurNrz emetteur = new EmetteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		
	}
}
