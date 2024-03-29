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
    /** indique si il s'agit d'une transmission analogique*/
    private Boolean isAnalogic = false;
    /** indique la forme de l'onde choisie*/
    private String messageAnalogicEncoding = "RZ";
    /** indique l'utilisation d'un SNR*/
    private boolean isSNR = false; 
    /** indique la valeur du SNR dans le cas d'un signal analogique bruit�*/
    private Float SNR;
    /**indique l'utilisation d'un transmetteur multi trajet*/
    private Boolean isTi = false;
    /** liste contenant les d�calages (en nombre d'�chantillons) dans le cas d'une transmission multi-trajets. **/
    private Integer [] dtList = new Integer[5];
    /** liste contenant les (en nombre d'�chantillons) dans le cas d'une transmission multi-trajets. **/
    private Float [] arList = new Float[5];
    /** indique l'utilisation d'un codeur*/
    private boolean unCodeur = false; 
    
    
   	
    /** le  composant Source de la chaine de transmission */
    public Source <Boolean>  source = null;
    /** le composant emetteur analogique de la chaine de transmission */
    public Transmetteur <Boolean,Float> emetteurAnalogique = new TransmetteurParfait();
    /** le  composant Transmetteur analogique parfait logique de la chaine de transmission */
    public Transmetteur <Float, Float>  transmetteurAnalogique1 = new TransmetteurParfait();
    /** un deuxi�me transmetteur analogique pour les multiples trajets (par d�faut parfait)*/
    public Transmetteur <Float,Float> transmetteurAnalogique2 = new TransmetteurParfait();
    /** un transmetteur pour le codeur(par d�faut parfait)*/
    public Transmetteur <Boolean,Boolean> emetteurCodeur = new TransmetteurParfait();
    /** un transmetteur pour le codeur(par d�faut parfait)*/
    public Transmetteur <Boolean,Boolean> recepteurCodeur = new TransmetteurParfait();
    /** le composant recepteur analogique de la chaine de transmission */
    public Transmetteur <Float, Boolean> recepteurAnalogique = new TransmetteurParfait();
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
    	
      	if (messageAleatoire) {
      		this.source = new SourceAleatoire(this.nbBitsMess);
      	}	
      	if (!(messageAleatoire)) {
      		this.source = new SourceFixe(this.messageString,this.nbBitsMess);
      	}	
      	if (this.isAnalogic)
      	{
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
      	}
      	if (this.isTi) {
      		this.transmetteurAnalogique1 = new TransmetteurAnalogiqueMultitrajet(this.dtList,this.arList);
      	}	 
      	if (this.isSNR) {
      		this.transmetteurAnalogique2 = new TransmetteurAnalogiqueBruite(this.nbEchantillon,  this.SNR);
      	}
      	if (this.unCodeur) {
      		this.emetteurCodeur = new TransmetteurCodage(this.nbEchantillon);
      		this.recepteurCodeur = new RecepteurCodeur(this.nbEchantillon);
      	}
      		

      	this.destination = new DestinationFinale();
      	
      	
      	this.source.connecter(this.emetteurCodeur);
      	this.emetteurCodeur.connecter(this.emetteurAnalogique);
      	this.emetteurAnalogique.connecter(this.transmetteurAnalogique1);
      	this.transmetteurAnalogique1.connecter(this.transmetteurAnalogique2);
      	this.transmetteurAnalogique2.connecter(this.recepteurAnalogique);
  		this.recepteurAnalogique.connecter(this.recepteurCodeur);
      	this.recepteurCodeur.connecter(this.destination);
      	
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
		        	this.isAnalogic = true;
		        	i++;
		        	if (args[i].matches("NRZ"))
		        	{
		        		this.messageAnalogicEncoding ="NRZ";
		        	}
		        	else if (args[i].matches("NRZT"))
		        	{
		        		this.messageAnalogicEncoding ="NRZT";
		        	}
		        	else if (args[i].matches("RZ"))
		        	{
		        		this.messageAnalogicEncoding ="RZ";
		        	}
		        	else
		        		throw new ArgumentsException ("Valeur du parametre -form invalide : " + args[i]);
		        }
				else if (args[i].matches("-nbEch"))
				{
					this.isAnalogic = true;
					i++;
					if (args[i].matches("[0-2]?")) {
						System.out.println("NnEch trop faible pour generer un signal. NbEch = 3 minimum.");
						this.nbEchantillon = 3;
					} else if (args[i].matches("[0-9]{1,10}")) {
						this.nbEchantillon = new Integer(args[i]);
					}
					else {
						throw new ArgumentsException ("Valeur du parametre -nbEch invalide : " + args[i]);
					}
				}
			else if (args[i].matches("-ampl"))
			{
				this.isAnalogic = true;
				i++;
				if (args[i].matches("[-]?([0-9]{1,10}[.])?[0-9]{1,10}"))
					this.amplitudeMin = Float.parseFloat(args[i]);
				else
	        		throw new ArgumentsException ("Valeur du parametre -ampl invalide : " + args[i]);
				i++;
				if (args[i].matches("[-]?([0-9]{1,10}[.])?[0-9]{1,10}"))
					this.amplitudeMax = Float.parseFloat(args[i]);
				else
	        		throw new ArgumentsException ("Valeur du parametre -ampl invalide : " + args[i]);
				
			}
			else if (args[i].matches("-snr"))
			{
				this.isAnalogic = true;
				i++;
				if (args[i].matches("^[-]?[0-9]*\\.?[0-9]+"))
				{
					this.SNR = new Float(args[i]);
					this.isSNR = true;
				}
				else
	        		throw new ArgumentsException ("Valeur du parametre -nbEch invalide : " + args[i]);
			}
			else if (args[i].matches("-ti"))
			{
					this.isAnalogic = true;
					int j=0; //compteur pour remplir dtList et ArList
					this.isTi = true;
					while (i<args.length-1 && (args[i+1].matches("[0-9]{1,10}") || args[i+1].matches("^[+]?[0-9]*\\.?[0-9]+")) ) {
					i++;
					if (args[i].matches("[0-9]{1,10}")) {
						this.dtList[j] = new Integer(args[i]);
					}
					else
		        		throw new ArgumentsException ("Valeur du parametre -ti dt invalide : " + args[i]);
					i++;  
					if (args[i].matches("^[+]?[0-9]*\\.?[0-9]+")) {
						this.arList[j] = new Float(args[i]);
						j++;
					}
					else
		        		throw new ArgumentsException ("Valeur du parametre -ti ar invalide : " + args[i]);
				}
			}
			else if (args[i].matches("-codeur"))
			{
				this.unCodeur = true;
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
      		if(this.unCodeur) {
      			SondeLogique sonde5 = new SondeLogique("Sonde sortie  Codage", 720);
          		sonde5.recevoir(this.emetteurCodeur.getInformationEmise());
      		}
      		if (this.isAnalogic)
      		{
	      		SondeAnalogique sonde2 = new SondeAnalogique("Sonde sortie emetteur analogique");
	      		sonde2.recevoir(this.emetteurAnalogique.getInformationEmise());
      		}
      		if(this.isSNR) {
      			SondeAnalogique sonde3 = new SondeAnalogique("Sonde sortie transmetteur analogique");
          		sonde3.recevoir(this.transmetteurAnalogique2.getInformationEmise());
      		}
      		SondeLogique sonde4 = new SondeLogique("Sonde sortie recepteur analogique",720);
      		sonde4.recevoir(this.recepteurAnalogique.getInformationEmise());
          	if(this.unCodeur) {
          		SondeLogique sonde6 = new SondeLogique("Sonde sortie recepteur codeur",720);
          		sonde6.recevoir(this.recepteurCodeur.getInformationEmise());
      		}
      	}
      	System.out.println("Param�tres utilises : ");
  		System.out.println(
  				" - Bruit blanc gaussien avec SNR : "+this.SNR+"\n"
  				+" - Transmission analogique : "+this.isAnalogic+"\n"
  				+" - Encodage analogique : "+this.messageAnalogicEncoding+"\n"
  				+" - Nombre d'�chantillon par symbole : "+this.nbEchantillon+"\n"
  				+" - Amplitude max : "+this.amplitudeMax+"\n"
  				+" - Amplitude min : "+this.amplitudeMin+"\n"
  				+" - Trajets multiples : "+this.isTi+"\n"
  				+" - pr�sence d'un codeur : "+this.unCodeur+"\n"
  				);
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

    	String[] argsBis = {"-s","-mess","1010111001","-form","RZ","-nbEch","99","-ti","50","0.5"};
    	
		try 
		{
			simulateur = new Simulateur(argsBis); //(pour tester les arguments passés en argBis)
			//simulateur = new Simulateur(args);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
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

