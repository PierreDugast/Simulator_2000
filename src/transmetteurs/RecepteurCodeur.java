package transmetteurs;

import information.Information;
import information.InformationNonConforme;

public class RecepteurCodeur extends Transmetteur<Boolean,Boolean>{
	
	public int nbEchantillon;
	
	public RecepteurCodeur(int nbEchantillon) {
		super();
		this.nbEchantillon=nbEchantillon;
		informationEmise = new Information<Boolean>();
	}

	public void recevoir(Information<Boolean> information) throws InformationNonConforme {
		informationRecue=information;
		emettre();
	}

	public void emettre() throws InformationNonConforme {
		
		Boolean [][] trueList = {{false,false,true},{true,false,false},{true,false,true},{true,true,true}};
		Boolean [][] falseList = {{false,false,false},{false,true,false},{false,true,true},{true,true,false}};
		Boolean [] oneBitList = new Boolean[3];
		//Boucle permettant de cr�er pour chaque bits re�u trois bits � 101 pour un 1 re�u et trois bits � 010 pour un 0 re�u
		int i = 0;
		int j = 0;
		//Variable qui contient la valeur d'un seul bit d�cod�
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
			informationEmise.add(elem);
			i=i+3;
		}
		
		//Envoie l'information aux diff�rentes destinations connect�es pr�sente dans la variable destinationsConnectees
		for(i=0;i<destinationsConnectees.size();i++){
			destinationsConnectees.get(i).recevoir(this.informationEmise);
		}
	}
	
}
