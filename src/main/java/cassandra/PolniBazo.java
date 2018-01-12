package main.java.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import main.polnjenje.*;

import java.util.Random;

public class PolniBazo {

    private Cluster cluster;
    private Session session;
    private Random rand;
    private Izdelek_Televizija izdelekTelevizija = new Izdelek_Televizija();
    private Izdelek_Oblacilo izdelekOblacilo = new Izdelek_Oblacilo();
    private Izdelek_Hrana izdelekHrana = new Izdelek_Hrana();
    private Narocilo narocilo = new Narocilo();
    private Stranka stranka = new Stranka();

    public Cluster setCluster(){
        cluster = Cluster.builder().addContactPoint("localhost")
                .withCredentials("avtohisa", "avtosola").withRetryPolicy(DefaultRetryPolicy.INSTANCE).build();
        return cluster;
    }

    public Session setSession(Cluster cluster){
        session = cluster.connect("spletna_trgovina");
        return session;
    }

//    POLNI IZDELEK ----------------------------------------------------------
    public String polniBazo(int steviloIzdelkov, int steviloStrank, int maxSteviloNarocilNaOsebo) {
        cluster = setCluster();
        setSession(cluster);

        rand = new Random();
        int stevec = 0;
        int izbira = 0;
        int idNarocila;

        while (stevec < steviloIzdelkov){
            izbira = rand.nextInt(3)+1;
                polniIzdelek(izbira);
            stevec = stevec + 1;
        }

        stevec = 0;
        while (stevec < steviloStrank){
            idNarocila = narocilo.polniNarocilo(session, maxSteviloNarocilNaOsebo);
            stranka.polniStranko(session, stevec, idNarocila);
            stevec = stevec + 1;
        }

        return "VNOS JE USPESEN";
    }


    public void polniIzdelek(int izbira){
        switch (izbira){
            case 1:
                izdelekTelevizija.polniTelevizijo(session);

                break;
            case 2:
                izdelekOblacilo.polniOblacilo(session);

                break;
            case 3:
                izdelekHrana.polniHrano(session);

                break;
        }
    }


}
