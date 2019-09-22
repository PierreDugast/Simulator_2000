package transmetteurs;
import information.Information;
import information.InformationNonConforme;

public class TransmetteurParfait<R,T> extends Transmetteur<R,T>
{
	
	public TransmetteurParfait()
	{
		super();
	}
	
	public void recevoir(Information<R> information) throws InformationNonConforme
	{
		informationRecue=information;
		emettre();
	}
	
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