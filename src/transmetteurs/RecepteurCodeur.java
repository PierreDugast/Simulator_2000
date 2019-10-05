package transmetteurs;

import information.Information;
import information.InformationNonConforme;

public class RecepteurCodeur <R,T> extends Transmetteur<R,T>{
	
	public int nbEchantillon;
	
	public RecepteurCodeur(int nbEchantillon) {
		super();
		this.nbEchantillon=nbEchantillon;
	}

	public void recevoir(Information<R> information) throws InformationNonConforme {
		informationRecue=information;
		emettre();
		
	}

	public void emettre() throws InformationNonConforme {
		//Tableau qui va contenir la nouvelle suite de bits
		Boolean [] emission = new Boolean[this.informationRecue.nbElements()/3];
		int j=0;
		//Boucle permettant de créer pour chaque bits reçu trois bits à 101 pour un 1 reçu et trois bits à 010 pour un 0 reçu
		for(int i=0 ; i<informationRecue.nbElements()/3 ; i++) {
			if(informationRecue.iemeElement(j).toString().equalsIgnoreCase("true") && informationRecue.iemeElement(j+1).toString().equalsIgnoreCase("false") && informationRecue.iemeElement(j+2).toString().equalsIgnoreCase("true")) {
				emission[i]=true;
				j=j+3;
			} else if(informationRecue.iemeElement(j).toString().equalsIgnoreCase("false") && informationRecue.iemeElement(j+1).toString().equalsIgnoreCase("true") && informationRecue.iemeElement(j+2).toString().equalsIgnoreCase("false")) {
				emission[i]=false;
				j=j+3;
			}
		}
		//Création de l'information à émettre
		this.informationEmise = new Information(emission);
		
		//System.out.println(informationEmise);
		
		//Envoie l'information aux différentes destinations connectées présente dans la variable destinationsConnectees
		for(int i=0;i<destinationsConnectees.size();i++){
			destinationsConnectees.get(i).recevoir(this.informationEmise);
		}
	}
	
}
