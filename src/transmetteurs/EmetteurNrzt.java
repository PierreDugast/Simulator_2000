package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.Information;
import information.InformationNonConforme;

public class EmetteurNrzt <R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{

	public EmetteurNrzt(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
	}

	@Override
	public void emettre() throws InformationNonConforme {
		Float [] emission = new Float[this.informationRecue.nbElements()*this.nbEchantillon];
		
		
		int c=0;
		int tier = nbEchantillon/3;
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			int j=c;
			if(informationRecue.iemeElement(i).equals(true)) {				
				while(c<(j+this.nbEchantillon)) {
					if (c<j+tier) {
						emission[c] = (c-j)*amplitudeMax/tier;
					} else if (c<j+2*tier){
						emission[c] = amplitudeMax;
					} else if (c<j+3*tier) {
						emission[c] = amplitudeMax + (j-c)*amplitudeMax/tier;
					}
					c++;
				}
			} else {
				while(c<(j+this.nbEchantillon)) {
					emission[c] = amplitudeMin;
					if (c<j+tier) {
						emission[c] = (j-c)*amplitudeMin/tier;
					} else if (c<j+2*tier){
						emission[c] = amplitudeMin;
					} else if (c<j+3*tier) {
						emission[c] = amplitudeMin + (c-j)*amplitudeMin/tier;
					}
					c++;
				}
			}
	  
		}
		this.informationEmise = new Information(emission);
		
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
		
	}

}
