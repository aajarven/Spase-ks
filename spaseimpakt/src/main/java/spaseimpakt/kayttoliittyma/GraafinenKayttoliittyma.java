/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.kayttoliittyma;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

/**
 * Luo käyttöliittymän ja hoitaa sen käyttämiseen liittyviä asioita sisältäen mm. tarvittavat kuuntelijat
 * @author Anni
 */
public class GraafinenKayttoliittyma implements Runnable {

    
    /**
     * JFrame johon komponentit asetellaan
     */
    private static JFrame frame;
    
    /**
     * Käyttöliittymän se osa, jossa itse pelitapahtumat (alus, ammukset, viholliset yms) näytetään.
     */
    private Ikkuna ikkuna;

    /**
     * Pelimoottori, joka huolehtii näytettävän pelin edistämisestä
     */
    private Pelimoottori moottori;

    /**
     * Konstruktori
     * @param moottori pelimoottori, jonka ajaman pelin tapahtumat piirretään
     */
    public GraafinenKayttoliittyma(Pelimoottori moottori) {
        this.moottori=moottori;
    }

    /**
     * Konstruktori.
     * Pelimoottori täytyy asettaa erikseen käyttäen setteriä.
     * @see setPelimoottori
     */
    public GraafinenKayttoliittyma() {
    }
    
    public void setPelimoottori(Pelimoottori moottori){
        this.moottori=moottori;
    }

    /**
     * Luo framen sekä kutsuu metodeja komponenttien ja kuuntelijoiden luomiseksi
     */
    @Override
    public void run() {
        frame = new JFrame("Spase Impakt");
//        frame.setPreferredSize(new Dimension(LEVEYS, KORKEUS+10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        luoKomponentit(frame.getContentPane());

        //TODO näppäimistökuuntelija
//        frame.addKeyListener(kuuntelija);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Lisää parametrina annettuun containeriin yläpalkin ja peli-ikkunan
     * @param container container, johon komponentit lisätään
     */
    private void luoKomponentit(Container container) {
        Ikkuna ikkuna=new Ikkuna(moottori);
        ikkuna.setPreferredSize(new Dimension(Pelirunko.LEVEYS, Pelirunko.KORKEUS));
        container.add(ikkuna);
    }


    
    /**
     * Palauttaa framen
     * @return frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Mahdollistaa pelin tapahtumia ohjaavan pelimoottorin asettamisen
     * @param moottori käytettävä pelimoottori
     */
    public void setMoottori(Pelimoottori moottori) {
        this.moottori = moottori;
    }

    /**
     * Päivittää grafiikat ikkunan repaint-metodilla
     * 
     * @see Ikkuna#repaint
     */
    public void piirra() {
        if (ikkuna != null) {
            ikkuna.repaint();
        } else {
//            System.out.println("ikkuna oli null");
        }
    }
}