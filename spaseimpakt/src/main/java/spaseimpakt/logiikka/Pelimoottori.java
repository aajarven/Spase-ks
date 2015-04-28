/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.logiikka;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.swing.JOptionPane;
import spaseimpakt.data.Alus;
import spaseimpakt.data.Ammus;
import spaseimpakt.data.Ase;
import spaseimpakt.data.Laser;
import spaseimpakt.data.Level;
import spaseimpakt.data.Piirrettava;
import spaseimpakt.data.Vihu;
import spaseimpakt.kayttoliittyma.GraafinenKayttoliittyma;

/**
 * Hoitaa pelin pyörittämisen ja ohjaa muiden luokkien toimintaa
 *
 * @author Anni
 */
public class Pelimoottori extends Thread {

    /**
     * True jos peli on käynnissä, muuten false
     */
    private boolean kaynnissa;
    /**
     * True jos kaikki levelit on selvitetty, muuten false
     */
    private boolean peliVoitettu;

    /**
     * Viive kahden ruudunpäivityksen välillä
     */
    private final int STEP = 1000 / 100;

    private Alus alus;
    private GraafinenKayttoliittyma kayttoliittyma;

    /**
     * Pelaajan ampumat aseet, jotka tällä hetkellä ovat ruudulla näkyvissä
     */
    private CopyOnWriteArrayList<Ase> aseet;

    /**
     * Kaikki pelihahmot (esim viholliset, alus), jotka näytölle pitää pirtää
     */
    private CopyOnWriteArraySet<Piirrettava> piirrettavat;

    /**
     * Näytöllä tällä hetkellä olevat viholliset
     */
    private CopyOnWriteArrayList<Vihu> viholliset;

    /**
     * LevelManager, joka ohjaa levelien vaihtamista
     */
    private LevelManager lvlManager;

    /**
     * Nykyinen taso tai null jos peli ei ole vielä alkanut
     */
    private Level lvl;

    /**
     * Polut leveltiedostoihin
     */
    private final String[] levelTiedostot = new String[]{"resources/levelit/ekalevel.json"};

    /**
     * Aika levelin alkaessa, tästä lasketaan vihollisten ilmestymisajat
     */
    private long levelinAlkuAika;

    /**
     * Törmäystestaaja, jota käytetään hahmojen välisten törmäysten tutkimiseen
     */
    private TormaysTestaaja tormaysTestaaja;

    private HighscoreManager highscoreManager;

    private int pisteet;
    private boolean peliHavitty;
    private boolean scoreKirjattu;

    /**
     * Konstruktori
     *
     * @param kayttoliittyma käyttöliittymä, joka näyttää pelin tapahtumat
     * @param maailma maailma, jonka tilannetta päivitetään
     */
    public Pelimoottori(GraafinenKayttoliittyma kayttoliittyma) {
        this.kayttoliittyma = kayttoliittyma;
        kaynnissa = true;
        alus = new Alus(0, Pelirunko.KORKEUS / 2, Pelirunko.LEVEYS, Pelirunko.KORKEUS, this);
        aseet = new CopyOnWriteArrayList<>();
        piirrettavat = new CopyOnWriteArraySet<>();
        viholliset = new CopyOnWriteArrayList<>();
        lvlManager = new LevelManager(levelTiedostot, this);
        lvl = lvlManager.lueSeuraavaLevel();
        levelinAlkuAika = System.currentTimeMillis();
        peliVoitettu = false;
        piirrettavat.add(alus);
        tormaysTestaaja = new TormaysTestaaja();
        peliHavitty = false;
        pisteet = 0;
        scoreKirjattu = false;
        highscoreManager = new HighscoreManager();
    }

    /**
     * Metodi, jota kutsutaan, kun pelaaja ampuu uuden aseen
     *
     * @param ase ammuttu ase
     */
    public void lisaaAse(Ase ase) {
        aseet.add(ase);
        if (ase instanceof Ammus) { // tai myöhemmin laser, pommia ei piirretä
            piirrettavat.add((Ammus) ase);
        } else if (ase instanceof Laser) {
            piirrettavat.add((Laser) ase);
        }
    }

    /**
     * Poistaa olemassaolevan aseen kun se ei enää ole toiminnassa
     *
     * @param ase ase, joka poistetaan
     */
    public void poistaAse(Ase ase) {
        aseet.remove(ase);
        piirrettavat.remove(ase);
    }

    /**
     * Palauttaa listan kaikista tällä hetkellä aktiivisista aseista
     *
     * @return Lista, joka sisältää kaikki aseet.
     */
    public CopyOnWriteArrayList<Ase> getAseet() {
        return aseet;
    }

    public CopyOnWriteArrayList<Vihu> getVihut() {
        return viholliset;
    }

    /**
     * Lisää peliin uuden vihollisen
     *
     * @param vihu lisättävä vihollinen
     */
    public void lisaaVihu(Vihu vihu) {
        viholliset.add(vihu);
        piirrettavat.add((Piirrettava) vihu);
    }

