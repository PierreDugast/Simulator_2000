package transmetteurs;
import information.Information;
import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;

/**
 * @author j�r�mie
 *
 * Classe permettant de cr�er un objet qui peut recevoir une information de type Boolean. A partir de cette information 
 * il est possible de cr�er une deuxi�me information qui contient pour chaque boolean de l'informationRecue nbEchantillon
 * flottant � une valeur d�finit par amplitudeMax si le boolean est true et nbEchantillon flottant � une valeur d�finit
 * par amplitudeMin si c'est un false. L'information �mise sera envoy�e aux objets pr�sents dans destinationsConnectees.
 * 
 */
public class EmetteurNrz extends ConvertisseurAnalogiqueNumerique<Boolean,Float>
{
	/**
	 * Constructeur permettant de cr�er un �metteur NRZ � partir du constructeur de transmetteur avec trois paramm�tres.
	 * Il initialise nbEchantillon, amplitudeMax et amplitudeMin avec les param�tres renseign�s dans le constructeur et 
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
	 * de valeurs flottantes soit � amplitudeMax si le boolean est true soit � amplitudeMin si le boolean est false.
	 * Puis utilise la m�thode recevoir des diff�rentes destinationsConnectees.
	 * 
	 */
	public void emettre() throws InformationNonConforme 
	{
		// Pour chaque boolean d'informationRecue si c'est true(resp false) ajoute � informationEmise
		// un flottant � amplitudeMax(resp amplitudeMin) tant que le compteur n'a pas atteint nbEchantillon. 
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
		// Envoie l'information aux diff�rentes destinations connect�es pr�sente dans la variable destinationsConnectees
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
		
	}
}
