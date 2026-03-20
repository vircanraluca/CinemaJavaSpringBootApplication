package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.model.HallShowtime;
import com.example.CinemaJavaSpringBootApplication.repository.HallShowtimeRepository;
import com.example.CinemaJavaSpringBootApplication.repository.ReservationRepository;
import com.example.CinemaJavaSpringBootApplication.service.HallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class SeatsController {

    private final ReservationRepository reservationRepository;
    private final HallShowtimeRepository hallShowtimeRepository;
    private final HallService hallService;

    public SeatsController(ReservationRepository reservationRepository,
                           HallShowtimeRepository hallShowtimeRepository,
                           HallService hallService) {
        this.reservationRepository = reservationRepository;
        this.hallShowtimeRepository = hallShowtimeRepository;
        this.hallService = hallService;
    }

    @GetMapping("/seats")
    public String showSeats(
            @RequestParam String movie,
            @RequestParam(required = false) Long showtimeId,
            @RequestParam(required = false) String error,
            Model model) {

        // Showtimes pentru filmul asta din ziua de azi incolo
        List<HallShowtime> showtimes = hallShowtimeRepository
                .findByMovieAndShowDateGreaterThanEqual(movie, LocalDate.now());

        List<String> occupiedLabels = showtimeId != null
                ? reservationRepository.getOccupiedSeatLabelsByShowtime(showtimeId)
                : new ArrayList<>();

        Integer hallNumber = null;
        String showTime = "";
        if (showtimeId != null) {
            Optional<HallShowtime> st = hallShowtimeRepository.findById(showtimeId);
            if (st.isPresent()) {
                showTime = st.get().getShowTime();
                hallNumber = st.get().getHallNumber();
            }
        }

        List<Row> rows = generateRows(occupiedLabels);

        model.addAttribute("movie", movie);
        model.addAttribute("hallNumber", hallNumber);
        model.addAttribute("showtimeId", showtimeId);
        model.addAttribute("showTime", showTime);
        model.addAttribute("showtimes", showtimes);
        model.addAttribute("rows", rows);
        model.addAttribute("error", error);
        return "seats";
    }

    @GetMapping("/seats/occupied")
    @ResponseBody
    public Map<String, Object> getOccupiedSeats(
            @RequestParam(required = false) Integer hallNumber,
            @RequestParam(required = false) Long showtimeId) {

        List<String> occupiedLabels = showtimeId != null
                ? reservationRepository.getOccupiedSeatLabelsByShowtime(showtimeId)
                : new ArrayList<>();

        Map<String, Object> result = new HashMap<>();
        result.put("occupiedLabels", occupiedLabels);
        return result;
    }

    private List<Row> generateRows(List<String> occupiedLabels) {
        String[] letters = {"A", "B", "C", "D", "E"};
        int seatsPerRow = 8;
        List<Row> rows = new ArrayList<>();

        for (String letter : letters) {
            Row row = new Row(letter);
            for (int i = 1; i <= seatsPerRow; i++) {
                String label = letter + i;
                row.addSeat(new Seat(i, occupiedLabels.contains(label)));
            }
            rows.add(row);
        }
        return rows;
    }

    public static class Row {
        private String letter;
        private List<Seat> seats = new ArrayList<>();
        public Row(String letter) { this.letter = letter; }
        public void addSeat(Seat seat) { seats.add(seat); }
        public String getLetter() { return letter; }
        public List<Seat> getSeats() { return seats; }
    }

    public static class Seat {
        private int number;
        private boolean occupied;
        public Seat(int number, boolean occupied) { this.number = number; this.occupied = occupied; }
        public int getNumber() { return number; }
        public boolean isOccupied() { return occupied; }
    }
}