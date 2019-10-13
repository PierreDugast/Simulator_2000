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
public class TransmetteurCodage extends Transmetteur<Boolean,Boolean>{
	
	public int nbEchantillon;
	
	//Constructeur
	public TransmetteurCodage(int nbEchantillon) {
		super();
		this.nbEchantillon=nbEchantillon;
		informationEmise = new Information<Boolean>();
	}

	/**
	 * M�thode qui r�cup�re l'information envoy�.
	 */
	public void recevoir(Information<Boolean> information) throws InformationNonConforme {
		informationRecue=information;
		emettre();
	}

	/**
	 * M�thode qui vas ins�rer de la redondance dans l'information pour ensuite le r��metre. Cette redondance consiste � 
	 * remplacer les 1 par trois bits � 101 et � remplacer les 0 par trois bits � 010.
	 */
	public void emettre() throws InformationNonConforme {
		
		int j=0;
		//Boucle permettant de cr�er pour chaque bits re�u trois bits � 101 pour un 1 re�u et trois bits � 010 pour un 0 re�u
		for(boolean bool : informationRecue) {
			if(bool) {  
				informationEmise.add(true);
				informationEmise.add(false);
				informationEmise.add(true);
				j=j+3;	
			}
			else{
				informationEmise.add(false);
				informationEmise.add(true);
				informationEmise.add(false);
				j=j+3;
			}
		}

		//Envoie l'information aux diff�rentes destinations connect�es pr�sente dans la variable destinationsConnectees
		for(int i=0;i<destinationsConnectees.size();i++){
			destinationsConnectees.get(i).recevoir(this.informationEmise);
		}	
	}

}
