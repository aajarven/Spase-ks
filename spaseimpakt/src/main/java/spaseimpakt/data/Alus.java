package spaseimpakt.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import spaseimpakt.logiikka.Pelimoottori;

/**
 *
 * @author Anni Järvenpää
 */
public class Alus implements Piirrettava{

    private Pelimoottori moottori;
    private int x;
    private int y;
    private int maxX;
    private int maxY;
    private Suunta suunta;
    private long edellinenAmmus;
    private int laserit;
    private int pommit;
    private Image sprite;

    // Onko tämä rumaa?
    public static final int NOPEUS = 5; // TODO sopiva nopeuden arvo alukselle
    private final int AMPUMIS_INTERVALLI = 150;
    private final int LASERIT_ALUSSA = 3;
    private final int POMMIT_ALUSSA = 3;

    // TODO mieti, miten otat aluksen korkeuden ja leveyden huomioon sallittuja koordinaatteja laskettaessa
    // TODO alukselle sprite
    // TODO törmäystarkastus
    // TODO elämät
    // TODO ammusten liikuttaminen (onko ylipäänsä tyhmää ja sinkkuvastuuperiaatteen vastaista, että alus käskee ammuksensa liikkumaan, pitäisi laittaa muualle? mieti minne?)
    /**
     * Alus, jolla pelaaja lentää. Jos annetut aloituskoordinaatit eivät ole
     * sallitulla alueella, luodaan alus niitä lähimpään sallittuun pisteeseen.
     *
     * @param x Aluksen x-koordinaatti (0 vasemmassa laidassa, kasvaa oikealle)
     * @param y Aluksen y-koodrinaatti (0 ylhäällä, kasvaa alas)
     * @param pelialueenLeveys Peli-ikkunan koko x-suunnassa
     * @param pelialueenKorkeus Peli-ikkunan koko y-suunnassa
     * (käytännössä pelialueen korkeus)
     */
    public Alus(int x, int y, int pelialueenLeveys, int pelialueenKorkeus, Pelimoottori moottori) {
        try{
            BufferedImage i = ImageIO.read(new File("resources/simppelialus.png"));
            this.sprite = i;
        } catch (IOException e){
            System.out.println("Aluksen kuvaa ei löytynyt");
        }
        
        this.x = x;
        this.y = y;
        this.maxX = pelialueenLeveys-sprite.getWidth(null); // TODO korjaa se, ettei mikään estä laittamasta maksimiarvoksi esim negatiivista lukua, jos ohjelmoija on tyhmä
        this.maxY = pelialueenKorkeus-sprite.getHeight(null);
        this.moottori = moottori;
        this.laserit = LASERIT_ALUSSA;
        this.pommit = POMMIT_ALUSSA;
        tarkastaPaikka();
        suunta = Suunta.PAIKALLAAN;
        edellinenAmmus = 0;
        
        
    }

    public int getX() {
        return x;
    }

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
    public void liiku() {
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
            moottori.lisaaAse(new Ammus(x, y, maxX, moottori));
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

    @Override
    public Image getSprite() {
        return sprite;
    }
}
