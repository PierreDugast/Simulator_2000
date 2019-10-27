package transmetteurs;
import information.Information;
import information.InformationNonConforme;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;

/**
 * 
 * @author jérémie
 *
 * Classe permettant de créer un objet à partir duquel on peut recevoir une information contenant des Booleans
 * que l'on place dans la variable informationRecue. On peut aussi émettre une information de type Float dans
 * laquelle on ajoute nbEchantillon flottant à une valeur amplitudeMax(resp amplitudeMin) si le boolean est 
 * true(resp false) une fois l'information completer on utilise la méthode recevoir des différentes 
 * destinationsConnectees avec en paramètre l'information que l'on vient de créer. 
 * 
 */
public class EmetteurRz extends ConvertisseurAnalogiqueNumerique<Boolean, Float> {
	
	/**
	 * Constructeur permettant de créer un émetteur RZ à partir du constructeur de transmetteur avec trois parammètres.
	 * Il initialise nbEchantillon, amplitudeMax et amplitudeMin avec les paramètres renseignés dans le constructeur et 
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
	 * Parcours l'informationRecue et pour chaque boolean remplit l'informationEmise courante avec nbEchantillon/3 à 0
	 * puis nbEchantillon/3 de valeurs flottantes soit à amplitudeMax si le boolean est true soit à amplitudeMin si le 
	 * boolean est false puis nbEchantillon/3 à 0.
	 * On utilise ensuite la méthode recevoir des différentes destinationsConnectees.
	 */
	public void emettre() throws InformationNonConforme {
		// Récupère la partie entière de la division par 3 du nombre d'échantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon=tier*3;		
		
		// Pour chaque boolean dans informationRecue on fait les traitements présents dans cette boucle
		for (int i=0; i < informationRecue.nbElements(); i++) {
			// Ajoute nbEchantillon/3 fois 0 à informationEmise
			for (int j=0; j < tier; j++) {
				informationEmise.add(0.0f);
			}
			// Si l'élèment de informationRecue est true on effectue le traitement présent dans ce if.
			if((Boolean) informationRecue.iemeElement(i)) {
				// Ajoute nbEchantillon/3 fois amplitudeMax à informationEmise
				for(int j=tier; j<2*tier; j++) {
					informationEmise.add(amplitudeMax);
				}
			}
			// Si l'élèment de informationRecue est false on effectue le traitement présent dans ce else.
			else {
				// Ajoute nbEchantillon/3 fois amplitudeMin à infomationEmise.
				for (int j=tier; j<2*tier; j++) {
					informationEmise.add(amplitudeMin);
				}
			}
			// Ajoute nbEchantillon/3 fois 0 à informationEmise
			for (int j=2*tier; j<nbEchantillon; j++) {
				informationEmise.add((float) 0);
			}
		}
		
		// Envoie l'information aux différentes destinations connectées présente dans la variable destinationsConnectees
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
}