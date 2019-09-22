import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;

import sources.*;
import destinations.*;
import information.Information;
import information.InformationNonConforme;
import transmetteurs.*;
import visualisations.*;
import java.util.Iterator;
import ExceptionsGlobales.AnalogicArgumentException;
import ExceptionsGlobales.ArgumentsException;

public class SimulateurTestTp2 {
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void SimulateurTest1() 
	{
		fail("Not yet implemented");
	}

}
