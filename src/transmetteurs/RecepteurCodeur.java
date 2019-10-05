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
		//Boucle permettant de cr�er pour chaque bits re�u trois bits � 101 pour un 1 re�u et trois bits � 010 pour un 0 re�u
		for(int i=0 ; i<informationRecue.nbElements()/3 ; i++) {
			if(informationRecue.iemeElement(j).toString().equalsIgnoreCase("true") && informationRecue.iemeElement(j+1).toString().equalsIgnoreCase("false") && informationRecue.iemeElement(j+2).toString().equalsIgnoreCase("true")) {
				emission[i]=true;
				j=j+3;
			} else if(informationRecue.iemeElement(j).toString().equalsIgnoreCase("false") && informationRecue.iemeElement(j+1).toString().equalsIgnoreCase("true") && informationRecue.iemeElement(j+2).toString().equalsIgnoreCase("false")) {
				emission[i]=false;
				j=j+3;
			}
		}
		//Cr�ation de l'information � �mettre
		this.informationEmise = new Information(emission);
		
		//System.out.println(informationEmise);
		
		//Envoie l'information aux diff�rentes destinations connect�es pr�sente dans la variable destinationsConnectees
		for(int i=0;i<destinationsConnectees.size();i++){
			destinationsConnectees.get(i).recevoir(this.informationEmise);
		}
	}
	
}
