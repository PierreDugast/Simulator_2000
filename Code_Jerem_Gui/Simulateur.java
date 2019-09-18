import sources.*;
import destinations.*;
import transmetteurs.*;

import information.*;

import visualisations.*;

import java.util.regex.*;
import java.util.*;
import java.lang.Math;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/** La classe Simulateur permet de construire et simuler une chaîne de
 * transmission composée d'une Source, d'un nombre variable de
 * Transmetteur(s) et d'une Destination.
 * @author cousin
 * @author prou
 *
 */

public class Simulateur {
      	
    /** indique si le Simulateur utilise des sondes d'affichage */
    private boolean affichage = false;
    /** indique si le Simulateur utilise un message généré de manière aléatoire */
    private boolean messageAleatoire = true;
    /** indique si le Simulateur utilise un germe pour initialiser les générateurs aléatoires */
    private boolean aleatoireAvecGerme = false;
    /** la valeur de la semence utilisée pour les générateurs aléatoires */
    private Integer seed = null;
    /** la longueur du message aléatoire à transmettre si un message n'est pas impose */
    private int nbBitsMess = 100; 
    /** la chaîne de caractères correspondant à m dans l'argument -mess m */
    private String messageString = "100";
   
   	
    /** le  composant Source de la chaine de transmission */
    private Source <Boolean>  source = null;
    /** le  composant Transmetteur parfait logique de la chaine de transmission */
    private Transmetteur <Boolean, Boolean>  transmetteurLogique = null;
    /** le  composant Destination de la chaine de transmission */
    private Destination <Boolean>  destination = null;
   	
   
    /** Le constructeur de Simulateur construit une chaîne de
     * transmission composée d'une Source <Boolean>, d'une Destination
     * <Boolean> et de Transmetteur(s) [voir la méthode
     * analyseArguments]...  <br> Les différents composants de la
     * chaîne de transmission (Source, Transmetteur(s), Destination,
     * Sonde(s) de visualisation) sont créés et connectés.
     * @param args le tableau des différents arguments.
     *
     * @throws ArgumentsException si un des arguments est incorrect
     * @throws InformationNonConforme 
     *
     */   
    public  Simulateur(String [] args) throws ArgumentsException, InformationNonConforme {
      
      	// analyser et récupérer les arguments
      	
    	analyseArguments(args);
    	
      	//A completer
    	
    	// fixe
    	/*
    	SondeLogique sondeFixe = new SondeLogique("sondeFixe", 500);
    	SondeLogique sondeFixeTransmetteur = new SondeLogique("sondeFixe", 500);
    	
    	SourceFixe sourceFixe = new SourceFixe();
    	TransmetteurParfait transmetteurFixe = new TransmetteurParfait();
    	Destination destinationFinaleFixe = new DestinationFinale();
    	
    	sourceFixe.connecter(sondeFixe);
    	sourceFixe.connecter(transmetteurFixe);
    	
    	transmetteurFixe.connecter(sondeFixeTransmetteur);
    	transmetteurFixe.connecter(destinationFinaleFixe);
    	
    	sourceFixe.emettre();
    	
    	transmetteurFixe.recevoir(sourceFixe.getInformationEmise());
    	
    	transmetteurFixe.emettre();
    	
    	sourceFixe.deconnecter(destinationFinaleFixe);
    	sourceFixe.deconnecter(transmetteurFixe);
    	sourceFixe.deconnecter(sondeFixe);
    	transmetteurFixe.deconnecter(destinationFinaleFixe);
    	transmetteurFixe.deconnecter(sondeFixeTransmetteur);
    	System.out.println(sourceFixe.getInformationEmise());
    	
    	//aléatoire
    	
    	SondeLogique sondeAleatoire = new SondeLogique("sondeFixe", 500);
    	SondeLogique sondeAleatoireTransmetteur = new SondeLogique("sondeFixe", 500);
    	
    	SourceAleatoire sourceAleatoire = new SourceAleatoire();
    	TransmetteurParfait transmetteurAleatoire = new TransmetteurParfait();
    	Destination destinationFinaleAleatoire = new DestinationFinale();
    	
    	sourceAleatoire.connecter(sondeAleatoire);
    	sourceAleatoire.connecter(transmetteurAleatoire);
    	
    	transmetteurAleatoire.connecter(sondeAleatoireTransmetteur);
    	transmetteurAleatoire.connecter(destinationFinaleAleatoire);
    	
    	sourceAleatoire.emettre();
    	
    	transmetteurAleatoire.recevoir(sourceAleatoire.getInformationEmise());
    	
    	transmetteurAleatoire.emettre();
    	
    	sourceAleatoire.deconnecter(destinationFinaleAleatoire);
    	sourceAleatoire.deconnecter(transmetteurAleatoire);
    	sourceAleatoire.deconnecter(sondeAleatoire);
    	transmetteurAleatoire.deconnecter(destinationFinaleAleatoire);
    	transmetteurAleatoire.deconnecter(sondeAleatoireTransmetteur);
    	System.out.println(sourceAleatoire.getInformationEmise());
    	*/
    	   	
    }
   
   
   
