package main.polnjenje;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Izdelek_Televizija {
    private Random rand = new Random();
    private UniverzalniPodatki uni = new UniverzalniPodatki();

    private ArrayList<String> naziv;
    private ArrayList<String> resolucija;
    private ArrayList<String> voltaza;
    private ArrayList<String> znamka;

    public void polniTelevizijo(Session session){
        String sql = "INSERT INTO spletna_trgovina.\"IZDELEK\" (\"id\", \"barva\", \"diagonala\", \"garancija\",\n" +
                "\"material\", \"naziv\", \"opis\", \"resolucija\", \"teza\", \"tip\", \"voltaza\", \"znamka\", \"poreklo\")\n" +
                "  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = null;
        BoundStatement boundStatement = null;

        try {
            preparedStatement = session.prepare(sql);
            boundStatement = new BoundStatement(preparedStatement);

            UUID uuid = UUID.randomUUID();
            LinkedHashSet<String> barva = uni.dobiBarvo();
            int diagonala = rand.nextInt(170)+1;
            int garancija = rand.nextInt(2)+1;
            LinkedHashSet<String> material = new LinkedHashSet<String>();
            material.add("plastika");
            material.add("steklo");
            String naziv = dobiNaziv();
            String opis = "Najboljsi televizor";
            LinkedHashSet<String> resolucija = dobiResolucijo();
            double teza = ThreadLocalRandom.current().nextDouble(0, 30);
            String tip = "Izdelek_Televizija";
            String voltaza = dobiVoltaza();
            String znamka = dobiZnamka();
            String poreklo = uni.dobiPoreklo();


            session.execute(boundStatement.bind()
                .setUUID(0, uuid)
                .setSet(1, barva)
                .setInt(2, diagonala)
                .setInt(3, garancija)
                .setSet(4, material)
                .setString(5, naziv)
                .setString(6, opis)
                .setSet(7, resolucija)
                .setDouble(8, teza)
                .setString(9, tip)
                .setString(10, voltaza)
                .setString(11, znamka));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String dobiNaziv(){
        String selekcija = null;

        if(naziv==null) {
            naziv = new ArrayList<String>();

            naziv.add("TV-2034");
            naziv.add("Selection");
            naziv.add("E-2012");
            naziv.add("LED Power");
            naziv.add("Zeldon 12");
            naziv.add("Zeldon 13");
            naziv.add("Zeldon 14");
            naziv.add("Hiatus");
            naziv.add("Chill");
            naziv.add("Trestin 65C");
            naziv.add("ColorBOSS");
            naziv.add("Latifa 256DMC");
            naziv.add("15C");
            naziv.add("Trilogy");
        }
        selekcija = naziv.get(rand.nextInt(naziv.size()));
        return selekcija;
    }

    public LinkedHashSet<String> dobiResolucijo(){

        if(resolucija==null) {
            resolucija = new ArrayList<String>();

            resolucija.add("HD");
            resolucija.add("Full HD");
            resolucija.add("4K");
        }

        LinkedHashSet<String> selekcija = new LinkedHashSet<String>();
        int izbira = rand.nextInt(3)+1;

        for (int i=0; i<izbira; i++){
            selekcija.add(resolucija.get(rand.nextInt(resolucija.size())));
        }

        return selekcija;
    }

    public String dobiVoltaza(){
        String selekcija = null;

        if(voltaza==null) {
            voltaza = new ArrayList<String>();
            voltaza.add("240V/60Hz");
            voltaza.add("440V/60Hz");
            voltaza.add("110V/60Hz");
        }
        selekcija = voltaza.get(rand.nextInt(voltaza.size()));
        return selekcija;
    }

    public String dobiZnamka(){
        String selekcija = null;

        if(znamka==null) {
            znamka = new ArrayList<String>();
            znamka.add("Samsung");
            znamka.add("Philips");
            znamka.add("LG");
            znamka.add("Sony");
            znamka.add("LG");
            znamka.add("Manta");
            znamka.add("Panasonic");
            znamka.add("Vox");
            znamka.add("Sharp");
        }
        selekcija = znamka.get(rand.nextInt(znamka.size()));
        return selekcija;
    }

}
