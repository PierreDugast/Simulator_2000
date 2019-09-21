package transmetteurs;

import information.InformationNonConforme;

public class RecepteurNrzt<R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{

	RecepteurNrzt(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void emettre() throws InformationNonConforme {
		// TODO Auto-generated method stub
		
	}
	
}
