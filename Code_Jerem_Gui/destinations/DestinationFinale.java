package destinations;

import information.Information;
import information.InformationNonConforme;

/**
 * La classe destination finale se contente de recevoir le signal 
 * du composant sur lequel elle est connectee
 * @author JRafinesque GChiquet
 *
 */

public class DestinationFinale extends Destination<Boolean>{
	
	public DestinationFinale() {
		
	}
	
	/**
	 * La methode recevoir permet a la destination de recuperer 
	 * l'information transmise par le transmetteur logique parfait 
	 * 
	 */
	public void recevoir(Information<Boolean> information) throws InformationNonConforme {
		informationRecue = information;
		System.out.println(informationRecue);
	}

}
