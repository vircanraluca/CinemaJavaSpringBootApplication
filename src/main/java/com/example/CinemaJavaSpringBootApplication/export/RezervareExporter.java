package com.example.CinemaJavaSpringBootApplication.export;

import com.example.CinemaJavaSpringBootApplication.model.Rezervare;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class RezervareExporter {

    public void exportTxt(List<Rezervare> rezervari) throws IOException {

        String numeFisier = "rezervari.txt";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try (FileWriter fw = new FileWriter(numeFisier)) {

            // Prima linie - data si ora salvarii
            fw.write("Salvare: " + LocalDateTime.now().format(dtf) + "\n\n");

            if (rezervari.isEmpty()) {
                fw.write("Nu există rezervări.");
            } else {
                for (Rezervare r : rezervari) {
                    fw.write(" Nume: " + r.getNume() + "\n");
                    fw.write("             Data rezervare: " + r.getDataRezervare().format(dateFormat) + "\n");
                    fw.write(" Film: " + r.getFilm() + "\n");
                    fw.write("\n");
                }
            }
        }
    }
}