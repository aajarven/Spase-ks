/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import java.awt.Image;
import java.awt.Polygon;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

/**
 * Pelin voimakkain ase, joka tyhjentää koko näytön vihollisista (poislukien
 * boss).
 *
 * @author Anni Järvenpää
 */
public class Pommi implements Ase{

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

    @Override
    public Polygon getVaikutusalue() {
        return new Polygon(new int[]{0, 0, Pelirunko.LEVEYS, Pelirunko.LEVEYS}, new int[]{0, Pelirunko.KORKEUS, Pelirunko.KORKEUS, 0}, 4);
    }

}
