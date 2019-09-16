package sources;
import java.util.*;
import information.*;
import ExceptionsGlobales.ArgumentsException;

public class SourceFixe extends Source <Boolean>
{
	public SourceFixe(String messageString, int nbBits) throws ArgumentsException
	{
		
		Boolean [] boolTab = new Boolean[nbBits];
		for (int i=0;i<messageString.length();i++)
		{
			if ('0' == messageString.charAt(i))
				boolTab[i] = false;
			if ('1' == messageString.charAt(i))
				boolTab[i] = true;
			else
				throw new ArgumentsException("argument non binaire en entrée d'une source fixe");
		}
		this.informationGeneree = new Information(boolTab);
	}
}
