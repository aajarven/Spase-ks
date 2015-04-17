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
public class FunktioLiikutin implements Liikutin{

    private int x;
    private int y;
    private int dx;
    private int[] kertoimet;
    
    /**
     * Konstruktori, jolla luodaan uusi liikutin, joka antaa koordinaatteja annetun neljännen asteen funktion mukaisesti
     * @param x liikuteltavan olion x-koordinaatti alussa
     * @param dx nopeus, jolla alus liikkuu x-suunnassa (pikseliä/askel)
     * @param kertoimet viisialkioinen taulukko [a, b, c, d, e], joka määrittää muotoa ax⁴+bx³+cx²+dx+e olevan funktion
     */
    public FunktioLiikutin(int x, int dx, int[] kertoimet) {
        this.x = x;
        this.dx=dx;
        this.kertoimet = kertoimet;
        paivitaY();
    }

    /**
     * Liikuttaa vihollista yhden askeleen
     * @return uudet koordinaatit muodossa [x, y]
     */
    @Override
    public int[] liiku() {
        x-=dx;
        paivitaY();
        return new int[]{x, y};
    }
    
    /**
     * Muuttaa y-koordinaatin vastaamaan nykyistä x-koordinaattia
     */
    private void paivitaY(){
        y=(int) (kertoimet[0]*Math.pow(x, 4)+kertoimet[1]*Math.pow(x, 3)+kertoimet[2]*Math.pow(x, 2)+kertoimet[3]*x+kertoimet[4]);
    }
    
    /**
     * 
     * @return y-koordinaatti
     */
    public int getY(){
        paivitaY();
        return y;
    }

    /**
     * 
     * @return x-koordinaatti
     */
    @Override
    public int getX() {
        return x;
    }

    public int[] getKertoimet() {
        return kertoimet;
    }

    public int getDx() {
        return dx;
    }
    
    
    
}
