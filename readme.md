# Simulator_2000

                                
## Descriptif du dossier

/
├── src/*     	 	//Répertoire contenant le code source Java                       
│    └── ...        
├── bin/*      	  	//Répertoire contenant les binaires Java compilés
│   └── ...
├── docs/       	//Répertoire contenant les fichiers relatifs à la Javadoc
│   └── ...
└── compile    	//Script permettant de compiler le code source Java
└── genDoc     	//Script permettant de génére la Javadoc
└── cleanAll      	//Script permettant de supprimer les fichiers compilés et la Javadoc
└── simulateur 	//Script de lancement d’une simulation
└── runTests   	//Script d'autotests
└── readme     	//Le fichier que vous êtes en train de lire, explique le contenu du dossier et la manière d'utiliser le logiciel.

                                
## Guide d'utilisation du logiciel

* Compiler le programme : exécuter ./compile

* Générer la Javadoc : exécuter ./genDoc 

* Lancer le script d'autotests : exécuter ./runTests

* Lancer la simulation : exécuter ./simulateur [arguments]  //Les arguments possibles sont listés dans le fichier commande_unique.pdf sur Moodle
    →  Exemples : ./simulateur -mess 1234 -s  
                ./simulateur -mess 10011011 -s
                
                
* Supprimer les fichiers compilés et la Javadoc : exécuter ./cleanAll






