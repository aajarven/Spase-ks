package spaseimpakt.data;

import java.awt.Image;
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
    int y;
    int maxX;
    public static final int NOPEUS = 5; // TODO järkevä nopeus
    Image sprite;

    public Ammus(int x, int y, int maxX, Pelimoottori moottori) {
        this.x = x;
        this.y = y;
        this.maxX = maxX;
        this.moottori = moottori;
        lueSprite();
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

}
