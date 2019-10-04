package transmetteurs;

import information.Information;
import information.InformationNonConforme;

public class TransmetteurCodage <R,T> extends Transmetteur<R,T>{

	@Override
	public void recevoir(Information<R> information) throws InformationNonConforme {
		informationRecue=information;
	}

	@Override
	public void emettre() throws InformationNonConforme {
		Float [] emission = new Float[this.informationRecue.nbElements()*3];
		int j=0;
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("1")) {
				emission[j]=(float)1;
				j++;
				emission[j]=(float)0;
				j++;
				emission[j]=(float)1;
			}
			if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("0")) {
				emission[j]=(float)0;
				j++;
				emission[j]=(float)1;
				j++;
				emission[j]=(float)0;
			}
		}
		//Cr�ation de l'information � �mettre
		this.informationEmise = new Information(emission);
		System.out.println(informationEmise);
		//Envoie l'information aux diff�rentes destinations connect�es pr�sente dans la variable destinationsConnectees
		for(int i=0;j<destinationsConnectees.size();i++){
			destinationsConnectees.get(i).recevoir(this.informationEmise);
		}	
	}

}
