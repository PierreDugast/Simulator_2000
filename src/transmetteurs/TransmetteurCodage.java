package transmetteurs;

import information.Information;
import information.InformationNonConforme;

/**
 * Cette classe permet de créer de la redondance sur le signal émit. Grasse à la méthode recevoir le signal 
 * envoyé par l'émetteur est récupéré puis traiter. Ce traitement consiste à créer une information dans laquelle 
 * on met 101 quand on reçoit un 1 et 010 quand on reçoit un 0. L'information créée est ensuite envoyé vers les 
 * destinations.
 * 
 * @author jérémie
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
	 * Méthode qui récupère l'information envoyé.
	 */
	public void recevoir(Information<Boolean> information) throws InformationNonConforme {
		informationRecue=information;
		emettre();
	}

	/**
	 * Méthode qui vas insérer de la redondance dans l'information pour ensuite le réémetre. Cette redondance consiste à 
	 * remplacer les 1 par trois bits à 101 et à remplacer les 0 par trois bits à 010.
	 */
	public void emettre() throws InformationNonConforme {
		
		int j=0;
		//Boucle permettant de créer pour chaque bits reçu trois bits à 101 pour un 1 reçu et trois bits à 010 pour un 0 reçu
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

		//Envoie l'information aux différentes destinations connectées présente dans la variable destinationsConnectees
		for(int i=0;i<destinationsConnectees.size();i++){
			destinationsConnectees.get(i).recevoir(this.informationEmise);
		}	
	}

}
