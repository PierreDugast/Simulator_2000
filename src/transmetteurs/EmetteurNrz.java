package transmetteurs;
import information.Information;
import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;

public class EmetteurNrz extends ConvertisseurAnalogiqueNumerique<Boolean,Float>
{

	public EmetteurNrz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		informationEmise = new Information<Float>();
	}

	public void emettre() throws InformationNonConforme 
	{
		int c=0;
		
		for(boolean bool : informationRecue) {
			int j = c;
			if(bool) {				
				while(c<(j+nbEchantillon)) {
					informationEmise.add(amplitudeMax);
					c++;
				}
			} else {
				while(c<(j+this.nbEchantillon)) {
					informationEmise.add(amplitudeMin);
					c++;
				}
			}
	  
		}
		
		for(int j=0;j<destinationsConnectees.size();j++)
		{
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
		
	}
	/*
	public boolean equals(Object o)
	{
		Boolean rep = false;
		EmetteurNrz e;
		if(o instanceof EmetteurNrz)
		{
			e = (EmetteurNrz) o;
			if ((e.amplitudeMax==this.amplitudeMax)&&(e.amplitudeMin==this.amplitudeMin)&&(e.nbEchantillon==this.nbEchantillon))
				rep = true;
		}
		
		return rep;
	}
	*/
}
