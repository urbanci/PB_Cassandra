package main.polnjenje;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Random;

public class UniverzalniPodatki {
    private Random rand = new Random();
    private ArrayList<String> poreklo;
    private ArrayList<String> barva;

    public String dobiPoreklo(){
        String selekcija = null;

        if(poreklo==null) {
            poreklo = new ArrayList<String>();

            poreklo.add("Kitajska");
            poreklo.add("Japonska");
            poreklo.add("Kitajska");
            poreklo.add("Nemcija");
            poreklo.add("Kitajska");
            poreklo.add("Anglija");
            poreklo.add("Kitajska");
            poreklo.add("Spanija");
            poreklo.add("ZDA");
        }

        selekcija = poreklo.get(rand.nextInt(poreklo.size()));
        return selekcija;
    }

    public LinkedHashSet<String> dobiBarvo(){

        if(poreklo==null) {
            barva = new ArrayList<String>();
            barva.add("crna");
            barva.add("bela");
            barva.add("rdeca");
            barva.add("modra");
            barva.add("siva");
            barva.add("zelena");
            barva.add("siva");
            barva.add("roza");
        }
        LinkedHashSet<String> selekcija = new LinkedHashSet<String>();
        int izbira = rand.nextInt(5)+1;

        for (int i=0; i<izbira; i++){
            selekcija.add(barva.get(rand.nextInt(barva.size())));
        }

        return selekcija;
    }

    public long dobiDatum(int rokTrajanja) {
        int stDni = rand.nextInt(50) + 1;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        if (rokTrajanja == 0) {
            cal.add(Calendar.DAY_OF_MONTH, (rand.nextInt(stDni) + 1) * -1);
        } else {
            cal.add(Calendar.DAY_OF_MONTH, rand.nextInt(stDni) + 1);
        }

        return cal.getTimeInMillis();
    }
}
