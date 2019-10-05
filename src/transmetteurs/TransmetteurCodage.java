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
	
	public int nbEchantillon;
	
	//Constructeur
	public TransmetteurCodage(int nbEchantillon) {
		super();
		this.nbEchantillon=nbEchantillon;
	}

	/**
	 * M�thode qui r�cup�re l'information envoy�.
	 */
	public void recevoir(Information<R> information) throws InformationNonConforme {
		informationRecue=information;
		emettre();
	}

	/**
	 * M�thode qui vas ins�rer de la redondance dans l'information pour ensuite le r��metre. Cette redondance consiste � 
	 * remplacer les 1 par trois bits � 101 et � remplacer les 0 par trois bits � 010.
	 */
	public void emettre() throws InformationNonConforme {
		//Tableau qui va contenir la nouvelle suite de bits
		Boolean [] emission = new Boolean[this.informationRecue.nbElements()*3];
		int j=0;
		//Boucle permettant de cr�er pour chaque bits re�u trois bits � 101 pour un 1 re�u et trois bits � 010 pour un 0 re�u
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("true")) {
				emission[j]=true;
				j++;
				emission[j]=false;
				j++;
				emission[j]=true;
				j++;
				
			}
			if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("false")) {
				emission[j]=false;
				j++;
				emission[j]=true;
				j++;
				emission[j]=false;
				j++;
			}
		}
		//Cr�ation de l'information � �mettre
		this.informationEmise = new Information(emission);

		//Envoie l'information aux diff�rentes destinations connect�es pr�sente dans la variable destinationsConnectees
		for(int i=0;i<destinationsConnectees.size();i++){
			destinationsConnectees.get(i).recevoir(this.informationEmise);
		}	
	}

}
