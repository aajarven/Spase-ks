/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.logiikka;

import java.util.ArrayList;
import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import spaseimpakt.data.Level;
import spaseimpakt.data.Vihu;
import spaseimpakt.kayttoliittyma.GraafinenKayttoliittyma;

/**
 *
 * @author Anni Järvenpää
 */
public class LevelManagerTest {
    
    public LevelManagerTest() {
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
    public void testLueYksinkertainenLevel() {
        LevelManager manager = new LevelManager(new String[]{"resources/levelit/testdata/test-funktioliikkuja.json"}, new Pelimoottori(new GraafinenKayttoliittyma()));
        Level luettu = manager.lueSeuraavaLevel();
        TreeMap<Integer, ArrayList<Vihu>> vihut = luettu.getVihut();
        assertEquals("Luettaessa level, jossa on yksi vihollinen, on palautettavassa levelissä väärä määrä vihollisia", 1, luettu.getVihut().size());
    }
    
    @Test
    public void testLueMonenVihunLevel(){
        LevelManager manager = new LevelManager(new String[]{"resources/levelit/testdata/test-montavihua.json"}, new Pelimoottori(new GraafinenKayttoliittyma()));
        Level luettu = manager.lueSeuraavaLevel();
        TreeMap<Integer, ArrayList<Vihu>> vihut = luettu.getVihut();
        assertEquals("Luettaessa level, jossa on monta vihollista, on palautettavassa levelissä väärä määrä vihollisia", 4, luettu.getVihut().size());
    }
    
    @Test
    public void testPalauttaaNullViimeisenJalkeen(){
        LevelManager manager = new LevelManager(new String[]{"resources/levelit/testdata/test-funktioliikkuja.json"}, new Pelimoottori(new GraafinenKayttoliittyma()));
        Level luettu = manager.lueSeuraavaLevel();
        luettu = manager.lueSeuraavaLevel();
        
        assertEquals("Kun levelejä ei enää ole, managerin pitäisi palauttaa null", null, luettu);
    }
}
