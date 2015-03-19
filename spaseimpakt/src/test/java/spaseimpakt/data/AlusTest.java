package spaseimpakt.data;

import spaseimpakt.data.Alus;
import spaseimpakt.data.Suunta;
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
public class AlusTest {

    Alus alus;
    int alkuperainenX;
    int alkuperainenY;
    int maxX;
    int maxY;
    int liikkumiskerrat;

    public AlusTest() {
        alkuperainenX = 10;
        alkuperainenY = 11;
        maxX = 15;
        maxY = 15;
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        
        alus = new Alus(alkuperainenX, alkuperainenY, maxX, maxY);
    }

    @After
    public void tearDown() {
    }

    /**
     * Aluksen getX-metodin testi
     */
    @Test
    public void testGetX() {
        assertEquals("Aluksen getX-metodi antaa väärän koordinaatin", alkuperainenX, alus.getX());
    }

    /**
     * Aluksen getY-metodin testi
     */
    @Test
    public void testGetY() {
        assertEquals("Aluksen getY-metodi antaa väärän koordinaatin", alkuperainenY, alus.getY());
    }

    /**
     * Aluksen tavanomaisen liikutelun testi
     */
    @Test
    public void testLiiku() {
        liikutaSuuntaan(alus, Suunta.YLOS);
        int odotettuX = alkuperainenX;
        int odotettuY = alkuperainenY - Alus.NOPEUS;
        assertEquals("Aluksen liikuttua ylös se oli väärässä x-koordinaatissa", odotettuX, alus.getX());
        assertEquals("Aluksen liikuttua ylös se oli väärässä y-koordinaatissa", odotettuY, alus.getY());

        liikutaSuuntaan(alus, Suunta.OIKEA);
        odotettuX += Alus.NOPEUS;
        assertEquals("Aluksen liikuttua oikealle se oli väärässä x-koordinaatissa", odotettuX, alus.getX());
        assertEquals("Aluksen liikuttua oikealle se oli väärässä y-koordinaatissa", odotettuY, alus.getY());

        liikutaSuuntaan(alus, Suunta.ALAS);
        odotettuY += Alus.NOPEUS;
        assertEquals("Aluksen liikuttua alas se oli väärässä x-koordinaatissa", odotettuX, alus.getX());
        assertEquals("Aluksen liikuttua alas se oli väärässä y-koordinaatissa", odotettuY, alus.getY());

        liikutaSuuntaan(alus, Suunta.VASEN);
        odotettuX -= Alus.NOPEUS;
        assertEquals("Aluksen liikuttua vasemmalle se oli väärässä x-koordinaatissa", odotettuX, alus.getX());
        assertEquals("Aluksen liikuttua vasemmalle se oli väärässä y-koordinaatissa", odotettuY, alus.getY());
    }

    /**
     * Aluksen vasempaan ja oikeaan reunaan liikuttelun testi
     */
    @Test
    public void yritaLiikkuaSivuistaLapi() {
        liikutaReunanYli(alus, Suunta.VASEN);
        assertEquals("Alus ei käyttäydy oikein vasemmassa reunassa", 0, alus.getX());

        liikutaSuuntaan(alus, Suunta.OIKEA);
        assertEquals("Alus ei liiku oikein pois vasemmasta reunasta lennettyään sitä vasten", Alus.NOPEUS, alus.getX());

        liikutaReunanYli(alus, Suunta.OIKEA);
        assertEquals("Alus ei käyttäydy oikein oikeassa reunassa", maxX, alus.getX());

        liikutaSuuntaan(alus, Suunta.VASEN);
        assertEquals("Alus ei liiku oikein pois oikeasta reunasta lennettyään sitä vasten", maxX - Alus.NOPEUS, alus.getX());

    }

    /**
     * Aluksen ylä- ja alareunaa kohti tapahtuvan liikuttelun testi
     */
    @Test
    public void yritaLiikkuaKatostaTaiLattiastaLapi() {
        liikutaReunanYli(alus, Suunta.YLOS);
        assertEquals("Alus ei käyttäydy oikein ylhäällä reunassa", 0, alus.getY());

        liikutaSuuntaan(alus, Suunta.ALAS);
        assertEquals("Alus ei liiku oikein pois yläreunasta lennettyään sitä vasten", Alus.NOPEUS, alus.getY());

        liikutaReunanYli(alus, Suunta.ALAS);
        assertEquals("Alus ei käyttäydy oikein alareunassa", maxY, alus.getY());

        liikutaSuuntaan(alus, Suunta.YLOS);
        assertEquals("Alus ei liiku oikein pois alareunasta lennettyään sitä vasten", maxY - Alus.NOPEUS, alus.getY());

    }
    