    /**
     * Poistaa pelistä vihollisen
     *
     * @param vihu poistettava vihollinen
     */
    public void poistaVihu(Vihu vihu) {
        viholliset.remove(vihu);
        piirrettavat.remove(vihu);
    }

    /**
     * Palauttaa pelaajan ohjaaman aluksen
     *
     * @return pelaajan lentämä alus
     */
    public Alus getAlus() {
        return alus;
    }

    /**
     * Varsinainen pelitapahtuma. Liikuttaa alusta, siirtää maailmaa ja
     * päivittää käyttöliittymän, minkä jälkeen tutkii, tuleeko pelin suoritusta
     * vielä jatkaa ja toimii asianmukaisesti.
     */
    @Override
    public void run() {

        if (Pelirunko.ekaPeli) {
            vaihdaPelaajanNimi();
        }

        do {
            alus.paivita();

            for (Ase ase : aseet) {
                ase.liiku();
            }

            for (Vihu vihu : viholliset) {
                vihu.liiku();
            }

            kasitteleTormaykset();
            kayttoliittyma.piirra();

            paivitaLevel();
            odota();
        } while (kaynnissa);
    }

    private void paivitaLevel() {
        if (lvl.ovatkoVihollisetLoppu() && viholliset.isEmpty()) {
            muutaPisteita(5000);
            lvl = null;
        }

        if (lvl == null) {
            lvl = lvlManager.lueSeuraavaLevel();
            if (lvl == null) {
                peliVoitettu = true;
                lopeta();
            }
            levelinAlkuAika = System.currentTimeMillis();
        } else {
            Long aikaLevelinAlustaLong = System.currentTimeMillis() - levelinAlkuAika;
            int aikaLevelinAlusta = aikaLevelinAlustaLong.intValue();
            while (lvl.onkoSeuraavanVihollisenAika(aikaLevelinAlusta)) {
                lisaaVihu(lvl.seuraavaVihollinen());
            }
        }
    }

    private void kasitteleTormaykset() {
        for (Vihu vihu : viholliset) {
            if (tormaysTestaaja.tormaa(alus, vihu)) {
                peliHavitty = true;
                lopeta();
            } else {
                for (Ase ase : aseet) {
                    if (tormaysTestaaja.tormaa(ase, vihu)) {
                        muutaPisteita(100);
                        poistaVihu(vihu);
                        if (ase.getClass() == Ammus.class) {
                            poistaAse(ase);
                        }
                    }
                }
            }
        }
    }

    /**
     * Pysäyttää pelin suorituksen muuttujan step osoittamaksi ajaksi.
     */
    public void odota() {
        try {
            Thread.sleep(STEP);
        } catch (InterruptedException e) {
            System.out.println("sleeping interrupted");
            e.printStackTrace();
//            System.exit(0);
        }
    }

    /**
     * Lopettaa pelitilanteen päivittämisen.
     */
    public void lopeta() {
        kaynnissa = false;

        kirjaaScore();
        
        //TODO viestit
        if (peliVoitettu) {
            kayttoliittyma.naytaViesti("Onnittelut, voitit pelin!", "Voitit");
            peliVoitettu=false;
        } else if (peliHavitty) {
            kayttoliittyma.naytaViesti("Hävisit pelin.", "Hävisit");
            peliHavitty=false;
        }
    }

    public CopyOnWriteArraySet<Piirrettava> getPiirrettavat() {
        return piirrettavat;
    }

    /**
     * Näyttää dialogin, jossa kysytään pelaajan nimeä. Mikäli kenttä jätetään
     * tyhjäksi, nimeksi vaihdetaan "Anonyymi", muuten pelaajan antama nimi.
     *
     * @throws HeadlessException
     */
    public void vaihdaPelaajanNimi() throws HeadlessException { //TODO jos cancel niin pidä edellinen nimi, ei anonyymi

        if (!Pelirunko.ekaPeli) {
            lopeta();
        }
        Pelirunko.pelaajanNimi = (String) JOptionPane.showInputDialog(
                kayttoliittyma.getFrame(),
                "Anna nimesi", "Pelaajan nimi",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                Pelirunko.pelaajanNimi);
        if (Pelirunko.pelaajanNimi == null || Pelirunko.pelaajanNimi.isEmpty()) {
            Pelirunko.pelaajanNimi = "Anonyymi";
        }

        Pelirunko.restart();
    }

    public void muutaPisteita(int muutos) {
        pisteet += muutos;
    }

    private void kirjaaScore() {
        if (!scoreKirjattu && pisteet > 0) {
            highscoreManager.lisaaScore(Pelirunko.pelaajanNimi, pisteet);
        }
        scoreKirjattu=true;
    }
    
    /**
     * Hakee pelattavan luolan hichscoret HighscoreManagerilta muotoiltuna Stringinä ja palauttaa ne
     * @return pelattavan luolan highscoret valmiiksi muotoiltuna Stringinä
     */
    public String haeScoret(){
        return highscoreManager.scoretMuotoiltunaStringina();
    }

    public int getPisteet() {
        return pisteet;
    }
}
