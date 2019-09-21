package transmetteurs;

import information.InformationNonConforme;

public class EmetteurNrzt <R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{

	EmetteurNrzt(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
	}

	@Override
	public void emettre() throws InformationNonConforme {
		// TODO Auto-generated method stub
		
	}

}
