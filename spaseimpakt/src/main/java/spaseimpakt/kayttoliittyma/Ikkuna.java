/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.kayttoliittyma;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import spaseimpakt.data.Alus;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

public class Ikkuna extends JPanel{
    
    
    Pelimoottori moottori;
    
    
    /**
     * ikkunaan piirrettävä alus
     */
    Alus alus;
    
    
    //TODO muut piirrettävät
    
    /**
     * Konstruktori
     * @param alus pelaajan ohjaama alus, joka näytölle piirretään
     */
    public Ikkuna(Pelimoottori moottori){
        this.alus=moottori.getAlus();
    }
    
    //TODO vihut, ammukset
    
    /**
     * Piirtää ikkunaan taustan ja aluksen, myöhemmin myös vihut ja ammukset sekä taustan
     * @param g käytettävä Graphics-olio
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Pelirunko.LEVEYS, Pelirunko.KORKEUS);
        
        g.drawImage(alus.getSprite(), alus.getX(), alus.getY(), this);        
    }
}