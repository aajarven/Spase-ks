package spaseimpakt.logiikka;

import java.util.HashMap;
import spaseimpakt.data.Level;
import spaseimpakt.data.Vihu;
import spaseimpakt.utils.JsonLukija;

/**
 * Hoitaa levelin vaihtamiseen liittyvän toiminnallisuuden
 *
 * @author Anni Järvenpää
 */
public class LevelManager {

    private static String[] levelPolut;
    private JsonLukija lukija = new JsonLukija();
    private Pelimoottori moottori;
    private int nykyinenLvl;

    /**
     * Konstruktori,
     *
     * @param moottori pelimoottori, jonka pyörittämään peliin luettu level
     * tulee
     */
    public LevelManager(String[] levelPolut, Pelimoottori moottori) {
        this.levelPolut = levelPolut;
        this.moottori = moottori;
        nykyinenLvl = 0;
    }

    /**
     * Mikäli levelejä on vielä jäljellä, palautetaan seuraava level. Jos ei
     * ole, palautetaan null.
     *
     * @return seuraava level tai null jos viimeinen level on jo haettu.
     */
    public Level lueSeuraavaLevel() {
        nykyinenLvl++;
        if (nykyinenLvl > levelPolut.length) {
            return null;
        } else {
            Level palautettava = new Level();
            HashMap<Vihu, Integer> vihuajat = lukija.lueVihut(levelPolut[nykyinenLvl - 1], moottori);
            for (Vihu vihu : vihuajat.keySet()) {
                palautettava.lisaaVihollinen(vihuajat.get(vihu), vihu);
            }
            return palautettava;
        }
    }

}
