package transmetteurs;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe permettant de cr�er un objet TransmetteurParfait. Cette objet re�oit une infromation 
 * et la r��met identique.
 * 
 * @author j�r�mie
 *
 * @param <R>
 * @param <T>
 */
public class TransmetteurParfait<R,T> extends Transmetteur<R,T>
{
	
	/**
	 * Constructeur utilisant le constructeur de Transmetteur sans param�tre.
	 */
	public TransmetteurParfait()
	{
		super();
	}
	
	/**
	 * M�thode permettant de recevoir une information.
	 */
	public void recevoir(Information<R> information) throws InformationNonConforme
	{
		informationRecue=information;
		emettre();
	}
	
	/**
	 * M�thode permettant d'�mettre l'informationRecue sans la modifier.
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