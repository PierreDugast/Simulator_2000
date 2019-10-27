package transmetteurs;
import information.Information;
import information.InformationNonConforme;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;

/**
 * 
 * @author j�r�mie
 *
 * Classe permettant de cr�er un objet � partir duquel on peut recevoir une information contenant des Booleans
 * que l'on place dans la variable informationRecue. On peut aussi �mettre une information de type Float dans
 * laquelle on ajoute nbEchantillon flottant � une valeur amplitudeMax(resp amplitudeMin) si le boolean est 
 * true(resp false) une fois l'information completer on utilise la m�thode recevoir des diff�rentes 
 * destinationsConnectees avec en param�tre l'information que l'on vient de cr�er. 
 * 
 */
public class EmetteurRz extends ConvertisseurAnalogiqueNumerique<Boolean, Float> {
	
	/**
	 * Constructeur permettant de cr�er un �metteur RZ � partir du constructeur de transmetteur avec trois paramm�tres.
	 * Il initialise nbEchantillon, amplitudeMax et amplitudeMin avec les param�tres renseign�s dans le constructeur et 
	 * initialise informationEmise avec le constructeur d'Information de type float.
	 * 
	 * @param nbEchantillon
	 * @param amplitudeMax
	 * @param amplitudeMin
	 * @throws AnalogicArgumentException
	 */
	public EmetteurRz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		informationEmise = new Information<Float>();
	}
	
	/**
	 * Parcours l'informationRecue et pour chaque boolean remplit l'informationEmise courante avec nbEchantillon/3 � 0
	 * puis nbEchantillon/3 de valeurs flottantes soit � amplitudeMax si le boolean est true soit � amplitudeMin si le 
	 * boolean est false puis nbEchantillon/3 � 0.
	 * On utilise ensuite la m�thode recevoir des diff�rentes destinationsConnectees.
	 */
	public void emettre() throws InformationNonConforme {
		// R�cup�re la partie enti�re de la division par 3 du nombre d'�chantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon=tier*3;		
		
		// Pour chaque boolean dans informationRecue on fait les traitements pr�sents dans cette boucle
		for (int i=0; i < informationRecue.nbElements(); i++) {
			// Ajoute nbEchantillon/3 fois 0 � informationEmise
			for (int j=0; j < tier; j++) {
				informationEmise.add(0.0f);
			}
			// Si l'�l�ment de informationRecue est true on effectue le traitement pr�sent dans ce if.
			if((Boolean) informationRecue.iemeElement(i)) {
				// Ajoute nbEchantillon/3 fois amplitudeMax � informationEmise
				for(int j=tier; j<2*tier; j++) {
					informationEmise.add(amplitudeMax);
				}
			}
			// Si l'�l�ment de informationRecue est false on effectue le traitement pr�sent dans ce else.
			else {
				// Ajoute nbEchantillon/3 fois amplitudeMin � infomationEmise.
				for (int j=tier; j<2*tier; j++) {
					informationEmise.add(amplitudeMin);
				}
			}
			// Ajoute nbEchantillon/3 fois 0 � informationEmise
			for (int j=2*tier; j<nbEchantillon; j++) {
				informationEmise.add((float) 0);
			}
		}
		
		// Envoie l'information aux diff�rentes destinations connect�es pr�sente dans la variable destinationsConnectees
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
}