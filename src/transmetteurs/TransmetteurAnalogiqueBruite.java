package transmetteurs;
import information.Information;
import information.InformationNonConforme;

/**
 * 
 * @author PC-CHIQUET
 *Classe du transmetteur analogique bruite 
 *elle permet de generer un bruit blanc gaussien et de le transmettre vers les recepteurs 
 *analogique 
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
	
	public float calculPuissane(int nbEchantillon, Information informationRecue) {
		
	}
	
	
	public float calculSigma(float puissanceBruit, float SNR) {
		
	}
	
	public Information<Float> generationBruitBlanc (Float sigma){
		
	}
	
	public void ajoutSignalBruite (Information bruitBlanc) {
		
	}
	
	public boolean equals (Object obj) {
		return (obj instanceof TransmetteurAnalogiqueBruite); 
	}
}
