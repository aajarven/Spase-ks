package spaseimpakt.logiikka;

import java.io.*;
import java.util.TreeSet;
import spaseimpakt.data.Tulos;
import spaseimpakt.utils.StringMuotoilija;

/**
 * Hoitaa parhaiden pelitulosten kirjaamisen sekä lukemisen tiedostosta.
 *
 * @author Anni Järvenpää
 */
public class HighscoreManager {

    private static final long serialVersionUID = 1L;
    private TreeSet<Tulos> scoret;
    private static final String SCORETIEDOSTO = "highscores.dat";
    private final int SAILYTETTAVAT_SCORET = 10;
    private final int NIMENPITUUS = 12;
    private final int MAX_PISTEET = 999999999;
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    /**
     * Konstruktori
     */
    public HighscoreManager() {
        scoret = new TreeSet();
    }

    /**
     * Lisää tuloksen tiedostoon mikäli se on tarpeeksi hyvä
     *
     * @param nimi pelaajan nimi
     * @param pisteet pelaajan saamat pisteet
     */
    public void lisaaScore(String nimi, int pisteet) {
        lataaScoretTiedostosta();
        taytaScoretaulu(); // jos scoreja ei ole tarpeeksi

        scoret.add(new Tulos(nimi, pisteet));
        if (scoret.size() > SAILYTETTAVAT_SCORET) {
            scoret.remove(scoret.last());
        }

        paivitaScoretTiedostoon();
    }

    /**
     * Luo nollan pisteen tuloksia anonyymeille pelaajille, kunnes scoretaulu on
     * täynnä
     */
    private void taytaScoretaulu() {
        int i = 0;
        while (scoret.size() < SAILYTETTAVAT_SCORET) {
            i++;
            String jarjestysluku = "";
            if (i < 10) {
                jarjestysluku = "0" + i;
            } else {
                jarjestysluku += i;
            }
            scoret.add(new Tulos("Anonyymi" + jarjestysluku, 0));
        }
    }

    // TODO mieti tätä
    /**
     * Lukee scoret tiedostosta, mikäli sellainen on olemassa. Mikäli ei ole,
     * sellainen luodaan.
     */
    private void lataaScoretTiedostosta() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(SCORETIEDOSTO));
            scoret = (TreeSet<Tulos>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            File scoret = new File(SCORETIEDOSTO);
        } catch (IOException e) {
            System.out.println("IO Error ladattaessa: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("CNF Error ladattaessa: " + e.getMessage());
        }
    }

    /**
     * Kirjoittaa scoret tiedostoon, mikäli sellainen on olemassa. Mikäli ei
     * ole, sellainen yritetään luoda.
     */
    private void paivitaScoretTiedostoon() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(SCORETIEDOSTO));
            outputStream.writeObject(scoret);
        } catch (FileNotFoundException e) {
            File scoret = new File(SCORETIEDOSTO);
        } catch (IOException e) {
            System.out.println("IO Error paivitettaessa: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Error paivitettaessa: " + e.getMessage());
            }
        }
    }

    /**
     * Palauttaa scoret highscoreiksi kirjoitettavaksi sopivaksi muotoiltuna
     * stringinä
     *
     * @return highscoret muotoiltuna highscoreikkunaan muotoiltuna sopivana
     * stringinä
     */
    public String scoretMuotoiltunaStringina() {
        StringBuffer scoreMuodostaja = new StringBuffer(
                SAILYTETTAVAT_SCORET * (StringMuotoilija.jarjestyslukuMuotoilija(SAILYTETTAVAT_SCORET, SAILYTETTAVAT_SCORET).length()
                + NIMENPITUUS
                + 1
                + StringMuotoilija.numeronPituusMerkkeina(MAX_PISTEET)
                + StringMuotoilija.numeronPituusMerkkeina(MAX_PISTEET) / 3)
                + SAILYTETTAVAT_SCORET - 1
        );
        lataaScoretTiedostosta();
        Object[] scoreArray = scoret.toArray();
        int i = 1;
        for (Object o : scoreArray) {
            if (o instanceof Tulos) {
                Tulos tulos = (Tulos) o;
                scoreMuodostaja.append(StringMuotoilija.jarjestyslukuMuotoilija(i, SAILYTETTAVAT_SCORET));
                scoreMuodostaja.append(StringMuotoilija.nimiOikeanMittaiseksi(tulos.getNimi(), NIMENPITUUS));
                scoreMuodostaja.append(" ");
                scoreMuodostaja.append(StringMuotoilija.pisteMuotoilija(tulos.getPisteet(), MAX_PISTEET));
                if (i != scoreArray.length) {
                    scoreMuodostaja.append("\n");
                }
                i++;
            }
        }

        return scoreMuodostaja.toString();
    }

}
