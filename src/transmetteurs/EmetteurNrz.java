package transmetteurs;
import information.Information;
import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;

/**
 * @author jérémie
 *
 * Classe permettant de créer un objet qui peut recevoir une information de type Boolean. A partir de cette information 
 * il est possible de créer une deuxième information qui contient pour chaque boolean de l'informationRecue nbEchantillon
 * flottant à une valeur définit par amplitudeMax si le boolean est true et nbEchantillon flottant à une valeur définit
 * par amplitudeMin si c'est un false. L'information émise sera envoyée aux objets présents dans destinationsConnectees.
 * 
 */
public class EmetteurNrz extends ConvertisseurAnalogiqueNumerique<Boolean,Float>
{
	/**
	 * Constructeur permettant de créer un émetteur NRZ à partir du constructeur de transmetteur avec trois parammètres.
	 * Il initialise nbEchantillon, amplitudeMax et amplitudeMin avec les paramètres renseignés dans le constructeur et 
	 * initialise informationEmise avec le constructeur d'Information de type float.
	 * 
	 * 
	 * @param nbEchantillon
	 * @param amplitudeMax
	 * @param amplitudeMin
	 * @throws AnalogicArgumentException
	 */
	public EmetteurNrz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		informationEmise = new Information<Float>();
	}
	
	/**
	 * Parcours l'informationRecue et pour chaque boolean remplit l'informationEmise courante avec nbEchantillon 
	 * de valeurs flottantes soit à amplitudeMax si le boolean est true soit à amplitudeMin si le boolean est false.
	 * Puis utilise la méthode recevoir des différentes destinationsConnectees.
	 * 
	 */
	public void emettre() throws InformationNonConforme 
	{
		// Pour chaque boolean d'informationRecue si c'est true(resp false) ajoute à informationEmise
		// un flottant à amplitudeMax(resp amplitudeMin) tant que le compteur n'a pas atteint nbEchantillon. 
		for(boolean bool : informationRecue) {
			int compteur=0;
			if(bool) {				
				while(compteur<nbEchantillon) {
					informationEmise.add(amplitudeMax);
					compteur++;
				}
			} else {
				while(compteur<nbEchantillon) {
					informationEmise.add(amplitudeMin);
					compteur++;
				}
			}
	  
		}
		// Envoie l'information aux différentes destinations connectées présente dans la variable destinationsConnectees
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
		
	}
}
