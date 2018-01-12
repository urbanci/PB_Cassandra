package main.polnjenje;

import com.datastax.driver.core.*;
import jnr.ffi.annotations.In;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Stranka {

    private Random rand = new Random();
    private LinkedHashSet<Double> sesteteCene;
    private LinkedHashSet<Double> cenaNarocila;
    private LinkedHashSet<Integer> idNarocil;
    private Session session;
    private int idNarocilaPrej = 0;

    private ArrayList<String> drzava;
    private ArrayList<String> ime;
    private ArrayList<String> priimek;
    private ArrayList<String> ulica;

    public void polniStranko(Session session, int stevec, int idNarocila){
        this.session = session;
        int stNarocilZaStranko = idNarocila-idNarocilaPrej;
        sesteteCene = new LinkedHashSet<Double>();
        idNarocil = new LinkedHashSet<Integer>();

        String polniStrankoSQL = "INSERT INTO spletna_trgovina.\"STRANKA\" (\"id\", \"cena\", \"drzava\", \"narocilo_id\", \"ime\", " +
                "\"mesto\", \"postna_st\", \"priimek\", \"ulica\", \"starost\")\n" +
                "  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        for(int i=0; i<stNarocilZaStranko; i++){
            dobiCeneNarocila(idNarocilaPrej+i+1);
            sesteteCene.add(sestejCeneNarocila());
        }

        String drzava = dobiDrzavo();

        for(int i=0; i<stNarocilZaStranko; i++){
            idNarocil.add(idNarocilaPrej+i+1);
        }

        String ime = dobiIme();
        String mesto = dobiMesto(drzava);
        int postnaSt = 0;

        if(mesto.equals("Maribor")){
            postnaSt = 2000;
        }else{
            postnaSt = rand.nextInt(12000)+1000;
        }

        String priimek = dobiPriimek();
        String ulica = dobiUlica();
        int starost = rand.nextInt(80)+10;

        PreparedStatement preparedStatement = null;
        BoundStatement boundStatement = null;

        try {
            preparedStatement = session.prepare(polniStrankoSQL);
            boundStatement = new BoundStatement(preparedStatement);

            session.execute(boundStatement.bind()
                    .setInt(0, stevec+1)
                    .setSet(1, sesteteCene)
                    .setString(2, drzava)
                    .setSet(3, idNarocil)
                    .setString(4, ime)
                    .setString(5, mesto)
                    .setInt(6, postnaSt)
                    .setString(7, priimek)
                    .setString(8, ulica)
                    .setInt(9, starost));

        } catch (Exception e) {
            e.printStackTrace();
        }

        idNarocilaPrej = idNarocila;
    }

    public double sestejCeneNarocila(){
        double cena = 0;
        ArrayList<Double> ceneNarocila = new ArrayList<>(cenaNarocila);

        for(int i=0; i<ceneNarocila.size(); i++){
            cena = cena + ceneNarocila.get(i);
        }

        return cena;
    }

    public void dobiCeneNarocila(int id){
        cenaNarocila = new LinkedHashSet<>();
        String dobiCeneNarocilaSQL = "select cena from spletna_trgovina.\"NAROCILO\"\n" +
                "where id="+id+";";

        ResultSet rs = session.execute(dobiCeneNarocilaSQL);
        for (Row row : rs) {
            cenaNarocila = (LinkedHashSet<Double>) row.getObject("cena");
        }
    }

    public String dobiDrzavo(){
        String selekcija = null;

        if(drzava==null){
            drzava = new ArrayList<String>();
            drzava.add("Slovenija");
            drzava.add("Slovenija");
            drzava.add("Avstrija");
            drzava.add("Italija");
            drzava.add("Slovenija");
            drzava.add("Hrvaska");
            drzava.add("Slovenija");
            drzava.add("Nemcija");
            drzava.add("Madzarska");
            drzava.add("Slovenija");
            drzava.add("Slovenija");
        }

        selekcija = drzava.get(rand.nextInt(drzava.size()));
        return selekcija;
    }

    public String dobiIme(){
        String selekcija = null;

        if(ime==null){
            ime = new ArrayList<String>();

            ime.add("Biserka");
            ime.add("Jan");
            ime.add("Matej");
            ime.add("Miha");
            ime.add("Urban");
            ime.add("Mojca");
            ime.add("Alen");
            ime.add("Tadeja");
            ime.add("Marko");
            ime.add("Blaz");
            ime.add("Matic");
            ime.add("Norman");
            ime.add("Tim");
            ime.add("Nika");
            ime.add("Ziga");
        }

        selekcija = ime.get(rand.nextInt(ime.size()));
        return selekcija;
    }

    public String dobiMesto(String drzava){
        String mesto = "";

        if(drzava.equals("Slovenija")){
            mesto = "Maribor";
        }

        return mesto;
    }

    public String dobiPriimek(){
        String selekcija = null;

        if(priimek==null) {
            priimek = new ArrayList<String>();

            priimek.add("Bracko");
            priimek.add("Tertinek");
            priimek.add("Boltic");
            priimek.add("Hericko");
            priimek.add("Kos");
            priimek.add("Marko");
            priimek.add("Ficko");
            priimek.add("Mirt");
            priimek.add("Vehovec");
            priimek.add("Miklus");
            priimek.add("Reisman");
            priimek.add("Reich");
            priimek.add("Zaric");
            priimek.add("Crnic");
            priimek.add("Fiser");
        }
        selekcija = priimek.get(rand.nextInt(priimek.size()));
        return selekcija;
    }

    public String dobiUlica(){
        String selekcija = null;

        if(ulica==null) {
            ulica = new ArrayList<String>();

            ulica.add("Ulica Staneta Severja");
            ulica.add("Rospoh");
            ulica.add("Nad creto");
            ulica.add("Pri pesnici");
            ulica.add("V Hocah");
            ulica.add("Ulica Pohorskega Bataljona");
            ulica.add("Oroznova Ulica");
            ulica.add("Sprahova Ulica");
            ulica.add("Smetanova Ulica");
            ulica.add("Pri Sentilju");
            ulica.add("Kolednikova Ulica");
            ulica.add("Presernova Ulica");
            ulica.add("Tezno");
            ulica.add("Ob Pobrezju");
        }

        int hisnaStevilka = rand.nextInt(90)+1;
        String crka = String.valueOf((char)(rand.nextInt(4) + 'a'));

        selekcija = ulica.get(rand.nextInt(ulica.size()))+" "+hisnaStevilka+crka;
        return selekcija;
    }
}
