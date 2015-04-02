/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.kayttoliittyma;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.swing.JPanel;
import spaseimpakt.data.Alus;
import spaseimpakt.data.Piirrettava;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

public class Ikkuna extends JPanel {

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
        this.piirrettavat=moottori.getPiirrettavat();
    }

    //TODO vihut, ammukset
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

        for(Piirrettava piirrettava:piirrettavat){
            g.drawImage(piirrettava.getSprite(), piirrettava.getX(), piirrettava.getY(), this);
        }
    }
}
