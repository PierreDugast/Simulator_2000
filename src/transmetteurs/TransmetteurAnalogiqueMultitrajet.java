package transmetteurs;
import information.Information;
import information.InformationNonConforme;
import java.util.*;

public class TransmetteurAnalogiqueMultitrajet<R,T> extends Transmetteur<R,T> {

	protected Integer [] dtList;
	protected Float [] arList;
	
	public TransmetteurAnalogiqueMultitrajet (Integer [] dtList, Float [] arList)
	{
		super();
		this.dtList=dtList;
		this.arList=arList;
	}
	
	@Override
	public void recevoir(Information<R> information) throws InformationNonConforme 
	{
		informationRecue=information; 
		emettre(); 
	}

	@Override
	public void emettre() throws InformationNonConforme 
	{
		this.informationEmise = this.creerMultitrajet(this.dtList, this.arList, this.informationRecue);
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
	
	private Information creerMultitrajet(Integer[] dtList, Float[] arList, Information<R> informationRecue) 
	{
		Information informationMultitrajet = new Information();
		
		//TODO: implémentation de la methode
		
		return informationMultitrajet;
	}

}
