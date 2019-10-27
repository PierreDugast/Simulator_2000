package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;
import information.Information;

/**
 * 
 * @author jérémie
 * 
 * 
 * Classe permettant de créer un objet qui peut recevoir une information contenant des flottant. Il peut aussi 
 * émettre une information contenant des boolean chaque boolean est déterminer à partir de 30 échantillons de 
 * l'informationRecue. Si la moyenne du deuxième tier des échantillons est supérieur(resp inférieur) à 
 * amplitudeMax+amplitudeMin/2 alors on ajoute à informationEmise true(resp false). Puis on utilise la 
 * méthode recevoir des différentes destinationsConnectees.
 * 
 */
public class RecepteurNrz extends ConvertisseurAnalogiqueNumerique<Float,Boolean>
{
	// Seuil comparé à la moyenne du deuxième tier des échantillons de l'informationRecue
	Float seuil;
	
	/**
	 * Constructeur permettant de créer un récepteur NRZ à partir du constructeur de transmetteur avec trois parammètres.
	 * Il initialise nbEchantillon, amplitudeMax et amplitudeMin avec les paramètres renseignés dans le constructeur et 
	 * initialise informationEmise avec le constructeur d'Information de type Boolean.
	 * 
	 * @param nbEchantillon
	 * @param amplitudeMax
	 * @param amplitudeMin
	 * @throws AnalogicArgumentException
	 */
	public RecepteurNrz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		seuil = (amplitudeMax + amplitudeMin) / 2; 
		informationEmise = new Information<Boolean>();
	}
	
	/**
	 * Parcours L'informationRecue et tous les nbEchantillons ajoute un boolean dans informationEmise à true (resp false)
	 * si la moyennes du deuxième tier des échantillons prient en compte est supérieur (resp inférieur) à 
	 * amplitudeMax+amplitudeMin/2. Puis utilise la méthode recevoir des destinationsConnectees avec en paramètre 
	 * informationEmise.
	 */
	public void emettre() throws InformationNonConforme {
		//Récupère la partie entière de la division par 3 du nombre d'échantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon= tier*3;	
				
		// Compteur permettant de savoir quel élément du tableau doit être modifié
		int tierCourant=0;
		
		float moyenneSignal=0;
		float valeurElementI=0;
				
		// Remplissage du tableau de boolean en fonction des valeurs des échantillons de l'information reçu
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			if(i%nbEchantillon==0) {
				tierCourant=0;
				moyenneSignal=0;
			}
			String elementI = informationRecue.iemeElement(i).toString();
			valeurElementI = Float.parseFloat(elementI);
			moyenneSignal=moyenneSignal+valeurElementI;
			if (tierCourant==(nbEchantillon-1)){
				moyenneSignal=moyenneSignal/(nbEchantillon-1);
				if(moyenneSignal>=(amplitudeMax+amplitudeMin)/2) {
					informationEmise.add(true);
				}
				if(moyenneSignal<(amplitudeMax+amplitudeMin)/2) {
					informationEmise.add(false);
				}
			}
			tierCourant++;
		}
		
		//Envoie l'information aux différentes destinations connectées présente dans la variable destinationsConnectees
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}	
}
