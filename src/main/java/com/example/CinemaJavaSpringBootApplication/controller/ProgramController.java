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
            Model model) {

        List<Movie> movies;

        if (genre != null && !genre.isEmpty()) {
            movies = movieRepository.findByGenre(genre);
        } else {
            movies = movieRepository.findAll();
        }

        List<String> genres = movieRepository.findAll()
                .stream()
                .map(Movie::getGenre)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        model.addAttribute("movies", movies);
        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", genre);
        return "program";
    }
}