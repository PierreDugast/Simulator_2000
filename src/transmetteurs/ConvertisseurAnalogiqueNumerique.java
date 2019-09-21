package transmetteurs;

import information.Information;
import information.InformationNonConforme;

public abstract class ConvertisseurAnalogiqueNumerique<R,T> extends Transmetteur<R,T>
{
	protected int nbEchantillon;
	protected Float amplitudeMax;
	protected Float amplitudeMin;
	
	public ConvertisseurAnalogiqueNumerique(int nbEchantillon,Float amplitudeMax,Float amplitudeMin)
	{
		this.nbEchantillon = nbEchantillon;
		this.amplitudeMax = amplitudeMax;
		this.amplitudeMin = amplitudeMin;
	}
	
	public void recevoir(Information<R> information) throws InformationNonConforme
	{
		informationRecue=information;
		emettre();
	}
	
	public abstract void emettre() throws InformationNonConforme;
}
