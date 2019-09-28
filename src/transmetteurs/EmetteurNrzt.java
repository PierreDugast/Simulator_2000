package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.Information;
import information.InformationNonConforme;

/**
 * 
 * @author j�r�mie
 *
 * Classe permettant d'emettre des message de type NRZT. C'est � dire des messages qui ont leur premier tier 
 * des �chantillons pour 1 symbol qui augmente jusqu'� la valeur max lin�airement puis le deuxi�me tier des
 * �chantillons reste � cette valeur puis le dernier tier diminue lin�airement jusqu'� 0 si le symbol suivant
 * est n�gatif. Pour un symbol n�gatif le premier tier diminue jusqu'� la valeur min et le dernier tier augmente
 * jusqu'� 0 si le symbol qui suit est n�gatif. Si des symbols cons�cutif sont identique la valeur entre le 
 * deuxi�me tier du premier symbol et le deuxi�me tier du dernier symbol cons�cutif la valeur reste constante. 
 */
public class EmetteurNrzt <R,T> extends ConvertisseurAnalogiqueNumerique<R,T>
{
	
	/**
	 * Constructeur permettant de cr�er un �metteur NRZT � partir du constructeur de transmetteur avec trois paramm�tres
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
	 * M�thode permettant de mettre en forme l'information re�u pour ensuite la r��mettre. La mise en forme consiste � 
	 * cr�er une information qui pour chaque symbole cr�e nbEchantillon. Le premier tier des �chantillons tend lin�airement
	 * vers la valeur amplitudeMax si le symbole est true et amplitudeMin si le symbole est false. Le deuxi�me tier est constant
	 * � la valeur amplitudeMax si le symbole est true et amplitudeMin si le symbole est false. Si le symbole suivant est diff�rent 
	 * du symbole courant le dernier tier tend vers 0 lin�airement. Si le symbole pr�c�dent est identique au courant la valeur reste 
	 * constante sur le premier tier du suivant et sur le dernier tier du courant. 
	 */
	public void emettre() throws InformationNonConforme {
		Float [] emission = new Float[this.informationRecue.nbElements()*this.nbEchantillon];
		
		int compteurEchantillon=0;
		int tier=nbEchantillon/3;
		//Boucle de mise en forme de l'information
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			int symbolCourant=compteurEchantillon;
			//Cas ou le symbole est true, que le suivant est false et que le pr�c�dent est false
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
			//Cas ou le symbole est false, que le suivant est true et que le pr�c�dent est true
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
				//Sous cas ou le pr�c�dent est true
				if(informationRecue.iemeElement(i-1).equals(true)) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						emission[compteurEchantillon] = amplitudeMax;
						compteurEchantillon++;
					}
				}
				//Sous cas ou le pr�c�dent est false
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
				//Sous cas ou le pr�c�dent est false
				if(informationRecue.iemeElement(i-1).equals(false)) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						emission[compteurEchantillon] = amplitudeMin;
						compteurEchantillon++;
					}
				}
				//Sous cas ou le pr�c�dent est true
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
		//Cr�ation de l'information � �mettre
		this.informationEmise = new Information(emission);
		
		//Envoie l'information aux diff�rentes destinations connectees pr�sente dans la variable destinationsConnectees
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
		
	}

}
