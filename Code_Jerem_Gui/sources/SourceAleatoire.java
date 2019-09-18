package sources;

import information.*; 
import visualisations.SondeLogique;
import java.util.*;
import java.util.Random; 



/**
 * Classe du composant Source Aleatoire 
 * Il permet d'emttre une sequence booleenne aleatoire 
 * @author JRafinesque GChiquet
 *
 */
public class SourceAleatoire extends Source<Boolean> {
	
	//generation aleatoire de booleen true / false
	Random RandomBoolean = new Random(); 
	
	/**
	 * Generation de l'information aleatoire a transmettre 
	 */
	public SourceAleatoire() {
		informationGeneree = new Information <Boolean>(); 
		for (int i = 0; i<10; i++) {
			//transmission d'une sequence de 10 booleen
			informationGeneree.add(getBoolean());
			//ajout de la sequence generee dans l'information generee 
		}
	}
	
	public boolean getBoolean() {
		return RandomBoolean.nextBoolean(); 
		
	}
	
	public static void main (String [] args) throws InformationNonConforme {
		
	
	}
}