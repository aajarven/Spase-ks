/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import spaseimpakt.logiikka.Pelimoottori;

/**
 * Pelin voimakkain ase, joka tyhjentää koko näytön vihollisista (poislukien
 * boss).
 *
 * @author Anni Järvenpää
 */
public class Pommi implements Ase {

    Pelimoottori moottori;

    /**
     * Luo uuden pommin
     * @param moottori pelimoottori, jonka pyörittämään peliin pommi luodaan.
     */
    public Pommi(Pelimoottori moottori) {
        this.moottori = moottori;
    }

    /**
     * Poistaa pommin, koska pommi on aktiivinen vain yhden framen ajan.
     */
    @Override
    public void liiku() {
        moottori.poistaAse(this);
    }
    
    //TODO vahinko vihollisiin

}
