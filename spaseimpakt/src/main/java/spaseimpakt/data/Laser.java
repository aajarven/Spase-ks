/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

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
    private final int KESTO = 600;
    private Image sprite;

    /**
     * Luo uuden laserin.
     *
     * @param alus alus, joka ampui laserin
     * @param moottori pelimoottori, jonka pyörittämään peliin laser luotiin
     */
    public Laser(Alus alus, Pelimoottori moottori) {
        this.alus = alus;
        this.moottori = moottori;
        lueSprite();
        paivitaKoordinaatit();
        luontihetki = System.currentTimeMillis();
    }

    /**
     * Liikuttaa laseria aluksen mukana tai jos laserin on jo aika sammua,
     * poistaa sen.
     */
    @Override
    public void liiku() {
        paivitaKoordinaatit();

        if (System.currentTimeMillis() - luontihetki > KESTO) {
            moottori.poistaAse(this);
        }
    }

    /**
     * Muuttaa laserin koordinaatit aluksen koordinaattien mukaisiksi
     */
    private void paivitaKoordinaatit() {
        this.x = alus.getX() + alus.getSprite().getWidth(null);
        this.y = alus.getY() + alus.getSprite().getHeight(null) / 2 - sprite.getHeight(null) / 2;
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
        return sprite;
    }

    @Override
    public Polygon getVaikutusalue() {
        return new Polygon(new int[]{x, Pelirunko.LEVEYS, Pelirunko.LEVEYS, x}, new int[]{y - sprite.getHeight(null) / 2, y - sprite.getHeight(null) / 2, y + sprite.getHeight(null) / 2, y + sprite.getHeight(null) / 2}, 4);
    }

    /**
     * Lukee aluksen kuvan tiedostosta
     */
    private void lueSprite() {
        try {
            BufferedImage i = ImageIO.read(new File("resources/laser-tile.png"));
            sprite = i;
        } catch (IOException e) {
            System.out.println("Laserin kuvaa ei löytynyt");
        }
    }

}
