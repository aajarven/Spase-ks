/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

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
public class LaserTest {

    Alus alus;
    Laser laser;
    Pelimoottori moottori;
    long alku;
    int xEro;
    int yEro;

    public LaserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        moottori = new Pelimoottori(new GraafinenKayttoliittyma());
        alus = new Alus(5, 10, 20, 30, moottori);
        alus.ammuLaser();
        laser = (Laser) moottori.getAseet().get(0);
        alku = System.currentTimeMillis();
        xEro = laser.getX() - alus.getX();
        yEro = laser.getY() - alus.getY();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLuodaanOikeaanKohtaan(){
        assertEquals("Laserin x-koordinaatti ei ole sama kuin aluksen kärjen", alus.getX()+alus.getSprite().getWidth(null), laser.getX());
        assertEquals("Laserin y-koordinaatti ei ole sellainen, että se lähtee keskeltä aluksen kärkeä", alus.getY()+alus.getSprite().getHeight(null)/2-laser.getSprite().getHeight(null)/2, laser.getY());
    }
    
    @Test
    public void testEiLiikuKunAlusPaikallaan() {
        laser.liiku();
        tarkastaX("Laserin x-koordinaatti muuttuu vaikka ei pitäisi");
        tarkastaY("Laserin y-koordinaatti muuttuu vaikka ei pitäisi");
    }

    @Test
    public void testLiikkuuAluksenKanssaYlos() {
        liikutaMolemmat(Suunta.YLOS);
        tarkastaX("Laserin x-koordinaatti ei seuraa alusta sen liikkuessa ylös");
        tarkastaY("Laser y-koordinaatti ei seuraa alusta sen liikkuessa ylös");
    }

    @Test
    public void testLiikkuuAluksenKanssaOikealle() {
        liikutaMolemmat(Suunta.OIKEA);
        tarkastaX("Laserin x-koordinaatti ei seuraa alusta sen liikkuessa oikealle");
        tarkastaY("Laser y-koordinaatti ei seuraa alusta sen liikkuessa oikealle");
    }

    @Test
    public void testLiikkuuAluksenKanssaAlas() {
        liikutaMolemmat(Suunta.ALAS);
        tarkastaX("Laserin x-koordinaatti ei seuraa alusta sen liikkuessa alas");
        tarkastaY("Laserin y-koordinaatti ei seuraa alusta sen liikkuessa alas");
    }

    @Test
    public void testLiikkuuAluksenKanssaVasemmalle() {
        liikutaMolemmat(Suunta.VASEN);
        tarkastaX("Laserin x-koordinaatti ei seuraa alusta sen liikkuessa vasemmalle");
        tarkastaY("Laserin y-koordinaatti ei seuraa alusta sen liikkuessa vasemmalle");
    }

    @Test
    public void testKatoaaOikein() {
        laser.liiku();
        assertEquals("Laser katoaa liian aikaisin", 1, moottori.getAseet().size());
        while (System.currentTimeMillis() - alku <= laser.getKESTO()) {
            laser.liiku();
        }
        laser.liiku();
        assertEquals("Laser ei sammu", 0, moottori.getAseet().size());
    }

    private void liikutaMolemmat(Suunta suunta) {
        alus.setSuunta(suunta);
        alus.paivita();
        laser.liiku();
    }
    
        private void tarkastaY(String virheviesti) {
        assertEquals(virheviesti, alus.getY(), laser.getY()-yEro);
    }

    private void tarkastaX(String virheviesti) {
        assertEquals(virheviesti, alus.getX(), laser.getX()-xEro);
    }

}
