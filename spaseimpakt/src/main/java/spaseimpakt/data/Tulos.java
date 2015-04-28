/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaseimpakt.data;

import java.io.Serializable;

public class Tulos implements Comparable<Tulos>, Serializable{
    private final int pisteet;
    private final String nimi;
    
    public Tulos(String nimi, int pisteet){
        this.nimi=nimi;
        this.pisteet=pisteet;
    }
    
    public int getPisteet(){
        return pisteet;
    }
    
    public String getNimi(){
        return nimi;
    }

    @Override
    public int compareTo(Tulos t) {
        if (this.pisteet>t.getPisteet()) return -1;
        else if (this.pisteet<t.getPisteet()) return 1;
        else return this.nimi.compareTo(t.getNimi());
    }
}
