package transmetteurs;
import ExceptionsGlobales.AnalogicArgumentException;
import information.Information;
import information.InformationNonConforme;

/**
 * 
 * @author j�r�mie
 * Classe r�alisant la reception pour des messages de types NRZT
 * 
 */
public class RecepteurNrzt<R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{
	
	/**
	 * Constructeur du recepteur NRZT utilisant le constructeur des transmetteurs avec 3 param�tres
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
	 * M�thode permettant de mettre en forme le message re�u puis de l'envoyer. La mise en forme du message consiste 
	 * � r�cup�rer l'information re�u et 1 fois tous les nbEchantillons mettre dans le tableau soit true soit false 
	 * en comparant la valeur de l'�chantillon pris en compte � amplitudeMax et amplitudeMin. Si l'�chantillon est 
	 * sup�rieur � amplitudeMax/2 alors on met true dans le tableau et si l'�chantillon est inf�rieur � amplitudeMin/2 
	 * on met false dans le tableau. On cr�e ensuite une information avec en param�tre du constructeur le tableau de 
	 * boolean cr�� que l'on met dans information Emise.
	 */
	public void emettre() throws InformationNonConforme {
		
		// D�claration et initialisation du tableau qui va contenir les booleans.
		Boolean [] emission = new Boolean[this.informationRecue.nbElements()/nbEchantillon];		
		
		// Compteur permettant de savoir quel �l�ment du tableau doit �tre modifi�
		int c=0;
		
		// Remplissage du tableau de boolean en fonction des valeurs des �chantillons de l'information re�u
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
		
		// Cr�ation de l'information contenant les valeur du tableau de boolean cr�� dans la boucle pr�c�dente
		this.informationEmise = new Information(emission);
		
		// Envoie de l'information mise en forme vers les destinations connect�es
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}		
	}
	
}
