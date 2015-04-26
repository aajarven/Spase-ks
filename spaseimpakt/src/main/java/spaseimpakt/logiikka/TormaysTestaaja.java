/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.logiikka;

import java.awt.geom.Area;
import spaseimpakt.data.Alus;
import spaseimpakt.data.Ase;
import spaseimpakt.data.Vihu;

/**
 * Luokka, jota käytetään törmäystestauksiin.
 * @author Anni Järvenpää
 */
public class TormaysTestaaja {

    public TormaysTestaaja() {
    }

    /**
     * Testaa, törmäävätkö parametreina annetut ase ja vihollinen toisiinsa
     * @param ase tutkittava ase
     * @param vihu tutkittava vihollinen
     * @return true jos tutkittavat oliot koskettavat toisiaan, false jos eivät
     */
    protected boolean tormaa(Ase ase, Vihu vihu) {
        Area aseAlue= new Area(ase.getVaikutusalue());
        Area vihunAlue = new Area(vihu.getBoundingBox());
        
        aseAlue.intersect(vihunAlue);
        return aseAlue.isEmpty();
    }

    /**
     * Testaa, törmäävätkö parametreina annetut alus ja vihollinen toisiinsa
     * @param alus tutkittava alus
     * @param vihu tutkittava vihollinen
     * @return true jos tutkittavat oliot koskettavat toisiaan, false jos eivät
     */
    protected boolean tormaa(Alus alus, Vihu vihu) {
        Area alusAlue = new Area(alus.getBoundingBox());
        Area vihunAlue = new Area(alus.getBoundingBox());
        
        alusAlue.intersect(alusAlue);
        return alusAlue.isEmpty();
        //TODO actual code
    }

}
