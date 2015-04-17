/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.data;

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
public class FunktioLiikutinTest {

    FunktioLiikutin liikutin;

    public FunktioLiikutinTest() {
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

    /**
     * Testaa liikkumista lineaarisesti oikealta vasemmalle
     */
    @Test
    public void testLineaarinenLiiku() {
        int x = 20;
        int y = 80;
        liikutin = new FunktioLiikutin(x, 1, new int[]{0, 0, 0, 0, y}); // y-koordinaatti vakio 80
        int askeleet = 0;
        while (x - askeleet >= 0) {
            assertEquals("X-koodrinaatti muuttuu väärin", x - askeleet, liikutin.getX());
            assertEquals("Y-koordinaatti muuttuu vaikka ei pitäisi", y, liikutin.getY());
            liikutin.liiku();
            askeleet++;
        }
    }

    @Test
    public void testLineaarinenLiikuVinoon() {
        int alkux = 50;
        //y=kx+b
        int k = 2;
        int b = 60;

        liikutin = new FunktioLiikutin(alkux, 1, new int[]{0, 0, 0, k, b});
        int askeleet = 0;
        while (alkux - askeleet >= 0) {
            assertEquals("X-koodrinaatti muuttuu  väärin", alkux - askeleet, liikutin.getX());
            assertEquals("Y-koordinaatti muuttuu väärin", (alkux - askeleet) * k + b, liikutin.getY());
            liikutin.liiku();
            askeleet++;
        }
    }

    @Test
    public void testaaMonimutkainenLiikkuminen() {
        int alkux = 50;
        //ax⁴+bx³+cx²+dx+e
        int a = 1;
        int b = 2;
        int c = -5;
        int d = 3;
        int e = 80;

        liikutin = new FunktioLiikutin(alkux, 1, new int[]{a, b, c, d, e});
        for(int askeleet=0; askeleet<30; askeleet++){
            assertEquals("y-koordinaatti muuttuu väärin", laskeY(alkux-askeleet, a, b, c, d, e), liikutin.getY());
            liikutin.liiku();
        }
    }
    
    private int laskeY(int x, int a, int b, int c, int d, int e){
        return (int) (a*Math.pow(x, 4)+b*Math.pow(x, 3)+c*Math.pow(x, 2)+d*x+e);
    }
}
