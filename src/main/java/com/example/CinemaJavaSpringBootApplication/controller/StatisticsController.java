package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.repository.ReservationRepository;
import com.example.CinemaJavaSpringBootApplication.model.Reservation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StatisticsController {

    private final ReservationRepository reservationRepository;

    public StatisticsController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/admin/statistics")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showStatistics(Model model) {
        List<Reservation> all = reservationRepository.findAll();

        // 1. Rezervari per zi (ultimele 7 zile)
        Map<LocalDate, Long> perDay = all.stream()
                .filter(r -> r.getReservationDate() != null &&
                        r.getReservationDate().isAfter(LocalDate.now().minusDays(7)))
                .collect(Collectors.groupingBy(Reservation::getReservationDate, Collectors.counting()));

        List<LocalDate> last7Days = new ArrayList<>();
        for (int i = 6; i >= 0; i--) last7Days.add(LocalDate.now().minusDays(i));

        List<String> dayLabels = last7Days.stream()
                .map(d -> d.getDayOfWeek().toString().substring(0, 3) + " " + d.getDayOfMonth())
                .collect(Collectors.toList());
        List<Long> dayCounts = last7Days.stream()
                .map(d -> perDay.getOrDefault(d, 0L))
                .collect(Collectors.toList());

        // 2. Ocupare per sala
        List<Integer> halls = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        int hallCapacity = 40;
        List<String> hallLabels = halls.stream().map(h -> "Hall " + h).collect(Collectors.toList());
        List<Integer> hallOccupied = halls.stream()
                .map(h -> reservationRepository.getOccupiedSeats(h))
                .collect(Collectors.toList());
        List<Integer> hallFree = hallOccupied.stream()
                .map(o -> Math.max(0, hallCapacity - o))
                .collect(Collectors.toList());

        // 3. Top 5 filme rezervate
        Map<String, Long> perMovie = all.stream()
                .filter(r -> r.getMovie() != null)
                .collect(Collectors.groupingBy(Reservation::getMovie, Collectors.counting()));

        List<Map.Entry<String, Long>> topMovies = perMovie.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());

        List<String> movieLabels = topMovies.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        List<Long> movieCounts = topMovies.stream().map(Map.Entry::getValue).collect(Collectors.toList());

        // Stats summary
        model.addAttribute("totalReservations", all.size());
        model.addAttribute("totalSeats", all.stream().mapToInt(Reservation::getSeats).sum());
        model.addAttribute("mostPopularMovie", movieLabels.isEmpty() ? "N/A" : movieLabels.get(0));

        model.addAttribute("dayLabels", dayLabels);
        model.addAttribute("dayCounts", dayCounts);
        model.addAttribute("hallLabels", hallLabels);
        model.addAttribute("hallOccupied", hallOccupied);
        model.addAttribute("hallFree", hallFree);
        model.addAttribute("movieLabels", movieLabels);
        model.addAttribute("movieCounts", movieCounts);

        return "admin/statistics";
    }
}