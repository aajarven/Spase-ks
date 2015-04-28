/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import java.awt.Image;
import java.awt.Polygon;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

/**
 * Pelin "tavallinen" vihollinen
 *
 * @author Anni Järvenpää
 */
public class Pikkuvihu implements Vihu, Piirrettava {

    private int x;
    private int y;
    private int maxY;
    private final Image sprite;
    private final Liikutin liikutin;
    private final Pelimoottori moottori;

    /**
     * Konstruktori.
     *
     * @param x
     * @param y
     * @param sprite
     * @param liikutin
     * @param moottori
     */
    public Pikkuvihu(int x, Image sprite, Liikutin liikutin, Pelimoottori moottori) {
        this.x = x;
        this.sprite = sprite;
        this.liikutin = liikutin;
        this.moottori = moottori;
        this.y = liikutin.getY();
        maxY = Pelirunko.KORKEUS - sprite.getHeight(null);
    }

    /**
     * liikuttaa vihollista yhden askeleen
     */
    @Override
    public void liiku() {
        int[] uudetKoordinaatit = liikutin.liiku();
        x = uudetKoordinaatit[0];
        y = uudetKoordinaatit[1];
        if (y < 0) {
            y = 0;
        } else if (y > maxY) {
            y = maxY;
        }
        if (x < 0 - sprite.getWidth(null)) {
            moottori.poistaVihu(this);
        }
    }

    /**
     * palauttaa vihollisen x-koordinaatin
     *
     * @return vasemman yläkulman x-koordinaatti
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * palauttaa vihollisen y-koordinaatin
     *
     * @return vasemman yläkulman y-koordinaatti
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Palauttaa vihollisen spriten
     *
     * @return vihollisen sprite
     */
    @Override
    public Image getSprite() {
        return sprite;
    }

    /**
     *
     * @return vihollisen liikumisesta määräävä liikutin
     */
    public Liikutin getLiikutin() {
        return this.liikutin;
    }

    @Override
    public Polygon getBoundingBox() {
        return new Polygon(new int[]{x, x, x + sprite.getWidth(null), x + sprite.getWidth(null)}, new int[]{y, y + sprite.getHeight(null), y + sprite.getHeight(null), y}, 4);
    }

}
