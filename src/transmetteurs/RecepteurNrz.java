package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;
import information.Information;

/**
 * 
 * @author j�r�mie
 * 
 * 
 * Classe permettant de cr�er un objet qui peut recevoir une information contenant des flottant. Il peut aussi 
 * �mettre une information contenant des boolean chaque boolean est d�terminer � partir de 30 �chantillons de 
 * l'informationRecue. Si la moyenne du deuxi�me tier des �chantillons est sup�rieur(resp inf�rieur) � 
 * amplitudeMax+amplitudeMin/2 alors on ajoute � informationEmise true(resp false). Puis on utilise la 
 * m�thode recevoir des diff�rentes destinationsConnectees.
 * 
 */
public class RecepteurNrz extends ConvertisseurAnalogiqueNumerique<Float,Boolean>
{
	// Seuil compar� � la moyenne du deuxi�me tier des �chantillons de l'informationRecue
	Float seuil;
	
	/**
	 * Constructeur permettant de cr�er un r�cepteur NRZ � partir du constructeur de transmetteur avec trois paramm�tres.
	 * Il initialise nbEchantillon, amplitudeMax et amplitudeMin avec les param�tres renseign�s dans le constructeur et 
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
	 * Parcours L'informationRecue et tous les nbEchantillons ajoute un boolean dans informationEmise � true (resp false)
	 * si la moyennes du deuxi�me tier des �chantillons prient en compte est sup�rieur (resp inf�rieur) � 
	 * amplitudeMax+amplitudeMin/2. Puis utilise la m�thode recevoir des destinationsConnectees avec en param�tre 
	 * informationEmise.
	 */
	public void emettre() throws InformationNonConforme {
		//R�cup�re la partie enti�re de la division par 3 du nombre d'�chantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon= tier*3;	
				
		// Compteur permettant de savoir quel �l�ment du tableau doit �tre modifi�
		int tierCourant=0;
		
		float moyenneSignal=0;
		float valeurElementI=0;
				
		// Remplissage du tableau de boolean en fonction des valeurs des �chantillons de l'information re�u
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
		
		//Envoie l'information aux diff�rentes destinations connect�es pr�sente dans la variable destinationsConnectees
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}	
}
