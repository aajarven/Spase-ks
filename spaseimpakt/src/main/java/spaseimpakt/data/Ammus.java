package spaseimpakt.data;

import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
    final int y;
    int maxX;
    public static final int NOPEUS = 5; // TODO järkevä nopeus
    Image sprite;

    /**
     * Luo uuden ammuksen.
     *
     * @param x ammuksen vasemman yläkulman x-koordinaatti
     * @param y ammuksen vasemman yläkulman y-koordinaatti
     * @param ruudunLeveys peli-ikkunan leveys, josta lasketaan, koska ammus
     * voidaan poistaa
     * @param moottori pelimoottori, jonka pyörittämään peliin ammus on luotu
     */
    public Ammus(int x, int y, int ruudunLeveys, Pelimoottori moottori) {
        lueSprite();
        this.x = x;
        this.y = y;
        this.maxX = ruudunLeveys - sprite.getWidth(null);
        this.moottori = moottori;
    }

    /**
     * Liikuttaa ammusta yhden askeleen. Jos ammus on jo pelialueen reunassa,
     * ammus poistetaan.
     */
    @Override
    public void liiku() {
        x += this.NOPEUS;
        if (x > maxX) {
            moottori.poistaAse(this);
        }
    }

    /**
     *
     * @return ammuksen vasemman yläkulman x-koordinaatti
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     *
     * @return ammuksen vasemman yläkulman y-koordinaatti
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     *
     * @return ammuksen sprite
     */
    @Override
    public Image getSprite() {
        return sprite;
    }

    private void lueSprite() {
        try {
            BufferedImage i = ImageIO.read(new File("resources/ammus.png"));
            this.sprite = i;
        } catch (IOException e) {
            System.out.println("Ammuksen kuvaa ei löytynyt");
        }
    }

    /**
     * Palauttaa alueen, jolla oleviin vihollisiin ammus tekee vahinkoa.
     *
     * @return ammuksen vaikutusalue
     */
    @Override
    public Polygon getVaikutusalue() {
        return new Polygon(new int[]{x, x, x + sprite.getWidth(null), x + sprite.getWidth(null)}, new int[]{y, y + sprite.getHeight(null), y + sprite.getHeight(null), y}, 4);
    }

}
