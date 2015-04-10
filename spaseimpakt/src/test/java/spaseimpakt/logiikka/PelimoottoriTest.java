/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.logiikka;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import spaseimpakt.data.Alus;
import spaseimpakt.data.Ammus;
import spaseimpakt.data.Ase;
import spaseimpakt.data.Piirrettava;
import spaseimpakt.kayttoliittyma.GraafinenKayttoliittyma;
import static spaseimpakt.logiikka.Pelirunko.moottori;

/**
 *
 * @author Anni Järvenpää
 */
public class PelimoottoriTest {
    
    Pelimoottori moottori;

    public PelimoottoriTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        GraafinenKayttoliittyma kali = new GraafinenKayttoliittyma();
        moottori = new Pelimoottori(kali);
        kali.setMoottori(moottori);
    }

    @After
    public void tearDown() {
    }

    /**
     * Pelimoottoriin aseen lisäämisen testaus
     */
    @Test
    public void testLisaaAse() {
        lisaaAmmus();
        assertEquals("Yhden aseen lisääminen ei onnistunut", 1, moottori.getAseet().size());
    }

    /**
     * Monen aseen lisäämisen testaus
     */
    @Test
    public void testLisaaMontaAsetta() {
        for (int i = 1; i < 75; i++) {
            lisaaAmmus();
            assertEquals("Monen ammuksen lisääminen ei onnistunut", i, moottori.getAseet().size());
        }
    }

    /**
     * Aseen poistamisen testi.
     */
    @Test
    public void testPoistaAse() {
        Ammus ammus1 = new Ammus(3, 4, 10, moottori);
        Ammus ammus2 = new Ammus(5, 6, 11, moottori);
        Ammus ammus3 = new Ammus(7, 8, 12, moottori);
        moottori.lisaaAse(ammus1);
        moottori.lisaaAse(ammus2);
        moottori.lisaaAse(ammus3);

        moottori.poistaAse(ammus1);
        assertEquals("ensimmäisen ammuksen poistamisen jälkeen ammuksia ei ollut odotettua määrää", 2, moottori.getAseet().size());
        moottori.poistaAse(ammus2);
        assertEquals("toisen ammuksen poistamisen jälkeen ammuksia ei ollut odotettua määrää", 1, moottori.getAseet().size());
        moottori.poistaAse(ammus3);
        assertEquals("kolmannen ammuksen poistamisen jälkeen ammuksia ei ollut odotettua määrää", 0, moottori.getAseet().size());
    }

    /**
     * Aseen, joka ei ole listassa, poistamisen testi
     */
    @Test
    public void testPoistaOlematon() {
        moottori.poistaAse(new Ammus(3, 4, 10, moottori));
        assertEquals("Olemattoman aseen poistaminen toimii väärin", 0, moottori.getAseet().size());
    }


    /**
     * Test of getPiirrettavat method, of class Pelimoottori.
     */
    @Test
    public void testGetPiirrettavat() {
        assertEquals("Piirrettävät eivät pelin alussa sisällä alusta yksin", 1, moottori.getPiirrettavat().size());
        lisaaAmmus();
        assertEquals("Ammuksen lisääminen ei lisä piirrettäviä oikein", 2, moottori.getPiirrettavat().size());
    }

    private void lisaaAmmus() {
        Ase ase = new Ammus(5, 5, 20, moottori);
        moottori.lisaaAse(ase);
    }

}
