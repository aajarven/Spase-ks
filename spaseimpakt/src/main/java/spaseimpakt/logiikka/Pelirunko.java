/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.logiikka;

import javax.swing.SwingUtilities;
import spaseimpakt.kayttoliittyma.GraafinenKayttoliittyma;

/**
 * Main, luo tarvittavat oliot ja käynnistää pelin.
 * @author Anni Järvenpää
 */
public class Pelirunko {

    public static final int KORKEUS = 600;
    public static final int LEVEYS = 800;

    public static Pelimoottori moottori;
    private static GraafinenKayttoliittyma kayttoliittyma;
    
    public static boolean ekaPeli;
    public static String pelaajanNimi="Anonyymi";

    public static void main(String[] args) {
        ekaPeli=true;
        alusta();
    }

    /**
     * Luo uuden pelimoottorin ja käyttöliittymän, käynnistää pelin
     */
    private static void alusta() {
        kayttoliittyma = new GraafinenKayttoliittyma();
        moottori = new Pelimoottori(kayttoliittyma);
        kayttoliittyma.setMoottori(moottori);
        SwingUtilities.invokeLater(kayttoliittyma);
        moottori.start();
    }

    /**
     * Aloittaa pelin alusta
     */
    public static void restart(){ // TODO: tosi ruma tapa tehdä tämä, keksi parempi joka toimii
        kayttoliittyma.sulje();
        moottori.lopeta();
        ekaPeli=false;
        alusta();
        
        
//        ekaPeli=false;
//        moottori.lopeta();
//        kayttoliittyma.tyhjenna();
//        moottori=new Pelimoottori(kayttoliittyma);
////        maailma.setMoottori(moottori);
//        kayttoliittyma.setMoottori(moottori);
//        moottori.start();
//        SwingUtilities.invokeLater(kayttoliittyma);
    }

}
