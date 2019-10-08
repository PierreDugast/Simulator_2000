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
		
		//comptage du nombre de trajets � effectuer (la liste passee en argument est toujours de m�me longeur)
		int nbTrajet = 0;
		while (this.dtList[nbTrajet] != null)
			nbTrajet++;
		
		//j est l'indice pour parcourrir les trajets
		int j = 0;
		//i est l'indice permettant de parcourrir un trajet.
		int i=0;
		
		//Tant qu'il reste des trajets � effectuer
		while (j<nbTrajet)
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
			while (i<(informationRecue.nbElements()-dtList[j]))
			{
				informationTrajet.add(arList[j]*((Float)informationRecue.iemeElement(i)));
				i++;
			}
			
			//l'information de chaque trajet est stock� dans la linkedList d'Informations informationMultitrajetList.
			informationMultitrajetList.add(informationTrajet);
			//on  passe au trajet suivant.
			j++;
		}
		
		//On ajoute �galement le trajet direct (informationRecue) � la LinkedList :
		informationMultitrajetList.add(informationRecue);
		
		
		
		//System.out.println("\nsignal refl�chi : "+informationMultitrajetList.get(0).toString());
		//System.out.println("\nsignal : "+informationMultitrajetList.get(1).toString());
		
		
		
		
		//une fois tous les trajets stock�s dans la likedList d'Informations informationMultitrajetsList
		//on additionne chaque �l�ment � son emplacement dans l'Information retounee informationMutlitrajet :
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
		
		//On cr�e l'information � envoyer :
		informationMultitrajet = new Information<Float>(info);
		
		//System.out.println("\nsortie: "+informationMultitrajet.toString());
		
		return informationMultitrajet;
	}

}
