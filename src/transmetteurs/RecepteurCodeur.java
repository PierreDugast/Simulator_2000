package transmetteurs;

import information.Information;
import information.InformationNonConforme;

public class RecepteurCodeur <R,T> extends Transmetteur<R,T>{
	
	public int nbEchantillon;
	
	public RecepteurCodeur(int nbEchantillon) {
		super();
		this.nbEchantillon=nbEchantillon;
	}

	public void recevoir(Information<R> information) throws InformationNonConforme {
		informationRecue=information;
		emettre();
		
	}

	public void emettre() throws InformationNonConforme {
		//Tableau qui va contenir la nouvelle suite de bits
		Boolean [] emission = new Boolean[this.informationRecue.nbElements()/3];
		Boolean [][] trueList = {{false,false,true},{true,false,false},{true,false,true},{true,true,true}};
		Boolean [][] falseList = {{false,false,false},{false,true,false},{false,true,true},{true,true,false}};
		Boolean [] oneBitList = new Boolean[3];
		//Boucle permettant de créer pour chaque bits reçu trois bits à 101 pour un 1 reçu et trois bits à 010 pour un 0 reçu
		int i = 0;
		int j = 0;
		//Variable qui contient la valeur d'un seul bit décodé
		Boolean elem = null;
		while(i<this.informationRecue.nbElements()) {
			oneBitList[0] = (Boolean) this.informationRecue.iemeElement(i);
			oneBitList[1] = (Boolean) this.informationRecue.iemeElement(i+1);
			oneBitList[2] = (Boolean) this.informationRecue.iemeElement(i+2);
			//System.out.println(""+oneBitList[0]+" "+oneBitList[1]+" "+oneBitList[2]+" | ");
			j = 0;
			while(j<trueList.length)
			{
				if ((oneBitList[0]==trueList[j][0])&&(oneBitList[1]==trueList[j][1])&&(oneBitList[2]==trueList[j][2]))
					elem = true;
				else if ((oneBitList[0]==falseList[j][0])&&(oneBitList[1]==falseList[j][1])&&(oneBitList[2]==falseList[j][2]))
					elem = false;
				j++;
			}
			emission[i/3] = elem;
			i=i+3;
		}
		
		//Création de l'information à émettre
		this.informationEmise = new Information(emission);
		
		//System.out.println(informationEmise);
		
		//Envoie l'information aux différentes destinations connectées présente dans la variable destinationsConnectees
		for(i=0;i<destinationsConnectees.size();i++){
			destinationsConnectees.get(i).recevoir(this.informationEmise);
		}
	}
	
}
