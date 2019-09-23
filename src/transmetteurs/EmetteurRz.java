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
//test guigui
public class EmetteurRz <R,T> extends ConvertisseurAnalogiqueNumerique<R,T> {

	public EmetteurRz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
	}
	
	public void emettre() throws InformationNonConforme {
		
		int nbMesure = nbEchantillon * informationRecue.nbElements();
		Float[] infoAnalogique = new Float[nbMesure];
		int tier = (nbEchantillon / 3); 
		
		for (int i=0; i < informationRecue.nbElements(); i++) {
			for (int j=0; j < tier; j++) {
				infoAnalogique[i*nbEchantillon+j] =amplitudeMin; 
				}
			if((boolean) informationRecue.iemeElement(i)) {
				for(int j=tier; j<2*tier; j++) {
					infoAnalogique[j*nbEchantillon+j]=amplitudeMax; 
				}
			}
			else {
				for (int j=tier; j<2*tier; j++) {
					infoAnalogique[i*nbEchantillon+j]=amplitudeMin; 
				}
			}
			for (int j=2*tier; j<nbEchantillon; j++) {
				infoAnalogique[i*nbEchantillon+j]=amplitudeMin; 
			}
			
	}
		this.informationEmise = new Information(infoAnalogique); 
		for (int i=0; i< destinationsConnectees.size(); i++) {
			destinationsConnectees.get(i).recevoir(informationEmise); 
		}
	}
}
