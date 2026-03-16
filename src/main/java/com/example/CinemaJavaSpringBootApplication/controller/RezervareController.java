package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.model.Movie;
import com.example.CinemaJavaSpringBootApplication.model.Rezervare;
import com.example.CinemaJavaSpringBootApplication.repository.MovieRepository;
import com.example.CinemaJavaSpringBootApplication.service.RezervareService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class RezervareController {

    private final RezervareService rezervareService;
    private final MovieRepository movieRepository;

    public RezervareController(RezervareService rezervareService,
                               MovieRepository movieRepository) {
        this.rezervareService = rezervareService;
        this.movieRepository = movieRepository;
    }

    // ===================== DASHBOARD =====================
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        model.addAttribute("username",
                authentication != null ? authentication.getName() : "");
        return "dashboard";
    }

    // ===================== REZERVARE =====================
    @GetMapping("/rezervare")
    public String showRezervareForm(Model model) {
        model.addAttribute("rezervare", new Rezervare());
        model.addAttribute("filme", getFilme());
        model.addAttribute("sali", getSali());
        return "rezervare";
    }

    @PostMapping("/rezervare")
    public String adaugaRezervare(@ModelAttribute Rezervare rezervare,
                                  Model model) {
        try {
            rezervareService.adaugaRezervare(rezervare);
            return "redirect:/confirmare?film=" + rezervare.getFilm()
                    + "&data=" + rezervare.getDataRezervare();
        } catch (IllegalArgumentException | IllegalStateException e) {
            try {
                String errorEncoded = java.net.URLEncoder.encode(
                        e.getMessage(), java.nio.charset.StandardCharsets.UTF_8);
                String filmEncoded = java.net.URLEncoder.encode(
                        rezervare.getFilm(), java.nio.charset.StandardCharsets.UTF_8);
                return "redirect:/seats?film=" + filmEncoded
                        + "&nrSala=" + rezervare.getNrSala()
                        + "&error=" + errorEncoded;
            } catch (Exception ex) {
                return "redirect:/seats?film=" + rezervare.getFilm()
                        + "&nrSala=" + rezervare.getNrSala() + "&error=Eroare";
            }
        }
    }

    @GetMapping("/confirmare")
    public String showConfirmare(
            @RequestParam String film,
            @RequestParam String data,
            Model model) {
        model.addAttribute("film", film);
        model.addAttribute("data", data);
        return "confirmare";
    }

    // ===================== AFISARE REZERVARI =====================
    @GetMapping("/afisareRezervari")
    public String showAfisareForm() {
        return "afisareRezervari";
    }

    @PostMapping("/afisareRezervari")
    public String afisareRezervari(@RequestParam String nume,
                                   Model model) {
        List<Rezervare> rezervari = rezervareService.getRezervariByNume(nume);
        model.addAttribute("rezervari", rezervari);
        model.addAttribute("nume", nume);
        return "afisareRezervari";
    }

    // ===================== VERIFICARE CAPACITATE =====================
    @GetMapping("/verificareCapacitate")
    public String showVerificareForm(Model model) {
        model.addAttribute("sali", getSali());
        return "verificareCapacitate";
    }

    @PostMapping("/verificareCapacitate")
    public String verificareCapacitate(@RequestParam int nrSala,
                                       Model model) {
        int locuriLibere = rezervareService.getLocuriLibere(nrSala);
        model.addAttribute("locuriLibere", locuriLibere);
        model.addAttribute("nrSala", nrSala);
        model.addAttribute("sali", getSali());
        return "verificareCapacitate";
    }

    // ===================== STERGERE =====================
    @PostMapping("/stergeRezervare")
    public String stergeRezervare(@RequestParam Long id) {
        rezervareService.stergeRezervare(id);
        return "redirect:/afisareRezervari";
    }

    // ===================== HELPER METHODS =====================
    private List<String> getFilme() {
        return movieRepository.findAll()
                .stream()
                .map(Movie::getTitlu)
                .collect(Collectors.toList());
    }

    private List<Integer> getSali() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
}