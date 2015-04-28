/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.data;

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
public class TulosTest {
    
    static Tulos tulos;
    static String nimi;
    static int pisteet;
    
    public TulosTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        nimi = "Jokulainen";
        pisteet = 715517;
        tulos = new Tulos(nimi, pisteet);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPisteet method, of class Tulos.
     */
    @Test
    public void testGetPisteet() {
        assertEquals("Tulos palauttaa väärät pisteet", pisteet, tulos.getPisteet());
    }

    /**
     * Test of getNimi method, of class Tulos.
     */
    @Test
    public void testGetNimi() {
        assertEquals("Tulos palauttaa väärän nimen", nimi, tulos.getNimi());
    }

    /**
     * Test of compareTo method, of class Tulos.
     */
    @Test
    public void testCompareTo() {
        Tulos pienempi = new Tulos("joku", pisteet-1);
        Tulos sama = new Tulos("joku muu", pisteet);
        Tulos isompi = new Tulos("kolmas tyyppi", pisteet+1);
        
        assertEquals("Tulos vertautuu väärin kun sitä verrataan pienemmän pistemäärän tulokseen", -1, tulos.compareTo(pienempi));
        assertEquals("Tulos vertautuu väärin kun sitä verrataan saman pistemäärän tulokseen", tulos.getNimi().compareTo(sama.getNimi()), tulos.compareTo(sama));
        assertEquals("Tulos vertautuu väärin kun sitä verrataan suuremman pistemäärän tulokseen", 1, tulos.compareTo(isompi));
    }
    
}
