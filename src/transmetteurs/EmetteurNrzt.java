package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.Information;
import information.InformationNonConforme;

/**
 * 
 * @author jérémie
 *
 * Classe permettant d'emettre des message de type NRZT. C'est à dire des messages qui ont leur premier tier 
 * des échantillons pour 1 symbol qui augmente jusqu'à la valeur max linéairement puis le deuxième tier des
 * échantillons reste à cette valeur puis le dernier tier diminue linéairement jusqu'à 0 si le symbol suivant
 * est négatif. Pour un symbol négatif le premier tier diminue jusqu'à la valeur min et le dernier tier augmente
 * jusqu'à 0 si le symbol qui suit est négatif. Si des symbols consécutif sont identique la valeur entre le 
 * deuxième tier du premier symbol et le deuxième tier du dernier symbol consécutif la valeur reste constante. 
 */
public class EmetteurNrzt <R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{
	
	/**
	 * Constructeur permettant de créer un émetteur NRZT à partir du constructeur de transmetteur avec trois parammètres
	 * @param nbEchantillon
	 * @param amplitudeMax
	 * @param amplitudeMin
	 * @throws AnalogicArgumentException
	 */
	public EmetteurNrzt(int nbEchantillon, Float amplitudeMax, Float amplitudeMin) throws AnalogicArgumentException 
	{
		super(nbEchantillon, amplitudeMax, amplitudeMin);
	}

	/**
	 * Méthode permettant de mettre en forme l'information reçu pour ensuite la réémettre. La mise en forme consiste à 
	 * créer une information qui pour chaque symbole crée nbEchantillon. Le premier tier des échantillons tend linéairement
	 * vers la valeur amplitudeMax si le symbole est true et amplitudeMin si le symbole est false. Le deuxième tier est constant
	 * à la valeur amplitudeMax si le symbole est true et amplitudeMin si le symbole est false. Si le symbole suivant est différent 
	 * du symbole courant le dernier tier tend vers 0 linéairement. Si le symbole précèdent est identique au courant la valeur reste 
	 * constante sur le premier tier du suivant et sur le dernier tier du courant. 
	 */
	public void emettre() throws InformationNonConforme {
		Float [] emission = new Float[this.informationRecue.nbElements()*this.nbEchantillon];
		
		int compteurEchantillon=0;
		int tier=nbEchantillon/3;
		//Boucle de mise en forme de l'information
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			int symbolCourant=compteurEchantillon;
			//Cas ou le symbole est true, que le suivant est false et que le précèdent est false
			if(informationRecue.iemeElement(i).equals(true) && informationRecue.iemeElement(i+1).equals(false) && informationRecue.iemeElement(i-1).equals(false) ) {				
				while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
					if (compteurEchantillon<symbolCourant+tier) {
						emission[compteurEchantillon] = (compteurEchantillon-symbolCourant)*amplitudeMax/tier;
					} else if (compteurEchantillon<symbolCourant+2*tier){
						emission[compteurEchantillon] = amplitudeMax;
					} else if (compteurEchantillon<symbolCourant+3*tier) {
						emission[compteurEchantillon] = amplitudeMax + ((symbolCourant+nbEchantillon)-compteurEchantillon)*amplitudeMax/tier;
					}
					compteurEchantillon++;
				}
			}
			//Cas ou le symbole est false, que le suivant est true et que le précèdent est true
			if(informationRecue.iemeElement(i).equals(false) && informationRecue.iemeElement(i+1).equals(true) && informationRecue.iemeElement(i-1).equals(true)) {
				while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
					emission[compteurEchantillon] = amplitudeMin;
					if (compteurEchantillon<symbolCourant+tier) {
						emission[compteurEchantillon] = (compteurEchantillon-symbolCourant)*amplitudeMin/tier;
					} else if (compteurEchantillon<symbolCourant+2*tier){
						emission[compteurEchantillon] = amplitudeMin;
					} else if (compteurEchantillon<symbolCourant+3*tier) {
						emission[compteurEchantillon] = amplitudeMin + ((symbolCourant+nbEchantillon)-compteurEchantillon)*amplitudeMin/tier;
					}
					compteurEchantillon++;
				}
			}
			//Cas ou le symbole est true que le suivant est true
			if(informationRecue.iemeElement(i).equals(true) && informationRecue.iemeElement(i+1).equals(true)) {
				//Sous cas ou le précèdent est true
				if(informationRecue.iemeElement(i-1).equals(true)) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						emission[compteurEchantillon] = amplitudeMax;
						compteurEchantillon++;
					}
				}
				//Sous cas ou le précèdent est false
				if(informationRecue.iemeElement(i-1).equals(false)) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						if (compteurEchantillon<symbolCourant+tier) {
							emission[compteurEchantillon] = (compteurEchantillon-symbolCourant)*amplitudeMax/tier;
						} else if (compteurEchantillon<symbolCourant+3*tier){
							emission[compteurEchantillon] = amplitudeMax;
						}
					}
				}
			}
			//Cas ou le symbole est false, le suivant est false
			if(informationRecue.iemeElement(i).equals(false) && informationRecue.iemeElement(i+1).equals(false)) {
				//Sous cas ou le précèdent est false
				if(informationRecue.iemeElement(i-1).equals(false)) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						emission[compteurEchantillon] = amplitudeMin;
						compteurEchantillon++;
					}
				}
				//Sous cas ou le précèdent est true
				if(informationRecue.iemeElement(i-1).equals(true)) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						if (compteurEchantillon<symbolCourant+tier) {
							emission[compteurEchantillon] = ((symbolCourant+nbEchantillon)-compteurEchantillon)*amplitudeMin/tier;
						} else if (compteurEchantillon<symbolCourant+3*tier){
							emission[compteurEchantillon] = amplitudeMin;
						}
					}
				}
			}
		}
		//Création de l'information à émettre
		this.informationEmise = new Information(emission);
		
		//Envoie l'information aux différentes destinations connectees présente dans la variable destinationsConnectees
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
		
	}

}
