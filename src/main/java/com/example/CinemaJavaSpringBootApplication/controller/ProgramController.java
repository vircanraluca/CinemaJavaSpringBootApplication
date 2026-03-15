package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.model.Movie;
import com.example.CinemaJavaSpringBootApplication.model.Rezervare;
import com.example.CinemaJavaSpringBootApplication.repository.MovieRepository;
import com.example.CinemaJavaSpringBootApplication.repository.RezervareRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProgramController {

    private final RezervareRepository rezervareRepository;
    private final MovieRepository movieRepository;

    public ProgramController(RezervareRepository rezervareRepository,
                             MovieRepository movieRepository) {
        this.rezervareRepository = rezervareRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/program")
    public String showProgram(
            @RequestParam(required = false) String gen,
            Model model) {

        List<Movie> filme;

        if (gen != null && !gen.isEmpty()) {
            filme = movieRepository.findByGen(gen);
        } else {
            filme = movieRepository.findAll();
        }

        List<String> genuri = movieRepository.findAll()
                .stream()
                .map(Movie::getGen)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        model.addAttribute("filme", filme);
        model.addAttribute("genuri", genuri);
        model.addAttribute("genSelectat", gen);
        return "program";
    }
}