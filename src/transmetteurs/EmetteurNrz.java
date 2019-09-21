package transmetteurs;

import information.InformationNonConforme;

public class EmetteurNrz <R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{

	EmetteurNrz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);		
	}

	@Override
	public void emettre() throws InformationNonConforme 
	{
		// TODO Auto-generated method stub	
	}
	
}
