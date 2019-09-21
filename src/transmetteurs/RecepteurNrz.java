package transmetteurs;

import information.InformationNonConforme;

public class RecepteurNrz<R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{

	public RecepteurNrz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void emettre() throws InformationNonConforme {
		// TODO Auto-generated method stub
		
	}
	
}
