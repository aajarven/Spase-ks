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

    public Pommi(Pelimoottori moottori) {
        this.moottori = moottori;
    }

    @Override
    public void liiku() {
        moottori.poistaAse(this);
    }

}
