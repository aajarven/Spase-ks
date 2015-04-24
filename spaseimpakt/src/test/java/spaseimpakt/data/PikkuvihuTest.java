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
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import spaseimpakt.kayttoliittyma.GraafinenKayttoliittyma;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

/**
 *
 * @author Anni Järvenpää
 */
public class PikkuvihuTest {

    private static Image sprite;

    public PikkuvihuTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            sprite = ImageIO.read(new File("resources/normivihu.png"));
        } catch (IOException e) {
            System.out.println("Vihollisen kuvaa ei löytynyt");
        }
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
    public void testLiiku() {
        FunktioLiikutin liikutin = new FunktioLiikutin(100, 1, new double[]{0, 0, 0, 1, 100});
        Pikkuvihu vihu = new Pikkuvihu(100, sprite, liikutin, new Pelimoottori(new GraafinenKayttoliittyma()));
        for (int i = 0; i < 50; i++) {
            assertEquals("vihollisen x-koordinaatti muuttuu väärin", liikutin.getX(), vihu.getX());
            assertEquals("vihollisen y-koordinaatti muuttuu väärin", liikutin.getY(), vihu.getY());
            vihu.liiku();
        }
    }

    @Test
    public void testEiVoiLiikkuaLiianAlas() {
        FunktioLiikutin liikutin = new FunktioLiikutin(100, 1, new double[]{0, 0, 0, 0, Integer.MAX_VALUE});
        Pikkuvihu vihu = new Pikkuvihu(100, sprite, liikutin, new Pelimoottori(new GraafinenKayttoliittyma()));
        for (int i = 0; i < 10; i++) {
            vihu.liiku();
            assertEquals("vihollisen y-koordinaatti ei ole sallitulla alueella", (Pelirunko.KORKEUS - sprite.getHeight(null)), vihu.getY());
        }
    }

    @Test
    public void testEiVoiLiikkuaLiianYlos() {
        FunktioLiikutin liikutin = new FunktioLiikutin(100, 1, new double[]{0, 0, 0, 0, -10});
        Pikkuvihu vihu = new Pikkuvihu(100, sprite, liikutin, new Pelimoottori(new GraafinenKayttoliittyma()));
        for (int i = 0; i < 10; i++) {
            vihu.liiku();
            assertEquals("vihollisen y-koordinaatti ei ole sallitulla alueella", 0, vihu.getY());
        }
    }

    @Test
    public void testVihuPoistuuKunReunassa() {
        FunktioLiikutin liikutin = new FunktioLiikutin(10, 1, new double[]{0, 0, 0, 0, 20});
        Pelimoottori moottori = new Pelimoottori(new GraafinenKayttoliittyma());
        Pikkuvihu vihu = new Pikkuvihu(10, sprite, liikutin, moottori);
        moottori.lisaaVihu(vihu);
        while(vihu.getX()>0-sprite.getWidth(null)) {
            assertFalse("Vihollinen ei poistu vasemmassa reunassa oikein", moottori.getVihut().isEmpty());
            assertEquals("vihollisen x-koordinaatti muuttuu väärin", liikutin.getX(), vihu.getX());
            vihu.liiku();
            assertFalse("Vihollinen poistetaan jo liian aikaisin", moottori.getVihut().isEmpty());
        }
        vihu.liiku();
        assertTrue("Vihollinen ei poistu vasemmassa reunassa oikein", moottori.getVihut().isEmpty());
    }
}
