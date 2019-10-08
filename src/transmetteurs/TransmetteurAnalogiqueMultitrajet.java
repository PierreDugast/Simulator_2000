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
		
		//System.out.println(""+this.dtList.toString());
	}
	
	@Override
	public void recevoir(Information information) throws InformationNonConforme 
	{
		informationRecue=information; 
		emettre(); 
	}

	@Override
	public void emettre() throws InformationNonConforme 
	{
		this.informationEmise = (Information<T>) this.creerMultitrajet(dtList, arList, informationRecue);
		for(int j=0;j<destinationsConnectees.size();j++){
			destinationsConnectees.get(j).recevoir(this.informationEmise);
		}
	}
	
	private Information<Float> creerMultitrajet(Integer[] dtList, Float[] arList, Information informationRecue) 
	{
		//Variable de type Information destinee a etre retounee
		Information<Float> informationMultitrajet;
		//Variable temporaire permettant de stocker un trajet
		Information<Float> informationTrajet; 
		//Liked list permettant de stocker tous les informationTrajet.
		LinkedList<Information<Float>> informationMultitrajetList = new LinkedList();
		
		//comptage du nombre de trajets à effectuer (la liste passee en argument est toujours de même longeur)
		int nbTrajet = 0;
		while (this.dtList[nbTrajet] != null)
			nbTrajet++;
		
		//j est l'indice pour parcourrir les trajets
		int j = 0;
		//i est l'indice permettant de parcourrir un trajet.
		int i=0;
		
		//Tant qu'il reste des trajets à effectuer
		while (j<nbTrajet)
		{
			//On instancie un nouveau trajet
			informationTrajet = new Information<Float>();
			
			//Pour créer un trajet décalé :
			//On ajoute des 0 au début de du nouvel objet "Information"
			i=0;
			while (i<dtList[j])
			{
				informationTrajet.add(0f);
				i++;
			}
			
			//Puis on ajoute les éléments de l'informationRecue initialement en enlevant les éléments finaux.
			//On change également l'amplitude (en fonction du trajet) des valeurs de l'informationRecue.
			i=0;
			while (i<(informationRecue.nbElements()-dtList[j]))
			{
				informationTrajet.add(arList[j]*((Float)informationRecue.iemeElement(i)));
				i++;
			}
			
			//l'information de chaque trajet est stocké dans la linkedList d'Informations informationMultitrajetList.
			informationMultitrajetList.add(informationTrajet);
			//on  passe au trajet suivant.
			j++;
		}
		
		//On ajoute également le trajet direct (informationRecue) à la LinkedList :
		informationMultitrajetList.add(informationRecue);
		
		
		
		//System.out.println("\nsignal refléchi : "+informationMultitrajetList.get(0).toString());
		//System.out.println("\nsignal : "+informationMultitrajetList.get(1).toString());
		
		
		
		
		//une fois tous les trajets stockés dans la likedList d'Informations informationMultitrajetsList
		//on additionne chaque élément à son emplacement dans l'Information retounee informationMutlitrajet :
		i = 0;
		Float [] info = new Float[informationRecue.nbElements()];
		
		while(i<informationRecue.nbElements())
		{
			info[i] = 0f;
			j=0;
			while(j<informationMultitrajetList.size())
			{
				info[i] = info[i] + informationMultitrajetList.get(j).iemeElement(i);
				j++;
			}
			i++;
		}
		
		//On crée l'information à envoyer :
		informationMultitrajet = new Information<Float>(info);
		
		//System.out.println("\nsortie: "+informationMultitrajet.toString());
		
		return informationMultitrajet;
	}

}
