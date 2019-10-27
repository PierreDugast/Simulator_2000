package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;
import information.Information;
import java.util.LinkedList;
import java.util.List;


/**
 * 
 * @author j�r�mie
 * 
 * Classe permettant de cr�er un objet r�cepteur RZ capable de recevoir un Information contenant des flottant. 
 * A partir de l'informationRecue remplit tous les nbEchantillons l'informationEise d'un boolean � true (resp false) si la 
 * moyenne du deuxi�me tier des nbEchantillon est sup�rieur (resp inf�rieur) � amplitudeMax+amplitudeMin/2.
 * On utilise ensuite la m�thode recevoir des diff�rentes destinationsConnectees.
 *
 */
public class RecepteurRz extends ConvertisseurAnalogiqueNumerique<Float,Boolean>
{
	
	/**
	 * Constructeur permettant de cr�er un �metteur RZ � partir du constructeur de transmetteur avec trois paramm�tres.
	 * Il initialise nbEchantillon, amplitudeMax et amplitudeMin avec les param�tres renseign�s dans le constructeur et 
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
	 * Tous les nbEchantillons ajoute � informationEmise true (resp false) si la moyenne du deuxi�me tier des nbEchantillons
	 * est sup�rieur (resp inf�rieur) � amplitudeMax+amplitudeMin/2. Utilise ensuite la m�thode recevoir des diff�rentes 
	 * destinationsConnectees.
	 */
	public void emettre() throws InformationNonConforme 
	{
		//R�cup�re la partie enti�re de la division par 3 du nombre d'�chantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon= tier*3;	
				
		// Compteur permettant de savoir quel �chantillon est utilis�
		int tierCourant=0;
		
		float moyenneSignal=0;
		float valeurElementI=0;
				
		// Boucle parcourant l'informationRecue, tous les nbEchantillon ajoute un boolean dans informationEmise
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			// R�initialise tierCourant et moyenneSignal toutes les nbEchantillion
			if(i%nbEchantillon==0) {
				tierCourant=0;
				moyenneSignal=0;
			}
			// Si tierCourant est dans le deuxi�me tier du nbEchantillon on ajoute la valeur de l'�chantillon
			// � moyenneSignal.
			if(tierCourant>=tier && tierCourant<=tier*2) {
				String elementI = informationRecue.iemeElement(i).toString();
				valeurElementI = Float.parseFloat(elementI);
				moyenneSignal=moyenneSignal+valeurElementI;
			}
			// Si on arrive � la fin du deuxi�me tier du nbEchantillon on divise la moyenneSignal par le nombre
			// d'�chantillon dans le deuxi�me tier des nbEchantillon.
			if (tierCourant==tier*2){
				moyenneSignal=moyenneSignal/(tier+1);
				// Si la moyenne est sup�rieur � amplitudeMax+amplitudeMin/2 on ajoute a informationEmise true
				if(moyenneSignal>=(amplitudeMax+amplitudeMin)/2) {
					informationEmise.add(true);
				}
				// Si la moyenne est inf�rieur � amplitudeMax+amplitudeMin/2 on ajoute a informationEmise false
				if(moyenneSignal<(amplitudeMax+amplitudeMin)/2) {
					informationEmise.add(false);
				}
			}
			tierCourant++;
		}	
		
		// Envoie de l'information mise en forme vers les destinations connect�es
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
}