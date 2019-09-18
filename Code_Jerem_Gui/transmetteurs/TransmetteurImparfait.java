package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;


/**
 * Classe du composant transmetteur logique imparfait 
 * Cette classe va nous permettre de modifier la séconde à transmettre
 * de cette manière nous pourrons valider le calcul du TEB
 * Ici nous connectons ce transmetteur a une seule destination
 * @author JRafinesque GChiquet
 *
 */

public class TransmetteurImparfait <Destination, Source> extends Transmetteur<Destination, Source>{
	
	public TransmetteurImparfait() {
		
	}
	/**
	 * La methode recevoir permet de recuperer l'information generee par la source 
	 * pour emettre cette information vers la destination connectee 
	 * a l'aide de la mathode emettre 
	 */
	public void recevoir(Information information) throws InformationNonConforme {
		informationRecue = information;
		//Information informationModifiee = new Information (); 
		Information informationModifiee = new Information<Boolean>();
		informationModifiee.add(true); 
		informationModifiee.add(true);
		informationModifiee.add(true); 
		informationModifiee.add(true);
		informationEmise = informationModifiee;
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
