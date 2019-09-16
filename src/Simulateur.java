import sources.*;
import destinations.*;
import information.Information;
import information.InformationNonConforme;
import transmetteurs.*;
import visualisations.*;
import java.util.Iterator;

import ExceptionsGlobales.ArgumentsException;
/*BLABLA*/
/** La classe Simulateur permet de construire et simuler une chaîne de
 * transmission composée d'une Source, d'un nombre variable de
 * Transmetteur(s) et d'une Destination.
 * @author cousin
 * @author prou
 * @author Arnaud Rohé & Pierre Dugast, FIP 2021
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
     *
     */   
    public  Simulateur(String [] args) throws ArgumentsException 
    {
      	// analyser et récupérer les arguments
    	analyseArguments(args);
    	
      	// assemblage des composants de la chaine de transmission pour le TP1 :
    	
      	if (messageAleatoire)
      	{
      		this.source = new SourceAleatoire(this.nbBitsMess);
      	}
      	if (!(messageAleatoire))
      	{
      		this.source = new SourceFixe(this.messageString,this.nbBitsMess);
      	}
      	this.transmetteurLogique = new TransmetteurParfait();
      	this.destination = new DestinationFinale();
      	
      	this.source.connecter(this.transmetteurLogique);
      	this.transmetteurLogique.connecter(this.destination);
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
      		SondeLogique sonde1 = new SondeLogique("Sonde sortie générateur",720);
      		sonde1.recevoir(this.source.getInformationEmise());
      		SondeLogique sonde2 = new SondeLogique("Sonde sortie transmetteur",720);
      		sonde2.recevoir(this.transmetteurLogique.getInformationEmise());
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
    	String[] argsBis = {"-mess","1234","-s"};
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

