/*
 * Copyright Anni Järvenpää 2015
 */
package spaseimpakt.kayttoliittyma;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import javax.swing.*;
import spaseimpakt.logiikka.Pelimoottori;
import spaseimpakt.logiikka.Pelirunko;

/**
 * Luo käyttöliittymän ja hoitaa sen käyttämiseen liittyviä asioita sisältäen
 * mm. tarvittavat kuuntelijat
 *
 * @author Anni
 */
public class GraafinenKayttoliittyma implements Runnable {

    /**
     * JFrame johon komponentit asetellaan
     */
    private static JFrame frame;

    /**
     * Käyttöliittymän se osa, jossa itse pelitapahtumat (alus, ammukset,
     * viholliset yms) näytetään.
     */
    private Ikkuna ikkuna;

    /**
     * Pelimoottori, joka huolehtii näytettävän pelin edistämisestä
     */
    private Pelimoottori moottori;

    /**
     * Luo uuden graafisen käyttöliittymän, joka näyttää parametrina annetun
     * pelimoottorin tapahtumat
     *
     * @param moottori pelimoottori, jonka ajaman pelin tapahtumat piirretään
     */
    public GraafinenKayttoliittyma(Pelimoottori moottori) {
        this.moottori = moottori;
    }

    /**
     * Konstruktori. Pelimoottori täytyy asettaa erikseen käyttäen setteriä.
     *
     * @see setPelimoottori
     */
    public GraafinenKayttoliittyma() {
    }

    /**
     * Asettaa pelimoottorin, jonka tapahtumat näytölle piirretään
     *
     * @param moottori pelimoottori, joka pyörittää kyseistä peliä
     */
    public void setPelimoottori(Pelimoottori moottori) {
        this.moottori = moottori;
    }

