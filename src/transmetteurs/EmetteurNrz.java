package transmetteurs;
import information.Information;
import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;

public class EmetteurNrz <R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{

	public EmetteurNrz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);		
	}

	@Override
	public void emettre() throws InformationNonConforme 
	{
		Float [] emission = new Float[this.informationRecue.nbElements()*this.nbEchantillon];
		
		
		int c=0;
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			if(informationRecue.iemeElement(i).equals(true)) {
				//for(c; c<this.nbEchantillon; c++)
				
				while(c<(c+this.nbEchantillon)) {
					emission[c] = amplitudeMax;
					c++;
				}
			} else {
				while(c<(c+this.nbEchantillon)) {
					emission[c] = amplitudeMin;
					c++;
				}
			}
	  
		}
		this.informationEmise = new Information(emission);
		
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
		
	}
	
}
