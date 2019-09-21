package transmetteurs;

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
		// TODO Auto-generated method stub	
	}
	
}
