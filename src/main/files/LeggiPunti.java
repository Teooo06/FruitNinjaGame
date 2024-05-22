package main.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LeggiPunti {

    public void LeggiDati() {
    }

    public int leggiPunti() {
        try {
            int punteggio = 0;
            //aprtura del file da leggere
            FileReader apertura = new FileReader("./src/main/files/punteggio.txt");
            BufferedReader fileDaLeggere = new BufferedReader(apertura);
            String linea;

            ArrayList<String> datiArrayList = new ArrayList<>();

            while ((linea = fileDaLeggere.readLine()) != null){
                String [] dati = linea.split("\\+");
                datiArrayList.addAll(Arrays.asList(dati));
            }

            for (int i = 0; i < datiArrayList.size(); i++) {
                punteggio += Integer.parseInt(datiArrayList.get(i));
            }

            fileDaLeggere.close();
            return punteggio;

        }catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    } 
}
