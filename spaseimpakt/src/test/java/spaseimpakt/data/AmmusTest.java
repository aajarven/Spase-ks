/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import spaseimpakt.logiikka.Pelimoottori;
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
    Alus alus;
    Pelimoottori moottori;

    public AmmusTest() {
        alkuX = 5;
        alkuY = 20;
        maxX=25;
        moottori=new Pelimoottori();
        alus=new Alus(alkuX, alkuY, maxX, 20, moottori);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        alus.ammuLaukaus();
        ammus = (Ammus) moottori.getAseet().get(0);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLiiku() {
        for (int i = 0; i < 10; i++) {
            ammus.liiku();
            assertEquals("Ammuksen liikkuessa sen x-koordinaatti muuttuu väärin", alkuX + (i+1)* Ammus.NOPEUS, ammus.getX());
            assertEquals("Ammuksen liikkuessa sen y-koordinaatin ei pitäisi muuttua", alkuY, ammus.getY());
        }

    }
    
    @Test
    public void tuhoutuuReunanJalkeen(){
        
        for(int i=1; Ammus.NOPEUS*i+alkuX<=maxX; i++){
            assertEquals("Ammus poistuu liian aikaisin", 1, moottori.getAseet().size());
            ammus.liiku();
        }
        assertTrue("Ammus poistuu jo kun se koskettaa reunaa mutta ei ole vielä ylittänyt sitä", moottori.getAseet().size()==1);
        ammus.liiku();
        assertEquals("Ammusta ei poisteta kun se poistuu pelialueelta", 0, moottori.getAseet().size());
    }

    
    
}