    /** La m�thode analyseArguments extrait d'un tableau de chaînes de
     * caractères les différentes options de la simulation.  Elle met
     * à jour les attributs du Simulateur.
     *
     * @param args le tableau des différents arguments.
     * <br>
     * <br>Les arguments autorisés sont : 
     * <br> 
     * <dl>
     * <dt> -mess m  </dt><dd> m (String) constitué de 7 ou plus digits à 0 | 1, le message à transmettre</dd>
     * <dt> -mess m  </dt><dd> m (int) constitué de 1 à 6 digits, le nombre de bits du message "aléatoire" à  transmettre</dd> 
     * <dt> -s </dt><dd> utilisation des sondes d'affichage</dd>
     * <dt> -seed v </dt><dd> v (int) d'initialisation pour les générateurs aléatoires</dd> 
     * </dl>
     *
     * @throws ArgumentsException si un des arguments est incorrect.
     *
     */   
    public  void analyseArguments(String[] args)  throws  ArgumentsException {
      		
    	for (int i=0;i<args.length;i++){ 
              
    		if (args[i].matches("-s")){
            		affichage = true;
    		}
    		else if (args[i].matches("-seed")) {
    			aleatoireAvecGerme = true;
    			i++; 
    			// traiter la valeur associee
    			try { 
    				seed =new Integer(args[i]);
    			}
    			catch (Exception e) {
    				throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
    			}           		
    		}
    		
            
    		else if (args[i].matches("-mess")){
    			i++; 
    			// traiter la valeur associee
    			messageString = args[i];
    			if (args[i].matches("[0,1]{7,}")) {
    				messageAleatoire = false;
    				nbBitsMess = args[i].length();
    			} 
    			else if (args[i].matches("[0-9]{1,6}")) {
    				messageAleatoire = true;
    				nbBitsMess = new Integer(args[i]);
    				if (nbBitsMess < 1) 
    					throw new ArgumentsException ("Valeur du parametre -mess invalide : " + nbBitsMess);
    			}
    			else 
    				throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
    		}
                                   
    		else throw new ArgumentsException("Option invalide :"+ args[i]);
    	}
      
    }
     
    
   	
    /** La méthode execute effectue un envoi de message par la source
     * de la chaîne de transmission du Simulateur.
     *
     * @throws Exception si un problème survient lors de l'exécution
     *
     */ 
    public void execute() throws Exception {      
         
      // source.emettre(); 
      	     	      
    }
   
   	   	
   	
