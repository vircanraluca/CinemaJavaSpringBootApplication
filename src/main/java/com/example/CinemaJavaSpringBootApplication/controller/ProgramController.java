package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.model.Rezervare;
import com.example.CinemaJavaSpringBootApplication.repository.RezervareRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProgramController {

    private final RezervareRepository rezervareRepository;

    public ProgramController(RezervareRepository rezervareRepository) {
        this.rezervareRepository = rezervareRepository;
    }

    @GetMapping("/program")
    public String showProgram(
            @RequestParam(required = false) String film,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            Model model) {

        List<Rezervare> rezervari;

        if (film != null && !film.isEmpty() && data != null) {
            rezervari = rezervareRepository.findByFilmAndDataRezervare(film, data);
        } else if (film != null && !film.isEmpty()) {
            rezervari = rezervareRepository.findByFilm(film);
        } else if (data != null) {
            rezervari = rezervareRepository.findByDataRezervare(data);
        } else {
            rezervari = rezervareRepository.findAll();
        }

        model.addAttribute("rezervari", rezervari);
        model.addAttribute("filme", getFilme());
        model.addAttribute("filmSelectat", film);
        model.addAttribute("dataSelectata", data);
        return "program";
    }

    private List<String> getFilme() {
        return Arrays.asList(
                "Top Gun: Maverick",
                "Ratatouille",
                "Inception",
                "Avatar",
                "Interstellar"
        );
    }
}