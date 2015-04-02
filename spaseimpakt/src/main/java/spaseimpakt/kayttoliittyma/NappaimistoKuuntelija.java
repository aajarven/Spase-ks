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
public class NappaimistoKuuntelija implements KeyListener{

    private Alus alus;
    private Pelimoottori moottori;

    public NappaimistoKuuntelija(Pelimoottori moottori) {
        this.moottori=moottori;
        this.alus =moottori.getAlus();
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode()==KeyEvent.VK_UP) {
            alus.setSuunta(Suunta.YLOS);
        } else if (e.getKeyCode()==KeyEvent.VK_DOWN){
            alus.setSuunta(Suunta.ALAS);
        } else if (e.getKeyCode()==KeyEvent.VK_LEFT){
            alus.setSuunta(Suunta.VASEN);
        } else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            alus.setSuunta(Suunta.OIKEA);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e){
//        alus.setSuunta(Suunta.PAIKALLAAN);
        
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
    }
}