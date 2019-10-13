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

public class EmetteurRz extends ConvertisseurAnalogiqueNumerique<Boolean, Float> {

	public EmetteurRz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		informationEmise = new Information<Float>();
	}
	
	public void emettre() throws InformationNonConforme {
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon=tier*3;
		//Float [] emission = new Float[this.informationRecue.nbElements()*this.nbEchantillon];
		
		
		for (int i=0; i < informationRecue.nbElements(); i++) {
			for (int j=0; j < tier; j++) {
				//emission[i*nbEchantillon+j] =(float) 0;
				informationEmise.add(0.0f);
				}
			if((Boolean) informationRecue.iemeElement(i)) {
				for(int j=tier; j<2*tier; j++) {
					informationEmise.add(amplitudeMax);
					//emission[i*nbEchantillon+j]=amplitudeMax; 
				}
			}
			else {
				for (int j=tier; j<2*tier; j++) {
					informationEmise.add(amplitudeMin);
					//emission[i*nbEchantillon+j]=amplitudeMin; 
				}
			}
			for (int j=2*tier; j<nbEchantillon; j++) {
				informationEmise.add((float) 0);
				//emission[i*nbEchantillon+j]=(float) 0; 
			}
			
		}
		//this.informationEmise = new Information(emission);
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
	/*
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
	*/
}