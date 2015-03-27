/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;
import logiikka.Pelimoottori;

/**
 *
 * @author Anni Järvenpää
 */
public class Pommi implements Ase{

    Pelimoottori moottori;
    
    public Pommi(Pelimoottori moottori){
        this.moottori=moottori;
    }
    
    @Override
    public void liiku() {
        moottori.poistaAse(this);
    }
    
}
