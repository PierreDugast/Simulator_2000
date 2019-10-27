package transmetteurs;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe permettant de créer un objet TransmetteurParfait. Cette objet reçoit une infromation 
 * et la réémet identique.
 * 
 * @author jérémie
 *
 * @param <R>
 * @param <T>
 */
public class TransmetteurParfait<R,T> extends Transmetteur<R,T>
{
	
	/**
	 * Constructeur utilisant le constructeur de Transmetteur sans paramètre.
	 */
	public TransmetteurParfait()
	{
		super();
	}
	
	/**
	 * Méthode permettant de recevoir une information.
	 */
	public void recevoir(Information<R> information) throws InformationNonConforme
	{
		informationRecue=information;
		emettre();
	}
	
	/**
	 * Méthode permettant d'émettre l'informationRecue sans la modifier.
	 */
	public void emettre() throws InformationNonConforme 
	{
		Information<T> info = (Information<T>) informationRecue;
			
		for(int i=0;i<destinationsConnectees.size();i++)
		{
			destinationsConnectees.get(i).recevoir(info);
		}
		informationEmise = info;
	}
}