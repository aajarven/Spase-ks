/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.utils;

import java.util.HashMap;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import spaseimpakt.data.FunktioLiikutin;
import spaseimpakt.data.Pikkuvihu;
import spaseimpakt.data.Vihu;
import spaseimpakt.kayttoliittyma.GraafinenKayttoliittyma;
import spaseimpakt.logiikka.Pelimoottori;

/**
 *
 * @author Anni Järvenpää
 */
public class JsonLukijaTest {
    
    public JsonLukijaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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

    @Test
    public void testLueYksiVihu() {
        JsonLukija lukija = new JsonLukija();
        HashMap<Vihu, Integer> luetut = lukija.lueVihut("resources/levelit/testdata/test-funktioliikkuja.json", new Pelimoottori(new GraafinenKayttoliittyma()));
        assertEquals("Luetussa levelissä oli väärä määrä vihollisia", 1, luetut.size());
        for(Vihu vihu: luetut.keySet()){
            Pikkuvihu pikkuvihu = (Pikkuvihu) vihu;
            
            assertEquals("Luetun vihun x-koordinaatti oli väärä", 500, pikkuvihu.getX());
            assertTrue("Luetun vihun ilmestymisaika on väärä", 5000==luetut.get(vihu));
            
            FunktioLiikutin liikutin = (FunktioLiikutin) pikkuvihu.getLiikutin();
            int[] kertoimet = liikutin.getKertoimet();
            assertArrayEquals("Luetun vihun kertoimet eivät ole oikein", new int[]{5, 7, -2, 1, 100}, liikutin.getKertoimet());
        }
    }
    
    @Test
    public void lueMontaVihua(){
        JsonLukija lukija = new JsonLukija();
        HashMap<Vihu, Integer> luetut = lukija.lueVihut("resources/levelit/testdata/test-montavihua.json", new Pelimoottori(new GraafinenKayttoliittyma()));
        assertEquals("Luetussa levelissä oli väärä määrä vihollisia", 4, luetut.size());
    }
    
}
