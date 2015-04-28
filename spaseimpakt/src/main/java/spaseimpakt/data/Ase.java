/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import java.awt.Polygon;

/**
 * Kaikki pelaajan käyttämät aseet, joilla voidaan tuhota vihollisia.
 *
 * @author Anni Järvenpää
 */
public interface Ase {

    /**
     * Liikuttaa asetta yhdessä framessa liikutettavan määrän.
     */
    public void liiku();

    /**
     * Palauttaa aseen vaikutusalueen (alueen, jolla oleviin vihollisiin ase
     * tekee vahinkoa)
     *
     * @return vaikutusalue
     */
    public Polygon getVaikutusalue();

}
