package main.polnjenje;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Izdelek_Hrana {
    private Random rand = new Random();
    private UniverzalniPodatki uni = new UniverzalniPodatki();

    private ArrayList<String> naziv;
    private ArrayList<String> znamka;

    public void polniHrano(Session session){
        String sql = "INSERT INTO spletna_trgovina.\"IZDELEK\" (\"id\", \"barva\", \"datum_nastanka\", \n" +
                "\t\"naziv\", \"opis\", \"poreklo\", \"rok_trajanja\", \"teza\", \"tip\", \"znamka\")\n" +
                "  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = null;
        BoundStatement boundStatement = null;

        try {
            preparedStatement = session.prepare(sql);
            boundStatement = new BoundStatement(preparedStatement);

            UUID uuid = UUID.randomUUID();
            LinkedHashSet<String> barva = uni.dobiBarvo();
            Long datum_nastanka = uni.dobiDatum(0);
            String naziv = dobiNaziv();
            String opis = "Odlicno in sveze za na kroznik";
            String poreklo = uni.dobiPoreklo();
            Long rok_trajanja = uni.dobiDatum(1);
            double teza = ThreadLocalRandom.current().nextDouble(0.05, 5);
            String tip = "Izdelek_Hrana";
            String znamka = dobiZnamko();

            session.execute(boundStatement.bind()
                    .setUUID(0, uuid)
                    .setSet(1, barva)
                    .setDate(2, LocalDate.fromMillisSinceEpoch(datum_nastanka))
                    .setString(3, naziv)
                    .setString(4, opis)
                    .setString(5, poreklo)
                    .setDate(6, LocalDate.fromMillisSinceEpoch(rok_trajanja))
                    .setDouble(7, teza)
                    .setString(8, tip)
                    .setString(9, znamka));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String dobiNaziv() {
        String selekcija = null;

        if(naziv==null) {
            naziv = new ArrayList<String>();
            naziv.add("Jabolke");
            naziv.add("Hruske");
            naziv.add("Slive");
            naziv.add("Krompir");
            naziv.add("Govedina");
            naziv.add("Piscanec");
            naziv.add("Svinjina");
            naziv.add("Solata");
            naziv.add("Cvetaca");
            naziv.add("Zelena");
            naziv.add("Radic");
            naziv.add("Korencek");
            naziv.add("Banane");
            naziv.add("Cebula");
            naziv.add("Paradiznik");
            naziv.add("Cesen");
            naziv.add("Redkvica");
        }

        selekcija = naziv.get(rand.nextInt(naziv.size()));
        return selekcija;
    }

    public String dobiZnamko(){
        String selekcija = null;

        if(znamka==null) {
            znamka = new ArrayList<String>();
            znamka.add("Kmetija SKOK");
            znamka.add("Good Foods");
            znamka.add("Fresh'n Clean");
            znamka.add("London Street Food");
            znamka.add("Kmetija FICKO");
            znamka.add("Turisticna Kmetija Zavec");
            znamka.add("Kmetija Brozdil");
        }

        selekcija = znamka.get(rand.nextInt(znamka.size()));
        return selekcija;
    }
}
