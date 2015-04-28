/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import java.awt.Polygon;

/**
 * Pelin pahikset.
 *
 * @author Anni Järvenpää
 */
public interface Vihu {

    /**
     * Liikuttaa vihollista yhden askeleen
     */
    public void liiku();

    /**
     *
     * @return vihollisen vasemman yläkulman x-koordinaatti
     */
    public int getX();

    /**
     *
     * @return vihollisen vasemman yläkulman y-koordinaatti
     */
    public int getY();

    /**
     * Palauttaa törmäystestauksissa tarvittavan alueen, jonka sisällä vihu on
     *
     * @return Polygoni, jonka sisään vihu mahtuu kokonaan
     */
    public Polygon getBoundingBox();
}
