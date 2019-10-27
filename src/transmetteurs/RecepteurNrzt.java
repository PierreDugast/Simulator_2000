package transmetteurs;
import ExceptionsGlobales.AnalogicArgumentException;
import information.Information;
import information.InformationNonConforme;

/**
 * 
 * @author Jérémie
 * Classe réalisant la reception pour des messages de types NRZT
 * 
 */
public class RecepteurNrzt extends ConvertisseurAnalogiqueNumerique<Float,Boolean>
{
	
	/**
	 * Constructeur permettant de créer un émetteur NRZT à partir du constructeur de transmetteur avec trois parammètres.
	 * Il initialise nbEchantillon, amplitudeMax et amplitudeMin avec les paramètres renseignés dans le constructeur et 
	 * initialise informationEmise avec le constructeur d'Information de type Boolean.
	 * 
	 * @param nbEchantillon
	 * @param amplitudeMax
	 * @param amplitudeMin
	 * @throws AnalogicArgumentException
	 */
	public RecepteurNrzt(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		informationEmise = new Information<Boolean>();
	}

	/**
	 * Méthode permettant de mettre en forme le message reçu puis de l'envoyer. La mise en forme du message consiste 
	 * à récupérer l'information reçu. Mettre dans le tableau soit true soit false en comparant la valeur de 
	 * la moyenne du deuxième tier des échantillons pris en compte avec amplitudeMax+amplitudeMin/2. Si l'échantillon est 
	 * supérieur à amplitudeMax+amplitudeMin/2 on met true dans le tableau et si l'échantillon est inférieur
	 * à amplitudeMax+amplitudeMin/2 on met false dans le tableau. On utilise ensuite la méthode recevoir des différentes
	 * destinationsConnectees.
	 */
	public void emettre() throws InformationNonConforme {
		//Récupère la partie entière de la division par 3 du nombre d'échantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon = tier*3;		
		
		// Compteur permettant de savoir quel échantillon est utilisé
		int tierCourant=0;
		
		float moyenneSignal=0;
		float valeurElementI=0;
		
		// Remplissage du tableau de boolean en fonction des valeurs des échantillons de l'information reçu
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			if(i%nbEchantillon==0) {
				tierCourant=0;
				moyenneSignal=0;
			}
			if(tierCourant>=tier && tierCourant<=tier*2) {
				String elementI = informationRecue.iemeElement(i).toString();
				valeurElementI = Float.parseFloat(elementI);
				moyenneSignal=moyenneSignal+valeurElementI;
			}
			if (tierCourant==tier*2){
				moyenneSignal=moyenneSignal/(tier+1);
				if(moyenneSignal>=(amplitudeMax+amplitudeMin)/2) {
					informationEmise.add(true);
				}
				if(moyenneSignal<(amplitudeMax+amplitudeMin)/2) {
					informationEmise.add(false);
				}
			}
			tierCourant++;
		}
		// Envoie de l'information mise en forme vers les destinations connectées
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}		
	}
}
