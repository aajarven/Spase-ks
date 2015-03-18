package spaseimpakt.data;

import java.util.ArrayList;

/**
 *
 * @author Anni Järvenpää
 */
public class Alus {

    private int x;
    private int y;
    private int maxX;
    private int maxY;
    private Suunta suunta;

    // Onko tämä rumaa?
    public static final int NOPEUS = 5; // TODO sopiva nopeuden arvo alukselle

    // TODO mieti, miten otat aluksen korkeuden ja leveyden huomioon sallittuja koordinaatteja laskettaessa
    // TODO alukselle sprite
    // TODO törmäystarkastus
    // TODO elämät
    // TODO ammusten liikuttaminen (onko ylipäänsä tyhmää ja sinkkuvastuuperiaatteen vastaista, että alus käskee ammuksensa liikkumaan, pitäisi laittaa muualle? mieti minne?)
    /**
     * Alus, jolla pelaaja lentää. Jos annetut aloituskoordinaatit eivät ole sallitulla alueella, luodaan alus niitä lähimpään sallittuun pisteeseen.
     *
     * @param x Aluksen x-koordinaatti (0 vasemmassa laidassa, kasvaa oikealle)
     * @param y Aluksen y-koodrinaatti (0 ylhäällä, kasvaa alas)
     * @param maxX Suurin sallittu x-koordinaatti, jota pidemmälle oikealle ei
     * voi liikkua (käytännössä pelialueen leveys)
     * @param maxY Suurin sallittu y-koordinaatti, jota alemmas ei voi liikkua
     * (käytännössä pelialueen korkeus)
     */
    public Alus(int x, int y, int maxX, int maxY) {
        this.x = x;
        this.y = y;
        this.maxX = maxX; // TODO korjaa se, ettei mikään estä laittamasta maksimiarvoksi esim negatiivista lukua, jos ohjelmoija on tyhmä
        this.maxY = maxY;
        tarkastaPaikka();
        suunta = Suunta.PAIKALLAAN;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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

    // TODO ampumistoiminnallisuus
}
