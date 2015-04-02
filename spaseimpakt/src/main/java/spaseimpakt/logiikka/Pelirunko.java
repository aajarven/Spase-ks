/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.logiikka;

import javax.swing.SwingUtilities;
import spaseimpakt.kayttoliittyma.GraafinenKayttoliittyma;

/**
 *
 * @author Anni Järvenpää
 */
public class Pelirunko {
    
    public static final int KORKEUS=600;
    public static final int LEVEYS=800;
    
    public static Pelimoottori moottori;
    private static GraafinenKayttoliittyma kayttoliittyma;
    
    
    //TODO pelaajan nimen kysyminen

    public static void main(String[] args) {
        kayttoliittyma=new GraafinenKayttoliittyma();
        moottori=new Pelimoottori(kayttoliittyma);
        kayttoliittyma.setMoottori(moottori);
        SwingUtilities.invokeLater(kayttoliittyma);
        
        moottori.start();
    }
    
    /**
     * Aloittaa pelin alusta
     */
    public static void restart(){
        //TODO mahdollisuus restartata
    }
    
}
