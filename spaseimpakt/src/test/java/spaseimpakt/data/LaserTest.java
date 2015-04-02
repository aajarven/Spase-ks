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
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void eiLiikuKunAlusPaikallaan() {
        laser.liiku();
        assertEquals("Laserin x-koordinaatti muuttuu vaikka ei pitäisi", alus.getX(), laser.getX());
    }

    @Test
    public void liikkuuAluksenKanssaYlos() {
        liikutaMolemmat(Suunta.YLOS);
        assertEquals("Laserin x-koordinaatti ei seuraa alusta sen liikkuessa ylös", alus.getX(), laser.getX());
        assertEquals("Laser y-koordinaatti ei seuraa alusta sen liikkuessa ylös", alus.getY(), laser.getY());
    }

    @Test
    public void liikkuuAluksenKanssaOikealle() {
        liikutaMolemmat(Suunta.OIKEA);
        assertEquals("Laserin x-koordinaatti ei seuraa alusta sen liikkuessa oikealle", alus.getX(), laser.getX());
        assertEquals("Laser y-koordinaatti ei seuraa alusta sen liikkuessa oikealle", alus.getY(), laser.getY());
    }

    @Test
    public void liikkuuAluksenKanssaAlas() {
        liikutaMolemmat(Suunta.ALAS);
        assertEquals("Laserin x-koordinaatti ei seuraa alusta sen liikkuessa alas", alus.getX(), laser.getX());
        assertEquals("Laser y-koordinaatti ei seuraa alusta sen liikkuessa alas", alus.getY(), laser.getY());
    }

    @Test
    public void liikkuuAluksenKanssaVasemmalle() {
        liikutaMolemmat(Suunta.VASEN);
        assertEquals("Laserin x-koordinaatti ei seuraa alusta sen liikkuessa vasemmalle", alus.getX(), laser.getX());
        assertEquals("Laser y-koordinaatti ei seuraa alusta sen liikkuessa vasemmalle", alus.getY(), laser.getY());
    }

    @Test
    public void katoaaOikein() {
        laser.liiku();
        assertEquals("Laser katoaa liian aikaisin", 1, moottori.getAseet().size());
        while(System.currentTimeMillis()-alku<=laser.getKESTO()){
            laser.liiku();
        }
        assertEquals("Laser ei sammu", 0, moottori.getAseet().size());
    }

    private void liikutaMolemmat(Suunta suunta) {
        alus.setSuunta(suunta);
        alus.paivita();
        laser.liiku();
    }
    
}
