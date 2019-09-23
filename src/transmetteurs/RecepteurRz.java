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
	}
	
	public void emettre() throws InformationNonConforme 
	{
		Boolean [] emission = new Boolean [this.informationRecue.nbElements()/this.nbEchantillon];
		//compteur du nombre de "packet" de taille nbElement parcourrut
		int nbPacket = 0;
		//compteur interne � chaque packet
		int i = 0;
		//tant qu'il reste un element non parcourrut dans la liste
		while((nbPacket*this.nbEchantillon+i)<this.informationRecue.nbElements())
		{
			//tant que i < � la taille d'un �chantillon
			while (i<this.nbEchantillon)
			{
				//Si la valeur de l'information actuelle est celle de l'amplitude max, c'est que la packet est forc�ment un 1 logique.
				if (this.informationRecue.iemeElement(nbPacket*this.nbEchantillon+i)==this.amplitudeMax)
				{
					//Assigner la valeur dans la liste de sortie
					emission[nbPacket] = true;
					//incr�menter directement le nombre de packet (�viter les calculs inutils)
					nbPacket++;
					//remettre i � -1 (il va �tre incr�ment� juste apr�s)
					i = -1;
				}
				//Sinon, incr�menter i
				i++;
			}
			//une fois arriv� � la taille de l'�chantillon, aucun bit � Amax n'a �t� d�tect�, c'est forc�ment un 0 logique.
			emission[nbPacket] = false;
			nbPacket++;
			i=0;
			//et rebelotte
		}
		
		//D�clanchement de la methode recevoir des destinations connectes
		this.informationEmise = new Information(emission);
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
}