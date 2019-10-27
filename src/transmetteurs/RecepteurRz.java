package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;
import information.Information;
import java.util.LinkedList;
import java.util.List;


/**
 * 
 * @author jérémie
 * 
 * Classe permettant de créer un objet récepteur RZ capable de recevoir un Information contenant des flottant. 
 * A partir de l'informationRecue remplit tous les nbEchantillons l'informationEise d'un boolean à true (resp false) si la 
 * moyenne du deuxième tier des nbEchantillon est supérieur (resp inférieur) à amplitudeMax+amplitudeMin/2.
 * On utilise ensuite la méthode recevoir des différentes destinationsConnectees.
 *
 */
public class RecepteurRz extends ConvertisseurAnalogiqueNumerique<Float,Boolean>
{
	
	/**
	 * Constructeur permettant de créer un émetteur RZ à partir du constructeur de transmetteur avec trois parammètres.
	 * Il initialise nbEchantillon, amplitudeMax et amplitudeMin avec les paramètres renseignés dans le constructeur et 
	 * initialise informationEmise avec le constructeur d'Information de type Boolean.
	 * 
	 * @param nbEchantillon
	 * @param amplitudeMax
	 * @param amplitudeMin
	 * @throws AnalogicArgumentException
	 */
	public RecepteurRz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);	
		//le seuil est egal a la difference des deux amplitudes divisees par 2 
		informationEmise = new Information<Boolean>();
		
	}
	
	/**
	 * Tous les nbEchantillons ajoute à informationEmise true (resp false) si la moyenne du deuxième tier des nbEchantillons
	 * est supérieur (resp inférieur) à amplitudeMax+amplitudeMin/2. Utilise ensuite la méthode recevoir des différentes 
	 * destinationsConnectees.
	 */
	public void emettre() throws InformationNonConforme 
	{
		//Récupère la partie entière de la division par 3 du nombre d'échantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon= tier*3;	
				
		// Compteur permettant de savoir quel échantillon est utilisé
		int tierCourant=0;
		
		float moyenneSignal=0;
		float valeurElementI=0;
				
		// Boucle parcourant l'informationRecue, tous les nbEchantillon ajoute un boolean dans informationEmise
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			// Réinitialise tierCourant et moyenneSignal toutes les nbEchantillion
			if(i%nbEchantillon==0) {
				tierCourant=0;
				moyenneSignal=0;
			}
			// Si tierCourant est dans le deuxième tier du nbEchantillon on ajoute la valeur de l'échantillon
			// à moyenneSignal.
			if(tierCourant>=tier && tierCourant<=tier*2) {
				String elementI = informationRecue.iemeElement(i).toString();
				valeurElementI = Float.parseFloat(elementI);
				moyenneSignal=moyenneSignal+valeurElementI;
			}
			// Si on arrive à la fin du deuxième tier du nbEchantillon on divise la moyenneSignal par le nombre
			// d'échantillon dans le deuxième tier des nbEchantillon.
			if (tierCourant==tier*2){
				moyenneSignal=moyenneSignal/(tier+1);
				// Si la moyenne est supérieur à amplitudeMax+amplitudeMin/2 on ajoute a informationEmise true
				if(moyenneSignal>=(amplitudeMax+amplitudeMin)/2) {
					informationEmise.add(true);
				}
				// Si la moyenne est inférieur à amplitudeMax+amplitudeMin/2 on ajoute a informationEmise false
				if(moyenneSignal<(amplitudeMax+amplitudeMin)/2) {
					informationEmise.add(false);
				}
			}
			tierCourant++;
		}	
		
		// Envoie de l'information mise en forme vers les destinations connectées
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
}