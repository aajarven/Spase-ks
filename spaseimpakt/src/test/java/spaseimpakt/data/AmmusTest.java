/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import logiikka.Pelimoottori;
import spaseimpakt.data.Ammus;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anni Järvenpää
 */
public class AmmusTest {

    int alkuX;
    int alkuY;
    int maxX;
    Ammus ammus;
    Pelimoottori moottori;

    public AmmusTest() {
        alkuX = 5;
        alkuY = 20;
        maxX=25;
        moottori=new Pelimoottori();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ammus = new Ammus(alkuX, alkuY, maxX, moottori);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLiiku() {
        for (int i = 0; i < 10; i++) {
            ammus.liiku();
            assertEquals("Ammuksen liikkuessa sen x-koordinaatti muuttuu väärin", alkuX + (i + 1) * Ammus.NOPEUS, ammus.getX());
            assertEquals("Ammuksen liikkuessa sen y-koordinaatin ei pitäisi muuttua", alkuY, ammus.getY());
        }

    }
    
    @Test
    public void tuhoutuuReunassa(){
        for(int i=0; Ammus.NOPEUS*i+alkuX<=maxX; i++){
            ammus.liiku();
        }
        assertTrue("Ammusta ei poisteta kun se poistuu pelialueelta", !moottori.getAseet().contains(ammus));
    }

    
    
}