    @Test
    public void jamptistiReunaanVoiMenna(){
        Alus jampti=new Alus(Alus.NOPEUS, Alus.NOPEUS, Alus.NOPEUS*2, Alus.NOPEUS*2);
        
        
        liikutaSuuntaan(jampti, Suunta.YLOS);
        assertEquals("Aluksen pitäisi voida liikkua tasan reunaan saakka ylöspäin mentäessä", 0, jampti.getY());
        
        liikutaSuuntaan(jampti, Suunta.ALAS);
        liikutaSuuntaan(jampti, Suunta.VASEN);
        assertEquals("Aluksen pitäisi voida liikkua tasan reunaan saakka vasemmalle mentäessä", 0, jampti.getX());
        
        liikutaSuuntaan(jampti, Suunta.OIKEA);
        liikutaSuuntaan(jampti, Suunta.ALAS);
        assertEquals("Aluksen pitäisi voida liikkua tasan reunaan saakka alas mentäessä", Alus.NOPEUS*2, jampti.getY());
        
        liikutaSuuntaan(jampti, Suunta.YLOS);
        liikutaSuuntaan(jampti, Suunta.OIKEA);
        assertEquals("Aluksen pitäisi voida liikkua tasan reunaan saakka oikealle mentäessä", Alus.NOPEUS*2, jampti.getX());
        
    }
    
    /**
     * Testaa, että alus liikkuu reunaa pitkin normaalisti
     */
    @Test
    public void kierraReunat(){
        liikutaReunanYli(alus, Suunta.VASEN);
        
        liikutaReunanYli(alus, Suunta.YLOS);
        assertEquals("Alus ei pääse liikkumaan vasenta reunaa pitkin yläkulmaan", 0, alus.getY());
        
        liikutaReunanYli(alus, Suunta.OIKEA);
        assertEquals("Alus ei pääse liikkumaan yläreunaa pitkin oikeaan reunaan", maxX, alus.getX());
        
        liikutaReunanYli(alus, Suunta.ALAS);
        assertEquals("Alus ei pääse liikkumaan oikeaa reunaa pitkin alakulmaan", maxY, alus.getY());
        
        liikutaReunanYli(alus, Suunta.VASEN);
        assertEquals("Alus ei pääse liikkumaan alareunaa pitkin vasempaan reunaan", 0, alus.getX());
        
    }

    /**
     * Testataan, ettei alus liiku paikallaan ollessaan
     */
    @Test
    public void eiLiikuPaikallaan() {
        liikutaSuuntaan(alus, Suunta.ALAS);
        int odotettuX = alus.getX();
        int odotettuY = alus.getY();
        liikutaSuuntaan(alus, Suunta.PAIKALLAAN);
        assertEquals("Alus liikkuu x-suunnassa vaikka se on paikallaan", odotettuX, alus.getX());
        assertEquals("Alus liikkuu y-suunnassa vaikka se on paikallaan", odotettuY, alus.getY());
    }

    /**
     * Liikuttaa alusta yhden askeleen annettuun suuntaan
     *
     * @param suunta suunta, johon alus liikkuu
     */
    private void liikutaSuuntaan(Alus alus, Suunta suunta) {
        alus.setSuunta(suunta);
        liikkumiskerrat++;
        alus.liiku();
    }

    
    /**
     * Liikuttaa aluksen annettuun reunaan.
     * @param suunta reuna, johon alus liikutetaan
     */
    private void liikutaReunanYli(Alus alus, Suunta suunta) {
        int alkuX = alus.getX();
        int alkuY = alus.getY();
        liikkumiskerrat = 0;
        if (suunta == Suunta.YLOS) {
            do {
                liikutaSuuntaan(alus, suunta);
            } while (alkuY - liikkumiskerrat * Alus.NOPEUS >= 0);
        } else if (suunta == Suunta.ALAS) {
            do {
                liikutaSuuntaan(alus, suunta);
            } while (alkuY + liikkumiskerrat * Alus.NOPEUS <= maxY);
        } else if (suunta == Suunta.VASEN) {
            do {
                liikutaSuuntaan(alus, suunta);
            } while (alkuX - liikkumiskerrat * Alus.NOPEUS >= 0);
        } else if (suunta == Suunta.OIKEA) {
            do {
                liikutaSuuntaan(alus, suunta);
            } while (alkuX + liikkumiskerrat * Alus.NOPEUS <= maxX);
        }
    }
    
    @Test
    public void eiVoiLuodaSallitunUlkopuolelle(){
        Alus vasemmallaUlkona = new Alus(-5, 5, 10, 10);
        assertEquals("Aluksen luomisen pelialueen vasemman reunan vasemmalle puolelle pitäisi olla mahdotonta", 0, vasemmallaUlkona.getX());
        
        Alus oikeallaUlkona = new Alus(15, 5, 10, 10);
        assertEquals("Aluksen luomisen pelialueen oikean reunan oikealle puolele pitäisi olla mahdotonta", 10, oikeallaUlkona.getX());
    
        Alus ylhaallaUlkona = new Alus(5, -5, 10, 10);
        assertEquals("Aluksen luomisen pelialueen yläreunan yläpuolelle pitäisi olla mahdotonta", 0, ylhaallaUlkona.getY());
        
        Alus alhaallaUlkona = new Alus(5, 15, 10, 10);
        assertEquals("Aluksen luomisen pelialueen alareunan alapuolelle pitäisi olla mahdotonta", 10, alhaallaUlkona.getY());
        
        Alus kahdessaSuunnassaUlkona = new Alus (-5, -5, 10, 10);
        assertEquals("Aluksen luomisen pelialueen vasemman reunan vasemmalle puolelle pitäisi olla mahdotonta", 0, kahdessaSuunnassaUlkona.getX());
        assertEquals("Aluksen luomisen pelialueen yläreunan yläpuolelle pitäisi olla mahdotonta", 0, kahdessaSuunnassaUlkona.getY());
    }

}
