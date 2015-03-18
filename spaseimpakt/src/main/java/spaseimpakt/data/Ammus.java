
package spaseimpakt.data;

/**
 * Pelin yleisin ase: tavallinen oikealle liikkuva ammus, joka tuhoutuu osuessaan kohteeseen.
 * 
 * @author Anni Järvenpää
 */
public class Ammus {
    int x;
    int y;
    public static final int NOPEUS=5; // TODO järkevä nopeus

    // TODO sprite
    
    public Ammus(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void liiku(){
        x+=this.NOPEUS;
        // TODO poisto kun oikeassa laidassa
    }

    public int getX() {
        return x;
    }    

    public int getY() {
        return y;
    }
    
    
    
}
