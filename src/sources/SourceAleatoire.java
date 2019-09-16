package sources;
import java.util.*;
import java.lang.*;
import information.*;
public class SourceAleatoire extends Source <Boolean>
{
	public SourceAleatoire(int nbBits)
	{
		super();
		Boolean [] boolTab = new Boolean[nbBits];
		Random bool = new Random();
		for(int i = 0; i<nbBits; i++)
			boolTab[i] = bool.nextBoolean();
		
		this.informationGeneree = new Information(boolTab);
	}	  
}
