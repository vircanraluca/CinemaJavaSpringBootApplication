package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.export.ReservationExporter;
import com.example.CinemaJavaSpringBootApplication.model.Movie;
import com.example.CinemaJavaSpringBootApplication.model.Reservation;
import com.example.CinemaJavaSpringBootApplication.repository.MovieRepository;
import com.example.CinemaJavaSpringBootApplication.service.ReservationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final MovieRepository movieRepository;
    private final ReservationExporter reservationExporter;

    public ReservationController(ReservationService reservationService,
                                 MovieRepository movieRepository,
                                 ReservationExporter reservationExporter) {
        this.reservationService = reservationService;
        this.movieRepository = movieRepository;
        this.reservationExporter = reservationExporter;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        model.addAttribute("username",
                authentication != null ? authentication.getName() : "");
        return "dashboard";
    }

    @GetMapping("/reservation")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("movies", getMovies());
        model.addAttribute("halls", getHalls());
        return "reservation";
    }

    @PostMapping("/reservation")
    public String addReservation(@ModelAttribute Reservation reservation, Model model) {
        try {
            reservationService.addReservation(reservation);
            return "redirect:/confirmation?movie=" + reservation.getMovie()
                    + "&date=" + reservation.getReservationDate();
        } catch (IllegalArgumentException | IllegalStateException e) {
            try {
                String errorEncoded = java.net.URLEncoder.encode(
                        e.getMessage(), java.nio.charset.StandardCharsets.UTF_8);
                String movieEncoded = java.net.URLEncoder.encode(
                        reservation.getMovie(), java.nio.charset.StandardCharsets.UTF_8);
                return "redirect:/seats?movie=" + movieEncoded
                        + "&hallNumber=" + reservation.getHallNumber()
                        + "&error=" + errorEncoded;
            } catch (Exception ex) {
                return "redirect:/seats?movie=" + reservation.getMovie()
                        + "&hallNumber=" + reservation.getHallNumber() + "&error=Error";
            }
        }
    }

    @GetMapping("/confirmation")
    public String showConfirmation(
            @RequestParam String movie,
            @RequestParam String date,
            Model model) {
        model.addAttribute("movie", movie);
        model.addAttribute("date", date);
        return "confirmation";
    }

    @GetMapping("/reservations")
    public String showReservationsForm() {
        return "reservations";
    }

    @PostMapping("/reservations")
    public String showReservations(@RequestParam String name, Model model) {
        List<Reservation> reservations = reservationService.getReservationsByName(name);
        model.addAttribute("reservations", reservations);
        model.addAttribute("name", name);
        return "reservations";
    }

    @GetMapping("/hallCapacity")
    public String showCapacityForm(Model model) {
        model.addAttribute("halls", getHalls());
        return "hallCapacity";
    }

    @PostMapping("/hallCapacity")
    public String checkHallCapacity(@RequestParam int hallNumber, Model model) {
        int availableSeats = reservationService.getAvailableSeats(hallNumber);
        model.addAttribute("availableSeats", availableSeats);
        model.addAttribute("hallNumber", hallNumber);
        model.addAttribute("halls", getHalls());
        return "hallCapacity";
    }

    @PostMapping("/deleteReservation")
    public String deleteReservation(@RequestParam Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/reservations";
    }

    @GetMapping("/saveFile")
    public void saveFile(HttpServletResponse response) throws IOException {
        List<Reservation> reservations = reservationService.getAllReservations();

        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"reservations.txt\"");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        java.io.PrintWriter writer = response.getWriter();

        writer.println("Saved: " + java.time.LocalDateTime.now().format(dtf));
        writer.println();

        if (reservations.isEmpty()) {
            writer.println("No reservations found.");
        } else {
            for (Reservation r : reservations) {
                writer.println("Name: " + r.getName());
                writer.println("Reservation date: " + r.getReservationDate().format(dateFormat));
                writer.println("Movie: " + r.getMovie());
                writer.println("Hall: " + r.getHallNumber());
                writer.println("Seats: " + r.getSeats());
                writer.println("---");
            }
        }

        writer.flush();
    }

    private List<String> getMovies() {
        return movieRepository.findAll()
                .stream()
                .map(Movie::getTitle)
                .collect(Collectors.toList());
    }

    private List<Integer> getHalls() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
}