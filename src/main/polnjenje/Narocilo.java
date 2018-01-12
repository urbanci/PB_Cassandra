package main.polnjenje;

import com.datastax.driver.core.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Narocilo {

    private Random rand = new Random();
    private UniverzalniPodatki uni = new UniverzalniPodatki();
    private ArrayList<UUID> idIzdelkov;
    private ArrayList<String> tipIzdelkov;
    private Session session;
    private int stevec = 0;

//    POLNI NAROCILO ------------------------------------------------------------
    public int polniNarocilo(Session session, int maxSteviloNarocilNaOsebo) {
        this.session = session;
        getPodatkiIzdelkov();

        String vstaviNarociloSQL = "INSERT INTO spletna_trgovina.\"NAROCILO\" (\"id\", \"cena\", \"datum\", \"id_izdelka\",\n" +
                "\t\"kolicina\", \"tip_placila\") VALUES(?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = null;
        BoundStatement boundStatement = null;

        for(int i=0; i<rand.nextInt(maxSteviloNarocilNaOsebo)+1; i++) {

            int stIzdelkov = rand.nextInt(5)+1;

            try {
                preparedStatement = session.prepare(vstaviNarociloSQL);
                boundStatement = new BoundStatement(preparedStatement);

                LinkedHashSet<Double> cena = dobiCeno(stIzdelkov);
                LocalDate datum = LocalDate.fromMillisSinceEpoch(uni.dobiDatum(0));
                LinkedHashSet<UUID> id_izdelka = dobiID(stIzdelkov);
                LinkedHashSet<Integer> kolicina = dobiKolicino(stIzdelkov);
                LinkedHashSet<String> tip_placila = dobiTipPlacila(stIzdelkov);

                session.execute(boundStatement.bind()
                        .setInt(0, stevec+1)
                        .setSet(1, cena)
                        .setDate(2, datum)
                        .setSet(3, id_izdelka)
                        .setSet(4, kolicina)
                        .setSet(5, tip_placila));

                stevec = stevec + 1;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stevec;
    }

    public void getPodatkiIzdelkov() {
        idIzdelkov = new ArrayList<UUID>();
        tipIzdelkov = new ArrayList<String>();

        String idIzdelkaSQL = "select id, tip from spletna_trgovina.\"IZDELEK\";";

        ResultSet rs = session.execute(idIzdelkaSQL);
        for (Row row : rs) {
            idIzdelkov.add(row.getUUID("id"));
            tipIzdelkov.add(row.getString("tip"));
        }
    }

    public LinkedHashSet<Double> dobiCeno(int stevilo){
        LinkedHashSet<Double> cene = new LinkedHashSet<Double>();

        for (int i=0; i<stevilo; i++){
            int tipIzdelkovID = rand.nextInt(tipIzdelkov.size());

            if(tipIzdelkov.get(tipIzdelkovID).equals("Izdelek_Televizija")){
                BigDecimal bd = new BigDecimal(ThreadLocalRandom.current().nextDouble(200, 1200));
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                cene.add(bd.doubleValue());
            }

            if(tipIzdelkov.get(tipIzdelkovID).equals("Izdelek_Hrana")){
                BigDecimal bd = new BigDecimal(ThreadLocalRandom.current().nextDouble(0.2, 30));
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                cene.add(bd.doubleValue());
            }

            if(tipIzdelkov.get(tipIzdelkovID).equals("Izdelek_Oblacilo")){
                BigDecimal bd = new BigDecimal(ThreadLocalRandom.current().nextDouble(2, 120));
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                cene.add(bd.doubleValue());
            }
        }

        return cene;
    }

    public LinkedHashSet<UUID> dobiID(int stevilo){
        LinkedHashSet<UUID> id = new LinkedHashSet<UUID>();

        for (int i=0; i<stevilo; i++){
            int randID = rand.nextInt(idIzdelkov.size());
            id.add(idIzdelkov.get(randID));
            idIzdelkov.remove(randID);
        }

        return id;
    }

    public LinkedHashSet<Integer> dobiKolicino(int stevilo){
        LinkedHashSet<Integer> kolicina = new LinkedHashSet<Integer>();

        for (int i=0; i<stevilo; i++){
            kolicina.add(rand.nextInt(30)+1);
        }

        return kolicina;
    }

    public LinkedHashSet<String> dobiTipPlacila(int stevilo){
        LinkedHashSet<String> tipPlacila = new LinkedHashSet<String>();
        boolean b = Math.random() < 0.5;
        int nakup = 0;

        for (int i=0; i<stevilo; i++){
            nakup = rand.nextInt(10)+1;
            if(b==true){
                if(nakup==5) {
                    tipPlacila.add("Gotovina");
                }else{
                    tipPlacila.add("Kartica");
                }
            }else{
                if(nakup==5) {
                    tipPlacila.add("Kartica");
                }else{
                    tipPlacila.add("Gotovina");
                }
            }
        }

        return tipPlacila;
    }

}
