/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import spaseimpakt.data.Vihu;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import spaseimpakt.data.FunktioLiikutin;
import spaseimpakt.data.Liikutin;
import spaseimpakt.data.Pikkuvihu;
import spaseimpakt.logiikka.Pelimoottori;

/**
 *
 * @author Anni Järvenpää
 */
public class JsonLukija {

    public JsonLukija() {
    }

    /**
     * Lukee annetussa polussa sijaitsevasta JSON-tiedostosta, mitä vihollisia leveliin kuuluu, ja palauttaa ne ilmestymisaikoineen
     * @param path polku leveliä kuvaavaan JSON-tiedostoon
     * @param moottori pelimoottori, jonka pyörittämään peliin viholliset luodaan
     * @return Viholliset ja ajanhetket, joilla niiden kuuluu ilmestyä näkyville
     */
    public HashMap<Vihu, Integer> lueVihut(String path, Pelimoottori moottori) {

        HashMap<Vihu, Integer> palautettava = new HashMap<>();

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(path));

            JSONObject level = (JSONObject) obj;
            JSONArray vihut = (JSONArray) level.get("vihut");

            Iterator<JSONObject> iterator = vihut.iterator();
            while (iterator.hasNext()) {
                Vihu lisattava;

                JSONObject vihuTiedot = iterator.next();
                int ilmestymisaika = ((Long) vihuTiedot.get("ilmestymisaika")).intValue();
                JSONObject vihu = (JSONObject) vihuTiedot.get("vihu");
                int x = ((Long) vihu.get("x")).intValue();
                int dx = ((Long) vihu.get("dx")).intValue();
                String spritePath = (String) vihu.get("sprite");
                BufferedImage sprite;
                try {
                    sprite = ImageIO.read(new File(spritePath));
                } catch (IOException e) {
                    e.printStackTrace();
                    sprite = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
                }
                int movemode = ((Long) vihu.get("movemode")).intValue(); // movemode 0=funktio, 1=seuraa, 2=random
                if (movemode == 0) {
                    JSONArray kerroinarray = (JSONArray) vihu.get("kertoimet");
                    double[] kertoimet = new double[kerroinarray.size()];
                    for(int i=0; i<kerroinarray.size(); i++){
                        kertoimet[i] = ((Number)kerroinarray.get(i)).doubleValue();
                    }
                    FunktioLiikutin liikutin = new FunktioLiikutin(x, dx, kertoimet);
                    lisattava = new Pikkuvihu(x, sprite, liikutin, moottori);
                } else {
                    lisattava=null;
                    System.out.println("Movemode ei ollut mikään sallituista (0)");
                }
                palautettava.put(lisattava, ilmestymisaika);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return palautettava;
    }
}
