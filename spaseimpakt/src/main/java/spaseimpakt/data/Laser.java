/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import java.awt.Image;
import spaseimpakt.logiikka.Pelimoottori;

/**
 * Ase, joka tuhoaa kaiken aluksen edessä olevan.
 *
 * @author Anni Järvenpää
 */
public class Laser implements Ase, Piirrettava {

    Alus alus;
    Pelimoottori moottori;
    private int x;
    private int y;
    private long luontihetki;
    private final int KESTO = 200;

    /**
     * Luo uuden laserin.
     *
     * @param alus alus, joka ampui laserin
     * @param moottori pelimoottori, jonka pyörittämään peliin laser luotiin
     */
    public Laser(Alus alus, Pelimoottori moottori) {
        this.alus = alus;
        this.moottori = moottori;
        x = alus.getX();
        y = alus.getY();
        luontihetki = System.currentTimeMillis();
    }

    /**
     * Liikuttaa laseria aluksen mukana tai jos laserin on jo aika sammua,
     * poistaa sen.
     */
    @Override
    public void liiku() {
        this.x = alus.getX();
        this.y = alus.getY();

        if (System.currentTimeMillis() - luontihetki > KESTO) {
            moottori.poistaAse(this);
        }
    }

    /**
     *
     * @return laserin alkukohdan x-koordinaatti
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return laserin alkukohdan y-koordinaatti
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return aika, jonka yksittäinen laser palaa
     */
    public int getKESTO() {
        return KESTO;
    }

    /**
     *
     * @return laserin sprite
     */
    @Override
    public Image getSprite() {
        //TODO oispa kuva
        return null;
    }

}
