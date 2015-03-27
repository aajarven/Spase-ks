/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import logiikka.Pelimoottori;
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
        moottori=new Pelimoottori();
        alus=new Alus(10, 10, 20, 20, moottori);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of liiku method, of class Pommi.
     */
    @Test
    public void testLiiku() {
        alus.ammuPommi();
        Pommi pommi=(Pommi) moottori.getAseet().get(0);
        pommi.liiku();
        assertTrue("Pommi ei katoa", !moottori.getAseet().contains(pommi));
    }
    
}
