/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import spaseimpakt.kayttoliittyma.GraafinenKayttoliittyma;
import spaseimpakt.logiikka.Pelimoottori;

/**
 *
 * @author Anni Järvenpää
 */
public class LevelTest {

    Level lvl;
    Vihu eka;
    Vihu toka;
    Vihu kolmas;
    int ilmestymisaika1;
    int ilmestymisaika2;

    public LevelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        eka = new Pikkuvihu(1, lueSprite(), new FunktioLiikutin(1, 1, new double[]{0, 0, 0, 0, 50}), new Pelimoottori(new GraafinenKayttoliittyma()));
        toka = new Pikkuvihu(2, lueSprite(), new FunktioLiikutin(1, 1, new double[]{0, 0, 0, 0, 50}), new Pelimoottori(new GraafinenKayttoliittyma()));
        kolmas = new Pikkuvihu(3, lueSprite(), new FunktioLiikutin(1, 1, new double[]{0, 0, 0, 0, 50}), new Pelimoottori(new GraafinenKayttoliittyma()));

        ilmestymisaika1 = 1;
        ilmestymisaika2 = 5;

        lvl = new Level();
        lvl.lisaaVihollinen(ilmestymisaika1, eka);
        lvl.lisaaVihollinen(ilmestymisaika2, toka);
        lvl.lisaaVihollinen(ilmestymisaika2, kolmas);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLisaaVihollinen() {
        Vihu eka = new Pikkuvihu(1, lueSprite(), new FunktioLiikutin(1, 1, new double[]{0, 0, 0, 0, 50}), new Pelimoottori(new GraafinenKayttoliittyma()));
        Level lisaysLevel = new Level();
        lisaysLevel.lisaaVihollinen(1, eka);
        TreeMap<Integer, ArrayList<Vihu>> vihuMap = lisaysLevel.getVihut();
        assertEquals("Ensimmäisen vihollisen lisääminen ei luo uutta joukkoa kyseisellä hetkellä ilmestyviä vihollisia varten", 1, vihuMap.keySet().size());
        assertEquals("Ensimmäisen vihollisen lisäämisen jälkeen vihollisia on väärä määrä", 1, vihuMap.get(vihuMap.firstKey()).size());

        Vihu toka = new Pikkuvihu(1, lueSprite(), new FunktioLiikutin(1, 1, new double[]{0, 0, 0, 0, 50}), new Pelimoottori(new GraafinenKayttoliittyma()));
        lisaysLevel.lisaaVihollinen(5, toka);
        assertEquals("Toisen eri aikaan ilmestyvän vihollisen lisääminen ei luo uutta joukkoa kyseisellä hetkellä ilmestyviä vihollisia varten", 2, vihuMap.keySet().size());
        assertEquals("Toisen vihollisen lisäämisen jälkeen vihollisia on väärä määrä kyseistä aikaa vastaavassa listassa", 1, vihuMap.get(5).size());
        assertEquals("Toisen vihollisen lisääminen muuttaa myös ensimmäisen vihollisen sisältävän ArrayListin kokoa", 1, vihuMap.get(5).size());

        Vihu kolmas = new Pikkuvihu(1, lueSprite(), new FunktioLiikutin(1, 1, new double[]{0, 0, 0, 0, 50}), new Pelimoottori(new GraafinenKayttoliittyma()));
        lisaysLevel.lisaaVihollinen(5, kolmas);
        assertEquals("Samaan aikaan edellisen kanssa ilmestyvän vihollisen lisääminen luo uuden avaimen vaikka sopiva on jo olemassa", 2, vihuMap.keySet().size());
        assertEquals("Samaan aikaan edellisen kanssa ilmestyvän vihollisen lisäämisen jälkeen vihollisia on väärä määrä kyseistä aikaa vastaavassa listassa", 2, vihuMap.get(5).size());
    }

    /**
     * Test of seuraavaVihollinen method, of class Level.
     */
    @Test
    public void testSeuraavaVihollinen() {
        assertEquals("Ensimmäisenä ei anneta aikaisimmin ilmestyvää vihollista", eka, lvl.seuraavaVihollinen());
        Vihu ekaSamanaikainen = lvl.seuraavaVihollinen();
        assertTrue("Toisena ei anneta jompaakumpaa seuraavaksi ilmestyvistä vihollisista", ekaSamanaikainen.equals(toka) || ekaSamanaikainen.equals(kolmas));
        Vihu tokaSamanaikainen = lvl.seuraavaVihollinen();
        assertFalse("seuraavaVihollinen()-metodi palauttaa kahdesti saman vihollisen", tokaSamanaikainen.equals(ekaSamanaikainen));
        assertTrue("Kolmantena ei anneta jompaakumpaa seuraavaksi ilmestyvistä vihollisista", tokaSamanaikainen.equals(toka) || tokaSamanaikainen.equals(kolmas));
    }

    /**
     * Test of onkoSeuraavanVihollisenAika method, of class Level.
     */
    @Test
    public void testOnkoSeuraavanVihollisenAika() {
        assertTrue("Ohjelman mukaan ei ole seuraavan aika, vaikka pitäisi olla", lvl.onkoSeuraavanVihollisenAika(ilmestymisaika1));
        assertFalse("Ohjelman mukaan on seuraavan aika, vaikka ei pitäisi olla", lvl.onkoSeuraavanVihollisenAika(0));
        assertTrue("Ohjlma ei tunnista, että seuraavan vihollisen pitäisi ilmestyä, vaikka sen aika olisi jo ohitettu", lvl.onkoSeuraavanVihollisenAika(ilmestymisaika1 + 1));
        while (lvl.onkoSeuraavanVihollisenAika(ilmestymisaika1)) {
            lvl.seuraavaVihollinen();
        }
        assertTrue("Ohjelman mukaan ei ole seuraavan aika, vaikka pitäisi olla", lvl.onkoSeuraavanVihollisenAika(ilmestymisaika2));
        assertFalse("Ohjelman mukaan on seuraavan aika, vaikka ei pitäisi olla", lvl.onkoSeuraavanVihollisenAika(ilmestymisaika2 - 1));
    }

    @Test
    public void testVihollistenLoppuminen() {
        for (int i = 0; i < 3; i++) {
            assertFalse("Vihollisten väitetään loppuvan liian aikaisin", lvl.ovatkoVihollisetLoppu());
            lvl.seuraavaVihollinen();
        }
        assertTrue("Vihollisten loppumista ei tunnisteta", lvl.ovatkoVihollisetLoppu());
    }

    private Image lueSprite() {
        try {
            BufferedImage i = ImageIO.read(new File("resources/normivihu.png"));
            return i;
        } catch (IOException e) {
            System.out.println("Vihollisen kuvaa ei löytynyt");
            return null;
        }
    }
}
