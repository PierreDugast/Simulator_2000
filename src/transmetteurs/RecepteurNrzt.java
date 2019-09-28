package transmetteurs;
import ExceptionsGlobales.AnalogicArgumentException;
import information.Information;
import information.InformationNonConforme;

/**
 * 
 * @author jérémie
 * Classe réalisant la reception pour des messages de types NRZT
 * 
 */
public class RecepteurNrzt<R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{
	
	/**
	 * Constructeur du recepteur NRZT utilisant le constructeur des transmetteurs avec 3 paramètres
	 * @param nbEchantillon
	 * @param amplitudeMax
	 * @param amplitudeMin
	 * @throws AnalogicArgumentException
	 */
	public RecepteurNrzt(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
	}

	/**
	 * Méthode permettant de mettre en forme le message reçu puis de l'envoyer. La mise en forme du message consiste 
	 * à récupérer l'information reçu et 1 fois tous les nbEchantillons mettre dans le tableau soit true soit false 
	 * en comparant la valeur de l'échantillon pris en compte à amplitudeMax et amplitudeMin. Si l'échantillon est 
	 * supérieur à amplitudeMax/2 alors on met true dans le tableau et si l'échantillon est inférieur à amplitudeMin/2 
	 * on met false dans le tableau. On crée ensuite une information avec en paramètre du constructeur le tableau de 
	 * boolean créé que l'on met dans information Emise.
	 */
	public void emettre() throws InformationNonConforme {
		
		// Déclaration et initialisation du tableau qui va contenir les booleans.
		Boolean [] emission = new Boolean[this.informationRecue.nbElements()/nbEchantillon];		
		
		// Compteur permettant de savoir quel élèment du tableau doit être modifié
		int c=0;
		
		// Remplissage du tableau de boolean en fonction des valeurs des échantillons de l'information reçu
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			if(i%nbEchantillon/2==0) {
				String elementI = informationRecue.iemeElement(i).toString();
				float valeurElementI = Float.parseFloat(elementI);
				if(valeurElementI>amplitudeMax/2) {
					emission[c] = true;
				}
				if(valeurElementI<amplitudeMin/2) {
					emission[c] = false;
				}
				c++;
			}
		}
		
		// Création de l'information contenant les valeur du tableau de boolean créé dans la boucle précèdente
		this.informationEmise = new Information(emission);
		
		// Envoie de l'information mise en forme vers les destinations connectées
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}		
	}
	
}
