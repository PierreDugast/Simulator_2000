package sources;
import information.*;
import ExceptionsGlobales.ArgumentsException;

public class SourceFixe extends Source <Boolean>
{
	public SourceFixe(String messageString, int nbBits)
	{
		
		Boolean [] boolTab = new Boolean[nbBits];
		for (int i=0;i<messageString.length();i++)
		{
			if ('0' == messageString.charAt(i))
				boolTab[i] = false;
			if ('1' == messageString.charAt(i))
				boolTab[i] = true;
		}
		this.informationGeneree = new Information(boolTab);
	}
}
