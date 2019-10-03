package transmetteurs;
import information.Information;
import information.InformationNonConforme;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;

/**
 * 
 * @author PC-CHIQUET
 *Cette classe permet d emettre une onde RZ, on enovoie des valeurs a une amplitude Min pour le premier tier 
 *et le dernier tier des echantillons. Ensuite les echantillons du second tier seront a une amplitude Max 
 *l amplitude max correpond a un bit a 1 et min a 0
 * @param <R>
 * @param <T>
 */

public class EmetteurRz <R,T> extends ConvertisseurAnalogiqueNumerique<R,T> {

	public EmetteurRz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
	}
	
	public void emettre() throws InformationNonConforme {
		
		Float [] emission = new Float[this.informationRecue.nbElements()*this.nbEchantillon];
		
		int tier = (nbEchantillon / 3); 
		
		for (int i=0; i < informationRecue.nbElements(); i++) {
			for (int j=0; j < tier; j++) {
				emission[i*nbEchantillon+j] =amplitudeMin; 
				}
			if((Boolean) informationRecue.iemeElement(i)) {
				for(int j=tier; j<2*tier; j++) {
					emission[i*nbEchantillon+j]=amplitudeMax; 
				}
			}
			else {
				for (int j=tier; j<2*tier; j++) {
					emission[i*nbEchantillon+j]=amplitudeMin; 
				}
			}
			for (int j=2*tier; j<nbEchantillon; j++) {
				emission[i*nbEchantillon+j]=amplitudeMin; 
			}
			
		}
		this.informationEmise = new Information(emission);
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
	
	public boolean equals(Object o)
	{
		Boolean rep = false;
		EmetteurRz<R,T> e;
		if(o instanceof EmetteurRz)
		{
			e = (EmetteurRz<R,T>) o;
			if ((e.amplitudeMax==this.amplitudeMax)&&(e.amplitudeMin==this.amplitudeMin)&&(e.nbEchantillon==this.nbEchantillon))
				rep = true;
		}
		return rep;
	}
}