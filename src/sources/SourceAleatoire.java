package sources;
import java.util.*;
import java.lang.*;
import information.*;
public class SourceAleatoire extends Source <Boolean>
{
	public SourceAleatoire(int nbBits)
	{
		super();
		informationGeneree= new Information<Boolean>();
		Random bool = new Random();
		for(int i = 0; i<nbBits; i++) {
			informationGeneree.add(bool.nextBoolean());
		}
		informationEmise = informationGeneree;
	}	  
}
