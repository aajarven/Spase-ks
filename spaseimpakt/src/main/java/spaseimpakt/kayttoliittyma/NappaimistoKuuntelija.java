/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.kayttoliittyma;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import spaseimpakt.data.Alus;
import spaseimpakt.data.Suunta;
import spaseimpakt.logiikka.Pelimoottori;

/**
 *
 * @author Anni Järvenpää
 */
public class NappaimistoKuuntelija implements KeyListener {

    private final Alus alus;
    private final Pelimoottori moottori;

    private boolean vasenpainettu;
    private boolean oikeapainettu;
    private boolean ylospainettu;
    private boolean alaspainettu;

    /**
     * Luo uuden näppäimistökuuntelijan, jolla voidaan välittää käyttäjän
     * näppäimistöltä antama input parametrina annetulle pelimoottorille.
     *
     * @param moottori pelimoottori, jonka pyörittämän pelin toimintaa ohjataan.
     */
    public NappaimistoKuuntelija(Pelimoottori moottori) {
        this.moottori = moottori;
        this.alus = moottori.getAlus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            alus.setAmpuuLaukauksiaNyt(true);
        }
        if(e.getKeyCode() == KeyEvent.VK_X){
            alus.setAmpuuLaseria(true);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            ylospainettu = true;
            alus.setSuunta(Suunta.YLOS);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            alaspainettu = true;
            alus.setSuunta(Suunta.ALAS);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            oikeapainettu = true;
            alus.setSuunta(Suunta.OIKEA);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            vasenpainettu = true;
            alus.setSuunta(Suunta.VASEN);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            alus.setAmpuuLaukauksiaNyt(false);
        }
        if(e.getKeyCode() == KeyEvent.VK_X){
            alus.setAmpuuLaseria(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            ylospainettu = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            alaspainettu = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            vasenpainettu = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            oikeapainettu = false;
        }
        tarkastaMuutSuunnat();
    }

    /**
     * Tarkastaa, pitääkö aluksen yhä liikkua johonkin suuntaan, vaikka yksi
     * nappuloista onkin päästetty irti
     */
    private void tarkastaMuutSuunnat() {
        if (ylospainettu == true) {
            alus.setSuunta(Suunta.YLOS);
        } else if (alaspainettu == true) {
            alus.setSuunta(Suunta.ALAS);
        } else if (oikeapainettu == true) {
            alus.setSuunta(Suunta.OIKEA);
        } else if (vasenpainettu == true) {
            alus.setSuunta(Suunta.VASEN);
        } else {
            alus.setSuunta(Suunta.PAIKALLAAN);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
