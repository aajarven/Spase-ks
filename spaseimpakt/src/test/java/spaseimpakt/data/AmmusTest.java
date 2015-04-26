/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import java.util.ArrayList;
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
public class AmmusTest {

    int alusX;
    int alusY;
    int ammusX;
    int ammusY;
    int ruudunLeveys;
    Ammus ammus;
    Alus alus;
    Pelimoottori moottori;

    public AmmusTest() {
        alusX = 5;
        alusY = 20;
        ruudunLeveys = 100;
        moottori = new Pelimoottori(new GraafinenKayttoliittyma());
        alus = new Alus(alusX, alusY, ruudunLeveys, 100, moottori);
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
        ammusY = ammus.getY();
        ammusX = ammus.getX();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLiiku() {
        for (int i = 0; i < 10; i++) {
            ammus.liiku();
            assertEquals("Ammuksen liikkuessa sen x-koordinaatti muuttuu väärin", ammusX + (i + 1) * Ammus.NOPEUS, ammus.getX());
            assertEquals("Ammuksen liikkuessa sen y-koordinaatin ei pitäisi muuttua", ammusY, ammus.getY());
        }

    }

    @Test
    public void tuhoutuuReunanJalkeen() { //TODO testin läpäisy

        for (int i = 1; Ammus.NOPEUS * i + ammusX <= ruudunLeveys - ammus.getSprite().getWidth(null); i++) {
            assertEquals("Ammus poistuu liian aikaisin", 1, moottori.getAseet().size());
            ammus.liiku();
        }
        assertTrue("Ammus poistuu jo kun se koskettaa reunaa mutta ei ole vielä ylittänyt sitä", moottori.getAseet().size() == 1);
        ammus.liiku();
        assertEquals("Ammusta ei poisteta kun se poistuu pelialueelta", 0, moottori.getAseet().size());
    }

}
