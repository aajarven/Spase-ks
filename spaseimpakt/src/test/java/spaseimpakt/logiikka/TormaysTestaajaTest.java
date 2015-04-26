/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.logiikka;

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
import spaseimpakt.data.Alus;
import spaseimpakt.data.Ammus;
import spaseimpakt.data.Ase;
import spaseimpakt.data.FunktioLiikutin;
import spaseimpakt.data.Pikkuvihu;
import spaseimpakt.data.Vihu;

/**
 *
 * @author Anni Järvenpää
 */
public class TormaysTestaajaTest {
    private static TormaysTestaaja testaaja;
    
    public TormaysTestaajaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        testaaja = new TormaysTestaaja();
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
     * Test of tormaa method, of class TormaysTestaaja.
     */
    @Test
    public void testTormaa_Ase_Vihu() {
        Ammus ammus = new Ammus(0, 0, 100, null);
        Vihu vihu = new Pikkuvihu(ammus.getX()+ammus.getSprite().getWidth(null)-1, lueVihusprite(), new FunktioLiikutin(ammus.getX()+ammus.getSprite().getWidth(null)-1, -1, new double[]{0,0,0,0,0}), null);
        assertTrue("Aseen ja vihollisen pitäisi törmäävän kun ne ovat vain hieman päällekäin", testaaja.tormaa(ammus, vihu));
        vihu.liiku();
        assertFalse("Aseen ja vihollisen ei pitäisi törmätä kun ne ovat aivan vierekkäin", testaaja.tormaa(ammus, vihu));
        
    }

    /**
     * Test of tormaa method, of class TormaysTestaaja.
     */
    @Test
    public void testTormaa_Alus_Vihu() {
        Alus alus = new Alus(0, 0, 500, 500, null);
        int vihuX = alus.getSprite().getWidth(null)-1;
        Vihu vihu = new Pikkuvihu(vihuX, lueVihusprite(), new FunktioLiikutin(vihuX, -1, new double[]{0,0,0,0,0}), null);
        assertTrue("Aluksen ja vihun pitäisi törmätä kun ne ovat päällekkäin", testaaja.tormaa(alus, vihu));
        vihu.liiku();
        assertFalse("Aluksen ja vihun ei pitäisi törmätä kun ne ovat vierekkäin mutta eivät päällekäin", testaaja.tormaa(alus, vihu));
        
        
//        Alus alus=new Alus(0, 0, 100, 100, null);
//        Vihu vihu = new Pikkuvihu(alus.getX()+alus.getSprite().getWidth(null)-1, lueVihusprite(), new FunktioLiikutin(alus.getX()+alus.getSprite().getWidth(null)-1, -1, new double[]{0,0,0,0,0}), null);
//        assertFalse("Aluksen ja vihollisen ei huomata törmäävän kun ne ovat vain hieman päällekäin", testaaja.tormaa(alus, vihu));
////        vihu.liiku();
//        vihu = new Pikkuvihu(alus.getX()+alus.getSprite().getWidth(null)+500, lueVihusprite(), new FunktioLiikutin(alus.getX()+alus.getSprite().getWidth(null)+500, -1, new double[]{0,0,0,0,0}), null);
//        assertTrue("Aluksen ja vihollisen ei pitäisi törmätä kun ne ovat aivan vierekkäin", testaaja.tormaa(alus, vihu));
    }
    
    private Image lueVihusprite(){
        try {
            BufferedImage i = ImageIO.read(new File("resources/normivihu.png"));
            return i;
        } catch (IOException e) {
            System.out.println("Vihollisen kuvaa ei löytynyt");
            return null;
        }
    }
    
}
