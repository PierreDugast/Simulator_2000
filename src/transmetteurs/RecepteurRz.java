package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;
import information.Information;
import java.util.LinkedList;
import java.util.List;


/**
 * @author PC-CHIQUET
 *Cette classe permet de decoder le cas d une onde impulsionnelle de type RZ 
 * @param <R>
 * @param <T>
 */
public class RecepteurRz<R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{
	
	public RecepteurRz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);	
		//le seuil est egal a la difference des deux amplitudes divisees par 2 
		
	}
	
	public void emettre() throws InformationNonConforme 
	{
		//R�cup�re la partie enti�re de la division par 3 du nombre d'�chantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon= tier*3;
		// D�claration et initialisation du tableau qui va contenir les booleans.
		Boolean [] emission = new Boolean[this.informationRecue.nbElements()/nbEchantillon];		
				
		// Compteur permettant de savoir quel �l�ment du tableau doit �tre modifi�
		int symboleCourant=0;
		int tierCourant=0;
		float moyenneSignal=0;
		float valeurElementI=0;
				
		// Remplissage du tableau de boolean en fonction des valeurs des �chantillons de l'information re�u
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			if(i%nbEchantillon==0) {
				tierCourant=0;
			}
			if(tierCourant>=tier && tierCourant<=tier*2) {
				String elementI = informationRecue.iemeElement(i).toString();
				valeurElementI = Float.parseFloat(elementI);
				moyenneSignal=moyenneSignal+valeurElementI;
			}
			if (tierCourant==tier*2){
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
		// Cr�ation de l'information contenant les valeur du tableau de boolean cr�� dans la boucle pr�c�dente
		this.informationEmise = new Information(emission);
		
		//D�clanchement de la methode recevoir des destinations connectes
		this.informationEmise = new Information(emission);
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
	
	public boolean equals (Object obj) {
		return (obj instanceof RecepteurRz)&& 
				(((RecepteurRz)obj).nbEchantillon== this.nbEchantillon) &&
				(((RecepteurRz)obj).amplitudeMax== this.amplitudeMax) &&
				(((RecepteurRz)obj).amplitudeMin== this.amplitudeMin); 
	}
}