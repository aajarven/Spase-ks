/*
 * Copyright Anni Järvenpää 2015
 */
package logiikka;

import java.util.ArrayList;
import spaseimpakt.data.Ammus;
import spaseimpakt.data.Ase;

/**
 *
 * @author Anni Järvenpää
 */
public class Pelimoottori {
    
    ArrayList<Ase> aseet;

    public Pelimoottori() {
        aseet=new ArrayList<>();
    }
    
    //TODO muu pelimoottori

    public void lisaaAse(Ase ase) {
        aseet.add(ase);
    }

    public void poistaAse(Ase ase) {
        aseet.remove(ase);
    }
    
    public ArrayList<Ase> getAseet(){
        return aseet;
    }
    
}
