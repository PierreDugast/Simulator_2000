package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;
import information.Information;


public class RecepteurNrz<R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{

	public RecepteurNrz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		// TODO Auto-generated constructor stub
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
				if (this.informationRecue.iemeElement(nbPacket*this.nbEchantillon+i)==this.amplitudeMax)
				{
					//Assigner la valeur dans la liste de sortie
					emission[nbPacket] = true;
			
					
			
				}
				else if (this.informationRecue.iemeElement(nbPacket*this.nbEchantillon+i)==this.amplitudeMin) {
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
