package transmetteurs;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class EmetteurAnalogique <Destination, Source> extends Transmetteur <Destination, Source>{

	Information<Float> informationEmise = new Information<Float>();
	LinkedList <DestinationInterface <Float>> destinationsConnectees = new LinkedList <DestinationInterface <Float>>();
	
 	public EmetteurAnalogique() {
		
	}
	
	/**
	 * La methode recevoir permet de recuperer l'information generee par la source 
	 * pour emettre cette information vers la destination connectee 
	 * a l'aide de la methode emettre 
	 */
	public void recevoir(Information information) throws InformationNonConforme {
		float symbole;
		informationRecue = information;
		for(int i=0 ; i<information.nbElements() ; i++) {
			if(information.iemeElement(i).equals(true)) {
				symbole = 1;
			} else {
				symbole = -1;
			}
			for(int j=0 ; j < 30 ; j++) {
				informationEmise.add(symbole);     
			}
		}
		System.out.println(informationEmise);
	}

	/**
	 * La methode emettre permet de transmettre l'information emise 
	 * vers la destination connectee 
	 */
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
    	}
	}
}
