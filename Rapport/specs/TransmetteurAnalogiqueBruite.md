# TransmetteurAnalogiqueBruité

## Variables d'entrée

* #InformationRecue: Information<R>
* #InformationEmise: Information<E>
* #nbEchantillon : int
* #SNR : Float



## Méthodes 

* +TransmetteurAnalogiqueBruite(int nbEchantillon, float SNR)
  * 

* +recevoir(Information)

* +emettre()

* -calculPuissance( int nbEchantillon, Information informationRecue): Float

  * Somme des échantillons² / nbEchantillons 

* -calculSigma(Float puissanceBruit, Float snr): Float

  * (On a puissanceBruit = Ps / (10^(SNR/10))) ?

  * sigma = sqrt(puissanceBruit)

    

* -generationBruitBlanc(Float sigma): Information<Float>

  * bruitblanc = sigma * sqrt(-2ln(1-a(1)))*cos 2*pi*a(2)

* -ajoutSignalBruite(Information bruitBlanc)

* +equals(Object obj): Boolean

# 