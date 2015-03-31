package spaseimpakt.data;

import java.awt.Image;
import spaseimpakt.logiikka.Pelimoottori;

/**
 * Pelin yleisin ase: tavallinen oikealle liikkuva ammus, joka tuhoutuu
 * osuessaan kohteeseen.
 *
 * @author Anni Järvenpää
 */
public class Ammus implements Ase, Piirrettava {

    Pelimoottori moottori;
    int x;
    int y;
    int maxX;
    public static final int NOPEUS = 5; // TODO järkevä nopeus

    // TODO sprite
    public Ammus(int x, int y, int maxX, Pelimoottori moottori) {
        this.x = x;
        this.y = y;
        this.maxX = maxX;
        this.moottori = moottori;
    }

    @Override
    public void liiku() {
        x += this.NOPEUS;
        if (x > maxX) {
            moottori.poistaAse(this);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public Image getSprite() {
        //TODO oispa kuva
        return null;
    }

}
