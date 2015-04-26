/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.data;

import java.awt.Image;
import java.awt.Polygon;

/**
 * Rajapinta, jonka kaikki näytölle piirrettävät oliot (esim. alus, aseet,
 * viholliset) toteuttavat.
 *
 * @author Anni Järvenpää
 */
public interface Piirrettava {

    /**
     *
     * @return piirrettävän objektin x-koordinaatti
     */
    public int getX();

    /**
     *
     * @return piirrettävän objektin y-koordinaatti
     */
    public int getY();

    /**
     *
     * @return piirrettävän objektin sprite
     */
    public Image getSprite();

}
