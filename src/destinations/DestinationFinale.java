package destinations;
import information.Information;
import information.InformationNonConforme;

public class DestinationFinale extends Destination<Boolean>{

	public DestinationFinale ()
	{
		super();
	}
	public void recevoir(Information<Boolean> information) throws InformationNonConforme {
		informationRecue=information;
	}

}