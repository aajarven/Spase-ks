/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import spaseimpakt.logiikka.Pelimoottori;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import spaseimpakt.kayttoliittyma.GraafinenKayttoliittyma;

/**
 *
 * @author Anni Järvenpää
 */
public class PommiTest {
    
    Alus alus;
    Pelimoottori moottori;
    
    public PommiTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        moottori = new Pelimoottori(new GraafinenKayttoliittyma());
        alus = new Alus(10, 10, 20, 20, moottori);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void pomminVoiAmpua() {
        alus.ammuPommi();
        assertEquals("Pommi ei synny kun se ammutaan", 1, moottori.getAseet().size());
    }

    /**
     * Pommin poistumisen testaaminen
     */
    @Test
    public void liikkuminenToimii() {
        alus.ammuPommi();
        Pommi pommi = (Pommi) moottori.getAseet().get(0);
        pommi.liiku();
        assertEquals("Pommi ei katoa", 0, moottori.getAseet().size());
    }
    
}
