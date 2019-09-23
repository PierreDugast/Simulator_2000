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
		boolean [] emission = new boolean[this.informationRecue.nbElements()/nbEchantillon];
		
		int c=0;
		int tier = nbEchantillon/3;
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			if(i%nbEchantillon/2==0) {
				if(informationRecue.iemeElement(i)>amplitudeMax/2) {
					emission[c] = true;
				}
				if(informationRecue.iemeElement(i)<amplitudeMin/2) {
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
