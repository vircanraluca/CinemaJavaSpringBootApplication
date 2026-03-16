package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.model.Movie;
import com.example.CinemaJavaSpringBootApplication.repository.MovieRepository;
import com.example.CinemaJavaSpringBootApplication.repository.ReservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProgramController {

    private final ReservationRepository reservationRepository;
    private final MovieRepository movieRepository;

    public ProgramController(ReservationRepository reservationRepository,
                             MovieRepository movieRepository) {
        this.reservationRepository = reservationRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/program")
    public String showProgram(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String title,
            Model model) {

        String genreTrimmed = (genre != null && !genre.isBlank()) ? genre : null;
        String titleTrimmed = (title != null && !title.isBlank()) ? title : null;

        List<Movie> movies = movieRepository.findByFilters(titleTrimmed, genreTrimmed);

        List<String> genres = movieRepository.findAll()
                .stream()
                .map(Movie::getGenre)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        model.addAttribute("movies", movies);
        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("selectedTitle", title);
        return "program";
    }
}