package sources;

import information.*;
import visualisations.SondeLogique;
import java.util.*;


/**
 * Classe du composant Source Fixe 
 * Il permetd'emettre une sequence booleenne fixe
 * @author JRafinesque GChiquet
 *
 */

public class SourceFixe extends Source<Boolean>{
	
	/**
	 * Generation de l'information fixe a transmettre 
	 * Il s'agit ici d'une sequence de 4 booleen 
	 */
	public SourceFixe() {
		informationGeneree = new Information<Boolean>();
		informationGeneree.add(true);
		informationGeneree.add(false);
		informationGeneree.add(true);
		informationGeneree.add(false);
	}

	public static void main (String []	args) throws InformationNonConforme {
	
	}
}

