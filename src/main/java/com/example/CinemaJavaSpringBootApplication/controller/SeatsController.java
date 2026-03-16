package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.repository.ReservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SeatsController {

    private final ReservationRepository reservationRepository;

    public SeatsController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/seats")
    public String showSeats(
            @RequestParam String movie,
            @RequestParam Integer hallNumber,
            @RequestParam(required = false) String error,
            Model model) {

        int occupiedSeats = reservationRepository.getOccupiedSeats(hallNumber);
        List<Row> rows = generateRows(occupiedSeats);

        model.addAttribute("movie", movie);
        model.addAttribute("hallNumber", hallNumber);
        model.addAttribute("rows", rows);
        model.addAttribute("error", error);
        return "seats";
    }

    @GetMapping("/seats/occupied")
    @ResponseBody
    public Map<String, Object> getOccupiedSeats(@RequestParam Integer hallNumber) {
        int occupied = reservationRepository.getOccupiedSeats(hallNumber);
        Map<String, Object> result = new HashMap<>();
        result.put("occupiedSeats", occupied);
        return result;
    }

    private List<Row> generateRows(int occupiedSeats) {
        String[] letters = {"A", "B", "C", "D", "E"};
        int seatsPerRow = 8;
        List<Row> rows = new ArrayList<>();
        int counter = 0;

        for (String letter : letters) {
            Row row = new Row(letter);
            for (int i = 1; i <= seatsPerRow; i++) {
                counter++;
                row.addSeat(new Seat(i, counter <= occupiedSeats));
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

        public Seat(int number, boolean occupied) {
            this.number = number;
            this.occupied = occupied;
        }
        public int getNumber() { return number; }
        public boolean isOccupied() { return occupied; }
    }
}