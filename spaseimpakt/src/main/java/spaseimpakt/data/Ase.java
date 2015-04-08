/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

/**
 * Kaikki pelaajan käyttämät aseet, joilla voidaan tuhota vihollisia.
 *
 * @author Anni Järvenpää
 */
public interface Ase {

    // TODO törmäystarkastus johonkin, mihin?
    /**
     * Liikuttaa asetta yhdessä framessa liikutettavan määrän.
     */
    public void liiku();

}
