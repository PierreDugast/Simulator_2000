package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;
import information.Information;


public class RecepteurNrz<R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{
	Float seuil;
	
	public RecepteurNrz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		//le seuil est egal a la difference des deux amplitudes divisees par 2 
		seuil = (amplitudeMax + amplitudeMin) / 2; 
	}

	@Override
	public void emettre() throws InformationNonConforme {
		//Récupère la partie entière de la division par 3 du nombre d'échantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon= tier*3;
		// Déclaration et initialisation du tableau qui va contenir les booleans.
		Boolean [] emission = new Boolean[this.informationRecue.nbElements()/nbEchantillon];		
				
		// Compteur permettant de savoir quel élément du tableau doit être modifié
		int symboleCourant=0;
		int tierCourant=0;
		float moyenneSignal=0;
		float valeurElementI=0;
				
		// Remplissage du tableau de boolean en fonction des valeurs des échantillons de l'information reçu
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			if(i%nbEchantillon==0) {
				tierCourant=0;
			}
			String elementI = informationRecue.iemeElement(i).toString();
			valeurElementI = Float.parseFloat(elementI);
			moyenneSignal=moyenneSignal+valeurElementI;
			if (tierCourant==tier*3-1){
				moyenneSignal=moyenneSignal/(tier+1);
				if(moyenneSignal>(amplitudeMax+amplitudeMin)/2) {
					emission[symboleCourant] = true;
				}
				if(moyenneSignal<(amplitudeMax+amplitudeMin)/2) {
					emission[symboleCourant] = false;
				}
				symboleCourant++;
			}
			tierCourant++;
		}
				
		// Crï¿½ation de l'information contenant les valeur du tableau de boolean créé dans la boucle précèdente
		this.informationEmise = new Information(emission);
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
	public boolean equals (Object obj) {
		return (obj instanceof RecepteurNrz)&& 
				(((RecepteurNrz)obj).nbEchantillon== this.nbEchantillon) &&
				(((RecepteurNrz)obj).amplitudeMax== this.amplitudeMax) &&
				(((RecepteurNrz)obj).amplitudeMin== this.amplitudeMin); 
	}
	
}
