package main.polnjenje;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Izdelek_Oblacilo {

    private Random rand = new Random();
    private DecimalFormat df = new DecimalFormat("####0.00");
    private UniverzalniPodatki uni = new UniverzalniPodatki();

    private ArrayList<String> material;
    private ArrayList<String> naziv;
    private ArrayList<String> rokavi;
    private ArrayList<String> velikost;
    private ArrayList<String> znamka;

    public void polniOblacilo(Session session){
        String sql = "INSERT INTO spletna_trgovina.\"IZDELEK\" (\"id\", \"barva\", \"material\", \"naziv\", \"opis\",\n" +
                "\t\"poreklo\", \"rokavi\", \"teza\", \"velikost\", \"znamka\", \"tip\")\n" +
                "  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = null;
        BoundStatement boundStatement = null;

        try {
            preparedStatement = session.prepare(sql);
            boundStatement = new BoundStatement(preparedStatement);

            UUID uuid = UUID.randomUUID();
            LinkedHashSet<String> barva = uni.dobiBarvo();
            LinkedHashSet<String> material = dobiMaterial();
            String naziv = dobiNaziv();
            String opis = "Najboljsa oblacila";
            String poreklo = uni.dobiPoreklo();
            String rokavi = dobiRokave();
            double teza = ThreadLocalRandom.current().nextDouble(0.05, 0.3);
            String velikost = dobiVelikost();
            String znamka = dobiZnamko();

            session.execute(boundStatement.bind()
                    .setUUID(0, uuid)
                    .setSet(1, barva)
                    .setSet(2, material)
                    .setString(3, naziv)
                    .setString(4, opis)
                    .setString(5, poreklo)
                    .setString(6, rokavi)
                    .setDouble(7, teza)
                    .setString(8, velikost)
                    .setString(9, znamka)
                    .setString(10, "Izdelek_Oblacilo"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinkedHashSet<String> dobiMaterial(){

        if(material==null) {
            material = new ArrayList<String>();
            material.add("100% Bombaz");
            material.add("100% Poliester");
            material.add("70% Bombaz, 30% Poliester");
            material.add("50% Bombaz, 50% Poliester");
            material.add("Volna");
            material.add("Usnje");
        }
        LinkedHashSet<String> selekcija = new LinkedHashSet<String>();

        selekcija.add(material.get(rand.nextInt(material.size())));

        return selekcija;
    }

    public String dobiNaziv() {
        String selekcija = null;

        if(naziv==null) {
            naziv = new ArrayList<String>();
            naziv.add("Good Feelin'");
            naziv.add("For Straght");
            naziv.add("Beautifull");
            naziv.add("Simple");
            naziv.add("Simple 1");
            naziv.add("For Workers");
            naziv.add("Elegant");
        }

        selekcija = naziv.get(rand.nextInt(naziv.size()));
        return selekcija;
    }

    public String dobiRokave() {
        String selekcija = null;

        if(rokavi==null) {
            rokavi = new ArrayList<String>();
            rokavi.add("Kratki");
            rokavi.add("Brez");
            rokavi.add("Dolgi");
            rokavi.add("Pol dolgi");
        }

        selekcija = rokavi.get(rand.nextInt(rokavi.size()));
        return selekcija;
    }

    public String dobiVelikost() {
        String selekcija = null;

        if(velikost==null) {
            velikost = new ArrayList<String>();
            velikost.add("XS");
            velikost.add("S");
            velikost.add("M");
            velikost.add("L");
            velikost.add("XL");
            velikost.add("XXL");
        }

        selekcija = velikost.get(rand.nextInt(velikost.size()));
        return selekcija;
    }

    public String dobiZnamko() {
        String selekcija = null;

        if(znamka==null) {
            znamka = new ArrayList<String>();
            znamka.add("DC");
            znamka.add("DVS");
            znamka.add("L.O.G.G.");
            znamka.add("Vans");
            znamka.add("Trasher");
            znamka.add("Fruit of the Bloom");
            znamka.add("Element");
            znamka.add("Lntrn Handcrafted");
            znamka.add("Dickies");
        }
        selekcija = znamka.get(rand.nextInt(znamka.size()));
        return selekcija;
    }
}
