/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.kayttoliittyma;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.swing.JPanel;
import spaseimpakt.data.Alus;
import spaseimpakt.data.Laser;
import spaseimpakt.data.Piirrettava;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

/**
 * Varsinainen peli-ikkuna, jossa pelin tapahtumat näkyvät
 *
 * @author Anni Järvenpää
 */
public class Ikkuna extends JPanel {

    /**
     * Moottori, jonka tapahtumia piirretään
     */
    Pelimoottori moottori;

    /**
     * ikkunaan piirrettävä alus
     */
    Alus alus;

    CopyOnWriteArraySet<Piirrettava> piirrettavat;

    //TODO muut piirrettävät
    /**
     * Konstruktori
     *
     * @param alus pelaajan ohjaama alus, joka näytölle piirretään
     */
    public Ikkuna(Pelimoottori moottori) {
        this.moottori = moottori;
        this.piirrettavat = moottori.getPiirrettavat();
    }

    /**
     * Piirtää ikkunaan taustan ja aluksen, myöhemmin myös vihut ja ammukset
     * sekä taustan
     *
     * @param g käytettävä Graphics-olio
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Pelirunko.LEVEYS, Pelirunko.KORKEUS);

        for (Piirrettava piirrettava : piirrettavat) {
            if (piirrettava instanceof Laser) {
                int x = piirrettava.getX();
                while (x < Pelirunko.LEVEYS) {
                    g.drawImage(piirrettava.getSprite(), x, piirrettava.getY(), this);
                    x++;
                }
            } else {
                g.drawImage(piirrettava.getSprite(), piirrettava.getX(), piirrettava.getY(), this);
            }
        }

        piirraPisteet(g);

    }

    /**
     * Piirtää pelaajan pistemäärän näytön oikeaan yläkulmaan
     *
     * @param g grafiikkaolio, jota piirtämiseen käytetään
     */
    private void piirraPisteet(Graphics g) {
        g.setColor(Color.WHITE);
        Font fontti = new Font(Font.MONOSPACED, 1, 20);
        g.setFont(fontti);
        String pisteet = ((Integer) moottori.getPisteet()).toString();
        g.drawString(pisteet, Pelirunko.LEVEYS - pisteet.length() * 12 - 20, 30);
    }
}
