package data;

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
        alus.setSuunta(Suunta.YLOS);
        alus.liiku();
        int odotettuX = alkuperainenX;
        int odotettuY = alkuperainenY - Alus.NOPEUS;
        assertEquals("Aluksen liikuttua ylös se oli väärässä x-koordinaatissa", odotettuX, alus.getX());
        assertEquals("Aluksen liikuttua ylös se oli väärässä y-koordinaatissa", odotettuY, alus.getY());

        alus.setSuunta(Suunta.OIKEA);
        alus.liiku();
        odotettuX += Alus.NOPEUS;
        assertEquals("Aluksen liikuttua oikealle se oli väärässä x-koordinaatissa", odotettuX, alus.getX());
        assertEquals("Aluksen liikuttua oikealle se oli väärässä y-koordinaatissa", odotettuY, alus.getY());

        alus.setSuunta(Suunta.ALAS);
        alus.liiku();
        odotettuY += Alus.NOPEUS;
        assertEquals("Aluksen liikuttua alas se oli väärässä x-koordinaatissa", odotettuX, alus.getX());
        assertEquals("Aluksen liikuttua alas se oli väärässä y-koordinaatissa", odotettuY, alus.getY());

        alus.setSuunta(Suunta.VASEN);
        alus.liiku();
        odotettuX -= Alus.NOPEUS;
        assertEquals("Aluksen liikuttua vasemmalle se oli väärässä x-koordinaatissa", odotettuX, alus.getX());
        assertEquals("Aluksen liikuttua vasemmalle se oli väärässä y-koordinaatissa", odotettuY, alus.getY());
    }

    /**
     * Aluksen vasempaan ja oikeaan reunaan liikuttelun testi
     */
    @Test
    public void yritaLiikkuaSivuistaLapi() {
        liikuReunaan(Suunta.VASEN);
        assertEquals("Alus ei käyttäydy oikein vasemmassa reunassa", 0, alus.getX());

        liikuSuuntaan(Suunta.OIKEA);
        assertEquals("Alus ei liiku oikein pois vasemmasta reunasta lennettyään sitä vasten", Alus.NOPEUS, alus.getX());

        liikuReunaan(Suunta.OIKEA);
        assertEquals("Alus ei käyttäydy oikein oikeassa reunassa", maxX, alus.getX());

        liikuSuuntaan(Suunta.VASEN);
        assertEquals("Alus ei liiku oikein pois oikeasta reunasta lennettyään sitä vasten", maxX - Alus.NOPEUS, alus.getX());

    }

    /**
     * Aluksen ylä- ja alareunaa kohti tapahtuvan liikuttelun testi
     */
    @Test
    public void yritaLiikkuaKatostaTaiLattiastaLapi() {
        liikuReunaan(Suunta.YLOS);
        assertEquals("Alus ei käyttäydy oikein ylhäällä reunassa", 0, alus.getY());

        liikuSuuntaan(Suunta.ALAS);
        assertEquals("Alus ei liiku oikein pois yläreunasta lennettyään sitä vasten", Alus.NOPEUS, alus.getY());

        liikuReunaan(Suunta.ALAS);
        assertEquals("Alus ei käyttäydy oikein alareunassa", maxY, alus.getY());

        liikuSuuntaan(Suunta.YLOS);
        assertEquals("Alus ei liiku oikein pois alareunasta lennettyään sitä vasten", maxY - Alus.NOPEUS, alus.getY());

    }
    
    /**
     * Testaa, että alus liikkuu reunaa pitkin normaalisti
     */
    @Test
    public void kierraReunat(){
        liikuReunaan(Suunta.VASEN);
        
        liikuReunaan(Suunta.YLOS);
        assertEquals("Alus ei pääse liikkumaan vasenta reunaa pitkin yläkulmaan", 0, alus.getY());
        
        liikuReunaan(Suunta.OIKEA);
        assertEquals("Alus ei pääse liikkumaan yläreunaa pitkin oikeaan reunaan", maxX, alus.getX());
        
        liikuReunaan(Suunta.ALAS);
        assertEquals("Alus ei pääse liikkumaan oikeaa reunaa pitkin alakulmaan", maxY, alus.getY());
        
        liikuReunaan(Suunta.VASEN);
        assertEquals("Alus ei pääse liikkumaan alareunaa pitkin vasempaan reunaan", 0, alus.getX());
        
    }

    /**
     * Testataan, ettei alus liiku paikallaan ollessaan
     */
    @Test
    public void eiLiikuPaikallaan() {
        liikuSuuntaan(Suunta.ALAS);
        int odotettuX = alus.getX();
        int odotettuY = alus.getY();
        liikuSuuntaan(Suunta.PAIKALLAAN);
        assertEquals("Alus liikkuu x-suunnassa vaikka se on paikallaan", odotettuX, alus.getX());
        assertEquals("Alus liikkuu y-suunnassa vaikka se on paikallaan", odotettuY, alus.getY());
    }

    /**
     * Liikuttaa alusta yhden askeleen annettuun suuntaan
     *
     * @param suunta suunta, johon alus liikkuu
     */
    private void liikuSuuntaan(Suunta suunta) {
        alus.setSuunta(suunta);
        liikkumiskerrat++;
        alus.liiku();
    }

    
    /**
     * Liikuttaa aluksen annettuun reunaan.
     * @param suunta reuna, johon alus liikutetaan
     */
    private void liikuReunaan(Suunta suunta) {
        int alkuX = alus.getX();
        int alkuY = alus.getY();
        liikkumiskerrat = 0;
        if (suunta == Suunta.YLOS) {
            do {
                liikuSuuntaan(suunta);
            } while (alkuY - liikkumiskerrat * Alus.NOPEUS >= 0);
        } else if (suunta == Suunta.ALAS) {
            do {
                liikuSuuntaan(suunta);
            } while (alkuY + liikkumiskerrat * Alus.NOPEUS <= maxY);
        } else if (suunta == Suunta.VASEN) {
            do {
                liikuSuuntaan(suunta);
            } while (alkuX - liikkumiskerrat * Alus.NOPEUS >= 0);
        } else if (suunta == Suunta.OIKEA) {
            do {
                liikuSuuntaan(suunta);
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
