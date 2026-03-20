package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.model.Hall;
import com.example.CinemaJavaSpringBootApplication.model.HallShowtime;
import com.example.CinemaJavaSpringBootApplication.model.Movie;
import com.example.CinemaJavaSpringBootApplication.repository.MovieRepository;
import com.example.CinemaJavaSpringBootApplication.service.HallService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/halls")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class HallController {

    private final HallService hallService;
    private final MovieRepository movieRepository;

    public HallController(HallService hallService, MovieRepository movieRepository) {
        this.hallService = hallService;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public String showHalls(Model model) {
        List<Hall> halls = hallService.getAllHalls();
        List<String> movies = movieRepository.findAll()
                .stream().map(Movie::getTitle).collect(Collectors.toList());

        // Showtimes per sala
        Map<Integer, List<HallShowtime>> showtimesMap = halls.stream()
                .collect(Collectors.toMap(
                        Hall::getHallNumber,
                        h -> hallService.getShowtimes(h.getHallNumber())
                ));

        model.addAttribute("halls", halls);
        model.addAttribute("movies", movies);
        model.addAttribute("showtimesMap", showtimesMap);
        return "admin/halls";
    }

    @PostMapping("/update/{hallNumber}")
    public String updateHall(@PathVariable Integer hallNumber,
                             @RequestParam Integer capacity,
                             @RequestParam(required = false) String currentMovie) {
        Hall hall = hallService.getHall(hallNumber);
        hall.setCapacity(capacity);
        hall.setCurrentMovie((currentMovie != null && !currentMovie.isBlank()) ? currentMovie : null);
        hallService.saveHall(hall);
        return "redirect:/admin/halls";
    }

    @PostMapping("/showtime/add/{hallNumber}")
    public String addShowtime(@PathVariable Integer hallNumber,
                              @RequestParam String showTime,
                              @RequestParam(required = false) String movie,
                              @RequestParam(required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate showDate) {
        hallService.addShowtime(hallNumber, showTime, movie, showDate);
        return "redirect:/admin/halls/" + hallNumber + "/schedule";
    }
    @GetMapping("/{hallNumber}/schedule")
    public String showSchedule(@PathVariable Integer hallNumber, Model model) {
        List<HallShowtime> showtimes = hallService.getShowtimes(hallNumber);
        List<String> movies = movieRepository.findAll()
                .stream().map(Movie::getTitle).collect(Collectors.toList());

        model.addAttribute("hallNumber", hallNumber);
        model.addAttribute("showtimes", showtimes);
        model.addAttribute("movies", movies);
        return "admin/hall-schedule";
    }

    @PostMapping("/showtime/delete/{id}")
    public String deleteShowtime(@PathVariable Long id) {
        hallService.deleteShowtime(id);
        return "redirect:/admin/halls";
    }
}