    /** La methode qui calcule le taux d'erreur binaire en comparant
     * les bits du message emis avec ceux du message recu.
     *
     * @return  La valeur du Taux dErreur Binaire.
     */   	   
    public float  calculTauxErreurBinaire(Destination destinationFinale, Source source) {
    	int cpt=0; //initialisation du compteur cpt 
    	for(int i=0; i< source.getInformationEmise().nbElements(); i++) {
    	//recuperation des informations emises
    		if (source.getInformationEmise().iemeElement(i) != destinationFinale.getInformationRecue().iemeElement(i)) {
    			//comparaison entre les informations emises et recus
    			//si elles ne sont pas egales alors on incremente le compteur cpt
    			cpt++;
    		}
    	}
    	return (float) cpt/source.getInformationEmise().nbElements();
    	//renvoie de la valeur nombre erreur / nombre d'information transmises
    }
   
   
   
   
    /** La fonction main instancie un Simulateur à l'aide des
     *  arguments paramètres et affiche le résultat de l'exécution
     *  d'une transmission.
     *  @param args les différents arguments qui serviront à l'instanciation du Simulateur.
     * @throws InformationNonConforme 
     */
    public static void main(String [] args) throws InformationNonConforme { 
    	// fixe
    	
    	SondeLogique sondeFixe = new SondeLogique("sondeFixe", 500);
    	SondeLogique sondeFixeTransmetteur = new SondeLogique("sondeFixe", 500);
    	
    	SourceFixe sourceFixe = new SourceFixe();
    	TransmetteurParfait transmetteurFixe = new TransmetteurParfait();
    	Destination destinationFinaleFixe = new DestinationFinale();
    	
    	sourceFixe.connecter(sondeFixe);
    	sourceFixe.connecter(transmetteurFixe);
    	
    	transmetteurFixe.connecter(sondeFixeTransmetteur);
    	transmetteurFixe.connecter(destinationFinaleFixe);
    	
    	sourceFixe.emettre();
    	
    	transmetteurFixe.recevoir(sourceFixe.getInformationEmise());
    	
    	transmetteurFixe.emettre();
    	
    	sourceFixe.deconnecter(destinationFinaleFixe);
    	sourceFixe.deconnecter(transmetteurFixe);
    	sourceFixe.deconnecter(sondeFixe);
    	transmetteurFixe.deconnecter(destinationFinaleFixe);
    	transmetteurFixe.deconnecter(sondeFixeTransmetteur);
    	System.out.println(sourceFixe.getInformationEmise());
    	
    	//aleatoire
    	
    	SondeLogique sondeAleatoire = new SondeLogique("sondeFixe", 500);
    	SondeLogique sondeAleatoireTransmetteur = new SondeLogique("sondeFixe", 500);
    	
    	SourceAleatoire sourceAleatoire = new SourceAleatoire();
    	TransmetteurParfait transmetteurAleatoire = new TransmetteurParfait();
    	Destination destinationFinaleAleatoire = new DestinationFinale();
    	
    	sourceAleatoire.connecter(sondeAleatoire);
    	sourceAleatoire.connecter(transmetteurAleatoire);
    	
    	transmetteurAleatoire.connecter(sondeAleatoireTransmetteur);
    	transmetteurAleatoire.connecter(destinationFinaleAleatoire);
    	
    	sourceAleatoire.emettre();
    	
    	transmetteurAleatoire.recevoir(sourceAleatoire.getInformationEmise());
    	
    	transmetteurAleatoire.emettre();
    	
    	sourceAleatoire.deconnecter(destinationFinaleAleatoire);
    	sourceAleatoire.deconnecter(transmetteurAleatoire);
    	sourceAleatoire.deconnecter(sondeAleatoire);
    	transmetteurAleatoire.deconnecter(destinationFinaleAleatoire);
    	transmetteurAleatoire.deconnecter(sondeAleatoireTransmetteur);
    	System.out.println(sourceAleatoire.getInformationEmise());
      
    	Simulateur simulateur = null;
      	
    	try {
            simulateur = new Simulateur(args);
    	}
    	catch (Exception e) {
    		System.out.println(e); 
    		System.exit(-1);
    	} 
      		
    	try {
            simulateur.execute();
            float tauxErreurBinaire = simulateur.calculTauxErreurBinaire(destinationFinaleFixe, sourceFixe);
            String s = "java  Simulateur  ";
            for (int i = 0; i < args.length; i++) {
            	s += args[i] + "  ";
            }
            System.out.println(s + "  =>   TEB : " + tauxErreurBinaire);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    		e.printStackTrace();
    		System.exit(-2);
    	}              	
    }
}