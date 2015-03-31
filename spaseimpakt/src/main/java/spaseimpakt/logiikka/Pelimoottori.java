/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.logiikka;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import spaseimpakt.data.Alus;
import spaseimpakt.data.Ase;
import spaseimpakt.data.Piirrettava;
import spaseimpakt.kayttoliittyma.GraafinenKayttoliittyma;

/**
 * Hoitaa pelin pyörittämisen ja ohjaa muiden luokkien toimintaa
 * @author Anni
 */
public class Pelimoottori extends Thread {

    //TODO kuolinilmoitus
    
    /**
     * True jos peli on käynnissä, muuten false
     */
    private boolean kaynnissa;
    
    /**
     * Viive kahden ruudunpäivityksen välillä
     */
    private final int STEP = 1000/75;
    
    /**
     * Pelimaailmassa liikkuva alus
     */
    private Alus alus;

    ArrayList<Ase> aseet;
    
    private GraafinenKayttoliittyma kayttoliittyma;
    
    private HashSet<Piirrettava> piirrettavat;

    public Pelimoottori() {
        aseet=new ArrayList<>();
    }
    
    //TODO muu pelimoottori

    public void lisaaAse(Ase ase) {
        aseet.add(ase);
    }

    public void poistaAse(Ase ase) {
        aseet.remove(ase);
    }
    
    public ArrayList<Ase> getAseet(){
        return aseet;
    }
    
    /**
     * Konstruktori
     * @param kayttoliittyma käyttöliittymä, joka näyttää pelin tapahtumat
     * @param maailma maailma, jonka tilannetta päivitetään
     */
    public Pelimoottori(GraafinenKayttoliittyma kayttoliittyma) {
        this.kayttoliittyma = kayttoliittyma;
        this.kaynnissa = true;
        this.alus = new Alus(0, 200, 600, 400, this);
    }

    /**
     * Varsinainen pelitapahtuma. 
     * Liikuttaa alusta, siirtää maailmaa ja päivittää käyttöliittymän, 
     * minkä jälkeen tutkii, tuleeko pelin suoritusta vielä jatkaa ja toimii asianmukaisesti.
     */
    @Override
    public void run() {
        
        //TODO alkuun varmaan dialogi, jossa kysytään pelaajan nimeä
        
        do{
            alus.liiku();
            for(Ase ase:aseet){
                ase.liiku();
            }
            kayttoliittyma.piirra();
            
             //TODO do stuff
            
            odota();
        } while(kaynnissa);
    }

    /**
     * Pysäyttää pelin suorituksen muuttujan step osoittamaksi ajaksi.
     */
    public void odota() {
        try {
            Thread.sleep(STEP);
        } catch (InterruptedException e) {
            System.out.println("sleeping interrupted");
            e.printStackTrace();
//            System.exit(0);
        }
    }
    
    /**
     * Lopettaa pelitilanteen päivittämisen.
     */
    public void lopeta(){
        kaynnissa=false;
    }

    public HashSet<Piirrettava> getPiirrettavat() {
        return piirrettavat;
    }

    
    
}