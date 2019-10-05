package transmetteurs;

import information.Information;
import information.InformationNonConforme;

/**
 * Cette classe permet de cr�er de la redondance sur le signal �mit. Grasse � la m�thode recevoir le signal 
 * envoy� par l'�metteur est r�cup�r� puis traiter. Ce traitement consiste � cr�er une information dans laquelle 
 * on met 101 quand on re�oit un 1 et 010 quand on re�oit un 0. L'information cr��e est ensuite envoy� vers les 
 * destinations.
 * 
 * @author j�r�mie
 *
 * @param <R>
 * @param <T>
 */
public class TransmetteurCodage <R,T> extends Transmetteur<R,T>{

	/**
	 * M�thode qui r�cup�re l'information envoy�.
	 */
	public void recevoir(Information<R> information) throws InformationNonConforme {
		informationRecue=information;
	}

	/**
	 * M�thode qui vas ins�rer de la redondance dans l'information pour ensuite le r��metre. Cette redondance consiste � 
	 * remplacer les 1 par trois bits � 101 et � remplacer les 0 par trois bits � 010.
	 */
	public void emettre() throws InformationNonConforme {
		//Tableau qui va contenir la nouvelle suite de bits
		Float [] emission = new Float[this.informationRecue.nbElements()*3];
		int j=0;
		//Boucle permettant de cr�er pour chaque bits re�u trois bits � 101 pour un 1 re�u et trois bits � 010 pour un 0 re�u
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
