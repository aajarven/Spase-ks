/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.logiikka;

import spaseimpakt.data.Alus;
import spaseimpakt.data.Ase;
import spaseimpakt.data.Vihu;

/**
 * Luokka, jota käytetään törmäystestauksiin.
 * @author Anni Järvenpää
 */
public class TormaysTestaaja {

    public TormaysTestaaja() {
    }

    /**
     * Testaa, törmäävätkö parametreina annetut ase ja vihollinen toisiinsa
     * @param ase tutkittava ase
     * @param vihu tutkittava vihollinen
     * @return true jos tutkittavat oliot koskettavat toisiaan, false jos eivät
     */
    protected boolean tormaa(Ase ase, Vihu vihu) {
        return false;
        //TODO actual code
    }

    /**
     * Testaa, törmäävätkö parametreina annetut alus ja vihollinen toisiinsa
     * @param alus tutkittava alus
     * @param vihu tutkittava vihollinen
     * @return true jos tutkittavat oliot koskettavat toisiaan, false jos eivät
     */
    protected boolean tormaa(Alus alus, Vihu vihu) {
        return false;
        //TODO actual code
    }

}
