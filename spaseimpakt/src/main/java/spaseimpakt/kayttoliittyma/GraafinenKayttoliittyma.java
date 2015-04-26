/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.kayttoliittyma;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

/**
 * Luo käyttöliittymän ja hoitaa sen käyttämiseen liittyviä asioita sisältäen
 * mm. tarvittavat kuuntelijat
 *
 * @author Anni
 */
public class GraafinenKayttoliittyma implements Runnable {

    /**
     * JFrame johon komponentit asetellaan
     */
    private static JFrame frame;

    /**
     * Käyttöliittymän se osa, jossa itse pelitapahtumat (alus, ammukset,
     * viholliset yms) näytetään.
     */
    private Ikkuna ikkuna;

    /**
     * Pelimoottori, joka huolehtii näytettävän pelin edistämisestä
     */
    private Pelimoottori moottori;

    /**
     * Luo uuden graafisen käyttöliittymän, joka näyttää parametrina annetun
     * pelimoottorin tapahtumat
     *
     * @param moottori pelimoottori, jonka ajaman pelin tapahtumat piirretään
     */
    public GraafinenKayttoliittyma(Pelimoottori moottori) {
        this.moottori = moottori;
    }

    /**
     * Konstruktori. Pelimoottori täytyy asettaa erikseen käyttäen setteriä.
     *
     * @see setPelimoottori
     */
    public GraafinenKayttoliittyma() {
    }

    public void setPelimoottori(Pelimoottori moottori) {
        this.moottori = moottori;
    }

    /**
     * Luo framen sekä kutsuu metodeja komponenttien ja kuuntelijoiden
     * luomiseksi
     */
    @Override
    public void run() {
        frame = new JFrame("Spase Impakt");
//        frame.setPreferredSize(new Dimension(LEVEYS, KORKEUS+10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        luoKomponentit(frame.getContentPane());

        //TODO näppäimistökuuntelija
        frame.addKeyListener(new NappaimistoKuuntelija(moottori));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Lisää parametrina annettuun containeriin yläpalkin ja peli-ikkunan
     *
     * @param container container, johon komponentit lisätään
     */
    private void luoKomponentit(Container container) {
        container.add(luoMenupalkki(), BorderLayout.NORTH);
        ikkuna = new Ikkuna(moottori);
        ikkuna.setPreferredSize(new Dimension(Pelirunko.LEVEYS, Pelirunko.KORKEUS));
        container.add(ikkuna, BorderLayout.CENTER);
    }
    
    /**
     * Luo ja palauttaa yläpalkin peli-ikkunaan. 
     * Palkki mahdollistaa pelin aloittamisen alusta, pelaajan nimen muuttamisen, 
     * luolan valitsemisen, highscorejen näyttämisen sekä ohjeiden ja tietojen näyttämisen.
     * @return menupalkki
     */
    private JMenuBar luoMenupalkki() {
        JMenuBar ylapalkki = new JMenuBar();


        JMenu pelimenu = new JMenu("Peli");

        JMenuItem restartnappi = new JMenuItem("Aloita alusta");
        restartnappi.addActionListener(new RestartKuuntelija());
        pelimenu.add(restartnappi);

        JMenuItem pelaajaValintaNappi = new JMenuItem("Pelaajan nimi");
        pelaajaValintaNappi.addActionListener(new PelaajaValintaKuuntelija());
        pelimenu.add(pelaajaValintaNappi);

//        JMenuItem highscorenappi = new JMenuItem("Highscores");
//        highscorenappi.addActionListener(new HighscoreKuuntelija());
//        pelimenu.add(highscorenappi);

        JMenu apuamenu = new JMenu("Apua");
        JMenuItem ohjenappi = new JMenuItem("Ohjeet");
        ohjenappi.addActionListener(new OhjeetKuuntelija());
        apuamenu.add(ohjenappi);

        JMenuItem tietojanappi = new JMenuItem("Tietoja");
        tietojanappi.addActionListener(new TietojaKuuntelija());
        apuamenu.add(tietojanappi);

        ylapalkki.add(pelimenu);
        ylapalkki.add(apuamenu);
        return ylapalkki;
    }

    /**
     * Palauttaa framen
     *
     * @return frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Mahdollistaa pelin tapahtumia ohjaavan pelimoottorin asettamisen
     *
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
        }
    }
    
    /**
     * Mahdollistaa pelaajan nimen vaihtamisen kutsumalla pelimoottorin vaihdaPelaajanNimi-metodia.
     * @see Pelimoottori#vaihdaPelaajanNimi() 
     */
    private class PelaajaValintaKuuntelija implements ActionListener {// pelaajan nimen valinnan voisi tehdä myös JDialogilla kuten chompissa

        @Override
        public void actionPerformed(ActionEvent e) {
            moottori.vaihdaPelaajanNimi();
        }
    }
    
    /**
     * Mahdollistaa pelin aloittamisen alusta kutsumalla pelirungon restart-metodia
     * @see Pelirunko#restart() 
     */
    private class RestartKuuntelija implements ActionListener {

        //Tänne highscore
        public RestartKuuntelija() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Pelirunko.restart();
        }
    }
    
    private static class OhjeetKuuntelija implements ActionListener {

        public OhjeetKuuntelija() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Splash t. ohjeetkuuntelija");
        }
    }
    
    private static class TietojaKuuntelija implements ActionListener {

        public TietojaKuuntelija() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Splash t. tietojakuuntelija");
        }
    }
}
