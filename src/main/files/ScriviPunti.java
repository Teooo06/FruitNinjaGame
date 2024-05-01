package main.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ScriviPunti {

    // Costruttore
    public ScriviPunti() {

    }

    // Metodo per scrivere il punteggio su file cancellando i dati precedenti
    public void scriviUtenti(int punteggio) {
        try {
            // Apertura del file da scrivere
            FileWriter apertura = new FileWriter("./src/main/files/punteggio.txt");
            BufferedWriter fileDaScrivere = new BufferedWriter(apertura);
            // Scrittura del punteggio su file
            fileDaScrivere.write(String.valueOf(punteggio));

            fileDaScrivere.flush();

            fileDaScrivere.close();


        } catch (IOException e) {
            System.err.println("Errore nella scrittura dei dati su file: " + e.getMessage());
        }
    }
}
