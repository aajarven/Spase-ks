/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import spaseimpakt.data.Ammus;
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
public class AmmusTest {

    int alkuX;
    int alkuY;
    Ammus ammus;

    public AmmusTest() {
        alkuX = 5;
        alkuY = 20;
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ammus = new Ammus(alkuX, alkuY);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of liiku method, of class Ammus.
     */
    @Test
    public void testLiiku() {
        for (int i = 0; i < 10; i++) {
            ammus.liiku();
            assertEquals("Ammuksen liikkuessa sen x-koordinaatti muuttuu väärin", alkuX + (i + 1) * Ammus.NOPEUS, ammus.getX());
            assertEquals("Ammuksen liikkuessa sen y-koordinaatin ei pitäisi muuttua", alkuY, ammus.getY());
        }

    }

}
