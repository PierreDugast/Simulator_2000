package transmetteurs;

import ExceptionsGlobales.AnalogicArgumentException;
import information.Information;
import information.InformationNonConforme;

/**
 * 
 * @author Jérémie
 *
 * Classe permettant d'emettre des message de type NRZT. C'est ï¿½ dire des messages qui ont leur premier tier 
 * des échantillons pour 1 symbole qui augmente jusqu'à la valeur max linï¿½airement puis le deuxième tier des
 * échantillons reste à cette valeur puis le dernier tier diminue linéairement jusqu'à 0 si le symbol suivant
 * est négatif. Pour un symbole négatif le premier tier diminue jusqu'à la valeur min et le dernier tier augmente
 * jusqu'à 0 si le symbol qui suit est positif. Si des symboles consécutif sont identique la valeur entre le 
 * deuxième tier du premier symbol et le deuxième tier du dernier symbol consécutif la valeur reste constante. 
 */
public class EmetteurNrzt extends ConvertisseurAnalogiqueNumerique<Boolean,Float>
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
		informationEmise = new Information<Float>();
	}

	/**
	 * Méthode permettant de mettre en forme l'information reçu pour ensuite la rémettre. La mise en forme consiste à 
	 * créer une information qui pour chaque symbole crée nbEchantillon. Le premier tier des échantillons tend linéairement
	 * vers la valeur amplitudeMax si le symbole est true et amplitudeMin si le symbole est false. Le deuxième tier est constant
	 * à la valeur amplitudeMax si le symbole est true et amplitudeMin si le symbole est false. Si le symbole suivant est différent 
	 * du symbole courant le dernier tier tend vers 0 linéairement. Si le symbole précèdent est identique au courant la valeur reste 
	 * constante sur le premier tier du suivant et sur le dernier tier du symbole courant. 
	 */
	public void emettre() throws InformationNonConforme {
		//Récupère la partie entière de la division par 3 du nombre d'échantillon
		int tier = (int) Math.floor(nbEchantillon/3);
		nbEchantillon=tier*3;
		int compteurEchantillon=0;		
		//Boucle de mise en forme de l'information
		for(int i=0 ; i<informationRecue.nbElements() ; i++) {
			int symbolCourant=compteurEchantillon;
			//Ici on ne s'intéresse qu'aux informations ne contenant que 1 symbole
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
			//Ici on ne s'intéresse qu'aux symboles entre le premier et le dernier exclu
			if(i>=1 && i<(informationRecue.nbElements()-1)) {
				//Cas ou le symbole est true
				if(informationRecue.iemeElement(i).toString().equalsIgnoreCase("true")) {
					//Cas ou le symbole est true et le suivant false
					if(informationRecue.iemeElement(i+1).toString().equalsIgnoreCase("false")) {
						//cas ou le symbole est true, le suivant est false et le précèdent est false
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
						//cas ou le symbole est true, le suivant est false et le précèdent est true
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
						//cas ou le symbole est true, le suivant est true et le précèdent est false
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
						//cas ou le symbole est true, le suivant est true et le précèdent est true
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
						//Cas ou le symbole est false, le suivant est true et le précèdent est true
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
						//Cas ou le symbole est false, le suivant est true et le précèdent est false
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
						//Cas ou le symbole est false, le suivant est false et le précèdent est true
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
						//Cas ou le symbole est false, le suivant est false et le précèdent est false
						if( informationRecue.iemeElement(i-1).toString().equalsIgnoreCase("false")) {
							while(compteurEchantillon<(symbolCourant+this.nbEchantillon)) {
								informationEmise.add(amplitudeMin);
								compteurEchantillon++;
							}
						}
						
					}
					
				}
			} else if(i==0 && informationRecue.nbElements()>1) {
				//Ici on ne s'intéresse qu'au premier symbole
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
			//Ici on ne s'intéresse qu'au dernier symbole
			} else if (i==(informationRecue.nbElements()-1) && informationRecue.nbElements()>1) {
				//Cas ou le symbole est true et le précèdent est false
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
				//Cas ou le symbole est true et le précèdent est true
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
				//Cas ou le symbole est false et le précèdent est true
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
				//Cas ou le symbole est false et le précèdent est false
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
		//Envoie l'information aux différentes destinations connectées présente dans la variable destinationsConnectees
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
