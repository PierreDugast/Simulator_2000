package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe du composant transmetteur logique parfait 
 * Il se contente a la reception du signal de l'emettre tel quel 
 * vers les destinations qui lui sont connectees 
 * Ici nous connectons ce transmetteur a une seule destination
 * @author JRafinesque GChiquet
 *
 */

public class TransmetteurParfait <Destination, Source> extends Transmetteur<Destination, Source>{
	
	public TransmetteurParfait() {
		
	}
	/**
	 * La methode recevoir permet de recuperer l'information generee par la source 
	 * pour emettre cette information vers la destination connectee 
	 * a l'aide de la methode emettre 
	 */
	public void recevoir(Information information) throws InformationNonConforme {
		informationRecue = information;
		informationEmise = information;
	}

	/**
	 * La methode emettre permet de transmettre l'information emise 
	 * vers la destination connectee 
	 */
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Source> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
    	}
    	  
		
	}
	
}
