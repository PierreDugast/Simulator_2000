package sources;
import information.*;
import ExceptionsGlobales.ArgumentsException;

public class SourceFixe extends Source <Boolean>
{
	public SourceFixe(String messageString, int nbBits)
	{
		informationGeneree = new Information<Boolean>();
		for (int i=0;i<messageString.length();i++)
		{
			if ('0' == messageString.charAt(i)) {
				informationGeneree.add(false);
			}
			if ('1' == messageString.charAt(i)) {
				informationGeneree.add(true);
			}
		}
		informationEmise = informationGeneree;
	}
}
