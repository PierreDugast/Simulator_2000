package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.Information;
import information.InformationNonConforme;

/**
 * 
 * @author J�r�mie
 *
 * Classe permettant d'emettre des message de type NRZT. C'est � dire des messages qui ont leur premier tier 
 * des �chantillons pour 1 symbole qui augmente jusqu'� la valeur max lin�airement puis le deuxi�me tier des
 * �chantillons reste � cette valeur puis le dernier tier diminue lin�airement jusqu'� 0 si le symbol suivant
 * est n�gatif. Pour un symbole n�gatif le premier tier diminue jusqu'� la valeur min et le dernier tier augmente
 * jusqu'� 0 si le symbol qui suit est positif. Si des symboles cons�cutif sont identique la valeur entre le 
 * deuxi�me tier du premier symbol et le deuxi�me tier du dernier symbol cons�cutif la valeur reste constante. 
 */
public class EmetteurNrzt extends ConvertisseurAnalogiqueNumerique<Boolean,Float>
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
		informationEmise = new Information<Float>();
	}

	/**
	 * M�thode permettant de mettre en forme l'information re�u pour ensuite la r�mettre. La mise en forme consiste � 
	 * cr�er une information qui pour chaque symbole cr�e nbEchantillon. Le premier tier des �chantillons tend lin�airement
	 * vers la valeur amplitudeMax si le symbole est true et amplitudeMin si le symbole est false. Le deuxi�me tier est constant
	 * � la valeur amplitudeMax si le symbole est true et amplitudeMin si le symbole est false. Si le symbole suivant est diff�rent 
	 * du symbole courant le dernier tier tend vers 0 lin�airement. Si le symbole pr�c�dent est identique au courant la valeur reste 
	 * constante sur le premier tier du suivant et sur le dernier tier du symbole courant. 
	 */
	public void emettre() throws InformationNonConforme {
		//R�cup�re la partie enti�re de la division par 3 du nombre d'�chantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon=tier*3;
		int compteurEchantillon=0;		
		//Boucle de mise en forme de l'information
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			int symbolCourant=compteurEchantillon;
			//Ici on ne s'int�resse qu'aux informations ne contenant que 1 symbole
			if(i==0 && informationRecue.nbElements()==1) {
				//Cas ou le symbole est true
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("true")) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						if (compteurEchantillon<symbolCourant+tier) {
							informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMax/tier);
						} else if (compteurEchantillon<symbolCourant+2*tier){
							informationEmise.add(amplitudeMax);
						} else if (compteurEchantillon<symbolCourant+3*tier) {
							informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMax/tier);
						}
						compteurEchantillon++;
					}
				}
				//Cas ou le symbole est false
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("false")) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						if (compteurEchantillon<symbolCourant+tier) {
							informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMin/tier);
						} else if (compteurEchantillon<symbolCourant+2*tier){
							informationEmise.add(amplitudeMin);
						} else if (compteurEchantillon<symbolCourant+3*tier) {
							informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMin/tier);
						}
						compteurEchantillon++;
					}
				}
			}
			//Ici on ne s'int�resse qu'aux symboles entre le premier et le dernier exclu
			if(i>=1 && i<(informationRecue.nbElements()-1)) {
				//Cas ou le symbole est true
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("true")) {
					//Cas ou le symbole est true et le suivant false
					if(informationRecue.iemeElement(i+1).toString().equalsIgnoreCase("false")) {
						//cas ou le symbole est true, le suivant est false et le pr�c�dent est false
						if( informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("false")) {
							while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
								if (compteurEchantillon<symbolCourant+tier) {
									informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMax/tier);
								} else if (compteurEchantillon<symbolCourant+2*tier){
									informationEmise.add(amplitudeMax);
								} else if (compteurEchantillon<symbolCourant+3*tier) {
									informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMax/tier);
								}
								compteurEchantillon++;
							}
						}
						//cas ou le symbole est true, le suivant est false et le pr�c�dent est true
						if( informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("true")) {
							while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
								if (compteurEchantillon<symbolCourant+2*tier){
									informationEmise.add(amplitudeMax);
								} else if (compteurEchantillon<symbolCourant+3*tier) {
									informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMax/tier);
								}
								compteurEchantillon++;
							}
						}
					}
					//cas ou le symbole est true, le suivant est true
					if(informationRecue.iemeElement(i+1).toString().equalsIgnoreCase("true")) {
						//cas ou le symbole est true, le suivant est true et le pr�c�dent est false
						if( informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("false")) {
							while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
								if (compteurEchantillon<symbolCourant+tier) {
									informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMax/tier);
								} else if (compteurEchantillon<symbolCourant+3*tier){
									informationEmise.add(amplitudeMax);
								}
								compteurEchantillon++;
							}
						}
						//cas ou le symbole est true, le suivant est true et le pr�c�dent est true
						if( informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("true")) {
							while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
								informationEmise.add(amplitudeMax);
								compteurEchantillon++;
							}
						}
						
					}
				}
				//cas ou le symbole est false
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("false")) {
					//Cas ou le symbole est false et le suivant est true
					if(informationRecue.iemeElement(i+1).toString().equalsIgnoreCase("true")) {
						//Cas ou le symbole est false, le suivant est true et le pr�c�dent est true
						if( informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("true")) {
							while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
								if (compteurEchantillon<symbolCourant+tier) {
									informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMin/tier);
								} else if (compteurEchantillon<symbolCourant+2*tier){
									informationEmise.add(amplitudeMin);
								} else if (compteurEchantillon<symbolCourant+3*tier) {
									informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMin/tier);
								}
								compteurEchantillon++;
							}
						}
						//Cas ou le symbole est false, le suivant est true et le pr�c�dent est false
						if( informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("false")) {
							while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
								if (compteurEchantillon<symbolCourant+2*tier){
									informationEmise.add(amplitudeMin);
								} else if (compteurEchantillon<symbolCourant+3*tier) {
									informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMin/tier);
								}
								compteurEchantillon++;
							}
						}
					}
					//Cas ou le symbole est false, le suivant est false
					if(informationRecue.iemeElement(i+1).toString().equalsIgnoreCase("false")) {
						//Cas ou le symbole est false, le suivant est false et le pr�c�dent est true
						if( informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("true")) {
							while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
								if (compteurEchantillon<symbolCourant+tier) {
									informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMin/tier);
								} else if (compteurEchantillon<symbolCourant+3*tier){
									informationEmise.add(amplitudeMin);
								}
								compteurEchantillon++;
							}
						}
						//Cas ou le symbole est false, le suivant est false et le pr�c�dent est false
						if( informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("false")) {
							while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
								informationEmise.add(amplitudeMin);
								compteurEchantillon++;
							}
						}
						
					}
					
				}
			} else if(i==0 && informationRecue.nbElements()>1) {
				//Ici on ne s'int�resse qu'au premier symbole
				//Cas ou le symbole est true et le suivant est false 
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("true") && informationRecue.iemeElement(i+1).toString().equalsIgnoreCase("false")) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						if (compteurEchantillon<symbolCourant+tier) {
							informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMax/tier);
						} else if (compteurEchantillon<symbolCourant+2*tier){
							informationEmise.add(amplitudeMax);
						} else if (compteurEchantillon<symbolCourant+3*tier) {
							informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMax/tier);
						}
						compteurEchantillon++;
					}
				}
				//Cas ou le symbole est true et le suivant est true
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("true") && informationRecue.iemeElement(i+1).toString().equalsIgnoreCase("true")) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						if (compteurEchantillon<symbolCourant+tier) {
							informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMax/tier);
						} else if (compteurEchantillon<symbolCourant+3*tier){
							informationEmise.add(amplitudeMax);
						}
						compteurEchantillon++;
					}
				}
				//Cas ou le symbole est false et le suivant est true
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("false") && informationRecue.iemeElement(i+1).toString().equalsIgnoreCase("true")) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						if (compteurEchantillon<symbolCourant+tier) {
							informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMin/tier);
						} else if (compteurEchantillon<symbolCourant+2*tier){
							informationEmise.add(amplitudeMin);
						} else if (compteurEchantillon<symbolCourant+3*tier) {
							informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMin/tier);
						}
						compteurEchantillon++;
					}
				}
				//Cas ou le symbole est false et le suivant est false
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("false") && informationRecue.iemeElement(i+1).toString().equalsIgnoreCase("false") ) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						if (compteurEchantillon<symbolCourant+tier) {
							informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMin/tier);
						} else if (compteurEchantillon<symbolCourant+3*tier){
							informationEmise.add(amplitudeMin);
						}
						compteurEchantillon++;
					}
				}
			//Ici on ne s'int�resse qu'au dernier symbole
			} else if (i==(informationRecue.nbElements()-1) && informationRecue.nbElements()>1) {
				//Cas ou le symbole est true et le pr�c�dent est false
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("true") && informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("false")) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						if (compteurEchantillon<symbolCourant+tier) {
							informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMax/tier);
						} else if (compteurEchantillon<symbolCourant+2*tier){
							informationEmise.add(amplitudeMax);
						} else if (compteurEchantillon<symbolCourant+3*tier) {
							informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMax/tier);
						}
						compteurEchantillon++;
					}
				}
				//Cas ou le symbole est true et le pr�c�dent est true
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("true") && informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("true")) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						if (compteurEchantillon<symbolCourant+2*tier){
							informationEmise.add(amplitudeMax);
						} else if (compteurEchantillon<symbolCourant+3*tier) {
							informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMax/tier);
						}
						compteurEchantillon++;
					}
				}
				//Cas ou le symbole est false et le pr�c�dent est true
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("false") && informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("true") ) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						//informationEmise.add(amplitudeMin);
						if (compteurEchantillon<symbolCourant+tier) {
							informationEmise.add((compteurEchantillon-symbolCourant)*amplitudeMin/tier);
						} else if (compteurEchantillon<symbolCourant+2*tier){
							informationEmise.add(amplitudeMin);
						} else if (compteurEchantillon<symbolCourant+3*tier) {
							informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMin/tier);
						}
						compteurEchantillon++;
					}
				}
				//Cas ou le symbole est false et le pr�c�dent est false
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("false") && informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("false")) {
					while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
						//informationEmise.add(amplitudeMin);
						if (compteurEchantillon<symbolCourant+2*tier){
							informationEmise.add(amplitudeMin);
						} else if (compteurEchantillon<symbolCourant+3*tier) {
							informationEmise.add((symbolCourant+nbEchantillon-compteurEchantillon)*amplitudeMin/tier);
						}
						compteurEchantillon++;
					}
				}
			}
		}
		//Envoie l'information aux diff�rentes destinations connect�es pr�sente dans la variable destinationsConnectees
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}		
	}	
	/*
	//Equals de Guigui
	public boolean equals(Object o)
	{
		Boolean rep = false;
		EmetteurNrzt<R,T> e;
		if(o instanceof EmetteurNrzt)
		{
			e = (EmetteurNrzt<R,T>) o;
			if ((e.amplitudeMax==this.amplitudeMax)&&(e.amplitudeMin==this.amplitudeMin)&&(e.nbEchantillon==this.nbEchantillon))
				rep = true;
		}
		return rep;
	}
	*/

}
