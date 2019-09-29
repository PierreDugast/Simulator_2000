import sources.*;

import destinations.*;
import information.Information;
import information.InformationNonConforme;
import transmetteurs.*;
import visualisations.*;
import java.util.Iterator;

import ExceptionsGlobales.AnalogicArgumentException;
import ExceptionsGlobales.ArgumentsException;
/** La classe Simulateur permet de construire et simuler une chaîne de
 * transmission composée d'une Source, d'un nombre variable de
 * Transmetteur(s) et d'une Destination.
 * @author cousin
 * @author prou
 * @author Arnaud Rohe, Pierre Dugast, Jeremie Rafinesque, Guillaume Chiquet 
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
    /** pr�cise l'�chantillonnage du signal analogique */
    private Integer nbEchantillon = 30;
    /** indique la valeur d'amplitude maximum */
    private Float amplitudeMax = 1.0f;
    /** indique la valeur d'amplitude minimum */
    private Float amplitudeMin = 0.0f;
    /** la chaîne de caractères correspondant à m dans l'argument -mess m */
    private String messageString = "100";
    /** indique la forme de l'onde choisie*/
    private String messageAnalogicEncoding = "RZ";
    /** indique l'utilisation d'un SNR*/
    private boolean isSNR = false; 
    /** indique la valeur du SNR dans le cas d'un signal analogique bruit�*/
    private Float SNR;
    
   	
    /** le  composant Source de la chaine de transmission */
    public Source <Boolean>  source = null;
    /** le composant emetteur analogique de la chaine de transmission */
    public Transmetteur <Boolean,Float> emetteurAnalogique = null;
    /** le  composant Transmetteur analogique parfait logique de la chaine de transmission */
    public Transmetteur <Float, Float>  transmetteurAnalogique = new TransmetteurParfait();
    /** le composant recepteur analogique de la chaine de transmission */
    public Transmetteur <Float, Boolean> recepteurAnalogique = null;
    /** le  composant Destination de la chaine de transmission */
    public Destination <Boolean>  destination = null;
   	
   
    /** Le constructeur de Simulateur construit une chaîne de
     * transmission composée d'une Source &lt; Boolean &gt;, d'une Destination
     * &lt;Boolean &gt; et de Transmetteur(s) [voir la méthode
     * analyseArguments]...  <br> Les différents composants de la
     * chaîne de transmission (Source, Transmetteur(s), Destination,
     * Sonde(s) de visualisation) sont créés et connectés.
     * @param args le tableau des différents arguments.
     *
     * @throws ArgumentsException si un des arguments est incorrect
     * @throws AnalogicArgumentException 
     *
     */   
    public  Simulateur(String [] args) throws ArgumentsException, AnalogicArgumentException 
    {
      	// analyser et récupérer les arguments
    	analyseArguments(args);
    	
      	// assemblage des composants de la chaine de transmission pour le TP1 :
    	
      	if (messageAleatoire)
      		this.source = new SourceAleatoire(this.nbBitsMess);
      	if (!(messageAleatoire))
      		this.source = new SourceFixe(this.messageString,this.nbBitsMess);
      	if (this.messageAnalogicEncoding == "RZ") 
      	{
      		this.emetteurAnalogique = new EmetteurRz(this.nbEchantillon, this.amplitudeMax, this.amplitudeMin);
      		this.recepteurAnalogique = new RecepteurRz(this.nbEchantillon, this.amplitudeMax, this.amplitudeMin);
      	}
      	if (this.messageAnalogicEncoding == "NRZ") 
      	{
      		this.emetteurAnalogique = new EmetteurNrz(this.nbEchantillon, this.amplitudeMax, this.amplitudeMin);
      		this.recepteurAnalogique = new RecepteurNrz(this.nbEchantillon, this.amplitudeMax, this.amplitudeMin);
      	}
      	if (this.messageAnalogicEncoding == "NRZT") 
      	{
      		this.emetteurAnalogique = new EmetteurNrzt(this.nbEchantillon, this.amplitudeMax, this.amplitudeMin);
      		this.recepteurAnalogique = new RecepteurNrzt(this.nbEchantillon, this.amplitudeMax, this.amplitudeMin);
      	}
      	if (this.isSNR)
      		this.transmetteurAnalogique = new TransmetteurAnalogiqueBruite(this.nbEchantillon,  this.SNR);
      	
      	this.destination = new DestinationFinale();
      	
      	this.source.connecter(this.emetteurAnalogique);
      	this.emetteurAnalogique.connecter(this.transmetteurAnalogique);
      	this.transmetteurAnalogique.connecter(this.recepteurAnalogique);
      	this.recepteurAnalogique.connecter(this.destination);
    }
   
   
   
    /** La méthode analyseArguments extrait d'un tableau de chaînes de
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
    public  void analyseArguments(String[] args)  throws  ArgumentsException 
    {
		for (int i=0;i<args.length;i++)
		{ 
			if (args[i].matches("-s"))
			{
				affichage = true;
	        }
	        else if (args[i].matches("-seed")) 
	        {
	        	aleatoireAvecGerme = true;
	        	i++; 
	            // traiter la valeur associee
	        	try 
	        	{ 
	        		seed =new Integer(args[i]);
	        	}
	        	catch (Exception e) 
	        	{
	        		throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
	        	}           		
	        }
	        else if (args[i].matches("-mess"))
	        {
	        	i++; 
	            // traiter la valeur associee
	        	messageString = args[i];
	        	if (args[i].matches("[0,1]{7,}")) 
	        	{
				    messageAleatoire = false;
				    nbBitsMess = args[i].length();
	        	} 
	        	else if (args[i].matches("[0-9]{1,6}")) 
	        	{
	        		messageAleatoire = true;
	        		nbBitsMess = new Integer(args[i]);
	        		if (nbBitsMess < 1) 
	        			throw new ArgumentsException ("Valeur du parametre -mess invalide : " + nbBitsMess);
	        	}
	        	else 
	        		throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
	        }
	        else if (args[i].matches("-form"))
	        {
	        	i++;
	        	if (args[i].matches("NRZ")||args[i].matches("NRZT")||args[i].matches("RZ")) 
	        	{
	        		this.messageAnalogicEncoding = args[i];
	        	}
	        	else
	        		throw new ArgumentsException ("Valeur du parametre -form invalide : " + args[i]);
	        }
			else if (args[i].matches("-nbEch"))
			{
				i++;
				if (args[i].matches("[0-9]{1,10}"))
					this.nbEchantillon = new Integer(args[i]);
				else
	        		throw new ArgumentsException ("Valeur du parametre -nbEch invalide : " + args[i]);
			}
			else if (args[i].matches("-ampl"))
			{
				i++;
				if (args[i].matches("[-][0-9]{1,10}"))
					this.amplitudeMin = Float.parseFloat(args[i]);
				else
	        		throw new ArgumentsException ("Valeur du parametre -ampl invalide : " + args[i]);
				i++;
				if (args[i].matches("[0-9]{1,10}"))
					this.amplitudeMax = Float.parseFloat(args[i]);
				else
	        		throw new ArgumentsException ("Valeur du parametre -ampl invalide : " + args[i]);
				
			}
			else if (args[i].matches("-snr"))
			{
				i++;
				if (args[i].matches("[0-9]{1,10}"))
				{
					this.SNR = new Float(args[i]);
					this.isSNR = true;
				}
				else
	        		throw new ArgumentsException ("Valeur du parametre -nbEch invalide : " + args[i]);
			}
	        else 
	        	throw new ArgumentsException("Option invalide :"+ args[i]);
		}
	}
     
    
   	
    /** La méthode execute effectue un envoi de message par la source
     * de la chaîne de transmission du Simulateur.
     *
     * @throws Exception si un problème survient lors de l'exécution
     *
     */ 
    public void execute() throws Exception 
    {      
    	this.source.emettre();
      	//Si l'affichage des sondes est demandée :
      	if(this.affichage)
      	{
      		SondeLogique sonde1 = new SondeLogique("Sonde sortie source logique",720);
      		sonde1.recevoir(this.source.getInformationEmise());
      		SondeAnalogique sonde2 = new SondeAnalogique("Sonde sortie emetteur analogique");
      		sonde2.recevoir(this.emetteurAnalogique.getInformationEmise());
      		SondeAnalogique sonde3 = new SondeAnalogique("Sonde sortie transmetteur analogique");
      		sonde3.recevoir(this.transmetteurAnalogique.getInformationEmise());
      		SondeLogique sonde4 = new SondeLogique("Sonde sortie recepteur analogique",720);
      		sonde4.recevoir(this.recepteurAnalogique.getInformationEmise());
      	}
    }
   
   	   	
   	
    /** La méthode qui calcule le taux d'erreur binaire en comparant
     * les bits du message émis avec ceux du message reçu.
     *
     * @return  La valeur du Taux dErreur Binaire.
     */   	   
    public float  calculTauxErreurBinaire()
    {  
      	//!!! Test calculTauxErreurBinaire() à conserver jusqu'à intégration dans les tests !!!____________________
      	//this.source = new SourceFixe("1111111111",10); //Deuxième génération de bits différents afin de changer les valeur dans la destination finale
      	//try {source.emettre();} catch (InformationNonConforme e) {e.printStackTrace();}
      	//!!!Fin test !!!__________________________________________________________________________________________
    	
    	int nbErreur = 0;
      	Iterator itInit = this.source.getInformationEmise().iterator();
      	Iterator itFinal = this.destination.getInformationRecue().iterator();
      	
      	while(itInit.hasNext())
      	{
      		if (itInit.next()!=itFinal.next())
      		{
      			nbErreur++;
      		}
      			
      	}
    	return  (((float)nbErreur)/((float)this.source.getInformationEmise().nbElements()));
    }
   
   
   
   
    /** La fonction main instancie un Simulateur à l'aide des
     *  arguments paramètres et affiche le résultat de l'exécution
     *  d'une transmission.
     *  @param args les différents arguments qui serviront à l'instanciation du Simulateur.
     */
    public static void main(String [] args) 
    { 
    	Simulateur simulateur = null;
    	//Test des arguments avec le String[] argBis :

    	String[] argsBis = {"-mess","1010101010","-s","-form","NRZT","-ampl","-2","2"};
    	
		try 
		{
			simulateur = new Simulateur(argsBis); //(pour tester les arguments passés en argBis)
			//simulateur = new Simulateur(args);
		}
		catch (Exception e) 
		{
		    System.out.println(e); 
		    System.exit(-1);
		} 		
		try 
		{
		    simulateur.execute();
		    float tauxErreurBinaire = simulateur.calculTauxErreurBinaire();
		    String s = "java  Simulateur  ";
		    for (int i = 0; i < args.length; i++) 
		    {
		    	s += args[i] + "  ";
		    }
		    System.out.println(s + "  =>   TEB : " + tauxErreurBinaire);
		}
		catch (Exception e) 
		{
		    System.out.println(e);
		    e.printStackTrace();
		    System.exit(-2);
		}              	
    }
}

