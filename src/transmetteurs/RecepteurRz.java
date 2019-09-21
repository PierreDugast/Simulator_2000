package transmetteurs;

import information.InformationNonConforme;

public class RecepteurRz<R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{

	public RecepteurRz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void emettre() throws InformationNonConforme {
		// TODO Auto-generated method stub
		
	}
	
}