    /**
     * Luo framen sekä kutsuu metodeja komponenttien ja kuuntelijoiden
     * luomiseksi
     */
    @Override
    public void run() {
        frame = new JFrame("Spase Impakt");
//        frame.setPreferredSize(new Dimension(LEVEYS, KORKEUS+10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        luoKomponentit(frame.getContentPane());

        //TODO näppäimistökuuntelija
        frame.addKeyListener(new NappaimistoKuuntelija(moottori));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Lisää parametrina annettuun containeriin yläpalkin ja peli-ikkunan
     *
     * @param container container, johon komponentit lisätään
     */
    private void luoKomponentit(Container container) {
        container.add(luoMenupalkki(), BorderLayout.NORTH);
        ikkuna = new Ikkuna(moottori);
        ikkuna.setPreferredSize(new Dimension(Pelirunko.LEVEYS, Pelirunko.KORKEUS));
        container.add(ikkuna, BorderLayout.CENTER);
    }

    /**
     * Luo ja palauttaa yläpalkin peli-ikkunaan. Palkki mahdollistaa pelin
     * aloittamisen alusta, pelaajan nimen muuttamisen, luolan valitsemisen,
     * highscorejen näyttämisen sekä ohjeiden ja tietojen näyttämisen.
     *
     * @return menupalkki
     */
    private JMenuBar luoMenupalkki() {
        JMenuBar ylapalkki = new JMenuBar();

        JMenu pelimenu = new JMenu("Peli");

        JMenuItem restartnappi = new JMenuItem("Aloita alusta");
        restartnappi.addActionListener(new RestartKuuntelija());
        pelimenu.add(restartnappi);

        JMenuItem pelaajaValintaNappi = new JMenuItem("Pelaajan nimi");
        pelaajaValintaNappi.addActionListener(new PelaajaValintaKuuntelija());
        pelimenu.add(pelaajaValintaNappi);

        JMenuItem highscorenappi = new JMenuItem("Highscores");
        highscorenappi.addActionListener(new HighscoreKuuntelija(moottori));
        pelimenu.add(highscorenappi);

        JMenu apuamenu = new JMenu("Apua");
        JMenuItem ohjenappi = new JMenuItem("Ohjeet");
        ohjenappi.addActionListener(new ApuaKuuntelija("Apua", "Apua", new File("resources/ohjeet.txt"), frame));
        apuamenu.add(ohjenappi);

        JMenuItem tietojanappi = new JMenuItem("Tietoja");
        tietojanappi.addActionListener(new ApuaKuuntelija("Tietoja", "Tietoja", new File("resources/tietoja.txt"), frame));
        apuamenu.add(tietojanappi);

        ylapalkki.add(pelimenu);
        ylapalkki.add(apuamenu);
        return ylapalkki;
    }

    /**
     * Palauttaa framen
     *
     * @return frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Mahdollistaa pelin tapahtumia ohjaavan pelimoottorin asettamisen
     *
     * @param moottori käytettävä pelimoottori
     */
    public void setMoottori(Pelimoottori moottori) {
        this.moottori = moottori;
    }

    /**
     * Päivittää grafiikat ikkunan repaint-metodilla
     *
     * @see Ikkuna#repaint
     */
    public void piirra() {
        if (ikkuna != null) {
            ikkuna.repaint();
        }
    }

    /**
     * Näyttää pelaajalle popup-ikkunan annetulla viestillä ja otsikolla.
     * Ikkunassa on ainoastaan ok- ja rastipainikkeet, ei muuta
     * interaktiivisuutta.
     *
     * @param viesti näytettävä viesti
     * @param otsikko viestin otsikko
     */
    public void naytaViesti(String viesti, String otsikko) {
        JOptionPane.showMessageDialog(frame, viesti, otsikko, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sulkee käyttöliittymän
     */
    public void sulje() {
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     * Mahdollistaa pelaajan nimen vaihtamisen kutsumalla pelimoottorin
     * vaihdaPelaajanNimi-metodia.
     *
     * @see Pelimoottori#vaihdaPelaajanNimi()
     */
    private class PelaajaValintaKuuntelija implements ActionListener {// pelaajan nimen valinnan voisi tehdä myös JDialogilla kuten chompissa

        @Override
        public void actionPerformed(ActionEvent e) {
            moottori.vaihdaPelaajanNimi();
        }
    }

    /**
     * Mahdollistaa pelin aloittamisen alusta kutsumalla pelirungon
     * restart-metodia
     *
     * @see Pelirunko#restart()
     */
    private class RestartKuuntelija implements ActionListener {

        public RestartKuuntelija() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Pelirunko.restart();
        }
    }

    /**
     * ActionListener, joka kuuntelee nappulaa, jolla pelaaja saa ohjeita
     * sisältävän ikkunan näkyviin.
     */
    private static class ApuaKuuntelija implements ActionListener {

        String ikkunaOtsikko;
        String otsikko;
        File tekstitiedosto;
        Frame frame;

        /**
         * Konstruktori.
         *
         * @param ikkunaOtsikko popup-ikkunan otsikko
         * @param otsikko otsikko, joka näytetään tekstin yläpuolella
         * @param teksti tiedosto, joka sisältää leipätekstin
         * @param frame frame, jonka lapseksi uusi ikkuna luodaan
         */
        public ApuaKuuntelija(String ikkunaOtsikko, String otsikko, File teksti, Frame frame) {
            this.ikkunaOtsikko = ikkunaOtsikko;
            this.otsikko = otsikko;
            this.tekstitiedosto = teksti;
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog naytettava = this.luoDialogi();
            naytettava.setLocationRelativeTo(frame);
            naytettava.pack();
            naytettava.setVisible(true);
        }

        /**
         * Luo uuden JDialogin, jossa on annettu otsikko ja kutsuu muita
         * metodeja, joilla siihen saadan oikea teksti.
         *
         * @return luotu ikkuna
         * @see #lisaaOtsikko(java.awt.GridBagConstraints, javax.swing.JDialog)
         * @see #lisaaTekstialue(java.awt.GridBagConstraints,
         * javax.swing.JDialog)
         */
        private JDialog luoDialogi() {
            JDialog dialogi = new JDialog(frame, ikkunaOtsikko, true);
            dialogi.setLayout(new GridBagLayout());
            dialogi.setPreferredSize(new Dimension(400, 350));

            GridBagConstraints c = new GridBagConstraints();

            lisaaOtsikko(c, dialogi);
            lisaaTekstialue(c, dialogi);

            return dialogi;
        }

        /**
         * Lisää annettuun JDialogiin otsikon
         *
         * @param c GridBagConstraints, jotka ohjaavat ikkunan layouttia
         * @param dialogi JDialog, johon otsikko lisätään
         */
        private void lisaaOtsikko(GridBagConstraints c, JDialog dialogi) {
            JLabel otsikko = new JLabel(this.otsikko);
            otsikko.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.insets = new Insets(10, 20, 0, 0);
            dialogi.add(otsikko, c);
        }

        /**
         * Lisää annettuun JDialogiin tekstin
         *
         * @param c GridBagConstraints, jotka ohjaavat ikkunan layouttia
         * @param dialogi JDialog, johon tekstilisätään
         */
        private void lisaaTekstialue(GridBagConstraints c, JDialog dialogi) {
            JTextArea tekstialue = new JTextArea();
            JScrollPane scrollialue = new JScrollPane(tekstialue);
            tekstialue.setEditable(false);
            tekstialue.setMargin(new Insets(5, 5, 5, 5));

            c.gridx = 0;
            c.gridy = 1;
            c.weightx = 1;
            c.weighty = 1;
            c.insets = new Insets(5, 5, 5, 5);
            c.fill = GridBagConstraints.BOTH;

            dialogi.add(scrollialue, c);
            tekstialue.setText(lueTekstiTiedostosta());
            tekstialue.setLineWrap(true);
            tekstialue.setWrapStyleWord(true);
        }

        /**
         * Lukee ikkunassa näytettävän tekstin tiedostosta
         *
         * @return tiedoston teksti Stringinä
         */
        private String lueTekstiTiedostosta() {
            String palautettava = "";
            try {
                Scanner lukija = new Scanner(tekstitiedosto, "UTF-8");
                while (lukija.hasNextLine()) {
                    palautettava += lukija.nextLine() + "\n";
                }
                lukija.close();
                return palautettava;
            } catch (Exception e) {
                return "Virhe tiedostoa luettaessa: " + e.getMessage();
            }
        }
    }

    /**
     * ActionListener, joka kuuntelee highscorenapin painallusta
     */
    private class HighscoreKuuntelija implements ActionListener {

        Pelimoottori moottori;

        /**
         * Konstruktori
         *
         * @param moottori pelimoottori, jonka highscoret näytetään
         */
        public HighscoreKuuntelija(Pelimoottori moottori) {
            this.moottori = moottori;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            moottori.lopeta();

            JDialog highscoreDialogi = new JDialog(frame, "Highscores");

            JTextArea tekstit = new JTextArea();
            tekstit.setMargin(new Insets(5, 5, 5, 5));
            tekstit.setText(moottori.haeScoret());
            tekstit.setFont(new Font(Font.MONOSPACED, 0, 20));
            tekstit.setBackground(Color.BLACK);
            tekstit.setForeground(Color.RED);
            tekstit.setEditable(false);

            highscoreDialogi.add(tekstit);

            highscoreDialogi.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            highscoreDialogi.pack();
            highscoreDialogi.setResizable(false);
            highscoreDialogi.setLocationRelativeTo(frame);
            highscoreDialogi.setVisible(true);
        }
    }
}
