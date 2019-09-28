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
		//compteur interne à chaque packet
		int i = 0;
		//Valeur numerique du bit
		Boolean value = false;
		//tant qu'il reste un element non parcourrut dans la liste
		while((nbPacket*this.nbEchantillon+i)<this.informationRecue.nbElements())
		{
			while ((i<this.nbEchantillon))
			{
				//Si la valeur de l'information actuelle est celle de l'amplitude max, c'est que le packet est forcément un 1 logique.
				if (this.informationRecue.iemeElement(nbPacket*this.nbEchantillon+i)==this.amplitudeMax)
				{
					//Assigner la valeur dans la liste de sortie
					value = true;
					//remettre i à -1 (il va être incrémenté juste après)
					break;
				}
				//System.out.print(""+(nbPacket)+" "+(nbPacket*this.nbEchantillon+i)+" | ");
				//Sinon, incrémenter i
				i++;
			}
			//une fois arrivé à la taille de l'échantillon, aucun bit à Amax n'a été détecté, c'est forcément un 0 logique.
			emission[nbPacket] = value;
			//Remise des compteurs aux valeurs initiales
			nbPacket++;
			i=0;
			value = false;
			//et rebelotte
		}
		
		//Déclanchement de la methode recevoir des destinations connectes
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