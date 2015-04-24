/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.data;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Anni Järvenpää
 */
public class Level {

    TreeMap<Integer, ArrayList<Vihu>> viholliset;
    // TODO levelille tausta-artti
    // TODO viesti tms levelin alkuun ehkä?

    public Level() {
        this.viholliset = new TreeMap<Integer, ArrayList<Vihu>>();
    }

    /**
     * Lisää tasossa annetun vihollisen ilmestymään annetulla ajanhetkellä tässä
     * levelissä
     *
     * @param aika ajanhetki, jolla vihollisen halutaan ilmestyvän
     * @param vihu levelissä esiintyvä vihollinen
     */
    public void lisaaVihollinen(int aika, Vihu vihu) {
        if (viholliset.containsKey(aika)) {
            viholliset.get(aika).add(vihu);
        } else {
            ArrayList<Vihu> lisattava = new ArrayList<Vihu>();
            lisattava.add(vihu);
            viholliset.put(aika, lisattava);
        }
    }

    /**
     * palauttaa seuraavana ilmestymisvuorossa olevan vihollisen ja poistaa sen
     * tästä levelistä
     *
     * @return Vihollinen, joka seuraavaksi ilmestyy näytölle
     */
    public Vihu seuraavaVihollinen() {
        if (viholliset.isEmpty()) {
            return null;
        }
        ArrayList<Vihu> palautettavat = viholliset.get(viholliset.firstKey());
        Vihu palautettava;
        if (palautettavat.size() == 1) {
            palautettava = palautettavat.get(0);
            viholliset.remove(viholliset.firstKey());
        } else {
            palautettava = palautettavat.get(palautettavat.size() - 1);
            palautettavat.remove(palautettava);
        }
        return palautettava;
    }

    /**
     * Kertoo, pitäisikö seuraavan vihollisen olla jo näytöllä
     *
     * @param nykyhetki
     * @return true, jos seuraavan vihollisen voi jo piirtää näytölle, false jos
     * ei
     */
    public boolean onkoSeuraavanVihollisenAika(int nykyhetki) {
        if (viholliset.isEmpty()) {
            return false;
        } else if (viholliset.firstKey() <= nykyhetki) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kertoo, onko kaikki tason viholliset jo haettu
     * @return true jos uusia vihollisia ei enää ole, false jos on
     */
    public boolean ovatkoVihollisetLoppu() {
        return viholliset.isEmpty();
    }

    /**
     * Testausta varten tarvittava metodi, joka palauttaa TreeMapin, jossa ovat tason vihut ja niiden ilmestymishetket
     * @return tason vihut ilmestymishetken mukaan
     */
    public TreeMap<Integer, ArrayList<Vihu>> getVihut(){
        return viholliset;
    }    
}
