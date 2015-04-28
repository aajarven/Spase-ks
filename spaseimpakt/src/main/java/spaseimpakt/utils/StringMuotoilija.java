package spaseimpakt.utils;

/**
 * Stringien muotoiluun käytettävä luokka
 *
 * @author Anni Järvenpää
 */
public class StringMuotoilija {

    /**
     * Palauttaa annetun merkkijonon pelirungon MAX_NIMENPITUUS-vakion
     * osoittaman mittaisena
     *
     * @param nimi merkkijono, jonka pituus muutetaan
     * @return oikean mittainen merkkijono
     */
    public static String nimiOikeanMittaiseksi(String nimi, int pituus) {
        if (nimi.length() > pituus) {
            return nimi.substring(0, pituus - 1);
        } else {
            StringBuffer nimiBuffer = new StringBuffer(nimi);
            while (nimiBuffer.length() < pituus) {
                nimiBuffer.append(' ');
            }
            return nimiBuffer.toString();
        }
    }

    /**
     * Tekee järjestysluvusta yhtä monta merkkiä pitkän kuin maksiminumerosta
     * lisäämällä alkuun nollia. Lisäksi loppuun lisätään piste ja välilyönti.
     *
     * @param numero käsiteltävä numero
     * @param maksimiNumero numero, jonka kanssa käsiteltävästä numerosta
     * tehdään yhtä pitkä
     * @return annettu numero johon on lisätty haluttu määrä alkunollia ja
     * loppuun piste ja välilyönti
     */
    public static String jarjestyslukuMuotoilija(int numero, int maksimiNumero) {
        StringBuffer buffer = new StringBuffer(numeronPituusMerkkeina(maksimiNumero) + 2);
        while (buffer.length() + numeronPituusMerkkeina(numero) < numeronPituusMerkkeina(maksimiNumero)) {
            buffer.append('0');
        }
        buffer.append(numero);
        buffer.append('.');
        buffer.append(' ');

        return buffer.toString();
    }

    /**
     * Palauttaa annetun numeron pituuden merkkeinä
     *
     * @param numero tutkittava numero
     * @return numeron pituus merkkeinä
     */
    public static int numeronPituusMerkkeina(int numero) {
        return Integer.toString(numero).length();
    }

    /**
     * Muotoilee annetut pisteet lisäämällä alkuun nollia niin, että pisteistä
     * tulee yhtä pitkä luku kuin pelirungon MAX_PISTEET-vakion arvo ja kutsuu
     * ryhmittele-metodia, joka ryhmittelee numerot kolmen ryhmiin
     *
     * @param pisteet muotoiltavat pisteet
     * @return pisteet Stringinä kolmen numeron ryhmiin ryhmiteltynä ja oikealla
     * määrällä etunollia varustettuna
     */
    public static String pisteMuotoilija(int pisteet, int maksimipisteet) {
        int maxPituus = Integer.toString(maksimipisteet).length();

        StringBuffer pisteBuffer = new StringBuffer(maxPituus);
        for (int i = 0; i < maxPituus - Integer.toString(pisteet).length(); i++) {
            pisteBuffer.append('0');
        }

        pisteBuffer.append(pisteet);

        return ryhmittele(pisteBuffer).toString();
    }

    /**
     * Ryhmittelee StringBufferin sisältämän luvun numerot kolmen numeron
     * ryhmiin
     *
     * @param buffer käsiteltävä bufferi
     * @return bufferi ryhmiteltynä
     */
    private static StringBuffer ryhmittele(StringBuffer buffer) {
        StringBuffer palautus = new StringBuffer(buffer.length() + buffer.length() / 3);
        buffer.reverse();
        for (int i = buffer.length() - 1; i >= 0; i--) {
            palautus.append(buffer.charAt(i));
            if (i % 3 == 0 && i != 0) {
                palautus.append(" ");
            }
        }
        return palautus;
    }
}
