package com.example.CinemaJavaSpringBootApplication.controller;

import com.example.CinemaJavaSpringBootApplication.export.RezervareExporter;
import com.example.CinemaJavaSpringBootApplication.model.Movie;
import com.example.CinemaJavaSpringBootApplication.model.Rezervare;
import com.example.CinemaJavaSpringBootApplication.repository.MovieRepository;
import com.example.CinemaJavaSpringBootApplication.service.RezervareService;
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
public class RezervareController {

    private final RezervareService rezervareService;
    private final MovieRepository movieRepository;
    private final RezervareExporter rezervareExporter;

    public RezervareController(RezervareService rezervareService,
                               MovieRepository movieRepository, RezervareExporter rezervareExporter) {
        this.rezervareService = rezervareService;
        this.movieRepository = movieRepository;
        this.rezervareExporter = rezervareExporter;
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

    @GetMapping("/salveazaFisier")
    public void salveazaFisier(HttpServletResponse response) throws IOException {
        List<Rezervare> rezervari = rezervareService.getAllRezervari();

        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"rezervari.txt\"");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        java.io.PrintWriter writer = response.getWriter();

        writer.println("Salvare: " + java.time.LocalDateTime.now().format(dtf));
        writer.println();

        if (rezervari.isEmpty()) {
            writer.println("Nu există rezervări.");
        } else {
            for (Rezervare r : rezervari) {
                writer.println("Nume: " + r.getNume());
                writer.println("Data rezervare: " + r.getDataRezervare().format(dateFormat));
                writer.println("Film: " + r.getFilm());
                writer.println("Sala: " + r.getNrSala());
                writer.println("Locuri: " + r.getLocuri());
                writer.println("---");
            }
        }

        writer.flush();
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