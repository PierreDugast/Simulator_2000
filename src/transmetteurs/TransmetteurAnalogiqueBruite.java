package transmetteurs;
import information.Information;
import information.InformationNonConforme;
import java.util.*;
import java.lang.*;
import java.io.*;

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

public class TransmetteurAnalogiqueBruite extends Transmetteur<Float,Float> {
	
	public int nbEchantillon; 
	public float SNR; 
	
	public TransmetteurAnalogiqueBruite(int nbEchantillon, float SNR) {
		super (); 
		this.nbEchantillon = nbEchantillon; 
		this.SNR = SNR;
		informationEmise=new Information<Float>();
	}
	
	public void recevoir (Information <Float> information) throws InformationNonConforme {
		informationRecue=information; 
		emettre(); 
	}
	
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
	 * Cette methode permet de calculer la puissance 
	 * Puissance = somme des echantillons au carre / nombre d echantillons 
	 * @param nbEchantillon
	 * @param informationRecue
	 * @return
	 */
	private float calculPuissance(int nbEchantillon, Information informationRecue) {
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
		calculPuissance = (float) sum / informationRecue.nbElements();
		return calculPuissance; 

	}
	
	private float calculSigma(float puissanceSignal, float SNR) {
		float sigma = 0;
		//calcul la valeur de sigma en fonction de la puissance du signal et du SNR
		sigma = (float) Math.sqrt(nbEchantillon*1/2*puissanceSignal/Math.pow(10, (SNR/10)));
		System.out.println("Sigma_BBG : "+sigma);
		return sigma;
	}
	
	private void generationSignalEtBruitBlanc (Float sigma)
	{
		Random r = new Random();
		for(Float elementInformationRecue : informationRecue) 
		{
			Double valueI;
			Float a1 = r.nextFloat();
			Float a2 = r.nextFloat();
			valueI = sigma*Math.sqrt(-2*Math.log(1-a1))*Math.cos(2*Math.PI*a2);
			informationEmise.add(valueI.floatValue() + elementInformationRecue);
		}
	}

	/*
	private void exportInformation(Information<Float> information)
	{
		File f = new File("export_BB_Simulation.txt");
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(""+information.toString());
			fw.flush();
			fw.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	public boolean equals (Object obj) {
		return (obj instanceof TransmetteurAnalogiqueBruite); 
	}
	*/
	  
}
