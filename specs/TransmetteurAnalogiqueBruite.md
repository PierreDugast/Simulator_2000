# TransmetteurAnalogiqueBruité

## Variables d'entrée

* #InformationRecue: Information<R>
* #InformationEmise: Information<E>
* #nbEchantillon : int
* #SNR : Float



## Méthodes 

* +TransmetteurAnalogiqueBruite(int nbEchantillon, float SNR)

* +recevoir(Information)
* +emettre()
* -calculPuissance( int nbEchantillon, Information informationRecue): Float
* -calculSigma(Float puissanceBruit, Float snr): Float
* -generationBruitBlanc(Float sigma): Information<Float>
* -ajoutSignalBruite(Information bruitBlanc)
* +equals(Object obj): Boolean