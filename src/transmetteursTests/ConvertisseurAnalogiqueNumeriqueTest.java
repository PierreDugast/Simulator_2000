package transmetteursTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;

import ExceptionsGlobales.AnalogicArgumentException;

import static org.hamcrest.CoreMatchers.is;



import transmetteurs.*;
import information.*;

public class ConvertisseurAnalogiqueNumeriqueTest {
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void ConvertisseurAnalogiqueNumeriqueTestOk()
	{
		int nbEchantillon = 100;
		Float amplitudeMax = (float) 5;
		Float amplitudeMin = (float) 1;
		try {EmetteurNrz emetteurNrz = new EmetteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);} 
		catch (Exception e) { collector.addError(new Throwable("Erreur détecté : Constructeur EmetteurNrz obtient une erreur innatendue"));}
		try {EmetteurNrzt emetteurNrzt = new EmetteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);}
		catch (Exception e) {collector.addError(new Throwable("Erreur détecté : constructeur EmetteurNrzt obtient une erreur innatendue"));}
		try {EmetteurRz emetteurRz = new EmetteurRz(nbEchantillon, amplitudeMax, amplitudeMin);}
		catch (Exception e) {collector.addError(new Throwable("Erreur détecté : constructeur EmetteurRz obtient une erreur innatendue"));}
		try {RecepteurNrz recepteurNrz = new RecepteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);}
		catch (Exception e) {collector.addError(new Throwable("Erreur détecté : constructeur RecepteurNrz obtient une erreur innatendue"));}
		try {RecepteurNrzt recepteurNrzt = new RecepteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);}
		catch (Exception e) {collector.addError(new Throwable("Erreur détecté : Constructeur RecepteurNrzt obtient une erreur innatendue"));}
		try {RecepteurRz recepteurRz = new RecepteurRz(nbEchantillon, amplitudeMax, amplitudeMin);}
		catch (Exception e) {collector.addError(new Throwable("Erreur détecté : Constructeur ReccepteurRz obtient une erreur innatendue"));}
	}
	
	@Test
	public void ConvertisseurAnalogiqueNumeriqueTestKoNbech0()
	{
		int nbEchantillon = 0;
		Float amplitudeMax = (float) 5;
		Float amplitudeMin = (float) 1;
		String errorMessage = " ne rejette pas l'erreur attendue pour nbEchantillon = 0";
		try {EmetteurNrz emetteurNrz = new EmetteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur EmetteurNrz"+errorMessage));} 
		catch (AnalogicArgumentException e) {}
		try {EmetteurNrzt emetteurNrzt = new EmetteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {EmetteurRz emetteurRz = new EmetteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrz recepteurNrz = new RecepteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur RecepteurNrz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrzt recepteurNrzt = new RecepteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur RecepteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurRz recepteurRz = new RecepteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur ReccepteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
	}
	
	@Test
	public void ConvertisseurAnalogiqueNumeriqueTestKoNbechNeg()
	{
		int nbEchantillon = -10;
		Float amplitudeMax = (float) 5;
		Float amplitudeMin = (float) 1;
		String errorMessage = " ne rejette pas l'erreur attendue pour nbEchantillon = -10";
		try {EmetteurNrz emetteurNrz = new EmetteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur EmetteurNrz"+errorMessage));} 
		catch (AnalogicArgumentException e) {}
		try {EmetteurNrzt emetteurNrzt = new EmetteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {EmetteurRz emetteurRz = new EmetteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrz recepteurNrz = new RecepteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur RecepteurNrz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrzt recepteurNrzt = new RecepteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur RecepteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurRz recepteurRz = new RecepteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur ReccepteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
	}
	
	@Test
	public void ConvertisseurAnalogiqueNumeriqueTestKoAmplitudeMax()
	{
		int nbEchantillon = 0;
		Float amplitudeMax = (float) -1;
		Float amplitudeMin = (float) 1;
		String errorMessage = " ne rejette pas l'erreur attendue pour amplitudeMax<0";
		try {EmetteurNrz emetteurNrz = new EmetteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur EmetteurNrz"+errorMessage));} 
		catch (AnalogicArgumentException e) {}
		try {EmetteurNrzt emetteurNrzt = new EmetteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {EmetteurRz emetteurRz = new EmetteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrz recepteurNrz = new RecepteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur RecepteurNrz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrzt recepteurNrzt = new RecepteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur RecepteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurRz recepteurRz = new RecepteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur ReccepteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
	}
	
	@Test
	public void ConvertisseurAnalogiqueNumeriqueTestKoAmplitudeMin()
	{
		int nbEchantillon = 0;
		Float amplitudeMax = (float) 5;
		Float amplitudeMin = (float) -1;
		String errorMessage = " ne rejette pas l'erreur attendue pour amplitudeMin<0";
		try {EmetteurNrz emetteurNrz = new EmetteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur EmetteurNrz"+errorMessage));} 
		catch (AnalogicArgumentException e) {}
		try {EmetteurNrzt emetteurNrzt = new EmetteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {EmetteurRz emetteurRz = new EmetteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrz recepteurNrz = new RecepteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur RecepteurNrz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrzt recepteurNrzt = new RecepteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur RecepteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurRz recepteurRz = new RecepteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur ReccepteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
	}
	
	@Test
	public void ConvertisseurAnalogiqueNumeriqueTestKoAmplitudeDiff()
	{
		int nbEchantillon = 0;
		Float amplitudeMax = (float) 1;
		Float amplitudeMin = (float) 5;
		String errorMessage = " ne rejette pas l'erreur attendue pour amplitudeMax<amplitudeMin";
		try {EmetteurNrz emetteurNrz = new EmetteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur EmetteurNrz"+errorMessage));} 
		catch (AnalogicArgumentException e) {}
		try {EmetteurNrzt emetteurNrzt = new EmetteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {EmetteurRz emetteurRz = new EmetteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrz recepteurNrz = new RecepteurNrz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur RecepteurNrz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrzt recepteurNrzt = new RecepteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur RecepteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurRz recepteurRz = new RecepteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur ReccepteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
	}
	
	@Test
	public void EmetteurRecepteurNrztTest()
	{
		int nbEchantillon = 1;
		Float amplitudeMax = (float) 5;
		Float amplitudeMin = (float) 1;
		String errorMessage = " ne rejette pas l'erreur attendue pour nbEchantillon = 1 (doit être supérieur ou égal à 2)";
		try {EmetteurNrzt emetteurNrzt = new EmetteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurNrzt recepteurNrzt = new RecepteurNrzt(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur RecepteurNrzt"+errorMessage));}
		catch (AnalogicArgumentException e) {}
	}
	
	@Test
	public void EmetteurRecepteurRzTest()
	{
		int nbEchantillon = 2;
		Float amplitudeMax = (float) 5;
		Float amplitudeMin = (float) 1;
		String errorMessage = " ne rejette pas l'erreur attendue pour nbEchantillon = 2 (doit être supérieur ou égal à 3)";
		try {EmetteurRz emetteurRz = new EmetteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : constructeur EmetteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
		try {RecepteurRz recepteurRz = new RecepteurRz(nbEchantillon, amplitudeMax, amplitudeMin);
		collector.addError(new Throwable("Erreur détecté : Constructeur ReccepteurRz"+errorMessage));}
		catch (AnalogicArgumentException e) {}
	}
}
