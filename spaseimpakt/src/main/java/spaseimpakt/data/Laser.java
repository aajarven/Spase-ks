/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import java.awt.Image;
import spaseimpakt.logiikka.Pelimoottori;

/**
 * Ase, joka tuhoaa kaiken aluksen edessä olevan.
 * @author Anni Järvenpää
 */
public class Laser implements Ase, Piirrettava {

    Alus alus;
    Pelimoottori moottori;
    private int x;
    private int y;
    private long luontihetki;
    private final int KESTO = 200;

    public Laser(Alus alus, Pelimoottori moottori) {
        this.alus = alus;
        this.moottori = moottori;
        x = alus.getX();
        y = alus.getY();
        luontihetki = System.currentTimeMillis();
    }

    @Override
    public void liiku() {
        this.x = alus.getX();
        this.y = alus.getY();

        if (System.currentTimeMillis() - luontihetki > KESTO) {
            moottori.poistaAse(this);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getKESTO() {
        return KESTO;
    }

    @Override
    public Image getSprite() {
        //TODO oispa kuva
        return null;
    }

}
