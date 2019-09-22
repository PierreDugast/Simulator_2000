package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.InformationNonConforme;
import information.Information;
import java.util.LinkedList;
import java.util.List;


/**
 * 
 * @author PC-CHIQUET
 *Cette classe permet de decoder le cas d une onde impulsionnelle de type RZ 
 *dans l intervalle [(nbBitDecode+0.5)*nbEchantillon-marge; (nbBitDecode+0.5)*nbEchantillon+marge]
 *si il y a une valeur qui depasse le seuil alors on a un true 1, si aucune valeur alors on a false 0
 * @param <R>
 * @param <T>
 */
public class RecepteurRz<R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{
	public RecepteurRz(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
		
	}
	
	public void emettre() throws InformationNonConforme {
		//Le seuil sera egal a la difference entre les deux amplitudes max et min divise par 2 
		Float seuil = (amplitudeMax - amplitudeMin) / ((float)2); 
		//on cree une liste pour connaitre le nombre de bit envoye
		//un true renvoiera 1 et un false 0
		List<Boolean> info = new LinkedList<Boolean>();
		//on va stocker dans la variable nbBitDecode le nombre de bit decode 
		int nbBitDecode = 0;
		//la variable marge va nous permettre de definir l intervalle sur laquelle nous allons 
		//regarder si le bit est a 1 ou 0
		int marge = nbEchantillon / 3;
		//permet de savoir si un bit a ete decode dans l intervalle ou non
		Boolean decode = false;
		int i = (nbEchantillon/2)-marge;
		
		
		while (i < informationRecue.nbElements()) {
			while (i < (((nbBitDecode+0.5) * nbEchantillon) + marge)) {
				if(this.informationRecue.iemeElement(i) > seuil){
					info.add(true);
					decode=true;
					break;
				}
				i++;
			}
			//ici dans aucune valeur ne depasse le seuil, nous aurons donc 0
			if(!decode){ 
				info.add(false);
			}
			decode=false;
			nbBitDecode++;
			i = (int) (((nbBitDecode + 0.5) * nbEchantillon) - marge);
	}
		Boolean[] infoRecu = new Boolean[info.size()];
		infoRecu = (Boolean[]) info.toArray(infoRecu);
		Information<Boolean> infoEnvoye = new Information<Boolean>(infoRecu);
		for(int j=0;i<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(informationEmise);
		}
}
}