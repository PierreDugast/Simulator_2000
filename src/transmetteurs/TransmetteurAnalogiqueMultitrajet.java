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
	public void recevoir(Information information) throws InformationNonConforme 
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
	
	private Information<Float> creerMultitrajet(Integer[] dtList, Float[] arList, Information<Float> informationRecue) 
	{
		//Variable de type Information destinee a etre retounee
		Information<Float> informationMultitrajet;
		//Variable temporaire permettant de stocker un trajet
		Information<Float> informationTrajet; 
		//Liked list permettant de stocker tous les informationTrajet.
		LinkedList<Information<Float>> informationMultitrajetList = new LinkedList();

		//j est l'indice pour parcourrir les trajets
		int j = 0;
		//i est l'indice permettant de parcourrir un trajet.
		int i;
		
		//Tant qu'il reste des trajets � effectuer
		while (j<dtList.length)
		{
			//On instancie un nouveau trajet
			informationTrajet = new Information<Float>();
			
			//Pour cr�er un trajet d�cal� :
			//On ajoute des 0 au d�but de du nouvel objet "Information"
			i=0;
			while (i<dtList[j])
			{
				informationTrajet.add(0f);
				i++;
			}
			
			//Puis on ajoute les �l�ments de l'informationRecue initialement en enlevant les �l�ments finaux.
			//On change �galement l'amplitude (en fonction du trajet) des valeurs de l'informationRecue.
			i=0;
			while (i<(informationRecue.nbElements()-dtList.length))
			{
				informationTrajet.add(arList[j]*informationRecue.iemeElement(i));
				i++;
			}
			
			//l'information de chaque trajet est stock� dans la linkedList d'Informations informationMultitrajetList.
			informationMultitrajetList.add(informationTrajet);
			//on  passe au trajet suivant.
			j++;
		}
		
		//On ajoute �galement le trajet direct (informationRecue) � la LinkedList :
		informationMultitrajetList.add(informationRecue);
		
		//une fois tous les trajets stock�s dans la likedList d'Informations informationMultitrajetsList
		//on additionne chaque �l�ment � son emplacement dans l'Information retounee informationMutlitrajet :
		i = 0;
		Float [] info = new Float[informationRecue.nbElements()];
		
		while(i<informationMultitrajetList.size())
		{
			info[i] = 0f;
			j=0;
			while(j<dtList.length)
			{
				info[i] = info[i] + informationMultitrajetList.get(j).iemeElement(i);
				j++;
			}
			i++;
		}
		
		//On cr�e l'information � envoyer :
		informationMultitrajet = new Information<Float>(info);
		
		return informationMultitrajet;
	}

}
