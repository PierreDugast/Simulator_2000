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

/*
 * Classe en construction : 
 * - tests de chaque argument possible
 * 
 * !!! EN CONSTRUCTION !!!
 */

public class SimulateurTestTp2 {
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	

	@Test
	/*
	 * Test des parametres par defaut du simulateur.
	 * on ne test pas le transmetteur parfait et la destination finale, car il n'y a pour l'instant pas d'autre possibilité.
	 */
	public void SimulateurTestDefaultValues() 
	{
		String [] args = {};
    	Simulateur simulateur = null;
		try {
			simulateur = new Simulateur(args);
			//Test de la source par defaut
			collector.checkThat("Test source par defaut",simulateur.source, is(new SourceAleatoire(100)));
			//Test de l'emetteur analogique par defaut
			collector.checkThat("Test emetteurAnalogique par defaut", simulateur.emetteurAnalogique,is(new EmetteurRz<Boolean,Float>(30,1.0f,0.0f)));
			//Test du recepteur analogique par defaut
			collector.checkThat("Test recepteurAnalogique par defaut",simulateur.recepteurAnalogique,is(new RecepteurRz<Float,Boolean>(30,1.0f,0.0f)));
			
		}
		catch (Exception e) {collector.addError(new Throwable("Erreur constructeur avec args : ")); e.printStackTrace();} 		
		try {
			simulateur.execute();
			Float zeroFloat = 0f;
			collector.checkThat("Test taux d'erreur binanire null pour transmission analogique parfaite", simulateur.calculTauxErreurBinaire(), is(zeroFloat));
		}
		catch (Exception e) {collector.addError(new Throwable("Erreur exectution avec args : "+args.toString()));} 
	}
	
	@Test
	/*
	 * Test du parametre -form NRZT du simulateur
	 */
	public void SimulateurTestFormNrzt() 
	{
		String [] args = {"-form","NRZT"};
    	Simulateur simulateur = null;
		try {
			simulateur = new Simulateur(args);
			//Test de la source par defaut
			collector.checkThat("Test source par defaut",simulateur.source, is(new SourceAleatoire(100)));
			//Test de l'emetteur analogique par defaut
			collector.checkThat("Test emetteurAnalogique NRZT", simulateur.emetteurAnalogique,is(new EmetteurNrzt<Boolean,Float>(30,1.0f,0.0f)));
			//Test du recepteur analogique par defaut
			collector.checkThat("Test recepteurAnalogique NRZT",simulateur.recepteurAnalogique,is(new RecepteurNrzt<Float,Boolean>(30,1.0f,0.0f)));
			
		}
		catch (Exception e) {collector.addError(new Throwable("Erreur constructeur avec args : "+args.toString()));} 		
		try {
			simulateur.execute();
			Float zeroFloat = 0f;
			collector.checkThat("Test taux d'erreur binanire null pour transmission analogique parfaite", simulateur.calculTauxErreurBinaire(), is(zeroFloat));
		}
		catch (Exception e) {collector.addError(new Throwable("Erreur exectution avec args : "+args.toString()));} 
	}
	
	@Test
	/*
	 * Test du parametre -form NRZ du simulateur
	 */
	public void SimulateurTestFormNrz() 
	{
		String [] args = {"-form","NRZ"};
    	Simulateur simulateur = null;
		try {
			simulateur = new Simulateur(args);
			//Test de la source par defaut
			collector.checkThat("Test source par defaut",simulateur.source, is(new SourceAleatoire(100)));
			//Test de l'emetteur analogique par defaut
			collector.checkThat("Test emetteurAnalogique NRZ", simulateur.emetteurAnalogique,is(new EmetteurNrz<Boolean,Float>(30,1.0f,0.0f)));
			//Test du recepteur analogique par defaut
			collector.checkThat("Test recepteurAnalogique NRZ",simulateur.recepteurAnalogique,is(new RecepteurNrz<Float,Boolean>(30,1.0f,0.0f)));
			
		}
		catch (Exception e) {collector.addError(new Throwable("Erreur constructeur avec args : "+args.toString()));} 		
		try {
			simulateur.execute();
			Float zeroFloat = 0f;
			collector.checkThat("Test taux d'erreur binanire null pour transmission analogique parfaite", simulateur.calculTauxErreurBinaire(), is(zeroFloat));
		}
		catch (Exception e) {collector.addError(new Throwable("Erreur exectution avec args : "+args.toString()));} 
	}
	
	@Test
	/*
	 * Test du parametre -nbEch 100 du simulateur
	 */
	public void SimulateurTestNbech() 
	{
		String [] args = {"-nbEch","100"};
    	Simulateur simulateur = null;
		try {
			simulateur = new Simulateur(args);
			//Test de la source par defaut
			collector.checkThat("Test source par defaut",simulateur.source, is(new SourceAleatoire(100)));
			//Test de l'emetteur analogique par defaut
			collector.checkThat("Test emetteurAnalogique defaut nbEch 100", simulateur.emetteurAnalogique,is(new EmetteurRz<Boolean,Float>(100,1.0f,0.0f)));
			//Test du recepteur analogique par defaut
			collector.checkThat("Test recepteurAnalogique defaut nbEch 100",simulateur.recepteurAnalogique,is(new RecepteurRz<Float,Boolean>(100,1.0f,0.0f)));
			
		}
		catch (Exception e) {collector.addError(new Throwable("Erreur constructeur avec args : "+args.toString()));} 		
		try {
			simulateur.execute();
			Float zeroFloat = 0f;
			collector.checkThat("Test taux d'erreur binanire null pour transmission analogique parfaite", simulateur.calculTauxErreurBinaire(), is(zeroFloat));
		}
		catch (Exception e) {collector.addError(new Throwable("Erreur exectution avec args : "+args.toString()));} 
	}
}

