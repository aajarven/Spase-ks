package spaseimpakt.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

/**
 * Alus, jota pelaaja ohjaa.
 *
 * @author Anni Järvenpää
 */
public class Alus implements Piirrettava {

    private final Pelimoottori moottori;
    private int x;
    private int y;
    private int maxX;
    private int maxY;
    private int pelialueenLeveys;
    private int pelialueenKorkeus;
    private Suunta suunta;
    private long edellinenAmmus;
    private int laserit;
    private int pommit;
    private Image sprite;
    private boolean ampuuLaukauksiaNyt;

    // Onko tämä rumaa?
    public static final int NOPEUS = 3; // TODO sopiva nopeuden arvo alukselle
    private final int AMPUMIS_INTERVALLI = 150;
    private final int LASERIT_ALUSSA = 3;
    private final int POMMIT_ALUSSA = 3;

    // TODO törmäystarkastus
    // TODO elämät
    
    /**
     * Luo aluksen, jolla pelaaja lentää. Jos annetut aloituskoordinaatit eivät
     * ole sallitulla alueella, luodaan alus niitä lähimpään sallittuun
     * pisteeseen.
     *
     * @param x Aluksen x-koordinaatti (0 vasemmassa laidassa, kasvaa oikealle)
     * @param y Aluksen y-koodrinaatti (0 ylhäällä, kasvaa alas)
     * @param pelialueenLeveys Peli-ikkunan koko x-suunnassa
     * @param pelialueenKorkeus Peli-ikkunan koko y-suunnassa (käytännössä
     * pelialueen korkeus)
     */
    public Alus(int x, int y, int pelialueenLeveys, int pelialueenKorkeus, Pelimoottori moottori) {
        lueSprite();
        this.x = x;
        this.y = y;
        this.pelialueenLeveys = pelialueenLeveys;
        this.pelialueenKorkeus = pelialueenKorkeus;
        this.maxX = pelialueenLeveys - sprite.getWidth(null); // TODO korjaa se, ettei mikään estä laittamasta maksimiarvoksi esim negatiivista lukua, jos ohjelmoija on tyhmä
        this.maxY = pelialueenKorkeus - sprite.getHeight(null);
        tarkastaPaikka();
        this.moottori = moottori;
        this.laserit = LASERIT_ALUSSA;
        this.pommit = POMMIT_ALUSSA;
        suunta = Suunta.PAIKALLAAN;
        edellinenAmmus = 0;
        ampuuLaukauksiaNyt = false;
    }

    private void lueSprite() {
        try {
            BufferedImage i = ImageIO.read(new File("resources/simppelialus.png"));
            this.sprite = i;
        } catch (IOException e) {
            System.out.println("Aluksen kuvaa ei löytynyt");
        }
    }

    /**
     *
     * @return aluksen vasemman yläkulman x-koordinaatti
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return aluksen vasemman yläkulman y-koordinaatti
     */
    public int getY() {
        return y;
    }

    /**
     * Asettaa aluksen kulkusuunnan.
     *
     * @param suunta
     */
    public void setSuunta(Suunta suunta) {
        this.suunta = suunta;
    }

    /**
     * Liikuttaa alusta pelaajan valitsemaan suuntaan yhden askeleen
     */
    public void paivita() {
        if (suunta == Suunta.ALAS) {
            this.y += NOPEUS;
        } else if (suunta == Suunta.YLOS) {
            this.y -= NOPEUS;
        } else if (suunta == Suunta.OIKEA) {
            this.x += NOPEUS;
        } else if (suunta == Suunta.VASEN) {
            this.x -= NOPEUS;
        }

        tarkastaPaikka();

        if (ampuuLaukauksiaNyt) {
            ammuLaukaus();
        }
    }

    private void tarkastaPaikka() {
        if (this.x > maxX) {
            this.x = maxX;
        } else if (this.x < 0) {
            this.x = 0;
        }

        if (this.y > maxY) {
            this.y = maxY;
        } else if (this.y < 0) {
            this.y = 0;
        }

    }

    /**
     * Laukaisee aluksen pääaseen. Ammus muistuttaa luotia, joka liikkuu
     * lineaarisesti kohti pelialueen laitaa. Ammus tuhoutuu, jos se koskettaa
     * vihollistaa.
     *
     * @see Ammus
     */
    public void ammuLaukaus() {
        if (System.currentTimeMillis() - edellinenAmmus > AMPUMIS_INTERVALLI) {
            moottori.lisaaAse(new Ammus(x, y, pelialueenLeveys, moottori));
            edellinenAmmus = System.currentTimeMillis();
        }
    }

    /**
     * Laukaisee aluksen laserin. Laser lähtee aluksen nokasta tuhoten kaikki
     * tavalliset viholliset, joihin se aktiivisena ollessaan osuu. Laserin voi
     * laukaista vain, jos käyttökertoja on vielä jäljellä.
     *
     * @see Laser
     */
    public void ammuLaser() {
        if (laserit > 0) {
            moottori.lisaaAse(new Laser(this, moottori));
            laserit--;
        }
    }

    /**
     * Ampuu pommin, joka tuhoaa kaikki tavalliset viholliset näytöltä.
     *
     * @see Pommi
     */
    public void ammuPommi() {
        if (pommit > 0) {
            moottori.lisaaAse(new Pommi(moottori));
            pommit--;
        }
    }

    /**
     *
     * @return palauttaa ammuttavissa olevien laserien määrän.
     */
    public int getLaserit() {
        return laserit;
    }

    /**
     *
     * @return palauttaa ammuttavissa olevien pommien määrän.
     */
    public int getPommit() {
        return pommit;
    }

    /**
     *
     * @return aluksen sprite
     */
    @Override
    public Image getSprite() {
        return sprite;
    }

    /**
     * Asettaa aluksen ampumistilan. Jos true, alus ampuu laukauksen seuraavana
     * mahdollisena ajanhetkenä, jos false, ei ammu.
     *
     * @param ampuuLaukauksiaNyt halutaanko aluksen ampuvan laukauksia.
     */
    public void setAmpuuLaukauksiaNyt(boolean ampuuLaukauksiaNyt) {
        this.ampuuLaukauksiaNyt = ampuuLaukauksiaNyt;
    }

}
