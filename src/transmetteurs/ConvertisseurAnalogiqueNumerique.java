package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.Information;
import information.InformationNonConforme;

public abstract class ConvertisseurAnalogiqueNumerique<R,T> extends Transmetteur<R,T>
{
	public int nbEchantillon;
	public Float amplitudeMax;
	public Float amplitudeMin;
	
	public ConvertisseurAnalogiqueNumerique(int nbEchantillon,Float amplitudeMax,Float amplitudeMin) throws AnalogicArgumentException
	{
		if (nbEchantillon <= 0)
			throw new AnalogicArgumentException("erreur : nbEchantillon<=0");
		if (amplitudeMax <= 0)
			throw new AnalogicArgumentException("erreur : amplitudeMax>=0");
		if (amplitudeMin <= 0)
			throw new AnalogicArgumentException("erreur : amplitudeMax>=0");
		if (amplitudeMax <= amplitudeMin)
			throw new AnalogicArgumentException("erreur : amplitudeMax<=amplitudeMin");
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


	public boolean equals (Object obj) {
		return (obj instanceof ConvertisseurAnalogiqueNumerique) && 
				((ConvertisseurAnalogiqueNumerique)obj.nbEchantillon== this.nbEchantillon) &&
				((ConvertisseurAnalogiqueNumerique)obj.amplitudeMax== this.amplitudeMax) &&
				((ConvertisseurAnalogiqueNumerique)obj.amplitudeMin== this.amplitudeMin); 
	}
}
	//faire pareil pour source, destination, transmetteurAnalogiquebruite et transmetteur parfait 
