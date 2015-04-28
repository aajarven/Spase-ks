/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.utils;

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
public class StringMuotoilijaTest {
    
    static StringMuotoilija muotoilija;
    
    public StringMuotoilijaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        muotoilija = new StringMuotoilija();
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
     * Test of nimiOikeanMittaiseksi method, of class StringMuotoilija.
     */
    @Test
    public void testNimiOikeanMittaiseksi() {
        assertEquals("Muotoilijan ei pitäisi lyhentää jo ennestään tarpeeksi lyhyttä nimeä", "lyly", muotoilija.nimiOikeanMittaiseksi("lyly", 4));
        assertEquals("Muotoilija lyhentää liian pitkää nimeä väärin", "kalh", muotoilija.nimiOikeanMittaiseksi("kalhu", 4));
    }

    /**
     * Test of jarjestyslukuMuotoilija method, of class StringMuotoilija.
     */
    @Test
    public void testJarjestyslukuMuotoilija() {
        assertEquals("sopivan mittaiseen lukuun ei pidä lisätä etunollia", "12. ", muotoilija.jarjestyslukuMuotoilija(12, 99));
        assertEquals("liian lyhyeen lukuun pitäisi lisätä etunollia", "0001. ", muotoilija.jarjestyslukuMuotoilija(1, 9999));
    }

    /**
     * Test of numeronPituusMerkkeina method, of class StringMuotoilija.
     */
    @Test
    public void testNumeronPituusMerkkeina() {
        assertEquals("numeron pituus lasketaan väärin", 6 , muotoilija.numeronPituusMerkkeina(715517));
    }

    /**
     * Test of pisteMuotoilija method, of class StringMuotoilija.
     */
    @Test
    public void testPisteMuotoilija() {
        assertEquals("pisteiden muoto on väärä", "015 071 551", muotoilija.pisteMuotoilija(15071551, 999999999));
    }
    
}
