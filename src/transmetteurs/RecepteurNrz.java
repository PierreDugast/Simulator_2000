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
		seuil = (amplitudeMax - amplitudeMin) / 2; 
	}

	@Override
	public void emettre() throws InformationNonConforme {
		Boolean [] emission = new Boolean [this.informationRecue.nbElements()/this.nbEchantillon];
		//compteur du nombre de "packet" de taille nbElement parcourus
		int nbPacket = 0;
		//compteur interne à chaque packet
		int i = 0;
		//tant qu'il reste un element non parcouru dans la liste
		while((nbPacket*this.nbEchantillon+i)<this.informationRecue.nbElements())
		{
			//tant que i < à la taille d'un échantillon
			while (i<this.nbEchantillon)
			{
				//Si la valeur de l'information actuelle est celle de l'amplitude max, c'est que la packet est forcément un 1 logique.
				if ((float) this.informationRecue.iemeElement(nbPacket*this.nbEchantillon+i) >= (float) seuil)
				{
					//Assigner la valeur dans la liste de sortie
					emission[nbPacket] = true;
			
					
			
				}
				else if ((float) this.informationRecue.iemeElement(nbPacket*this.nbEchantillon+i) <= (float) seuil) {
					emission[nbPacket] = false;
					
				}
				//Sinon, incrémenter i
				i++;
			}
			nbPacket++;
			i=0;
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
		return (obj instanceof RecepteurNrz)&& 
				(((RecepteurNrz)obj).nbEchantillon== this.nbEchantillon) &&
				(((RecepteurNrz)obj).amplitudeMax== this.amplitudeMax) &&
				(((RecepteurNrz)obj).amplitudeMin== this.amplitudeMin); 
	}
	
}
