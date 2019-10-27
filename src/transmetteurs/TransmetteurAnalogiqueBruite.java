package transmetteurs;
import information.Information;
import information.InformationNonConforme;
import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * 
 * @author j�r�mie
 * 
 * Classe permettant de cr�er un objet TransmetteurAnalogiqueBruite.
 * Elle permet de generer un bruit blanc gaussien et de l'ajouter � l'informationEmise pour 
 * simuler le passage de l'information dans un canal bruit�.
 * Cette classe contient differentes m�thodes qui vont nous permettre de g�n�rer du 
 * du bruit blanc gaussien. 
 * 
 * @param <R>
 * @param <T>
 */
public class TransmetteurAnalogiqueBruite extends Transmetteur<Float,Float> {
	
	public int nbEchantillon; 
	public float SNR; 
	
	/**
	 * Constructeur d'un transmetteurAnalogiqueBruite utilisant le constructeur d'un transmetteur � 
	 * deux param�tres. Il initialise aussi nbEchantillon et SNR avec les param�tres du constructeur
	 * et l'informationEmise avec le constructeur d'Information contenant des Float.
	 * 
	 * @param nbEchantillon
	 * @param SNR
	 */
	public TransmetteurAnalogiqueBruite(int nbEchantillon, float SNR) {
		super (); 
		this.nbEchantillon = nbEchantillon; 
		this.SNR = SNR;
		informationEmise=new Information<Float>();
	}
	
	/**
	 * M�thode permettant de recevoir l'information. 
	 */
	public void recevoir (Information <Float> information) throws InformationNonConforme {
		informationRecue=information; 
		emettre(); 
	}
	
	/**
	 * M�thode permettant d'�mettre l'informationEmise qui � �tait modifier par le bruit apr�s avoir
	 * calculer la puissance du signal puis d'avoir g�n�rer le bruit et l'ajouter � l'information �mise.
	 */
	public void emettre() throws InformationNonConforme 
	{
		float puissanceSignal = this.calculPuissance(this.nbEchantillon, this.informationRecue);	
		float sigma = this.calculSigma(puissanceSignal, this.SNR);	
		generationSignalEtBruitBlanc(sigma);
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
		
	}
	
	/**
	 * Cette methode permet de calculer la puissance du signal
	 * Puissance = somme des echantillons au carre / nombre d echantillons 
	 * 
	 * @param nbEchantillon
	 * @param informationRecue
	 * @return
	 */
	private float calculPuissance(int nbEchantillon, Information informationRecue) {
		float calculPuissance = 0; 
		float carreValeurs;
		int sum = 0;
		
		//parcours l information recue pour recuperer chaque valeur des �chantillons pour l'�lever au carr�.
		for (int i=0; i<informationRecue.nbElements(); i++) {
			float Valeurs = (float) informationRecue.iemeElement(i); 
			//met au carre chaque valeur de l information recue
			carreValeurs = Valeurs * Valeurs; 
			//somme des carres des valeurs
			sum += carreValeurs; 
		}

		// Calcul de la puissance du l'informationRecue en divisant la somme des carr� obtenu dans la boucle pr�c�dente
		// par le nombre d'�l�ment de l'informationRecue.
		calculPuissance = (float) sum / informationRecue.nbElements();
		return calculPuissance; 

	}
	
	/**
	 * M�thode permettant de calculer le sigma qui va �tre ensuite utilis� pour g�n�rer le bruit.
	 * 
	 * @param puissanceSignal
	 * @param SNR
	 * @return
	 */
	private float calculSigma(float puissanceSignal, float SNR) {
		float sigma = 0;
		//calcul la valeur de sigma en fonction de la puissance du signal et du SNR
		sigma = (float) Math.sqrt(nbEchantillon*1/2*puissanceSignal/Math.pow(10, (SNR/10)));
		System.out.println("Sigma_BBG : "+sigma);
		return sigma;
	}
	
	/**
	 * M�thode permettant de g�n�rer l'informationEmise en ajoutant � chaque �chantillon de l'informationEmise
	 * le bruit calcul� al�atoirement � chaque fois.
	 * 
	 * @param sigma
	 */
	private void generationSignalEtBruitBlanc (Float sigma)
	{
		Random r = new Random();
		// Ajoute du bruit � chaque �chantillon de l'informationRecue, l'�chantillon ainsi obtenu est alors ajout� � 
		// informationEmise
		for(Float elementInformationRecue : informationRecue) 
		{
			Double valueI;
			Float a1 = r.nextFloat();
			Float a2 = r.nextFloat();
			valueI = sigma*Math.sqrt(-2*Math.log(1-a1))*Math.cos(2*Math.PI*a2);
			informationEmise.add(valueI.floatValue() + elementInformationRecue);
		}
	}	  
}
