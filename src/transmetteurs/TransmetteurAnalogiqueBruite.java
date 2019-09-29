package transmetteurs;
import information.Information;
import information.InformationNonConforme;
import java.util.*;
import java.lang.*;

/**
 * 
 * @author PC-CHIQUET
 *Classe du transmetteur analogique bruite 
 *elle permet de generer un bruit blanc gaussien et de le transmettre vers les recepteurs 
 *analogique 
 *Cette classe contient differentes methodes qui vont nous permettre de generer du 
 *du bruit blanc gaussien
 * @param <R>
 * @param <T>
 */

public class TransmetteurAnalogiqueBruite <R,T> extends Transmetteur<R,T> {
	
	public int nbEchantillon; 
	public float SNR; 
	
	public TransmetteurAnalogiqueBruite(int nbEchantillon, float SNR) {
		super (); 
		this.nbEchantillon = nbEchantillon; 
		this.SNR = SNR; 
		
	}
	
	public void recevoir (Information <R> information) throws InformationNonConforme {
		informationRecue=information; 
		emettre(); 
	}
	public void emettre() throws InformationNonConforme {
		
	}
	
	/**
	 * Cette methode permet de calculer la puissance 
	 * Puissance = somme des echantillons au carre / nombre d echantillons 
	 * @param nbEchantillon
	 * @param informationRecue
	 * @return
	 */
	public float calculPuissance(int nbEchantillon, Information informationRecue) {
		float calculPuissance = 0; 
		float carreValeurs;
		int sum = 0;
		
		//parcours l information recue pour recuperer chaque valeur
		for (int i=0; i<informationRecue.nbElements(); i++) {
			float Valeurs = (float) informationRecue.iemeElement(i); 
			//met au carre chaque valeur de l information recue
			carreValeurs = Valeurs * Valeurs; 
			//somme des carres des valeurs
			sum += carreValeurs; 
		}
		//calcul de la puissance 
		calculPuissance = sum / informationRecue.nbElements();
		return calculPuissance; 

	}
	
	public float calculSigma(float puissanceSignal, float SNR) {
		float sigma = 0;
		//calcul la valeur de sigma en fonction de la puissance du signal et du SNR
		sigma = (float) Math.sqrt(puissanceSignal/Math.pow(10, (SNR/10)));
		return sigma;
	}
	
	public Information<Float> generationBruitBlanc (Float sigma)
	{
		Float [] bruitBlanc = new Float[this.informationRecue.nbElements()];
		Random r = new Random();
		int i = 0;
		while(i<this.informationRecue.nbElements())
		{
			Double a1 = r.nextGaussian();
			Double a2 = r.nextGaussian();
			Double valueI;
			valueI = sigma*Math.sqrt(-2*Math.log(1-a1))*Math.cos(2*Math.PI*a2);
			bruitBlanc[i] = valueI.floatValue();
			i++;
		}
		
		Information<Float> generationBruitBlanc = new Information<Float>(bruitBlanc);		
		return generationBruitBlanc;
	}
	
	public void ajoutSignalBruite (Information<Float> bruitBlanc) {
		
	}
	
	public boolean equals (Object obj) {
		return (obj instanceof TransmetteurAnalogiqueBruite); 
	}
	  
}
