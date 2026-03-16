package com.example.CinemaJavaSpringBootApplication.export;

import com.example.CinemaJavaSpringBootApplication.model.Reservation;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReservationExporter {

    public void exportTxt(List<Reservation> reservations) throws IOException {

        String fileName = "reservations.txt";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try (FileWriter fw = new FileWriter(fileName)) {

            fw.write("Saved: " + LocalDateTime.now().format(dtf) + "\n\n");

            if (reservations.isEmpty()) {
                fw.write("No reservations found.");
            } else {
                for (Reservation r : reservations) {
                    fw.write(" Name: " + r.getName() + "\n");
                    fw.write("             Reservation date: " + r.getReservationDate().format(dateFormat) + "\n");
                    fw.write(" Movie: " + r.getMovie() + "\n");
                    fw.write("\n");
                }
            }
        }
    }
}