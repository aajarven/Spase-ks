/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.data;

/**
 *
 * @author Anni Järvenpää
 */
public interface Liikutin {

    /**
     * Liikuttaa vihollista sen seuraavaan sijaintiin.
     *
     * @return vihollisen uudet koordinaatit kaksialkioisessa taulukossa [x, y]
     */
    public int[] liiku();

    /**
     *
     * @return vihollisen x-koordinaatti
     */
    public int getX();

    /**
     *
     * @return vihollisen y-koordinaatti
     */
    public int getY();

}
