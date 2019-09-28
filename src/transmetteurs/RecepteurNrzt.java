package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.Information;
import information.InformationNonConforme;

public class RecepteurNrzt<R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{

	public RecepteurNrzt(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void emettre() throws InformationNonConforme {
		Boolean [] emission;
		if (nbEchantillon%2==0) {
			emission = new Boolean[this.informationRecue.nbElements()/nbEchantillon];
		} else {
			emission = new Boolean[this.informationRecue.nbElements()/(nbEchantillon+1)];
		}
		
		
		int c=0;
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
			}
		}
		
		this.informationEmise = new Information(emission);
		
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}		
	}
	
}
